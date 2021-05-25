package com.buaa.ops.Service.Exc;

public class RepetitiveOperationException extends Exception{
    @Override
    public String toString() {
        return "重复操作";
    }
}
