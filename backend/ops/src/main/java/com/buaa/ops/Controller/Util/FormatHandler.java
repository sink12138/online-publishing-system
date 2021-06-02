package com.buaa.ops.Controller.Util;

import java.util.ArrayList;

public class FormatHandler {

    public ArrayList<String> castToArrayList(Object object) throws ClassCastException {
        if (!(object instanceof ArrayList))
            throw new ClassCastException();
        @SuppressWarnings("unchecked")
        ArrayList<String> arrayList = (ArrayList<String>) object;
        return arrayList;
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
