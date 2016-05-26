package com.chsi.knowledge.web.util;

public class MyPropertyFilter implements net.sf.json.util.PropertyFilter {

    public boolean apply(Object source, String name, Object value) {
        return name.equals("createTime") || name.equals("updateTime") || name.equals("createon") || name.equals("updateon") 
                || name.equals("data") || name.equals("systemDatas");
    }

}
