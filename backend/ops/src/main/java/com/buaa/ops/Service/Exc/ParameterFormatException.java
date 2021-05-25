package com.buaa.ops.Service.Exc;

public class ParameterFormatException extends Exception{
    @Override
    public String toString() {
        return "参数格式不合法";
    }
}