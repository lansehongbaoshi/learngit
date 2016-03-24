package com.chsi.knowledge.vo;

import java.util.List;

import com.chsi.knowledge.dic.AType;

public class AnswerVO<T> {
    private AType aType;
    private List<T> result;
    private String content;

    public AType getAType() {
        return aType;
    }

    public void setAType(AType aType) {
        this.aType = aType;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
