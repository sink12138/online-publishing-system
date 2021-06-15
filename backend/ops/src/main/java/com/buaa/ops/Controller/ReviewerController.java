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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/reviewer")
public class ReviewerController {

    @Autowired
    private ReviewerService reviewerService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/articles")
    ArrayList<Map<String, Object>> articles() {
        HttpSession session = httpServletRequest.getSession();
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        try {
            Account account = accountService.getAccountBySession(session);
            Reviewer reviewer = reviewerService.getReviewerByAccountId(account.getAccountId());
            if (reviewer == null) {
                throw new LoginVerificationException();
            }
            ArrayList<Article> articleArrayList = reviewerService.getReviewingArticles(reviewer.getReviewerId());
            map.put("success", true);
            map.put("results", articleArrayList.size());
            arrayList.add(map);
            for (Article article : articleArrayList) {
                Map<String, Object> articleInfos = new HashMap<>();
                Integer articleId = article.getArticleId();
                if (!reviewerService.hasReviewed(articleId, reviewer.getReviewerId())) {
                    articleInfos.put("articleId", articleId);
                    articleInfos.put("title", article.getTitle());
                    articleInfos.put("keywords", article.getKeywords().split(";"));
                    articleInfos.put("status", article.getStatus());
                    arrayList.add(articleInfos);
                }
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

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuthorService authorService;

    @PostMapping("/review/submit")
    public Map<String, Object> submitReview(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Reviewer reviewer = reviewerService.getReviewerByAccountId(account.getAccountId());
            if (reviewer == null) {
                throw new LoginVerificationException();
            }
            Integer articleId;
            Boolean pass;
            String comments;
            try {
                articleId = (Integer) request.get("articleId");
                pass = (Boolean) request.get("pass");
                comments = (String) request.get("comments");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null) {
                throw new ObjectNotFoundException();
            }
            if (comments == null || pass == null) {
                throw new ObjectNotFoundException();
            }
            java.sql.Date date = new java.sql.Date(new Date().getTime());
            Review review = new Review(articleId, reviewer.getReviewerId(), comments, pass, date);
            reviewerService.submitReview(review);
            map.put("success", true);
            // judge whether to send email
            Map<String, Boolean> reviewingResult = reviewerService.completeReview(articleId);
            if (reviewingResult.get("complete")) {
                EmailFactory emailFactory = new EmailFactory();
                String articleTitle = article.getTitle();
                // send email to editor
                Editor editor = editorService.getEditorById(article.getEditorId());
                Account editorAccount = accountService.getAccountByAccountId(editor.getAccountId());
                String editorRealName = editorAccount.getRealName();
                String editorEmail = editorAccount.getEmail();
                ReminderEmail reminderEmailToEditor = emailFactory.makeReviewFinishedEmail(editorRealName, articleTitle);
                emailService.sendReminderEmail(editorEmail, reminderEmailToEditor);
                // send email to submitter
                Author author = authorService.getAuthorByAuthorId(article.getSubmitterId());
                Account submitterAccount = accountService.getAccountByAccountId(author.getAccountId());
                String submitterRealName = submitterAccount.getRealName();
                String submitterEmail = submitterAccount.getEmail();
                ReminderEmail reminderEmailToSubmitter = reviewingResult.get("pass") ?
                        emailFactory.makeReviewPassEmail(submitterRealName, articleTitle) : emailFactory.makeReviewFailEmail(submitterRealName, articleTitle);
                emailService.sendReminderEmail(submitterEmail, reminderEmailToSubmitter);
                articleService.setArticleStatus(articleId, reviewingResult.get("pass") ? "审核通过" : "审核未通过");
            }
        } catch (ParameterFormatException | LoginVerificationException |
                ObjectNotFoundException | RepetitiveOperationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/cancel")
    public Map<String, Object> cancel() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            Reviewer reviewer = reviewerService.getReviewerByAccountId(account.getAccountId());
            if (reviewer == null) {
                throw new LoginVerificationException();
            }
            ArrayList<Article> articleArrayList = reviewerService.getReviewingArticles(reviewer.getReviewerId());
            if (articleArrayList.isEmpty()) {
                reviewerService.removeReviewer(reviewer.getReviewerId());
                map.put("success", true);
            } else {
                throw new IllegalAuthorityException();
            }
        } catch (LoginVerificationException | ObjectNotFoundException | IllegalAuthorityException exception) {
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
