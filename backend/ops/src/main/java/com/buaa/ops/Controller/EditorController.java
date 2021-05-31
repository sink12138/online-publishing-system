package com.buaa.ops.Controller;

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
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/editor")
public class EditorController {

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/cancel/author")
    public Map<String, Object> cancelAuthor(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer authorId;
            try {
                authorId = (Integer) request.get("authorId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            authorService.removeAuthor(authorId);
            Author author = authorService.getAuthorByAuthorId(authorId);
            Account authorAccount = accountService.getAccountByAccountId(author.getAccountId());
            map.put("success", true);
            // send email
            String authorName = authorAccount.getRealName();
            String authorEmail = authorAccount.getEmail();
            EmailFactory emailFactory = new EmailFactory();
            ReminderEmail reminderEmail = emailFactory.makeAuthorCancelledEmail(authorName, account.getRealName());
            emailService.sendReminderEmail(authorEmail, reminderEmail);
        } catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @Autowired
    ReviewerService reviewerService;

    @PostMapping("/certify/reviewer")
    public Map<String, Object> certifyReviewer(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer accountId;
            String organization;
            try {
                accountId = (Integer) request.get("accountId");
                organization = (String) request.get("organization");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            if (accountService.getAccountByAccountId(accountId) == null) {
                throw new ObjectNotFoundException();
            }
            Reviewer reviewer = new Reviewer(accountId, organization);
            reviewerService.addReviewer(reviewer);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/cancel/reviewer")
    public Map<String, Object> cancelReviewer(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer reviewerId;
            try {
                reviewerId = (Integer) request.get("reviewerId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            reviewerService.removeReviewer(reviewerId);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/authors")
    public ArrayList<Map<String, Object>> authors() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            ArrayList<Author>authorArrayList = authorService.getAuthors();
            map.put("success", true);
            map.put("results", authorArrayList.size());
            arrayList.add(map);
            for (Author author : authorArrayList) {
                Map<String, Object> authorInfos = new HashMap<>();
                Account authorAccount = accountService.getAccountByAccountId(author.getAuthorId());
                authorInfos.put("authorId", author.getAuthorId());
                authorInfos.put("realName", authorAccount.getRealName());
                authorInfos.put("institution", author.getInstitution());
                authorInfos.put("articleCount", authorService.getMyArticles(author.getAuthorId()).size());
                arrayList.add(authorInfos);
            }
        } catch (LoginVerificationException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
            arrayList.add(map);
        }
        return arrayList;
    }

    @GetMapping("/reviewers")
    public ArrayList<Map<String, Object>> reviewers() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            ArrayList<Reviewer> reviewerArrayList = reviewerService.getReviewers();
            map.put("success", true);
            map.put("results", reviewerArrayList.size());
            arrayList.add(map);
            for (Reviewer reviewer : reviewerArrayList) {
                Map<String, Object> reviewerInfos = new HashMap<>();
                Account reviewerAccount = accountService.getAccountByAccountId(reviewer.getAccountId());
                reviewerInfos.put("reviewerId", reviewer.getReviewerId());
                reviewerInfos.put("realName", reviewerAccount.getRealName());
                reviewerInfos.put("organization", reviewer.getOrganization());
                arrayList.add(reviewerInfos);
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
            arrayList.add(map);
        }
        return arrayList;
    }

    @GetMapping("/articles")
    public ArrayList<Map<String, Object>> articles() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            ArrayList<Article> articleArrayList = editorService.getEditingArticles(editor.getEditorId());
            map.put("success", true);
            map.put("results", articleArrayList.size());
            arrayList.add(map);
            for (Article article : articleArrayList) {
                Map<String, Object> articleInfos = new HashMap<>();
                articleInfos.put("articleId", article.getArticleId());
                articleInfos.put("title", article.getTitle());
                articleInfos.put("keywords", article.getKeywords().split(";"));
                articleInfos.put("firstAuthor", article.getFirstAuthor());
                if (article.getOtherAuthors() != null) {
                    articleInfos.put("otherAuthors", article.getOtherAuthors().split(";"));
                }
                articleInfos.put("status", article.getStatus());
                arrayList.add(articleInfos);
            }
        } catch (LoginVerificationException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
            arrayList.add(map);
        }
        return arrayList;
    }

    @PostMapping("/accept")
    public Map<String, Object> accept(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(httpServletRequest.getSession());
            Integer articleBufferId;
            // Begin parameter format checks
            try {
                articleBufferId = (Integer) requestMap.get("articleBufferId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleBufferId == null || articleBufferId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            ArticleBuffer articleBuffer = articleService.getArticleBufferById(articleBufferId);
            if (articleBuffer == null) // Article not found
                throw new ObjectNotFoundException();
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            // Begin authority checks
            if (editor == null) // Poster is not an editor
                throw new IllegalAuthorityException();
            Integer editorId = editor.getEditorId();
            Integer articleEditorId = articleBuffer.getEditorId();
            if (articleEditorId == null) // Article has not been submitted
                throw new IllegalAuthorityException();
            if (!articleEditorId.equals(editorId))
                // Poster is not editor of the article
                throw new IllegalAuthorityException();
            // End authority checks
            // Move the article from buffer to formal table
            Integer articleId = articleService.moveToArticle(articleBufferId);
            Article article = articleService.getArticleById(articleId);
            File file = new File(article.getFilePath());
            Integer overwrite = articleBuffer.getOverwrite();
            if (overwrite == null) { // New submission
                // Set article status
                articleService.setArticleStatus(articleId, "待审核");
            }
            else { // Revised draft
                // Set article status
                articleService.setArticleStatus(articleId, "审核中");
                // Get all reviewers of this article
                ArrayList<Reviewer> reviewers = reviewerService.getReviewersByArticleId(articleId);
                // Clear all reviews of this article
                articleService.clearReviews(articleId);
                // Make and send emails to reviewers of the article
                EmailFactory emailFactory = new EmailFactory();
                for (Reviewer reviewer : reviewers) {
                    Account reviewerAccount = accountService.getAccountByAccountId(reviewer.getAccountId());
                    // Make
                    ReminderEmail reminderEmail = emailFactory.makeReviewAssignmentEmail(
                            reviewerAccount.getRealName(),
                            account.getRealName(),
                            article.getTitle()
                    );
                    // Send
                    emailService.sendAttachmentEmail(reviewerAccount.getEmail(), file, reminderEmail);
                }
            }
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException exc) {
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

    @PostMapping("/reject")
    public Map<String, Object> reject(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(httpServletRequest.getSession());
            // Begin parameter format checks
            Integer articleBufferId;
            try {
                articleBufferId = (Integer) requestMap.get("articleBufferId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleBufferId == null || articleBufferId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            ArticleBuffer articleBuffer = articleService.getArticleBufferById(articleBufferId);
            if (articleBuffer == null) // Article not found
                throw new ObjectNotFoundException();
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            // Begin authority checks
            if (editor == null) // Poster is not an editor
                throw new IllegalAuthorityException();
            Integer editorId = editor.getEditorId();
            Integer articleEditorId = articleBuffer.getEditorId();
            if (articleEditorId == null) // Article has not been submitted
                throw new IllegalAuthorityException();
            if (!articleEditorId.equals(editorId))
                // Poster is not editor of the article
                throw new IllegalAuthorityException();
            // End authority checks
            // Remove the article from buffer
            articleService.rejectArticle(articleBufferId);
            // Get author of the article
            Author author = authorService.getAuthorByAuthorId(articleBuffer.getSubmitterId());
            Account authorAccount = accountService.getAccountByAccountId(author.getAccountId());
            // Make and send an email to the author
            EmailFactory emailFactory = new EmailFactory();
            Integer overwrite = articleBuffer.getOverwrite();
            if (overwrite == null) { // New submission
                ReminderEmail reminderEmail = emailFactory.makeRejectArticleEmail(
                        authorAccount.getRealName(),
                        account.getRealName(),
                        articleBuffer.getTitle()
                );
                emailService.sendReminderEmail(authorAccount.getEmail(), reminderEmail);
            }
            else { // Revised draft
                Article article = articleService.getArticleById(overwrite);
                ReminderEmail reminderEmail = emailFactory.makeRejectDraftEmail(
                        authorAccount.getRealName(),
                        account.getRealName(),
                        article.getTitle()
                );
                emailService.sendReminderEmail(authorAccount.getEmail(), reminderEmail);
            }
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException exc) {
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

    @Autowired
    ArticleService articleService;

    @PostMapping("/assign")
    public Map<String, Object> assign(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer articleId;
            ArrayList<Integer> reviewerIds;
            try {
                articleId = (Integer) request.get("articleId");
                Integer[] ids = (Integer[]) request.get("reviewerId");
                reviewerIds = new ArrayList<>(Arrays.asList(ids));
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null) {
                throw new ObjectNotFoundException();
            }
            if (!article.getEditorId().equals(editor.getEditorId())) {
                throw new IllegalAuthorityException();
            }
            for (Integer reviewerId : reviewerIds) {
                if (reviewerService.getReviewerById(reviewerId) == null) {
                    throw new ObjectNotFoundException();
                }

            }
            reviewerService.assignReviewers(articleId, reviewerIds);
            map.put("success", true);

            // send email
            EmailFactory emailFactory = new EmailFactory();
            String title = article.getTitle();
            String editorName = account.getRealName();
            for (Integer reviewerId : reviewerIds) {
                Reviewer reviewer = reviewerService.getReviewerById(reviewerId);
                Account reviewerAccount = accountService.getAccountByAccountId(reviewer.getAccountId());
                ReminderEmail reminderEmail = emailFactory.makeReviewAssignmentEmail(reviewerAccount.getRealName(), editorName, title);
                emailService.sendReminderEmail(reviewerAccount.getEmail(), reminderEmail);
            }
        } catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException | RepetitiveOperationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/reviews")
    public ArrayList<Map<String, Object>> reviews(@RequestBody Map<String, Object> requestMap) {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer articleId;
            try {
                articleId = (Integer) requestMap.get("articleId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null || !article.getStatus().equals("审核通过") || !article.getStatus().equals("审核未通过") || !article.getStatus().equals("审核中")) {
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

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam(value = "file", required = false) Object objectFile,
                                      @RequestParam(value = "articleId", required = false) Object objectId) {
        Map<String, Object> map = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(httpServletRequest.getSession());
            MultipartFile file;
            Integer articleId;
            // Begin parameter format checks
            try {
                file = (MultipartFile) objectFile;
                articleId = (Integer) objectId;
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (file == null)
                throw new ParameterFormatException();
            if (articleId == null || articleId <= 0)
                throw new ParameterFormatException();
            // End parameter format checks
            Article article = articleService.getArticleById(articleId);
            if (article == null) // Article not found
                throw new ObjectNotFoundException();
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            // Begin authority checks
            if (editor == null) // Poster is not an editor
                throw new IllegalAuthorityException();
            Integer editorId = editor.getEditorId();
            if (!article.getEditorId().equals(editorId))
                // Poster is not editor of the article
                throw new IllegalAuthorityException();
            if (!article.getStatus().equals("编辑中"))
                // Not the editor's turn to upload
                throw new IllegalAuthorityException();
            // End authority checks
            // Save the uploaded file into the storage
            articleService.saveEditedFile(articleId, file);
            map.put("success", true);
        }
        catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException exc) {
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

    @PostMapping("/publish")
    public Map<String, Object> publish(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer articleId;
            String identifier;
            try {
                articleId = (Integer) request.get("articleId");
                identifier = (String) request.get("identifier");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null || !article.getStatus().equals("编辑中")) {
                throw new ObjectNotFoundException();
            }
            if (!article.getEditorId().equals(editor.getEditorId())) {
                throw new IllegalAuthorityException();
            }
            articleService.publishArticle(articleId, identifier);
            map.put("success", true);
            // send email
            Author author = authorService.getAuthorByAuthorId(article.getSubmitterId());
            Account authorAccount = accountService.getAccountByAccountId(author.getAccountId());
            EmailFactory emailFactory = new EmailFactory();
            ReminderEmail reminderEmail = emailFactory.makeArticlePublishedEmail(authorAccount.getRealName(), article.getTitle());
            emailService.sendReminderEmail(authorAccount.getEmail(), reminderEmail);
        } catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException | RepetitiveOperationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/claims")
    public ArrayList<Map<String, Object>> claims() {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> statusMap = new HashMap<>();
        try {
            Account account = accountService.getAccountBySession(httpServletRequest.getSession());
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) // Getter is not an editor
                throw new IllegalAuthorityException();
            Integer editorId = editor.getEditorId();
            ArrayList<Write> results = editorService.getClaims(editorId);
            statusMap.put("success", true);
            if (results == null || results.isEmpty()) {
                statusMap.put("results", 0);
                maps.add(statusMap);
            }
            else {
                statusMap.put("results", results.size());
                maps.add(statusMap);
                for (Write write : results) {
                    Integer articleId = write.getArticleId();
                    Article article = articleService.getArticleById(articleId);
                    String title = article.getTitle();
                    Integer authorId = write.getAuthorId();
                    Author author = authorService.getAuthorByAuthorId(authorId);
                    Account authorAccount = accountService.getAccountByAccountId(author.getAccountId());
                    String realName = authorAccount.getRealName();
                    String email = authorAccount.getEmail();
                    Boolean confirmed = write.getConfirmed();
                    Map<String, Object> map = new HashMap<>();
                    map.put("articleId", articleId);
                    map.put("title", title);
                    map.put("authorId", authorId);
                    map.put("realName", realName);
                    map.put("email", email);
                    map.put("confirmed", confirmed);
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

    @PostMapping("/confirm/claim")
    public Map<String, Object> confirmClaim(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer authorId;
            Integer articleId;
            Boolean confirm;
            try {
                authorId = (Integer) request.get("authorId");
                articleId = (Integer) request.get("articleId");
                confirm = (Boolean) request.get("confirm");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Author author = authorService.getAuthorByAuthorId(authorId);
            Article article = articleService.getArticleById(articleId);
            if (article == null || !article.getStatus().equals("已出版") || author == null) {
                throw new ObjectNotFoundException();
            }
            if (!article.getEditorId().equals(editor.getEditorId())) {
                throw new IllegalAuthorityException();
            }
            map.put("success", true);
            // send email
            EmailFactory emailFactory = new EmailFactory();
            Account authorAccount  = accountService.getAccountByAccountId(author.getAccountId());
            String title = article.getTitle();
            String editorName = account.getRealName();
            String authorName = authorAccount.getRealName();
            String email = authorAccount.getEmail();
            ReminderEmail reminderEmail;
            if (confirm) {
                editorService.confirmClaim(articleId, authorId);
                reminderEmail = emailFactory.makeAcceptClaimEmail(authorName, editorName, title);
            } else {
                reminderEmail = emailFactory.makeRejectClaimEmail(authorName, editorName, title);
                editorService.removeClaim(articleId, authorId);
            }
            emailService.sendReminderEmail(email, reminderEmail);
        } catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException | RepetitiveOperationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/confirm/withdraw")
    public Map<String, Object> confirmWithdraw(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            if (editor == null) {
                throw new IllegalAuthorityException();
            }
            Integer articleId;
            Boolean confirm;
            try {
                articleId = (Integer) request.get("articleId");
                confirm = (Boolean) request.get("confirm");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null || !article.getStatus().equals("已出版")) {
                throw new ObjectNotFoundException();
            }
            if (!article.getEditorId().equals(editor.getEditorId())) {
                throw new IllegalAuthorityException();
            }
            map.put("success", true);
            String title = article.getTitle();
            Author author = authorService.getAuthorByAuthorId(article.getSubmitterId());
            Account authorAccount = accountService.getAccountByAccountId(author.getAccountId());
            String authorName = authorAccount.getRealName();
            String email = authorAccount.getEmail();
            String editorName = account.getRealName();
            EmailFactory emailFactory = new EmailFactory();
            ReminderEmail reminderEmail;
            if (confirm) {
                articleService.removeArticle(articleId);
                reminderEmail = emailFactory.makeAcceptWithdrawEmail(authorName, editorName, title);
            } else {
                articleService.setArticleStatus(articleId, "已出版");
                reminderEmail = emailFactory.makeRejectWithdrawEmail(authorName, editorName, title);
            }
            emailService.sendReminderEmail(email, reminderEmail);
        } catch (LoginVerificationException | ParameterFormatException |
                ObjectNotFoundException | IllegalAuthorityException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

}
