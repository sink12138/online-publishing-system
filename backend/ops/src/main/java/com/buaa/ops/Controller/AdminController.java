package com.buaa.ops.Controller;

import com.buaa.ops.Entity.Account;
import com.buaa.ops.Entity.Editor;
import com.buaa.ops.Service.AccountService;
import com.buaa.ops.Service.ArticleService;
import com.buaa.ops.Service.EditorService;
import com.buaa.ops.Service.Exc.LoginVerificationException;
import com.buaa.ops.Service.Exc.ObjectNotFoundException;
import com.buaa.ops.Service.Exc.ParameterFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        return session.getAttribute("username").equals(USERNAME) && session.getAttribute("password").equals(PASSWORD);
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
            session.setAttribute("username", username);
            session.setAttribute("password", password);
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

    @PostMapping("/logoout")
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
    AccountService accountService;

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
            Account newAccount = new Account(email, password, realName);
            accountService.addAccount(newAccount);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException exception) {
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
    EditorService editorService;

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
            Editor newEditor = new Editor(accountService.getAccountByEmail(email).getAccountId());
            editorService.addEditor(newEditor);
            map.put("success", true);
        } catch (LoginVerificationException | ParameterFormatException exception) {
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
    ArticleService articleService;

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


}
