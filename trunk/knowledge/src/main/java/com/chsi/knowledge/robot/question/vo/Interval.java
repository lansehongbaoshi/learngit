package com.chsi.knowledge.robot.question.vo;

public class Interval {
    public String start;
    public String end;
    public String point;
    
    public Interval(){
        
    }
    
    public Interval(String start,String end,String point){
        this.start = start;
        this.end = end;
        this.point = point;
    }
    
    
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public String getEnd() {
        return end;
    }
    public void setEnd(String end) {
        this.end = end;
    }
    public String getPoint() {
        return point;
    }
    public void setPoint(String point) {
        this.point = point;
    }
    
    
}
