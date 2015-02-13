package com.chsi.knowledge.vo;

import com.chsi.knowledge.pojo.KnowledgeData;
import com.chsi.news.vo.Article;
/**
 * 知识VO 便于保存 知识信息和CMS 中保存的信息
 * @author chsi-pc
 *
 */
public class KnowledgeVO {

    private KnowledgeData knowledgeData;

    private Article article;
    
    public KnowledgeVO(){
        
    }
    
    public KnowledgeVO(KnowledgeData knowledgeData, Article article) {
        this.knowledgeData = knowledgeData;
        this.article = article;
    }

    public KnowledgeData getKnowledgeData() {
        return knowledgeData;
    }

    public void setKnowledgeData(KnowledgeData knowledgeData) {
        this.knowledgeData = knowledgeData;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

}
