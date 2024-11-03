package org.game.model;

import java.util.List;


public class Game {

    public String statut;
    public List<ClassementElement> classement;
    public TypeGame modeJeu;

    public List<ClassementElement> getClassement() {
        return classement;
    }

    public void setClassement(List<ClassementElement> classement) {
        this.classement = classement;
    }

    public TypeGame getModeJeu() {
        return modeJeu;
    }

    public void setModeJeu(TypeGame modeJeu) {
        this.modeJeu = modeJeu;
    }

}
