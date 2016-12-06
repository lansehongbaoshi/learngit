package com.chsi.knowledge.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.chsi.framework.pojos.PersistentObject;

@Entity
@Table(name = "WEATHERCODE")
@DynamicUpdate(value = true)
public class WeatherCodeData extends PersistentObject {

    /**
     * 
     */
    private static final long serialVersionUID = -7874406749124023937L;

    private String id;
    private String name;
    private String weatherCode;

    public WeatherCodeData() {
    }

    public WeatherCodeData(String id, String name, String weatherCode) {
        this.id = id;
        this.name = name;
        this.weatherCode = weatherCode;
    }

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "WEATHERCODE")
    public String getWeatherCode() {
        return weatherCode;
    }

    public void setWeatherCode(String weatherCode) {
        this.weatherCode = weatherCode;
    }

}
