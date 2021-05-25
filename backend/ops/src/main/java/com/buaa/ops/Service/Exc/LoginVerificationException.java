package com.buaa.ops.Service.Exc;

public class LoginVerificationException extends Exception{
    @Override
    public String toString() {
        return "登录验证失败";
    }
}
