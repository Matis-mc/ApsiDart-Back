package org.game.dto.dart;

import jakarta.annotation.Nullable;

public class DartPerformDto {
    String idJoueur;
    String pseudo;
    String score;
    @Nullable Double elo;
    @Nullable String numeroTour;
    @Nullable String delta;
    @Nullable String volee;
    @Nullable String positionClassement;

    

    public DartPerformDto(String idJoueur, String pseudo, String score, Double elo, String numeroTour, String delta,
            String volee, String positionClassement) {
        this.idJoueur = idJoueur;
        this.pseudo = pseudo;
        this.score = score;
        this.elo = elo;
        this.numeroTour = numeroTour;
        this.delta = delta;
        this.volee = volee;
        this.positionClassement = positionClassement;
    }

    public DartPerformDto() {
    }

    public String getIdJoueur() {
        return idJoueur;
    }
    public String getPseudo() {
        return pseudo;
    }
    public String getScore() {
        return score;
    }
    public Double getElo() {
        return elo;
    }
    public String getNumeroTour() {
        return numeroTour;
    }
    public String getDelta() {
        return delta;
    }
    public String getVolee() {
        return volee;
    }

    public String getPositionClassement() {
        return positionClassement;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public void setScore(String score) {
        this.score = score;
    }
    public void setElo(Double elo) {
        this.elo = elo;
    }
    public void setNumeroTour(String numeroTour) {
        this.numeroTour = numeroTour;
    }
    public void setDelta(String delta) {
        this.delta = delta;
    }
    public void setVolee(String volee) {
        this.volee = volee;
    }
    public void setPositionClassement(String positionClassement) {
        this.positionClassement = positionClassement;
    }    
    
}
