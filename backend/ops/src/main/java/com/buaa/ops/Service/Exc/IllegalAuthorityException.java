package com.buaa.ops.Service.Exc;

public class IllegalAuthorityException extends Exception{
    @Override
    public String toString() {
        return "无权进行操作";
    }
}