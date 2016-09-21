package com.chsi.knowledge.dao;

import java.util.List;

import com.chsi.knowledge.pojo.WeatherCodeData;

public interface WeatherCodeDataDAO {

    void save(List<WeatherCodeData> weatherCodeDatas);

    WeatherCodeData getWeatherCodeByName(String string);

}
