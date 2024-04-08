package co.istad.mobilebanking.util;

import java.util.Random;

public class RandomUtil {
    public static String generate9Digit(){
        Random random = new Random();
        int randomNumber = random.nextInt(1000000000);
        return String.format("%09d", randomNumber);
    }
}
