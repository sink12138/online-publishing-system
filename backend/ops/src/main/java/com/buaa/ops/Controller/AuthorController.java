package com.buaa.ops.Controller;

import com.buaa.ops.Entity.Account;
import com.buaa.ops.Entity.Article;
import com.buaa.ops.Entity.ArticleBuffer;
import com.buaa.ops.Entity.Author;
import com.buaa.ops.Service.*;
import com.buaa.ops.Service.Emails.EmailFactory;
import com.buaa.ops.Service.Emails.ReminderEmail;
import com.buaa.ops.Service.Exc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private EditorService editorService;

    private final EmailFactory emailFactory = new EmailFactory();

    @PostMapping("/certify")
    public Map<String, Object> certify(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
/*
            String email;
            String password;
            Account account;
            try {
                HttpSession session = request.getSession();
                email = (String) session.getAttribute("email");
                password = (String) session.getAttribute("password");
                account = accountService.getAccount(email);
                if (!password.equals(account.getPassword()))
                    throw new Exception();
            }
            catch (Exception e) {
                throw new LoginVerificationException();
            }
*/
            Account account = accountService.getAccountBySession(request.getSession());
            String institution;
            String researchInterests;
            try {
                institution = (String) requestMap.get("institution");
                researchInterests = (String) requestMap.get("researchInterests");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            Integer accountId = account.getAccountId();
            if (authorService.getAuthorByAccountId(accountId) != null)
                throw new RepetitiveOperationException();
            Author author = new Author(accountId, institution, researchInterests);
            authorService.addAuthor(author);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException | RepetitiveOperationException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    @PostMapping("/cancel")
    public Map<String, Object> cancel() {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer accountId = account.getAccountId();
            Author author = authorService.getAuthorByAccountId(accountId);
            if (author == null)
                throw new IllegalAuthorityException();
            authorService.removeAuthor(author.getAuthorId());
            map.put("success", true);
        }
        catch (LoginVerificationException | IllegalAuthorityException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    @PostMapping("/new/upload")
    public Map<String, Object> newUpload(@RequestParam(value = "file", required = false) Object file,
                                         @RequestParam(value = "bufferId", required = false) Object bufferId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            MultipartFile multipartFile;
            Integer articleBufferId;
            // Begin parameter format checks
            try {
                multipartFile = (MultipartFile) file;
                articleBufferId = (Integer) bufferId;
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (multipartFile == null)
                throw new ParameterFormatException();
            if (articleBufferId == null || articleBufferId < 0)
                throw new ParameterFormatException();
            // End parameter format checks
            ArticleBuffer articleBuffer = articleService.getArticleBufferById(articleBufferId);
            if (articleBufferId > 0 && articleBuffer == null) // Article not found
                throw new ObjectNotFoundException();
            // Begin authority checks
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (articleBufferId > 0 && !authorId.equals(articleBuffer.getSubmitterId()))
                // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            // End authority checks
            if (articleBufferId == 0)
                articleBufferId = null;
            Integer id = articleService.saveArticleFile(author.getAuthorId(), articleBufferId, null, multipartFile);
            map.put("success", true);
            map.put("articleBufferId", id);
        }
        catch (LoginVerificationException | IllegalAuthorityException | ParameterFormatException | ObjectNotFoundException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.clear();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    @PostMapping("/new/submit")
    public Map<String, Object> newSubmit(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleBufferId;
            String title;
            String articleAbstract;
            String[] keywords;
            String firstAuthor;
            String[] otherAuthors;
            // Begin parameter format checks
            try {
                articleBufferId = (Integer) requestMap.get("articleBufferId");
                title = (String) requestMap.get("title");
                articleAbstract = (String) requestMap.get("abstract");
                keywords = (String[]) requestMap.get("keywords");
                firstAuthor = (String) requestMap.get("firstAuthor");
                otherAuthors = (String[]) requestMap.get("otherAuthors");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleBufferId == null || articleBufferId <= 0)
                throw new ParameterFormatException();
            if (title == null || title.isEmpty())
                throw new ParameterFormatException();
            if (articleAbstract == null || articleAbstract.isEmpty())
                throw new ParameterFormatException();
            if (keywords == null || keywords.length == 0)
                throw new ParameterFormatException();
            if (firstAuthor == null || firstAuthor.isEmpty())
                throw new ParameterFormatException();
            // End parameter format checks
            ArticleBuffer articleBuffer = articleService.getArticleBufferById(articleBufferId);
            if (articleBuffer == null) // Article not found
                throw new ObjectNotFoundException();
            // Begin authority checks
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (!authorId.equals(articleBuffer.getSubmitterId()))
                // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            // End authority checks
            // Create a new article
            ArticleBuffer newArticle = new ArticleBuffer(
                    articleBufferId,
                    title,
                    buildArrayToString(keywords),
                    articleAbstract,
                    firstAuthor,
                    buildArrayToString(otherAuthors)
            );
            // Submit the article into database
            Integer editorId = articleService.submitArticle(newArticle);
            // Create a reminder email
            Integer editorAccountId = editorService.getEditorById(editorId).getAccountId();
            Account editor = accountService.getAccountByAccountId(editorAccountId);
            ReminderEmail reminderEmail = emailFactory.makeNewArticleEmail(
                    editor.getRealName(),
                    account.getRealName(),
                    title
            );
            // Send the email to the editor
            emailService.sendReminderEmail(editor.getEmail(), reminderEmail);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException | IllegalAuthorityException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    @PostMapping("/revise/upload")
    public Map<String, Object> revisedUpload(@RequestParam(value = "file", required = false) Object file,
                                             @RequestParam(value = "bufferId", required = false) Object bufferId,
                                             @RequestParam(value = "overwrite", required = false) Object overwrite) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            MultipartFile multipartFile;
            Integer articleBufferId;
            Integer overwriteId;
            // Begin parameter format checks
            try {
                multipartFile = (MultipartFile) file;
                articleBufferId = (Integer) bufferId;
                overwriteId = (Integer) overwrite;
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (multipartFile == null)
                throw new ParameterFormatException();
            if (articleBufferId == null || articleBufferId < 0)
                throw new ParameterFormatException();
            if (overwriteId == null || overwriteId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            // Begin existence checks
            ArticleBuffer articleBuffer = articleService.getArticleBufferById(articleBufferId);
            if (articleBufferId > 0 && articleBuffer == null) // ArticleBuffer not found
                throw new ObjectNotFoundException();
            Article overwrittenArticle = articleService.getArticleById(overwriteId);
            if (overwrittenArticle == null) // Overwritten article not found
                throw new ObjectNotFoundException();
            // End existence checks
            // Begin authority checks
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (articleBufferId > 0 && !authorId.equals(articleBuffer.getSubmitterId()))
                // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            if (!authorId.equals(overwrittenArticle.getArticleId()))
                // Poster is not submitter of the overwritten article
                throw new IllegalAuthorityException();
            String status = overwrittenArticle.getStatus();
            if (!status.equals("审核通过") && !status.equals("审核未通过"))
                // The overwritten article is not currently revisable for its author
                throw new IllegalAuthorityException();
            // End authority checks
            if (articleBufferId == 0)
                articleBufferId = null;
            Integer id = articleService.saveArticleFile(author.getAuthorId(), articleBufferId, overwriteId, multipartFile);
            map.put("success", true);
            map.put("articleBufferId", id);
        }
        catch (LoginVerificationException | IllegalAuthorityException | ParameterFormatException | ObjectNotFoundException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.clear();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    @PostMapping("/revise/submit")
    public Map<String, Object> revisedSubmit(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleBufferId;
            String title;
            String articleAbstract;
            String[] keywords;
            // Begin parameter format checks
            try {
                articleBufferId = (Integer) requestMap.get("articleBufferId");
                title = (String) requestMap.get("title");
                articleAbstract = (String) requestMap.get("abstract");
                keywords = (String[]) requestMap.get("keywords");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleBufferId == null || articleBufferId <= 0)
                throw new ParameterFormatException();
            if (title == null || title.isEmpty())
                throw new ParameterFormatException();
            if (articleAbstract == null || articleAbstract.isEmpty())
                throw new ParameterFormatException();
            if (keywords == null || keywords.length == 0)
                throw new ParameterFormatException();
            // End parameter format checks
            // Begin existence checks
            ArticleBuffer articleBuffer = articleService.getArticleBufferById(articleBufferId);
            if (articleBuffer == null) // Article not found
                throw new ObjectNotFoundException();
            Integer overwriteId = articleBuffer.getOverwrite();
            if (overwriteId == null || overwriteId == 0) // Not a valid revision
                throw new ObjectNotFoundException();
            // End existence checks
            // Begin authority checks
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (!authorId.equals(articleBuffer.getSubmitterId()))
                // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            Article overwrittenArticle = articleService.getArticleById(overwriteId);
            String status = overwrittenArticle.getStatus();
            if (!status.equals("审核通过") && !status.equals("审核未通过"))
                // The overwritten article is not currently revisable for its author
                throw new IllegalAuthorityException();
            // End authority checks
            // Modify the article
            articleBuffer.setTitle(title);
            articleBuffer.setArticleAbstract(articleAbstract);
            articleBuffer.setKeywords(buildArrayToString(keywords));
            // Submit the article into database
            Integer editorId = articleService.submitArticle(articleBuffer);
            // Create a reminder email
            Integer editorAccountId = editorService.getEditorById(editorId).getAccountId();
            Account editor = accountService.getAccountByAccountId(editorAccountId);
            ReminderEmail reminderEmail = emailFactory.makeRevisedDraftEmail(
                    editor.getRealName(),
                    account.getRealName(),
                    title
            );
            // Send the email to the editor
            emailService.sendReminderEmail(editor.getEmail(), reminderEmail);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException | IllegalAuthorityException exc) {
            map.put("success", false);
            map.put("message", exc.toString());
        }
        catch (Exception exception) {
            exception.printStackTrace();
            map.put("success", false);
            map.put("message", "操作失败");
        }
        return map;
    }

    private String buildArrayToString(String[] array) {
        if (array == null || array.length == 0)
            return null;
        StringBuilder sb = new StringBuilder();
        for (String element : array) {
            sb.append(element);
            sb.append(";");
        }
        return sb.toString();
    }
}
