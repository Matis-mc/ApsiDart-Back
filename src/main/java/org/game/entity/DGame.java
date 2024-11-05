package org.game.entity;

import java.time.LocalDate;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "dartGame")
public class DGame extends PanacheEntity {

    public String type;

    public String statut;

    public LocalDate date;

    @OneToMany
    public List<DPerform> dPerform;


    
}