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
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


@RestController
public class UserController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String email;
            String password;
            try {
                email = (String) request.get("email");
                password = (String) request.get("password");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            AccountBuffer accountBuffer = new AccountBuffer(email, password);
            accountService.addAccountBuffer(accountBuffer);
            emailService.sendCheckEmail(accountBuffer.getAccountBufferId(), email, true);
            map.put("success", true);
        } catch (ParameterFormatException | RepetitiveOperationException e) {
            map.put("success", false);
            map.put("message", e.toString());
        } catch (Exception otherException) {
            map.put("success", false);
            map.put("message", "操作失败");
            otherException.printStackTrace();
        }
        return map;
    }

    @PostMapping("/verify")
    public Map<String, Object> verify(@RequestBody Map<String, Object> request){
        Map<String, Object> map = new HashMap<>();
        try {
            String code;
            try {
                code = (String) request.get("code");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Integer accountBufferId = accountService.checkCode(code);
            accountService.moveToAccount(accountBufferId);
            map.put("success", true);
        } catch (ObjectNotFoundException | ParameterFormatException e) {
            map.put("success", false);
            map.put("message", e.toString());
        } catch (Exception otherException) {
            map.put("success", false);
            map.put("message", "操作失败");
            otherException.printStackTrace();
        }
        return map;
    }

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private EditorService editorService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            String email;
            String password;
            int authority = 0;
            try {
                email = (String) request.get("email");
                password = (String) request.get("password");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Account account = accountService.getAccountByEmail(email);
            if (account == null || !account.getPassword().equals(password)) {
                throw new LoginVerificationException();
            }
            map.put("success", true);
            session.setAttribute("email", email);
            session.setAttribute("password", password);
            if (authorService.getAuthorByAccountId(account.getAccountId()) != null) {
                authority |= 7;
            }
            if (reviewerService.getReviewerByAccountId(account.getAccountId()) != null) {
                authority |= 3;
            }
            if (editorService.getEditorByAccountId(account.getAccountId()) != null) {
                authority |= 1;
            }
            map.put("role", authority);
        } catch (ParameterFormatException | LoginVerificationException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception otherException) {
            map.put("success", false);
            map.put("message", "操作失败");
            otherException.printStackTrace();
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

    @GetMapping("/search")
    public ArrayList<Map<String, Object>> search(@RequestBody Map<String, Object> requestMap) {
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        Map<String, Object> statusMap = new HashMap<>();
        try {
            String searchType;
            String searchString;
            // Begin parameter format checks
            try {
                searchType = (String) requestMap.get("searchType");
                searchString = (String) requestMap.get("searchString");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (searchType == null || searchString == null)
                throw new ParameterFormatException();
            if (!searchType.equals("title") && !searchType.equals("keyword") && !searchType.equals("author"))
                throw new ParameterFormatException();
            if (searchString.isEmpty())
                throw new ParameterFormatException();
            if (searchString.contains(";"))
                throw new ParameterFormatException();
            // End parameter format checks
            ArrayList<Article> results = articleService.searchPublishedArticles(searchType, searchString);
            statusMap.put("success", true);
            if (results == null || results.isEmpty()) {
                statusMap.put("results", 0);
                maps.add(statusMap);
            }
            else {
                statusMap.put("results", results.size());
                maps.add(statusMap);
                for (Article article: results) {
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
                    if (otherAuthors != null) {
                        map.put("otherAuthors", otherAuthors.split(";"));
                    }
                    maps.add(map);
                }
            }
        }
        catch (ParameterFormatException pfe) {
            statusMap.put("success", false);
            statusMap.put("message", pfe.toString());
            maps.add(statusMap);
        }
        catch (Exception e) {
            e.printStackTrace();
            statusMap.clear();
            statusMap.put("success", false);
            statusMap.put("message", "操作失败");
            maps.clear();
            maps.add(statusMap);
        }
        return maps;
    }

    @Autowired
    ArticleService articleService;

    @GetMapping("/article")
    public Map<String, Object> article(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        Integer articleId;
        try {
            try {
                articleId = (Integer) request.get("article");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Article article = articleService.getArticleById(articleId);
            if (article == null) {
                throw new ObjectNotFoundException();
            }
            if (!article.getStatus().equals("已出版"))
                throw new IllegalAuthorityException();
            map.put("success", true);
            map.put("title", article.getTitle());
            map.put("articleAbstract", article.getArticleAbstract());
            map.put("keywords", article.getKeywords());
            map.put("firstAuthor", article.getFirstAuthor());
            map.put("otherAuthor", article.getOtherAuthors().split(";"));
            map.put("identifier", article.getIdentifier());
            ArrayList<Author> authorArrayList = authorService.getAuthorsByArticleId(articleId);
            Map<String, Integer> authors = new HashMap<>();
            for (Author author : authorArrayList) {
                authors.put(accountService.getAccountByAccountId(author.getAccountId()).getRealName(), author.getAuthorId());
            }
            map.put("authorMap", authors);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            map.put("publishingDate", sdf.format(article.getPublishingDate()));
        } catch (ParameterFormatException | ObjectNotFoundException | IllegalAuthorityException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @Value("${admin.username}")
    private String USERNAME;

    @Value("${admin.password}")
    private String PASSWORD;

    @GetMapping("/download")
    public void download(@RequestBody Map<String, Object> requestMap) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            Account account = accountService.getAccountBySession(httpServletRequest.getSession());
            Integer articleId;
            // Begin parameter format checks
            try {
                articleId = (Integer) requestMap.get("articleId");
            }
            catch (ClassCastException cce) {
                throw new ParameterFormatException();
            }
            if (articleId == null || articleId == 0)
                throw new ParameterFormatException();
            // End parameter format checks
            Article article = null;
            ArticleBuffer articleBuffer= null;
            // Begin existence checks
            if (articleId > 0)
                article = articleService.getArticleById(articleId);
            else
                articleBuffer = articleService.getArticleBufferById(-articleId);
            if (article == null && articleBuffer == null)
                throw new ObjectNotFoundException();
            // End existence checks
            boolean authority = false;
            Integer accountId = account.getAccountId();
            Author author = authorService.getAuthorByAccountId(accountId);
            Reviewer reviewer = reviewerService.getReviewerByAccountId(accountId);
            Editor editor = editorService.getEditorByAccountId(accountId);
            // Begin authority checks
            // User authority
            if (article != null && article.getStatus().equals("已出版"))
                // A published article is available for any user
                authority = true;
            else if (author != null) { // Author authority
                Integer authorId = author.getAuthorId();
                // An unpublished article is available for its submitter
                if (article != null && article.getSubmitterId().equals(authorId) && !article.getStatus().equals("编辑中"))
                    authority = true;
                else if (articleBuffer != null && articleBuffer.getSubmitterId().equals(authorId))
                    authority = true;
            }
            else if (reviewer != null) { // Reviewer authority
                Integer reviewerId = reviewer.getReviewerId();
                if (article != null && !article.getStatus().equals("编辑中")) {
                    ArrayList<Reviewer> reviewers = reviewerService.getReviewersByArticleId(articleId);
                    for (Reviewer r : reviewers) {
                        if (r.getReviewerId().equals(reviewerId)) {
                            // An unpublished article is available for its reviewers
                            authority = true;
                            break;
                        }
                    }
                }
            }
            else if (editor != null) { // Editor authority
                Integer editorId = editor.getEditorId();
                // An unpublished article is available for its editor
                if (article != null && article.getEditorId().equals(editorId))
                    authority = true;
                else if (articleBuffer != null && articleBuffer.getEditorId().equals(editorId))
                    authority = true;
            }
            else { // Admin authority
                HttpSession session = httpServletRequest.getSession();
                if (session.getAttribute("USERNAME").equals(USERNAME) &&
                        session.getAttribute("PASSWORD").equals(PASSWORD))
                    // Any article is available for admin
                    authority = true;
            }
            if (!authority)
                throw new IllegalAuthorityException();
            // End authority checks
            File file;
            if (articleId > 0)
                file = articleService.getArticleFile(articleId);
            else
                file = new File(articleBuffer.getFilePath());
            String fileName = file.getName();
            httpServletResponse.setContentType("application/force-download");
            httpServletResponse.addHeader("Content-Disposition","attachment; filename=\""
                    + fileName + "\";filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20"));
            byte[] buffer = new byte[1024];
            fis = new FileInputStream(file);
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
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Autowired
    private AuthorService authorService;

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping("/home")
    public Map<String, Object> home() {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            map.put("success", true);
            map.put("email", account.getEmail());
            map.put("password", account.getPassword());
            if (account.getRealName() != null) {
                map.put("realName", account.getRealName());
            }
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            Reviewer reviewer = reviewerService.getReviewerByAccountId(account.getAccountId());
            if (author != null) {
                if (author.getInstitution() != null) {
                    map.put("institution", author.getInstitution());
                }
                if (author.getResearchInterests() != null) {
                    map.put("researchInterests", author.getResearchInterests());
                }
            }
            if (reviewer != null && reviewer.getOrganization() != null) {
                map.put("organization", reviewer.getOrganization());
            }
        } catch (LoginVerificationException e) {
            map.put("success", false);
            map.put("message", e.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/home/modify")
    public Map<String, Object> modify(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            String password;
            String realName;
            Account account = accountService.getAccountBySession(session);
            Author author = authorService.getAuthorByAccountId(account.getAccountId());
            Reviewer reviewer = reviewerService.getReviewerByAccountId(account.getAccountId());
            Editor editor = editorService.getEditorByAccountId(account.getAccountId());
            try {
                password = (String) request.get("password");
                realName = (String) request.get("realName");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            if (password == null || !isPasswordValid(password)) {
                throw new ParameterFormatException();
            }
            if (author != null) {
                if (realName == null) {
                    throw new ParameterFormatException();
                }
                String institution;
                String researchInterests;
                try {
                    institution = (String) request.get("institution");
                    researchInterests = (String) request.get("researchInterests");
                } catch (Exception e) {
                    throw new ParameterFormatException();
                }
                Author newAuthorInfos = new Author(account.getAccountId(), institution, researchInterests);
                authorService.modifyInfos(newAuthorInfos);
            }
            if (reviewer != null) {
                if (realName == null) {
                    throw new ParameterFormatException();
                }
                String organization;
                try {
                    organization = (String) request.get("organization");
                } catch (Exception e) {
                    throw new ParameterFormatException();
                }
                Reviewer newReviewerInfos = new Reviewer(account.getAccountId(), organization);
                reviewerService.modifyInfos(newReviewerInfos);
            }
            if (editor != null && realName == null) {
                throw new ParameterFormatException();
            }
            Account newAccountInfos = new Account(account.getEmail(), password, realName);
            accountService.modifyInfos(newAccountInfos);
            map.put("success", true);
        } catch (ParameterFormatException | LoginVerificationException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
        } catch (Exception e) {
            map.put("success", false);
            map.put("message", "操作失败");
            e.printStackTrace();
        }
        return map;
    }

    @PostMapping("/home/modify/email")
    public Map<String, Object> modifyEmail(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        String newEmail;
        HttpSession session = httpServletRequest.getSession();
        try {
            Account account = accountService.getAccountBySession(session);
            try {
                newEmail = (String) request.get("email");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Account newInfo = new Account(newEmail, account.getPassword(), account.getRealName());
            accountService.modifyInfos(newInfo);
            emailService.sendCheckEmail(account.getAccountId(), newEmail, false);
            map.put("success", true);
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

    @GetMapping("/infos")
    public ArrayList<Map<String, Object>> infos(@RequestBody Map<String, Object> request) {
        ArrayList<Map<String, Object>> arrayList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        Integer authorId;
        try {
            try {
                authorId = (Integer) request.get("authorId");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Author author = authorService.getAuthorByAuthorId(authorId);
            Account account = accountService.getAccountByAccountId(author.getAccountId());
            ArrayList<Article> articleArrayList = authorService.getMyArticles(authorId);
            map.put("success", true);
            map.put("email", account.getEmail());
            map.put("realName", account.getRealName());
            map.put("institution", author.getInstitution());
            map.put("researchInterests", author.getResearchInterests());
            map.put("articleCount", articleArrayList.size());
            arrayList.add(map);
            for (Article article : articleArrayList) {
                if (article.getStatus().equals("已出版")) {
                    Map<String, Object> articleInfo = new HashMap<>();
                    articleInfo.put("articleId", article.getArticleId());
                    articleInfo.put("title", article.getTitle());
                    articleInfo.put("keywords", article.getKeywords());
                    articleInfo.put("firstAuthor", article.getFirstAuthor());
                    articleInfo.put("otherAuthor", article.getOtherAuthors());
                    arrayList.add(articleInfo);
                }
            }
        } catch (ParameterFormatException | ObjectNotFoundException exception) {
            map.put("success", false);
            map.put("message", exception.toString());
            arrayList.add(map);
        } catch (Exception e) {
            arrayList.clear();
            map.clear();
            map.put("success", false);
            map.put("message", "操作失败");
            arrayList.add(map);
            e.printStackTrace();
        }
        return arrayList;
    }

    // todo
    private Boolean isPasswordValid(String password) {
        return password.length() > 5;
    }

}
