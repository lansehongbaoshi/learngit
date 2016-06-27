package com.chsi.knowledge.pojo;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import com.chsi.framework.pojos.PersistentObject;
import com.chsi.framework.util.TimeUtil;
import com.chsi.knowledge.dic.KnowledgeStatus;
import com.chsi.knowledge.util.RemoteCallUtil;
import com.chsi.knowledge.util.SearchUtil;
import com.chsi.news.vo.Article;

/**
 * 知识表
 * @author chenjian
 * 
 */
@Entity
@Table(name = "KNOWLEDGE")
@DynamicUpdate(value = true)
public class KnowledgeData extends PersistentObject {

    private static final long serialVersionUID = -4645122472610059168L;
    private String id;
    private String keywords;
    private String type;
    private String cmsId;
    private int visitCnt;
    private int sort;
    private KnowledgeStatus knowledgeStatus;
    private String creater;
    private Calendar createTime;
    private String updater;
    private Calendar updateTime;
    private Calendar topTime;
    private Article article;//临时变量
    private List<SystemData> systemDatas;

    public void setData(PersistentObject persistentObject) {
        KnowledgeData knowledgeData = (KnowledgeData) persistentObject;
        this.id = knowledgeData.getId();
        this.keywords = knowledgeData.getKeywords();
        this.cmsId = knowledgeData.getCmsId();
        this.visitCnt = knowledgeData.getVisitCnt();
        this.sort = knowledgeData.getSort();
        this.knowledgeStatus = knowledgeData.getKnowledgeStatus();
        this.creater = knowledgeData.getCreater();
        this.createTime = knowledgeData.getCreateTime();
        this.updater = knowledgeData.getUpdater();
        this.updateTime = knowledgeData.getUpdateTime();
        this.type = knowledgeData.getType();
    }

    public KnowledgeData(){
        super();
    }
    
    public KnowledgeData(String id, String keywords,
            String cmsId, int visitCnt, int sort,
            KnowledgeStatus knowledgeStatus, String creater,
            Calendar createTime, String updater, Calendar updateTime, String type) {
        super();
        this.id = id;
        this.keywords = keywords;
        this.cmsId = cmsId;
        this.visitCnt = visitCnt;
        this.sort = sort;
        this.knowledgeStatus = knowledgeStatus;
        this.creater = creater;
        this.createTime = createTime;
        this.updater = updater;
        this.updateTime = updateTime;
        this.type = type;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "app_seq")
    @GenericGenerator(name = "app_seq", strategy = "com.chsi.framework.hibernate.StringRandomGenerator")
    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String arg0) {
        this.id = arg0;
    }

    @Column(name = "KEYWORDS")
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Column(name = "TYPE")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "CMS_ID")
    public String getCmsId() {
        return cmsId;
    }

    public void setCmsId(String cmsId) {
        this.cmsId = cmsId;
    }

    @Column(name = "VISIT_CNT")
    public int getVisitCnt() {
        return visitCnt;
    }

    public void setVisitCnt(int visitCnt) {
        this.visitCnt = visitCnt;
    }

    @Column(name = "SORT")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Column(name = "STATUS")
    public KnowledgeStatus getKnowledgeStatus() {
        return knowledgeStatus;
    }

    public void setKnowledgeStatus(KnowledgeStatus knowledgeStatus) {
        this.knowledgeStatus = knowledgeStatus;
    }

    @Column(name = "CREATER")
    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Column(name = "CREATE_TIME")
    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    @Column(name = "UPDATER")
    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    @Column(name = "UPDATE_TIME")
    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }

    @Column(name = "TOP_TIME")
    public Calendar getTopTime() {
        return topTime;
    }

    public void setTopTime(Calendar topTime) {
        this.topTime = topTime;
    }

    @Transient
    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    //创建者姓名
    @Transient
    public String getCreaterName() {
        return RemoteCallUtil.getXmByUserId(this.creater);
    }
    
    //最后更新者姓名
    @Transient
    public String getUpdaterName() {
        return RemoteCallUtil.getXmByUserId(this.updater);
    }
    
    @Transient
    public String getSummary() {
        if(this.article!=null) {
            String content = this.article.getContent();
            content = SearchUtil.resultFilter(content);
            int tempLength = content.length() < 40 ? content.length() : 40;
            content = content.substring(0, tempLength) + "...";
            return content;
        } else {
            return "";
        }
    }

    
    @Transient
    public String getLastOperTime(String format) {
        Calendar lastTime = null;
        if(this.updateTime!=null) {
            lastTime = this.updateTime;
        } else {
            lastTime = this.createTime;
        }
        if(lastTime!=null) {
            return TimeUtil.getTime(lastTime, format);
        }
        return "";
    }

    @Transient
    public List<SystemData> getSystemDatas() {
        return systemDatas;
    }

    @Transient
    public void setSystemDatas(List<SystemData> systemDatas) {
        this.systemDatas = systemDatas;
    }
    
    @Transient
    public String getSystemName() {
        String result = "";
        if(systemDatas!=null&&systemDatas.size()>0) {
            result = systemDatas.get(0).getName();
            if(systemDatas.size()>1) {
                result += "...";
            }
        }
        return result;
    }
    
    @Transient
    public String getSystemNames() {
        String result = "";
        if(systemDatas!=null&&systemDatas.size()>0) {
            for(SystemData systemData:systemDatas) {
                result += systemData.getName() + "&nbsp;";
            }
        }
        return result;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj!=null) {
            KnowledgeData vo = (KnowledgeData)obj;
            return this.getId().equals(vo.getId());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
