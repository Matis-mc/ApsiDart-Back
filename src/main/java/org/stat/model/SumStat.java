package org.stat.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class SumStat extends PanacheEntity {

    private double value;
    private String label;

    public SumStat(){};
    
    @JsonGetter("label")
    String getLabel(){
        return this.label;
    };

    @JsonGetter("value")
    Double getValue(){
        return this.value;
    }

    public SumStat(double value, String label) {
        this.value = value;
        this.label = label;
    }

    public double computeStat(double d) {
        this.value += d;
        return this.value;
    }
 

}
