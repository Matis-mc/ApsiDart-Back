package org.common;

public class Constant {

    public static class Game {
        public static final String STATUT_IN_PROGRESS = "IN_PROGRESS";
        public static final String STATUT_CANCEL = "CANCEL";
        public static final String STATUT_COMPLETED = "COMPLETED";
        public static final String DOUBLE_PREFIX = "D";
        public static final String TRIPLE_PREFIX = "T";
    }

    public static class Cricket {
        public static final Integer NOMBRE_HIT_TO_CLOSE_ZONE = 3;
    }

    public static class Stat{
        public static final Double INITIAL_ELO = 1000d;
    }

    public static class Prompt{
        public static final String DART_COMMENTATEUR_INIT = "Tu es un commentateur francais d'une partie de fléchettes, en mode Cricket.";
        public static final String DART_COMMENTAIRE_CONTEXT = "Tu  dois commenter en maximum 50 mots, de façon sensationnelle le tour qui vient d'être joué : {question}";
    }

}
