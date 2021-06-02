package com.buaa.ops.Controller;

import com.buaa.ops.Controller.Util.FormatHandler;
import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.*;
import com.buaa.ops.Service.Emails.EmailFactory;
import com.buaa.ops.Service.Emails.ReminderEmail;
import com.buaa.ops.Service.Exc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
            String realName = account.getRealName();
            if (realName == null || realName.isEmpty())
                // Cannot certify without real name
                throw new IllegalAuthorityException();
            Integer accountId = account.getAccountId();
            if (authorService.getAuthorByAccountId(accountId) != null)
                throw new RepetitiveOperationException();
            Author author = new Author(accountId, institution, researchInterests);
            authorService.addAuthor(author);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException | IllegalAuthorityException | RepetitiveOperationException exc) {
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
        catch (LoginVerificationException | ObjectNotFoundException | IllegalAuthorityException exc) {
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
    public Map<String, Object> newUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                         @RequestParam(value = "articleBufferId", required = false) Object bufferId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleBufferId;
            // Begin parameter format checks
            try {
                articleBufferId = Integer.parseInt(bufferId.toString());
            }
            catch (NumberFormatException | NullPointerException e) {
                throw new ParameterFormatException();
            }
            if (multipartFile == null)
                throw new ParameterFormatException();
            if (articleBufferId < 0)
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
            ArrayList<String> keywords;
            String firstAuthor;
            ArrayList<String> otherAuthors;
            FormatHandler handler = new FormatHandler();
            // Begin parameter format checks
            try {
                articleBufferId = (Integer) requestMap.get("articleBufferId");
                title = (String) requestMap.get("title");
                articleAbstract = (String) requestMap.get("abstract");
                keywords = handler.castToStringList(requestMap.get("keywords"));
                firstAuthor = (String) requestMap.get("firstAuthor");
                otherAuthors = handler.castToStringList(requestMap.get("otherAuthors"));
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
            if (keywords == null || keywords.isEmpty())
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
                    handler.buildArrayListToString(keywords),
                    articleAbstract,
                    firstAuthor,
                    handler.buildArrayListToString(otherAuthors)
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
    public Map<String, Object> revisedUpload(@RequestParam(value = "file", required = false) MultipartFile multipartFile,
                                             @RequestParam(value = "articleBufferId", required = false) Object bufferId,
                                             @RequestParam(value = "overwrite", required = false) Object overwrite) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleBufferId;
            int overwriteId;
            // Begin parameter format checks
            try {
                articleBufferId = Integer.parseInt(bufferId.toString());
                overwriteId = Integer.parseInt(overwrite.toString());
            }
            catch (ClassCastException | NullPointerException e) {
                throw new ParameterFormatException();
            }
            if (multipartFile == null)
                throw new ParameterFormatException();
            if (articleBufferId < 0)
                throw new ParameterFormatException();
            if (overwriteId <= 0)
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
            ArrayList<String> keywords;
            FormatHandler handler = new FormatHandler();
            // Begin parameter format checks
            try {
                articleBufferId = (Integer) requestMap.get("articleBufferId");
                title = (String) requestMap.get("title");
                articleAbstract = (String) requestMap.get("abstract");
                keywords = handler.castToStringList(requestMap.get("keywords"));
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
            if (keywords == null || keywords.isEmpty())
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
            articleBuffer.setKeywords(handler.buildArrayListToString(keywords));
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

    @PostMapping("/confirm/draft")
    public Map<String, Object> confirmDraft(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleId;
            // Begin parameter format checks
            try {
                articleId = (Integer) requestMap.get("articleId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleId == null || articleId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            Article article = articleService.getArticleById(articleId);
            if (article == null) // Article not found
                throw new ObjectNotFoundException();
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            // Begin authority checks
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (!article.getSubmitterId().equals(authorId)) // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            String status = article.getStatus();
            if (!status.equals("审核通过") && !status.equals("编辑中") && !status.equals("已出版"))
                // The article has not been reviewed or has not passed the review
                throw new IllegalAuthorityException();
            // End authority checks
            // Begin repetition checks
            if (status.equals("编辑中") || status.equals("已出版"))
                // The article has already been confirmed final draft
                throw new RepetitiveOperationException();
            // End repetition checks
            // Update the status of the article
            articleService.setArticleStatus(articleId, "编辑中");
            // Remove all reviews of the article
            articleService.removeReviews(articleId);
            // Create a reminder email
            Integer editorId = article.getEditorId();
            Integer editorAccountId = editorService.getEditorById(editorId).getAccountId();
            Account editor = accountService.getAccountByAccountId(editorAccountId);
            ReminderEmail reminderEmail = emailFactory.makeConfirmDraftEmail(
                    editor.getRealName(),
                    account.getRealName(),
                    article.getTitle()
            );
            // Send the email to the editor
            emailService.sendReminderEmail(editor.getEmail(), reminderEmail);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException
                | IllegalAuthorityException | RepetitiveOperationException exc) {
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

    @PostMapping("/abort")
    public Map<String, Object> abort(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleId;
            // Begin parameter format checks
            try {
                articleId = (Integer) requestMap.get("articleId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleId == null || articleId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            Article article = articleService.getArticleById(articleId);
            if (article == null) // Article not found
                throw new ObjectNotFoundException();
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            // Begin authority checks
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (!article.getSubmitterId().equals(authorId)) // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            String status = article.getStatus();
            if (!status.equals("审核通过") && !status.equals("审核未通过"))
                // The process is not currently able to abort
                throw new IllegalAuthorityException();
            // End authority checks
            // Remove the article from the database
            articleService.removeArticle(articleId);
            // Create a reminder email
            Integer editorId = article.getEditorId();
            Integer editorAccountId = editorService.getEditorById(editorId).getAccountId();
            Account editor = accountService.getAccountByAccountId(editorAccountId);
            ReminderEmail reminderEmail = emailFactory.makeAbortPublishingEmail(
                    editor.getRealName(),
                    account.getRealName(),
                    article.getTitle()
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

    @PostMapping("/claim")
    public Map<String, Object> claim(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleId;
            // Begin parameter format checks
            try {
                articleId = (Integer) requestMap.get("articleId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleId == null || articleId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            Article article = articleService.getArticleById(articleId);
            if (article == null) // Article not found
                throw new ObjectNotFoundException();
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            // Begin authority checks
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            String status = article.getStatus();
            if (!status.equals("已出版")) // Unpublished articles cannot be claimed
                throw new IllegalAuthorityException();
            String firstAuthor = article.getFirstAuthor();
            String[] otherAuthors = article.getOtherAuthors().split(";");
            String realName = account.getRealName();
            if (!firstAuthor.equals(realName) &&
                    !Arrays.asList(otherAuthors).contains(realName))
                // Name of poster does not match any authors of the article
                throw new IllegalAuthorityException();
            // End authority checks
            // Submit the claim into the database
            authorService.claimArticle(author.getAuthorId(), articleId);
            // Create a reminder email
            Integer editorId = article.getEditorId();
            Integer editorAccountId = editorService.getEditorById(editorId).getAccountId();
            Account editor = accountService.getAccountByAccountId(editorAccountId);
            ReminderEmail reminderEmail = emailFactory.makeClaimArticleEmail(
                    editor.getRealName(),
                    account.getRealName(),
                    article.getTitle()
            );
            // Send the email to the editor
            emailService.sendReminderEmail(editor.getEmail(), reminderEmail);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException | RepetitiveOperationException exc) {
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

    @GetMapping("/articles")
    public ArrayList<Map<String, Object>> articles() {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> statusMap = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) // Getter is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            ArrayList<Article> results = authorService.getMyArticles(authorId);
            statusMap.put("success", true);
            if (results == null || results.isEmpty()) {
                statusMap.put("results", 0);
                maps.add(statusMap);
            }
            else {
                statusMap.put("results", results.size());
                maps.add(statusMap);
                for (Article article : results) {
                    Map<String, Object> map = new HashMap<>();
                    Integer articleId = article.getArticleId();
                    map.put("articleId", articleId);
                    String title = article.getTitle();
                    map.put("title", title);
                    String[] keywords = article.getKeywords().split(";");
                    map.put("keywords", keywords);
                    String firstAuthor = article.getFirstAuthor();
                    map.put("firstAuthor", firstAuthor);
                    String otherAuthors = article.getOtherAuthors();
                    if (otherAuthors != null)
                        map.put("otherAuthors", otherAuthors.split(";"));
                    String status = article.getStatus();
                    map.put("status", status);
                    Boolean authorized = article.getSubmitterId().equals(authorId);
                    map.put("authorized", authorized);
                    maps.add(map);
                }
            }
        }
        catch (LoginVerificationException | ObjectNotFoundException | IllegalAuthorityException exc) {
            statusMap.put("success", false);
            statusMap.put("message", exc.toString());
            maps.add(statusMap);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            statusMap.clear();
            statusMap.put("success", false);
            statusMap.put("message", "操作失败");
            maps.clear();
            maps.add(statusMap);
        }
        return maps;
    }

    @PostMapping("/withdraw")
    public Map<String, Object> withdraw(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Integer articleId;
            // Begin parameter format checks
            try {
                articleId = (Integer) requestMap.get("articleId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleId == null || articleId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            Article article = articleService.getArticleById(articleId);
            if (article == null) // Article not found
                throw new ObjectNotFoundException();
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            // Begin authority checks
            if (author == null) // Poster is not an author
                throw new IllegalAuthorityException();
            Integer authorId = author.getAuthorId();
            if (!article.getSubmitterId().equals(authorId)) // Poster is not submitter of the article
                throw new IllegalAuthorityException();
            String status = article.getStatus();
            if (!status.equals("已出版") && !status.equals("待撤稿")) // Unpublished article cannot be withdrawn
                throw new IllegalAuthorityException();
            // End authority checks
            if (status.equals("待撤稿"))
                throw new RepetitiveOperationException();
            // Update status of the article
            articleService.setArticleStatus(articleId, "待撤稿");
            // Create a reminder email
            Integer editorId = article.getEditorId();
            Integer editorAccountId = editorService.getEditorById(editorId).getAccountId();
            Account editor = accountService.getAccountByAccountId(editorAccountId);
            ReminderEmail reminderEmail = emailFactory.makeWithdrawArticleEmail(
                    editor.getRealName(),
                    account.getRealName(),
                    article.getTitle()
            );
            // Send the email to the editor
            emailService.sendReminderEmail(editor.getEmail(), reminderEmail);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException | RepetitiveOperationException exc) {
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

    @GetMapping("/editors")
    public ArrayList<Map<String, Object>> editors() {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> statusMap = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(request.getSession());
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) // Getter is not an author
                throw new IllegalAuthorityException();
            ArrayList<Editor> results = editorService.getEditors();
            statusMap.put("success", true);
            if (results == null || results.isEmpty()) {
                statusMap.put("results", 0);
                maps.add(statusMap);
            }
            else {
                statusMap.put("results", results.size());
                maps.add(statusMap);
                for (Editor editor : results) {
                    Map<String, Object> map = new HashMap<>();
                    Integer editorAccountId = editor.getAccountId();
                    Account editorAccount = accountService.getAccountByAccountId(editorAccountId);
                    map.put("name", editorAccount.getRealName());
                    map.put("email", editorAccount.getEmail());
                    maps.add(map);
                }
            }
        }
        catch (LoginVerificationException | IllegalAuthorityException exc) {
            statusMap.put("success", false);
            statusMap.put("message", exc.toString());
            maps.add(statusMap);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            statusMap.clear();
            statusMap.put("success", false);
            statusMap.put("message", "操作失败");
            maps.clear();
            maps.add(statusMap);
        }
        return maps;
    }

    @GetMapping("/reviews")
    public ArrayList<Map<String, Object>> reviews(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        HttpSession session = request.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            if (author == null) {
                throw new IllegalAuthorityException();
            }
            Integer articleId;
            try {
                articleId = (Integer) requestMap.get("articleId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null || !article.getStatus().equals("审核通过") || !article.getStatus().equals("审核未通过")) {
                throw new ObjectNotFoundException();
            }
            map.put("success", true);
            ArrayList<Review> reviews = authorService.getReviews(articleId);
            map.put("results", reviews.size());
            arrayList.add(map);
            for (Review review : reviews) {
                Map<String, Object> infos = new HashMap<>();
                infos.put("comments", review.getComments());
                infos.put("pass", review.getPass());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                infos.put("date", simpleDateFormat.format(review.getReviewingDate()));
                arrayList.add(infos);
            }
        } catch (LoginVerificationException | IllegalAuthorityException | ParameterFormatException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "操作失败");
            arrayList.add(map);
        }
        return arrayList;
    }

}
