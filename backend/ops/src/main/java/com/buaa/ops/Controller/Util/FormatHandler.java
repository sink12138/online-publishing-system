package com.buaa.ops.Controller.Util;

import java.util.ArrayList;

public class FormatHandler {

    public ArrayList<String> castToStringList(Object object) throws ClassCastException {
        if (object == null)
            return null;
        if (!(object instanceof ArrayList))
            throw new ClassCastException();
        @SuppressWarnings("unchecked")
        ArrayList<Object> objectList = (ArrayList<Object>) object;
        ArrayList<String> stringList = new ArrayList<>();
        for (Object obj : objectList) {
            String str = obj.toString();
            if (!str.isEmpty())
                stringList.add(obj.toString());
        }
        return stringList;
    }

    public ArrayList<Integer> castToIntegerList(Object object) throws ClassCastException, NumberFormatException {
        if (object == null)
            return null;
        if (!(object instanceof ArrayList))
            throw new ClassCastException();
        @SuppressWarnings("unchecked")
        ArrayList<Object> objectList = (ArrayList<Object>) object;
        ArrayList<Integer> integerList = new ArrayList<>();
        for (Object obj : objectList)
            integerList.add(Integer.parseInt(obj.toString()));
        return integerList;
    }

    public String buildArrayListToString(ArrayList<String> arrayList) {
        if (arrayList == null || arrayList.isEmpty())
            return null;
        StringBuilder sb = new StringBuilder();
        for (String element : arrayList) {
            sb.append(element);
            sb.append(";");
        }
        return sb.toString();
    }

}
