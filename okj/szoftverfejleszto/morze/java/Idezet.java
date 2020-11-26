import java.util.*;
import java.util.stream.*;

public class Idezet{
    public final String szerzo;
    public final String uzenet;

    public Idezet(String sor, Map<String, String> abc) {
        var split = sor.split(";");

        this.szerzo = morze2Szoveg(split[0], abc);
        this.uzenet = morze2Szoveg(split[1], abc);
    }

    public static String morze2Szoveg(String uzenet, Map<String, String> abc) {
        return Arrays.stream(uzenet.split("       "))
                     .map(k -> Arrays.stream(k.split("   "))
                                     .map(abc::get)
                                     .collect(Collectors.joining()))
                     .collect(Collectors.joining(" "));
    }
}