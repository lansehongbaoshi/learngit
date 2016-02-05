package com.chsi.knowledge.dic;

/**
 * 知识所处状态
 * @author chenjian
 */
public enum KnowledgeStatus {

    YSH, WSH, YSC, CDSC; //已审核、未审核、已删除、彻底删除

    public String toString() {
        switch (this) {
        case YSH:
            return "已审核"; // 0
        case WSH:
            return "未审核"; // 1
        case YSC:
            return "已删除"; // 2
        case CDSC:
            return "彻底删除"; // 3
        }
        return super.toString();
    }

    public int getOrdinal() {
        return this.ordinal();
    }

    public static KnowledgeStatus getType(int ordinal) {
        switch (ordinal) {
        case 0:
            return KnowledgeStatus.YSH;
        case 1:
            return KnowledgeStatus.WSH;
        case 2:
            return KnowledgeStatus.YSC;
        case 3:
            return KnowledgeStatus.CDSC;
        default:
            throw new IllegalStateException("无此消息管理对象类型:" + ordinal);
        }
    }

    public String getToString() {
        return toString();
    }

}
