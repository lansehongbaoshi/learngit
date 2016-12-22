package com.chsi.knowledge.vo;

import com.chsi.knowledge.pojo.KnowledgeData;

public class DiscussCountVO {

    private String useful;
    private String unuseful;
    private String sum;
    private String usefulPersent;
    private String unusefulPersent;
    private KnowledgeData knowledge;
    private String title;

    public String getUseful() {
        return useful;
    }

    public void setUseful(String useful) {
        this.useful = useful;
    }

    public String getUnuseful() {
        return unuseful;
    }

    public void setUnuseful(String unuseful) {
        this.unuseful = unuseful;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getUsefulPersent() {
        return usefulPersent;
    }

    public void setUsefulPersent(String usefulPersent) {
        this.usefulPersent = usefulPersent;
    }

    public String getUnusefulPersent() {
        return unusefulPersent;
    }

    public void setUnusefulPersent(String unusefulPersent) {
        this.unusefulPersent = unusefulPersent;
    }
    public KnowledgeData getKnowledge() {
        return knowledge;
    }
    public void setKnowledge(KnowledgeData knowledge) {
        this.knowledge = knowledge;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
}
