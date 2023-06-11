package lt.kitm.utils;

import lt.kitm.model.Vartotojas;

public class PrisijungesVartotojas {
    private static Vartotojas vartotojas;

    private PrisijungesVartotojas() {
        vartotojas = null;
    }

    public static void pridetiVartotoja(Vartotojas vartotojas1) {
        vartotojas = vartotojas1;
    }

    public static void pasalintiVartotoja() {
        vartotojas = null;
    }

    public static Vartotojas getVartotojas() {
        return vartotojas;
    }
}
