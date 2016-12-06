package com.chsi.knowledge.dic;

/**
 * 评价类型
 * 
 * @author chenjian
 */
public enum DiscussStatus {

    UNUSEFUL, USEFUL;

    public String toString() {
        switch (this) {
        case USEFUL:
            return "认为该问题有用"; // 1
        case UNUSEFUL:
            return "认为该问题无用"; // 0
        }
        return super.toString();
    }

    public int getOrdinal() {
        return this.ordinal();
    }

    public static DiscussStatus getType(int ordinal) {
        switch (ordinal) {
        case 1:
            return DiscussStatus.USEFUL;
        case 0:
            return DiscussStatus.UNUSEFUL;
        default:
            throw new IllegalStateException("无此消息管理对象类型:" + ordinal);
        }
    }

    public String getToString() {
        return toString();
    }

}
