package com.chsi.knowledge.common;

import java.io.Serializable;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;

/**
 * 综合查询
 * @author weiqc <a href="mailto:weiqc@chsi.com.cn">weiqc</a>
 *
 */
public class WssqCriteria implements Serializable{
    private static final long serialVersionUID = 7745478156376071265L;
    private QueryWords link;
    private QueryWords compare;
    private boolean opposite;
    private String property;
    private Object[] values;
    
    public WssqCriteria(){}

    public boolean isOpposite() {
        return opposite;
    }

    public void setOpposite(boolean opposite) {
        this.opposite = opposite;
    }

    public QueryWords getLink() {
        return link;
    }

    public void setLink(QueryWords link) {
        this.link = link;
    }

    public QueryWords getCompare() {
        return compare;
    }

    public void setCompare(QueryWords compare) {
        this.compare = compare;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public Object[] getValues() {
        return values;
    }

    public void setValues(Object[] values) {
        this.values = values;
    }

    public static WssqCriteria between(String property,Object ...objects){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.BETWEEN);
        wc.setProperty(property);
        wc.setValues(objects);
        return wc;
    }
    
    public static WssqCriteria like(String property,Object ...objects){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.LIKE);
        wc.setProperty(property);
        wc.setValues(objects);
        return wc;
    }

    public static WssqCriteria order(String property,QueryWords sortWord){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.ORDER);
        wc.setCompare(sortWord);
        wc.setProperty(property);
        return wc;
    }
    
    public static WssqCriteria eq(String property,Object object){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.EQ);
        wc.setProperty(property);
        wc.setValues(new Object[]{object});
        return wc;
    }
    
    public static WssqCriteria ne(String property,Object object){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.NE);
        wc.setProperty(property);
        wc.setValues(new Object[]{object});
        return wc;
    }
    
    public static WssqCriteria ge(String property,Object object){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.GE);
        wc.setProperty(property);
        wc.setValues(new Object[]{object});
        return wc;
    }
    
    public static WssqCriteria le(String property,Object object){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.LE);
        wc.setProperty(property);
        wc.setValues(new Object[]{object});
        return wc;
    }
    
    public static WssqCriteria gt(String property,Object object){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.GT);
        wc.setProperty(property);
        wc.setValues(new Object[]{object});
        return wc;
    }
    
    public static WssqCriteria lt(String property,Object object){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.LT);
        wc.setProperty(property);
        wc.setValues(new Object[]{object});
        return wc;
    }
    
    public static WssqCriteria in(String property,Object ...objects){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.IN);
        wc.setProperty(property);
        wc.setValues(objects);
        return wc;
    }
    
    public static WssqCriteria isNull(String property){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.ISNULL);
        wc.setProperty(property);
        return wc;
    }
    
    public static WssqCriteria isNotNull(String property){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.AND);
        wc.setCompare(QueryWords.ISNOTNULL);
        wc.setProperty(property);
        return wc;
    }
    
    public static WssqCriteria not(WssqCriteria wc){
        wc.setOpposite(true);
        return wc;
    }
    
    /**
     * 实际为disjunction。
     * 查询对象属性只能在同表中可寻，不支持外联查询非对象引用字段
     * @param conditions
     * @return
     */
    public static WssqCriteria or(WssqCriteria...conditions){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.OR);
        wc.setValues(conditions);
        return wc;
    }
    
    public static WssqCriteria sql(String hql,Object[] values,Type[] types){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.SQL);
        wc.setProperty(hql);
        if(values != null && values.length != 0){
            wc.setValues(new Object[]{Restrictions.sqlRestriction(hql, values, types)});
        }else{
            wc.setValues(new Object[]{Restrictions.sqlRestriction(hql)});
        }
        return wc;
    }
    
    /**
     * 查询对象属性只能在同表中可寻，不支持外联查询非对象引用字段
     * @param conditions
     * @return
     */
    public static WssqCriteria conjunction(WssqCriteria...conditions){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.CONJUNCTION);
        wc.setValues(conditions);
        return wc;
    }
    
    public static WssqCriteria projectionList(ProjectionList list){
        WssqCriteria wc = new WssqCriteria();
        wc.setLink(QueryWords.PROJECTIONS);
        wc.setValues(new Object[]{list});
        return wc;
    }
    
    public static enum QueryWords{
        /**
         * 与
         */
        AND,
        /**
         * 或
         */
        OR,
        /**
         * 非
         */
        NOT,
        /**
         * 组合
         */
        CONJUNCTION,
        /**
         * 之间
         */
        BETWEEN,
        /**
         * like
         */
        LIKE,
        /**
         * 排序
         */
        ORDER,
        /**
         * =
         */
        EQ,
        /**
         * !=
         */
        NE,
        /**
         * >=
         */
        GE,
        /**
         * <=
         */
        LE,
        /**
         * 大于
         */
        GT,
        /**
         * 小于
         */
        LT,
        /**
         * 在...范围
         */
        IN,
        /**
         * 升序
         */
        ASC,
        /**
         * 降序
         */
        DESC,
        /**
         * 空
         */
        ISNULL,
        /**
         * 非空
         */
        ISNOTNULL,
        /**
         * HQL
         */
        SQL,
        /**
         * 投影
         */
        PROJECTIONS,
        /**
         * 分页
         */
        PAGE;
    }
    

}
