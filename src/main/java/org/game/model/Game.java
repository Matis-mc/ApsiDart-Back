package org.game.model;

import java.util.List;

import org.player.entity.Player;


public class Game {

    public List<ClassementElement> classement;
    public List<Player> participants;
    public TypeGame modeJeu;

    public List<ClassementElement> getClassement() {
        return classement;
    }

    public void setClassement(List<ClassementElement> classement) {
        this.classement = classement;
    }

    public List<Player> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Player> participants) {
        this.participants = participants;
    }

    public TypeGame getModeJeu() {
        return modeJeu;
    }

    public void setModeJeu(TypeGame modeJeu) {
        this.modeJeu = modeJeu;
    }

}
