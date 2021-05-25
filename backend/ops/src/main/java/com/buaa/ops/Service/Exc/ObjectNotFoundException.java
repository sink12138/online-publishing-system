package com.buaa.ops.Service.Exc;

public class ObjectNotFoundException extends Exception{
    @Override
    public String toString() {
        return "未找到相关对象";
    }
}
