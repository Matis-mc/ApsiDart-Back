package org.stat.model;

import java.io.Serializable;

public abstract class Statistique implements Serializable{

    private double value;
    private String label;
    
    String getLabel(){
        return this.label;
    };

    Double getValue(){
        return this.value;
    }

    public Statistique(double value, String label) {
        this.value = value;
        this.label = label;
    };    

}
