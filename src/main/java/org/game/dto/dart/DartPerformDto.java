package org.game.dto.dart;

import jakarta.annotation.Nullable;

public class DartPerformDto {
    String idJoueur;
    String pseudo;
    String score;
    Integer historiquePositionJeu;
    @Nullable Double elo;
    @Nullable String numeroTour;
    @Nullable String delta;
    @Nullable String volee;
    @Nullable Integer positionDepart;

    

    public DartPerformDto(String idJoueur, String pseudo, String score, Double elo, Integer historiquePositionJeu, String numeroTour, String delta,
            String volee, Integer positionDepart) {
        this.idJoueur = idJoueur;
        this.pseudo = pseudo;
        this.score = score;
        this.elo = elo;
        this.historiquePositionJeu = historiquePositionJeu;
        this.numeroTour = numeroTour;
        this.delta = delta;
        this.volee = volee;
        this.positionDepart = positionDepart;
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

    public Integer getPositionDepart() {
        return positionDepart;
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
    public void setPositionDepart(Integer positionClassement) {
        this.positionDepart = positionClassement;
    }

    @Override
    public String toString() {
        return "DartPerformDto [idJoueur=" + idJoueur + ", pseudo=" + pseudo + ", score=" + score + ", elo=" + elo
                + ", volee=" + volee + ", positionClassement=" + positionDepart + "]";
    }    

    
    
}
