package com.buaa.ops.Controller;

import com.buaa.ops.Entity.*;
import com.buaa.ops.Service.*;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.ParameterFormatException;
import com.buaa.ops.Service.Exc.RepetitiveOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            map.put("success", true);
            map.put("title", article.getTitle());
            map.put("articleAbstract", article.getArticleAbstract());
            map.put("keywords", article.getKeywords());
            map.put("firstAuthor", article.getFirstAuthor());
            map.put("otherAuthor", article.getOtherAuthor().split(";"));
            map.put("identifier", article.getIdentifier());
            ArrayList<Author> authorArrayList = authorService.getAuthorsByArticleId(articleId);
            Map<String, Integer> authors = new HashMap<>();
            for (Author author : authorArrayList) {
                authors.put(accountService.getAccountByAccountId(author.getAccountId()).getRealName(), author.getAuthorId());
            }
            map.put("authorMap", authors);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
            map.put("publishingDate", sdf.format(article.getPublishingDate()));
        } catch (ParameterFormatException | ObjectNotFoundException exception) {
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
            map.clear();
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
            try {
                password = (String) request.get("password");
                realName = (String) request.get("realName");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Account newAccountInfos = new Account(account.getEmail(), password, realName);
            accountService.modifyInfos(newAccountInfos);
            if (author != null) {
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
                String organization;
                try {
                    organization = (String) request.get("organization");
                } catch (Exception e) {
                    throw new ParameterFormatException();
                }
                Reviewer newReviewerInfos = new Reviewer(account.getAccountId(), organization);
                reviewerService.modifyInfos(newReviewerInfos);
            }
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
                Map<String, Object> articleInfo= new HashMap<>();
                articleInfo.put("articleId", article.getArticleId());
                articleInfo.put("title", article.getTitle());
                articleInfo.put("keywords", article.getKeywords());
                articleInfo.put("firstAuthor", article.getFirstAuthor());
                articleInfo.put("otherAuthor", article.getOtherAuthor());
                arrayList.add(articleInfo);
            }
        } catch (ParameterFormatException exception) {
            arrayList.clear();
            map.clear();
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
}
