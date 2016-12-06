package com.chsi.knowledge.robot.question.vo;

import java.util.HashMap;
import java.util.Map;

public class Time {
    /*
     * less 时间区间 某个时间点之前 greater 时间区间 某个时间点之后 equal 时间点 某个时间点 around 时间区间
     * 某个时间点左右 interval 时间区间 两个时间点之间
     */
    static String[] type_def = { "less", "greater", "equal", "around", "interval" };
    public Map<String, Interval> times = new HashMap<String, Interval>();
    public Map<String, Integer> dateTypes = new HashMap<String, Integer>();
    public Integer dateType;

    public Time() {
        times.put("早晨", new Interval("06:00", "08:00", "07:00"));
        times.put("早上", new Interval("06:00", "08:00", "07:00"));
        times.put("清晨", new Interval("06:00", "08:00", "07:00"));

        times.put("上午", new Interval("08:00", "12:00", "10:00"));

        times.put("中午", new Interval("11:00", "13:00", "12:00"));
        times.put("晌午", new Interval("11:00", "13:00", "12:00"));

        times.put("晚上", new Interval("18:00", "24:00", "21:00"));
        times.put("傍晚", new Interval("18:00", "24:00", "21:00"));

        times.put("凌晨", new Interval("00:00", "05:00", "03:00"));

        times.put("半夜", new Interval("23:00", "01:00", "00:00"));
        times.put("深夜", new Interval("23:00", "01:00", "00:00"));
        times.put("午夜", new Interval("23:00", "01:00", "00:00"));

        dateTypes.put("大前天", -3);
        dateTypes.put("前天", -2);
        dateTypes.put("昨天", -1);
        dateTypes.put("今天", 0);
        dateTypes.put("明天", 1);
        dateTypes.put("后天", 2);
        dateTypes.put("大后天", 3);

    }

    /* 上表中定义的5种取值 */
    String type;
    /* query中的原始描述 */
    String raw;
    /* 归一化后的时间描述 注：norm是一个数组，其中的每个元素都是一个日期时间，格式为：yyyy-mm-dd hh:mm:ss */
    String[] norm;
    /* 0正常， 1超长范围错误, 2未知错误 */
    String error_code;
    /* 1相对时间, 0绝对时间 */
    String relative_mode;

    public Map<String, Interval> getTimes() {
        return times;
    }

    public void setTimes(Map<String, Interval> times) {
        this.times = times;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String[] getNorm() {
        return norm;
    }

    public void setNorm(String[] norm) {
        this.norm = norm;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getRelative_mode() {
        return relative_mode;
    }

    public void setRelative_mode(String relative_mode) {
        this.relative_mode = relative_mode;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

}
