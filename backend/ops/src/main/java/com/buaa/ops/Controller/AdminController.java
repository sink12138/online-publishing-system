package com.buaa.ops.Controller;

import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.*;
import com.buaa.ops.Service.Exc.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.sql.Date;
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

    @Autowired
    private HttpServletResponse httpServletResponse;

    private Boolean checkAuthority() {
        HttpSession session = httpServletRequest.getSession();
        String username = (String) session.getAttribute("USERNAME");
        String password = (String) session.getAttribute("PASSWORD");
        return username != null && password != null && username.equals(USERNAME) && password.equals(PASSWORD);
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
            if (username == null || password == null || !username.equals(USERNAME) || !password.equals(PASSWORD)) {
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
            map.put("message", "????????????");
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
            map.put("message", "????????????");
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
            if (!accountService.isPasswordValid(password)) {
                throw new ParameterFormatException();
            }
            Account newAccount = new Account(email, password, realName);
            accountService.addAccount(newAccount);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException | RepetitiveOperationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "????????????");
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
            map.put("message", "????????????");
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
            map.put("message", "????????????");
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
        } catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException | IllegalAuthorityException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "????????????");
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
                map.put("message", "??????????????????????????????");
            }
        } catch (LoginVerificationException | ParameterFormatException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "????????????");
            e.printStackTrace();
        }
        return map;
    }

    @GetMapping("/select/articles")
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
            map.put("results", articleArrayList.size());
            if (articleArrayList.size() > 0) {
                for (Article article : articleArrayList) {
                    Map<String, Object> articleInfo = new HashMap<>();
                    articleInfo.put("articleId", article.getArticleId());
                    articleInfo.put("title", article.getTitle());
                    articleInfo.put("articleAbstract", article.getArticleAbstract());
                    articleInfo.put("keywords", article.getKeywords().split(";"));
                    articleInfo.put("firstAuthor", article.getFirstAuthor());
                    if (article.getOtherAuthors() != null) {
                        articleInfo.put("otherAuthors", article.getOtherAuthors().split(";"));
                    }
                    if (article.getIdentifier() != null) {
                        articleInfo.put("identifier", article.getIdentifier());
                    }
                    articleInfo.put("status", article.getStatus());
                    Date publishingDate = article.getPublishingDate();
                    if (publishingDate != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy???MM???dd???");
                        articleInfo.put("publishingDate", sdf.format(article.getPublishingDate()));
                    }
                    arrayList.add(articleInfo);
                }
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "????????????");
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
            map.put("results", accountArrayList.size());
            if (accountArrayList.size() > 0) {
                for (Account account : accountArrayList) {
                    Map<String, Object> accountInfos = new HashMap<>();
                    accountInfos.put("accountId", account.getAccountId());
                    accountInfos.put("email", account.getEmail());
                    accountInfos.put("password", account.getPassword());
                    if (account.getRealName() != null) {
                        accountInfos.put("realName", account.getRealName());
                    }
                    arrayList.add(accountInfos);
                }
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "????????????");
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @Autowired
    private AuthorService authorService;

    @GetMapping("/select/authors")
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
            map.put("results", authorArrayList.size());
            if (authorArrayList.size() > 0) {
                for (Author author : authorArrayList) {
                    Map<String, Object> authorInfos = new HashMap<>();
                    Account account = accountService.getAccountByAccountId(author.getAccountId());
                    authorInfos.put("authorId", author.getAuthorId());
                    authorInfos.put("accountId", author.getAccountId());
                    authorInfos.put("email", account.getEmail());
                    authorInfos.put("password", account.getPassword());
                    authorInfos.put("realName", account.getRealName());
                    if (author.getInstitution() != null) {
                        authorInfos.put("institution", author.getInstitution());
                    }
                    if (author.getResearchInterests() != null) {
                        authorInfos.put("researchInterests", author.getResearchInterests());
                    }
                    arrayList.add(authorInfos);
                }
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "????????????");
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping("/select/reviewers")
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
            map.put("results", reviewerArrayList.size());
            if (reviewerArrayList.size() > 0) {
                for (Reviewer reviewer : reviewerArrayList) {
                    Map<String, Object> reviewerInfos = new HashMap<>();
                    Account account = accountService.getAccountByAccountId(reviewer.getAccountId());
                    reviewerInfos.put("reviewerId", reviewer.getReviewerId());
                    reviewerInfos.put("accountId", reviewer.getAccountId());
                    reviewerInfos.put("email", account.getEmail());
                    reviewerInfos.put("password", account.getPassword());
                    reviewerInfos.put("realName", account.getRealName());
                    if (reviewer.getOrganization() != null) {
                        reviewerInfos.put("organization", reviewer.getOrganization());
                    }
                    arrayList.add(reviewerInfos);
                }
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "????????????");
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    @GetMapping("/select/editors")
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
            map.put("results", editorArrayList.size());
            if (editorArrayList.size() > 0) {
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
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            map.clear();
            arrayList.clear();
            map.put("success", false);
            map.put("message", "????????????");
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
                map.put("message", "????????????");
            }
        } catch (LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "????????????");
            e.printStackTrace();
        }
        return map;
    }

    @Value("${file.logs-path}")
    private String logsPath;

    @GetMapping("/logs")
    public void logs() {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        File logFile = new File(logsPath + "/log.txt");
        try {
            if (!checkAuthority())
                throw new LoginVerificationException();
            httpServletResponse.setContentType("application/force-download");
            httpServletResponse.addHeader("Content-Disposition","attachment; filename=log.txt");
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(logFile);
            bis = new BufferedInputStream(fis);
            OutputStream os = httpServletResponse.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (bis != null) bis.close();
                if (fis != null) fis.close();
                FileWriter fw = new FileWriter(logFile);
                fw.write("");
                fw.flush();
                fw.close();
                System.err.close();
                PrintStream ps = new PrintStream(new FileOutputStream(logsPath + "/log.txt"));
                System.setErr(ps);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
