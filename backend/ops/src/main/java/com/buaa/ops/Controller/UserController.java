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

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, Object> request) {
        Map<String, Object> map = new HashMap<>();
        HttpSession session = httpServletRequest.getSession();
        try {
            String email;
            String password;
            try {
                email = (String) request.get("email");
                password = (String) request.get("password");
            } catch (Exception e) {
                throw new ParameterFormatException();
            }
            Account account = accountService.getAccount(email);
            if (account == null || !account.getPassword().equals(password)) {
                throw new LoginVerificationException();
            }
            map.put("success", true);
            session.setAttribute("email", email);
            session.setAttribute("password", password);
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
    private AuthorService authorService;

    @Autowired
    private ReviewerService reviewerService;

    @GetMapping("/home")
    private Map<String, Object> home() {
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
        try {

        } catch (ParameterFormatException | LoginVerificationException exception) {

        }
    }
}
