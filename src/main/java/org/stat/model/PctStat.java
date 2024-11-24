package org.stat.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class  PctStat extends PanacheEntity {
    
    private double poid;
    private double value;
    private String label;
    
    public PctStat(){};

    @JsonGetter("label")
    String getLabel(){
        return this.label;
    };

    @JsonGetter("value")
    Double getValue(){
        return this.value;
    }

    public PctStat(double value, String label) {
        this.value = value;
        this.label = label;
        this.poid = 1d;
    }

    public PctStat(boolean isVictory, String label) {
        this.value = isVictory?1d:0d;
        this.label = label;
        this.poid = 1d;
    }

    public double computeStat(double pct) {
        if(pct > 1){
            pct = pct / 100d;
        }
        this.value = ( (this.value)*(this.poid) + pct ) / (this.poid + 1d);
        this.poid ++;

        return this.value;
    }

}
