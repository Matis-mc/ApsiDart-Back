package org.player.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "player")
public class Player extends PanacheEntity {

    public String firstName;
    public String lastName;
    public String pseudo;

    public String getFistName() {
        return firstName;
    }
    public void setFistName(String fistName) {
        this.firstName = fistName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }




    
}
