package com.ozansoyak.mr_ct_appointment_system.util;
import java.util.Random;

public class ReservationCodeGenerator {

    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int CODE_LENGTH = 6;

    private ReservationCodeGenerator(){}

    public static String generateReservationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < 2; i++) {
            char letter = ALPHABET.charAt(random.nextInt(ALPHABET.length()));
            code.append(letter);
        }

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            code.append(digit);
        }

        return code.toString();
    }
}
