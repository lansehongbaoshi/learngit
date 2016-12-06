package com.chsi.knowledge.dic;

/**
 * 评价类型
 * 
 * @author zhangzh
 */
public enum SystemProperty {

    PUBLIC, PRIVATE;

    public String toString() {
        switch (this) {
        case PUBLIC:
            return "0"; // 0
        case PRIVATE:
            return "1"; // 1
        }
        return super.toString();
    }

    public int getOrdinal() {
        return this.ordinal();
    }

    public static SystemProperty getType(int ordinal) {
        switch (ordinal) {
        case 0:
            return SystemProperty.PUBLIC;
        case 1:
            return SystemProperty.PRIVATE;
        default:
            throw new IllegalStateException("无此类型:" + ordinal);
        }
    }

}
