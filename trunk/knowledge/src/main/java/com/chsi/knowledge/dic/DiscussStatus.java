package com.chsi.knowledge.dic;


/**
 * 评价类型
 * @author chenjian
 * 
 */
public enum DiscussStatus {

    GOOD, GENERAL, BAD;

    public String toString() {
        switch (this) {
        case GOOD:
            return "很满意"; // 1
        case GENERAL:
            return "一般"; // 2
        case BAD:
            return "不满意"; // 3
        }
        return super.toString();
    }

    public int getOrdinal() {
        return this.ordinal();
    }

    public static DiscussStatus getType(int ordinal) {
        switch (ordinal) {
        case 1:
            return DiscussStatus.GOOD;
        case 2:
            return DiscussStatus.GENERAL;
        case 3:
            return DiscussStatus.BAD;
        default:
            throw new IllegalStateException("无此消息管理对象类型:" + ordinal);
        }
    }

    public String getToString() {
        return toString();
    }
    
}
