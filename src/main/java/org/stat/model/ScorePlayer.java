package org.stat.model;

public class ScorePlayer {
    public Long idPlayer;
    public Integer score;

    public ScorePlayer(Long idPlayer, Integer score) {
        this.idPlayer = idPlayer;
        this.score = score;
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    
}


