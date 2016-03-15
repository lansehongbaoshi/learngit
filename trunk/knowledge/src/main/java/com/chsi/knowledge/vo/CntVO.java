package com.chsi.knowledge.vo;

public class CntVO implements Comparable<CntVO>{
    private String systemId;
    private String id;
    private int cnt;
    public String getSystemId() {
        return systemId;
    }
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getCnt() {
        return cnt;
    }
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
    
    @Override
    public boolean equals(Object obj) {
        CntVO vo = (CntVO)obj;
        return this.getId().equals(vo.getId());
    }
    
    @Override
    public int compareTo(CntVO o) {
        return this.cnt - o.getCnt();
    }
}
