package com.buaa.ops.Controller;

import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.*;
import com.buaa.ops.Service.Emails.EmailFactory;
import com.buaa.ops.Service.Emails.ReminderEmail;
import com.buaa.ops.Service.Exc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
                reviewerIds = (ArrayList<Integer>) request.get("reviewerId");
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
                authorService.claimArticle(authorId, articleId);
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
            if (confirm) {
                articleService.removeArticle(articleId);
                // TODO send email
            } else {
                article.setStatus("已出版");
                // TODO send email
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
}
