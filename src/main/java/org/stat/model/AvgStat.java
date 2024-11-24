package org.stat.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class AvgStat extends PanacheEntity{
    
    private double poid;
    private double value;
    private String label;
    
    @JsonGetter("label")
    String getLabel(){
        return this.label;
    };

    @JsonGetter("value")
    double getValue(){
        return this.value;
    }

    public AvgStat(){};

    public AvgStat(Double value, String label) {
        this.value = value;
        this.label = label;
        this.poid = 1d;
    }

    public double computeStat(double d) {
        this.value = ( (this.value)*(this.poid) + d ) / (this.poid + 1d);
        this.poid ++;

        return this.value;
    }
}
