package com.chsi.knowledge.vo;

public class KnowledgeVisitVO {
    
    private String visitType;
    private String knowledgeId;
    private int visitCNT;
    
    public KnowledgeVisitVO(){
        super();
    }
    public KnowledgeVisitVO(String knowledgeId,String visitType,int visitCNT){
        this.knowledgeId = knowledgeId;
        this.visitType = visitType;
        this.visitCNT = visitCNT;
    }
    
    public String getVisitType() {
        return visitType;
    }
    public void setVisitType(String visitType) {
        this.visitType = visitType;
    }
    public String getKnowledgeId() {
        return knowledgeId;
    }
    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }
    public int getVisitCNT() {
        return visitCNT;
    }
    public void setVisitCNT(int visitCNT) {
        this.visitCNT = visitCNT;
    }
    
}
