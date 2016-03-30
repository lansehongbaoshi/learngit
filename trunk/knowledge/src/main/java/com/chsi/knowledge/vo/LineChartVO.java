package com.chsi.knowledge.vo;

import java.util.List;

public class LineChartVO {
    private List<String> legend;
    private List<String> xAxis;
    private List<SeriesVO> series;
    
    public LineChartVO(List<String> legend, List<String> xAxis, List<SeriesVO> series) {
        this.legend = legend;
        this.xAxis = xAxis;
        this.series = series;
    }

    public List<String> getLegend() {
        return legend;
    }

    public void setLegend(List<String> legend) {
        this.legend = legend;
    }

    public List<String> getxAxis() {
        return xAxis;
    }

    public void setxAxis(List<String> xAxis) {
        this.xAxis = xAxis;
    }
    
    public List<SeriesVO> getSeries() {
        return series;
    }

    public void setSeries(List<SeriesVO> series) {
        this.series = series;
    }

}
