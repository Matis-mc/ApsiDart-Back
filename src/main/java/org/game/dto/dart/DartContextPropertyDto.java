package org.game.dto.dart;

import jakarta.annotation.Nullable;

public record DartContextPropertyDto(
    String idJoueur,
    String pseudo,
    String score,
    @Nullable String numeroTour,
    @Nullable String delta,
    @Nullable String volee,
    @Nullable String position
) {

    public DartContextPropertyDto(){
        this("", "", "", "", "", "", "");
    }
    
}
