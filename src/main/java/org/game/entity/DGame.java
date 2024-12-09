package org.game.entity;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "dartGame")
public class DGame extends PanacheEntity {

    public String type;

    public String statut;

    public LocalDate date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    public List<DPerform> dPerform;
    
    public static List<DGame> getAll(){
       return DGame.find("SELECT g FROM DGame g LEFT JOIN FETCH g.dPerform").list();
    }

    @Override
    public String toString() {
        return "DGame [type=" + type + ", statut=" + statut + ", date=" + date + ", dPerform=" + dPerform + "]";
    }

    
}
