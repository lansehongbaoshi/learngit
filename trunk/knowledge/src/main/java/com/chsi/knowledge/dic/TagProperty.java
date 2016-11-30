package com.chsi.knowledge.dic;
/**
 * 评价类型
 * @author zhangzh
 */
public enum TagProperty {

    NULL, DEFAULT;

    public String toString() {
        switch (this) {
        case NULL:
            return "0"; //0
        case DEFAULT:
            return "1"; //1
        }
        return super.toString();
    }

    public int getOrdinal() {
        return this.ordinal();
    }

    public static TagProperty getType(int ordinal) {
        switch (ordinal) {
        case 0:
            return TagProperty.NULL;
        case 1:
            return TagProperty.DEFAULT;
        default:
            throw new IllegalStateException("无此类型:" + ordinal);
        }
    }
    
}
