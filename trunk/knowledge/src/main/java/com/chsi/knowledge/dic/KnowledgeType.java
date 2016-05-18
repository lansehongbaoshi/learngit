package com.chsi.knowledge.dic;

/**
 * 知识类型
 * @author zhangzh
 */
public enum KnowledgeType {

    PRIVATE("PRIVATE"), PUBLIC("PUBLIC"); //内部、公开
    
    private String value;
    
    private KnowledgeType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }

    public static KnowledgeType getKnowledgeType(String type) throws IllegalArgumentException {
        if("PRIVATE".equals(type)) {
            return KnowledgeType.PRIVATE;
        } else if("PUBLIC".equals(type)) {
            return KnowledgeType.PUBLIC;
        } else {
            throw new IllegalArgumentException("知识类型有误");
        }
    }

}
