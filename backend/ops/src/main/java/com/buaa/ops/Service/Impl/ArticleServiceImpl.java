package com.buaa.ops.Service.Impl;

import com.buaa.ops.Dao.ArticleBufferDao;
import com.buaa.ops.Dao.ArticleDao;
import com.buaa.ops.Dao.EditorDao;
import com.buaa.ops.Dao.ReviewDao;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Entity.Editor;
import com.buaa.ops.Entity.Review;
import com.buaa.ops.Service.ArticleService;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private ArticleBufferDao articleBufferDao;

    @Autowired
    private EditorDao editorDao;

    @Autowired
    private ReviewDao reviewDao;

    @Value("${file.root-path}")
    private String rootPath;

    @Override
    public ArrayList<Article> searchPublishedArticles(String searchType, String searchString) {
        ArrayList<Article> results;
        switch (searchType) {
            case "title":
                results = articleDao.selectPublishedByTitle(searchString);
                break;
            case "author":
                results = articleDao.selectPublishedByAuthor(searchString);
                break;
            case "keyword":
                results = articleDao.selectPublishedByKeyword(searchString);
                break;
            default:
                return null;
        }
        return results;
    }

    @Override
    public Article getArticleById(Integer id) {
        return articleDao.selectById(id);
    }

    @Override
    public ArticleBuffer getArticleBufferById(Integer id) {
        return articleBufferDao.selectById(id);
    }

    @Override
    public File getArticleFile(Integer id) throws ObjectNotFoundException {
        Article article = articleDao.selectById(id);
        if (article == null)
            throw new ObjectNotFoundException();
        String path = article.getFilePath();
        if (path == null)
            throw new ObjectNotFoundException();
        File file = new File(path);
        if (!file.exists())
            throw new ObjectNotFoundException();
        return file;
    }

    @Override
    public Integer saveArticleFile(Integer submitterId, Integer articleBufferId, Integer overwrite, MultipartFile file) throws IOException {
        String path;
        ArticleBuffer newArticle = new ArticleBuffer();
        if (articleBufferId == null) { // New upload
            // Save the file
            path = fileSave(file, null);
            // Insert new record
            newArticle.setSubmitterId(submitterId);
            newArticle.setOverwrite(overwrite);
            newArticle.setFilePath(path);
            articleBufferDao.insert(newArticle);
        }
        else { // Replacement
            ArticleBuffer articleBuffer = articleBufferDao.selectById(articleBufferId);
            // Replace the file
            path = fileSave(file, articleBuffer.getFilePath());
            // Update old record
            newArticle.setArticleBufferId(articleBufferId);
            newArticle.setFilePath(path);
            articleBufferDao.updateById(newArticle);
        }
        return newArticle.getArticleBufferId();
    }

    @Override
    public Integer submitArticle(ArticleBuffer articleBuffer) throws ObjectNotFoundException {
        Integer overwrite = articleBuffer.getOverwrite();
        Integer editorId;
        if (overwrite == null) { // New submission
            Editor targetEditor = editorDao.selectLeastBusy();
            if (targetEditor == null)
                throw new ObjectNotFoundException();
            editorId = targetEditor.getEditorId();
        }
        else {
            Article article = articleDao.selectById(overwrite);
            if (article == null)
                throw new ObjectNotFoundException();
            editorId = article.getEditorId();
        }
        articleBuffer.setEditorId(editorId);
        if (articleBufferDao.updateById(articleBuffer) == 0)
            throw new ObjectNotFoundException();
        return editorId;
    }

    @Override
    public Integer moveToArticle(Integer articleBufferId) throws IOException {
        ArticleBuffer articleBuffer = articleBufferDao.selectById(articleBufferId);
        File file = new File(articleBuffer.getFilePath());
        String path;
        Article article = new Article();
        Integer overwrite = articleBuffer.getOverwrite();
        if (overwrite == null) { // New submission
            path = fileMove(file, null);
            article.setTitle(articleBuffer.getTitle());
            article.setArticleAbstract(articleBuffer.getArticleAbstract());
            article.setKeywords(articleBuffer.getKeywords());
            article.setFirstAuthor(articleBuffer.getFirstAuthor());
            article.setOtherAuthors(articleBuffer.getOtherAuthors());
            article.setFilePath(path);
            article.setSubmitterId(articleBuffer.getSubmitterId());
            article.setEditorId(articleBuffer.getEditorId());
            articleDao.insert(article);
        }
        else { // Revised draft
            Article overwrittenArticle = articleDao.selectById(overwrite);
            path = fileMove(file, overwrittenArticle.getFilePath());
            article.setArticleId(overwrite);
            article.setTitle(articleBuffer.getTitle());
            article.setArticleAbstract(articleBuffer.getArticleAbstract());
            article.setKeywords(articleBuffer.getKeywords());
            article.setFilePath(path);
            articleDao.updateById(article);
        }
        return article.getArticleId();
    }

    @Override
    public void setArticleStatus(Integer articleId, String status) throws ObjectNotFoundException {
        Article article = new Article();
        article.setArticleId(articleId);
        article.setStatus(status);
        if (articleDao.updateById(article) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void removeArticle(Integer id) throws IOException, ObjectNotFoundException {
        Article article = articleDao.selectById(id);
        if (articleDao.deleteById(id) == 0)
            throw new ObjectNotFoundException();
        fileDelete(article.getFilePath());
    }

    @Override
    public void rejectArticle(Integer articleBufferId) throws IOException {
        ArticleBuffer articleBuffer = articleBufferDao.selectById(articleBufferId);
        articleBufferDao.deleteById(articleBufferId);
        fileDelete(articleBuffer.getFilePath());
    }

    @Override
    public void saveEditedFile(Integer articleId, MultipartFile file) throws IOException, ObjectNotFoundException {
        Article article = articleDao.selectById(articleId);
        if (article == null)
            throw new ObjectNotFoundException();
        String filePath = fileSave(file, article.getFilePath());
        Article updateArticle = new Article();
        updateArticle.setFilePath(filePath);
        if (articleDao.updateById(updateArticle) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public void publishArticle(Integer articleId, String identifier) throws ObjectNotFoundException, RepetitiveOperationException {
        Article article = articleDao.selectById(articleId);
        if (article == null)
            throw new ObjectNotFoundException();
        if (article.getStatus().equals("已出版"))
            throw new RepetitiveOperationException();
        Article updateArticle = new Article();
        updateArticle.setArticleId(articleId);
        updateArticle.setIdentifier(identifier);
        updateArticle.setStatus("已出版");
        updateArticle.setPublishingDate(new java.sql.Date(new java.util.Date().getTime()));
        if (articleDao.updateById(updateArticle) == 0)
            throw new ObjectNotFoundException();
    }

    @Override
    public Boolean cleanBuffer() {
        ArrayList<ArticleBuffer> garbage = articleBufferDao.selectNeverSubmitted();
        if (garbage.isEmpty())
            return true;
        int success = 0;
        int deleted;
        for (ArticleBuffer buffer : garbage) {
            deleted = articleBufferDao.deleteById(buffer.getArticleBufferId());
            try {
                fileDelete(buffer.getFilePath());
                success += deleted;
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        return success == garbage.size();
    }

    @Override
    public ArrayList<Article> getArticles() {
        return articleDao.selectAll();
    }

    @Override
    public void clearReviews(Integer articleId) throws ObjectNotFoundException {
        ArrayList<Review> reviews = reviewDao.selectByArticleId(articleId);
        if (reviews.isEmpty())
            throw new ObjectNotFoundException();
        int success = 0;
        for (Review r : reviews) {
            Review review = new Review();
            review.setArticleId(r.getArticleId());
            review.setArticleId(r.getArticleId());
            success += reviewDao.updateBySelf(review);
        }
        if (success != reviews.size())
            throw new ObjectNotFoundException();
    }

    @Override
    public void removeReviews(Integer articleId) throws ObjectNotFoundException {
        ArrayList<Review> reviews = reviewDao.selectByArticleId(articleId);
        if (reviewDao.deleteByArticleId(articleId) < reviews.size())
            throw new ObjectNotFoundException();
    }

    /**
     * Save a multipart file into the storage.<br/>
     * If path of the old file is given, this will delete the old file
     * and transfer the multipart file to the same directory;
     * otherwise, this will transfer the multipart file into
     * a new directory created in path <code>#{rootPath}/buffers</code>.
     * Name of the directory is based on the current timestamp.
     * @param file The uploaded multipart file
     * @param oldPath A path referring to the old file in storage; null if there is not
     * @return New path of the saved file
     */
    private String fileSave(MultipartFile file, String oldPath) throws IOException {
        String newPath;
        String fileName = file.getName();
        String path;
        if (oldPath == null) {
            path = String.format("%s/buffers/%d", rootPath, System.currentTimeMillis());
            File dir = new File(path);
            if (!dir.mkdirs())
                throw new IOException();
        }
        else {
            path = oldPath.substring(0, oldPath.lastIndexOf('/'));
            File dst = new File(oldPath);
            if (!dst.delete())
                throw new IOException();
        }
        newPath = String.format("%s/%s", path, fileName);
        file.transferTo(new File(newPath));
        return newPath;
    }

    /**
     * Move a file from the buffer folder to the formal article folder.<br/>
     * If the target path is given, this will delete the target file,
     * and move the source file to the same directory;
     * otherwise, this will move the source file into
     * a new directory created in path <code>#{rootPath}/articles</code>.
     * Name of the directory is based on the current timestamp.
     * @param src Source file to be moved
     * @param destPath A path referring to the target file in storage; null if there is not
     * @return New path of the saved file
     */
    private String fileMove(File src, String destPath) throws IOException {
        String newPath;
        String fileName = src.getName();
        String path;
        if (destPath == null) {
            path = String.format("%s/articles/%d", rootPath, System.currentTimeMillis());
            File dir = new File(path);
            if (!dir.mkdirs())
                throw new IOException();
        }
        else {
            path = destPath.substring(0, destPath.lastIndexOf('/'));
            File dst = new File(destPath);
            if (!dst.delete())
                throw new IOException();
        }
        newPath = String.format("%s/%s", path, fileName);
        File parent = src.getParentFile();
        if (!src.renameTo(new File(newPath)))
            throw new IOException();
        if (!parent.delete())
            throw new IOException();
        return newPath;
    }

    /**
     * Delete a file and its parent directory from the storage.
     * @param targetPath A path referring to the target file to be deleted
     */
    private void fileDelete(String targetPath) throws IOException {
        File target = new File(targetPath);
        File dir = target.getParentFile();
        if (!target.delete() || !dir.delete())
            throw new IOException();
    }

}
