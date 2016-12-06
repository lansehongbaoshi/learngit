package com.chsi.knowledge.dic;

public enum IntentType {
    date, time, weather;

    public String toString() {
        switch (this) {
        case date:
            return "日期"; // 0
        case time:
            return "时间"; // 1
        case weather:
            return "天气"; // 2
        }
        return super.toString();
    }

    public static IntentType getType(int type) {
        switch (type) {
        case 0:
            return IntentType.date;
        case 1:
            return IntentType.time;
        case 2:
            return IntentType.weather;
        default:
            throw new IllegalStateException("无此消息管理对象类型:" + type);
        }
    }

}
