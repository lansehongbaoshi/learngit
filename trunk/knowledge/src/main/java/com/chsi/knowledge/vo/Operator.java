package com.chsi.knowledge.vo;

public class Operator {

    private String id;
    private String name;
    
    public Operator(){
        super();
    }
    public Operator(String id,String name){
        this.id = id;
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    
}
