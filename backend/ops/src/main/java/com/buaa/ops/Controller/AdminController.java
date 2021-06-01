package com.buaa.ops.Controller;

import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.*;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.ParameterFormatException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Value("${admin.username}")
    private String USERNAME;

    @Value("${admin.password}")
    private String PASSWORD;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private Boolean checkAuthority() {
        HttpSession session = httpServletRequest.getSession();
        return session.getAttribute("USERNAME").equals(USERNAME) && session.getAttribute("PASSWORD").equals(PASSWORD);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            String username;
            String password;
            try {
                username = (String) request.get("username");
                password = (String) request.get("password");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            if (!username.equals(USERNAME) || !password.equals(PASSWORD)) {
                throw new LoginVerificationException();
            }
            map.put("success", true);
            session.setAttribute("USERNAME", username);
            session.setAttribute("PASSWORD", password);
        } catch (ParameterFormatException | LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Enumeration<String> em = session.getAttributeNames();
            while (em.hasMoreElements()) {
                session.removeAttribute(em.nextElement());
            }
            map.put("success", true);
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @Autowired
    private AccountService accountService;

    @PostMapping("/insert/account")
    public Map<String, Object> insertAccount(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            String email;
            String password;
            String realName;
            try {
                email = (String) request.get("email");
                password = (String) request.get("password");
                realName = (String) request.get("realName");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            // todo judge password
            Account newAccount = new Account(email, password, realName);
            accountService.addAccount(newAccount);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException | RepetitiveOperationException exception) {
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
    private EditorService editorService;

    @PostMapping("/insert/editor")
    public Map<String, Object> insertEditor(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            String email;
            try {
                email = (String) request.get("email");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Account account = accountService.getAccountByEmail(email);
            if (account == null) {
                throw new ObjectNotFoundException();
            }
            Editor another =  editorService.getEditorByAccountId(account.getAccountId());
            if (another != null) {
                throw new RepetitiveOperationException();
            }
            Editor newEditor = new Editor(account.getAccountId());
            editorService.addEditor(newEditor);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException | RepetitiveOperationException exception) {
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
    private ArticleService articleService;

    @PostMapping("/delete/article")
    public Map<String, Object> deleteArticle(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            Integer articleId;
            try {
                articleId = (Integer) request.get("articleId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            articleService.removeArticle(articleId);
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

    @PostMapping("/delete/account")
    public Map<String, Object> deleteAccount(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            Integer accountId;
            try {
                accountId = (Integer) request.get("accountId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            accountService.deleteAccount(accountId);
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

    @PostMapping("/delete/editor")
    public Map<String, Object> deleteEditor(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            Integer editorId;
            try {
                editorId = (Integer) request.get("editorId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            if (editorService.removeEditor(editorId)) {
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("message", "仅剩一名编辑无法移除");
            }
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

    @GetMapping("/selectArticles")
    public  ArrayList<Map<String, Object>> selectArticle() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            ArrayList<Article> articleArrayList = articleService.getArticles();
            map.put("success", true);
            arrayList.add(map);
            for (Article article : articleArrayList) {
                Map<String, Object> articleInfo = new HashMap<>();
                articleInfo.put("articleId", article.getArticleId());
                articleInfo .put("title", article.getTitle());
                articleInfo .put("articleAbstract", article.getArticleAbstract());
                articleInfo .put("keywords", article.getKeywords().split(";"));
                articleInfo .put("firstAuthor", article.getFirstAuthor());
                if (article.getOtherAuthors() != null) {
                    articleInfo.put("otherAuthors", article.getOtherAuthors().split(";"));
                }
                articleInfo .put("identifier", article.getIdentifier());
                articleInfo.put("status", article.getStatus());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                articleInfo .put("publishingDate", sdf.format(article.getPublishingDate()));
                arrayList.add(articleInfo);
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
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @GetMapping("/select/accounts")
    public ArrayList<Map<String, Object>> selectAccounts() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            ArrayList<Account> accountArrayList = accountService.getAccounts();
            map.put("success", true);
            arrayList.add(map);
            for (Account account : accountArrayList) {
                Map<String, Object> accountInfos = new HashMap<>();
                accountInfos.put("accountId", account.getAccountId());
                accountInfos.put("email", account.getEmail());
                accountInfos.put("password", account.getPassword());
                accountInfos.put("realName", account.getRealName());
                arrayList.add(accountInfos);
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
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @Autowired
    private AuthorService authorService;

    @GetMapping("/selectAuthor")
    public ArrayList<Map<String, Object>> selectAuthor() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            ArrayList<Author> authorArrayList = authorService.getAuthors();
            map.put("success", true);
            arrayList.add(map);
            for (Author author : authorArrayList) {
                Map<String, Object> authorInfos = new HashMap<>();
                Account account = accountService.getAccountByAccountId(author.getAuthorId());
                authorInfos.put("authorId", author.getAuthorId());
                authorInfos.put("accountId", author.getAccountId());
                authorInfos.put("email", account.getEmail());
                authorInfos.put("password", account.getPassword());
                authorInfos.put("realName", account.getRealName());
                authorInfos.put("institution", author.getInstitution());
                authorInfos.put("researchInterests", author.getResearchInterests());
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
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping("/select/reviewer")
    public ArrayList<Map<String, Object>> selectReviewer() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            ArrayList<Reviewer> reviewerArrayList = reviewerService.getReviewers();
            map.put("success", true);
            arrayList.add(map);
            for (Reviewer reviewer : reviewerArrayList) {
                Map<String, Object> reviewerInfos = new HashMap<>();
                Account account = accountService.getAccountByAccountId(reviewer.getAccountId());
                reviewerInfos.put("reviewerId", reviewer.getReviewerId());
                reviewerInfos.put("accountId", reviewer.getAccountId());
                reviewerInfos.put("email", account.getEmail());
                reviewerInfos.put("password", account.getPassword());
                reviewerInfos.put("realName", account.getRealName());
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
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @GetMapping("/select/editor")
    public ArrayList<Map<String, Object>> selectEditor() {
        Map<String, Object> map = new HashMap<>();
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            ArrayList<Editor> editorArrayList = editorService.getEditors();
            map.put("success", true);
            arrayList.add(map);
            for (Editor editor : editorArrayList) {
                Map<String, Object> editorInfos = new HashMap<>();
                Account account = accountService.getAccountByAccountId(editor.getAccountId());
                editorInfos.put("editorId", editor.getEditorId());
                editorInfos.put("accountId", editor.getAccountId());
                editorInfos.put("email", account.getEmail());
                editorInfos.put("password", account.getPassword());
                editorInfos.put("realName", account.getRealName());
                arrayList.add(editorInfos);
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
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @PostMapping("/clean")
    public Map<String, Object> clean() {
        Map<String, Object> map = new HashMap<>();
        try {
            if (!checkAuthority()) {
                throw new LoginVerificationException();
            }
            if (accountService.cleanBuffer() && articleService.cleanBuffer()) {
                map.put("success", true);
            } else {
                map.put("success", false);
                map.put("message", "操作失败");
            }
        } catch (LoginVerificationException exception) {
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
