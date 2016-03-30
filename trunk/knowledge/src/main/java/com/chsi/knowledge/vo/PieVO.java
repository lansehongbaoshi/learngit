package com.chsi.knowledge.vo;

import java.util.ArrayList;
import java.util.List;


public class PieVO {
    private String name;
    private Long value;

    public PieVO(String name, Long value) {
        this.name = name;
        this.value = value;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
    
    public static List<String> getNames(List<PieVO> list) {
        List<String> strList = new ArrayList<String>();
        for(PieVO vo:list) {
            strList.add(vo.getName());
        }
        return strList;
    }
    
    public static long totalCnt(List<PieVO> list) {
        long cnt = 0;
        for(PieVO vo:list) {
            cnt+=vo.getValue();
        }
        return cnt;
    }
}
