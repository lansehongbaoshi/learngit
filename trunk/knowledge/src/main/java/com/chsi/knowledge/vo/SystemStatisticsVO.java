package com.chsi.knowledge.vo;

import com.chsi.knowledge.pojo.SystemData;

/**
 * @author ShiKF
 *
 */
public class SystemStatisticsVO {
    public SystemData system;
    public String name;
    public float goodPercent;
    public float badPercent;
    public int goodNum;
    public int badNum;
    public SystemData getSystem() {
        return system;
    }
    public void setSystem(SystemData system) {
        this.system = system;
    }
    public float getGoodPercent() {
        return goodPercent;
    }
    public void setGoodPercent(float goodPercent) {
        this.goodPercent = goodPercent;
    }
    public float getBadPercent() {
        return badPercent;
    }
    public void setBadPercent(float badPercent) {
        this.badPercent = badPercent;
    }
    public int getGoodNum() {
        return goodNum;
    }
    public void setGoodNum(int goodNum) {
        this.goodNum = goodNum;
    }
    public int getBadNum() {
        return badNum;
    }
    public void setBadNum(int badNum) {
        this.badNum = badNum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
