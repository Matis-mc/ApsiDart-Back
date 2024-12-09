package org.game.dto.dart;

import jakarta.annotation.Nullable;

public record DartPerformDto(
    String idJoueur,
    String pseudo,
    String score,
    @Nullable String numeroTour,
    @Nullable String delta,
    @Nullable String volee,
    @Nullable String positionClassement
) {

    public DartPerformDto(){
        this("", "", "", "", "", "", "");
    }
    
}
