import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Toto {

    public static void main(String[] args) throws IOException {
        var fordulok = Files.lines(Path.of("toto.txt"))
                            .skip(1)
                            .map(Fordulo::new)
                            .toArray(Fordulo[]::new);

        System.out.println("3. Feladat: Fordulók száma: " + fordulok.length);

        var telitalalatosDb = Arrays.stream(fordulok)
                                    .mapToInt(k -> k.telitalalatosSzelvenyDbSzam)
                                    .sum();

        System.out.println("4. Feladat: Telitalálatos szelvények száma: " + telitalalatosDb);

        var osszNyeremeny = Arrays.stream(fordulok)
                                  .mapToDouble(k -> k.telitalalatosSzelvenyDbSzam * k.telitalalatosSzelvenyNyeremenye)
                                  .sum();

        System.out.printf("5. Feladat: Átlag nyeremény: %d\n", Math.round(osszNyeremeny / telitalalatosDb));

        var legkisebbTeliFizetett = Arrays.stream(fordulok)
                                          .min(Comparator.comparingInt(k -> k.telitalalatosSzelvenyNyeremenye))
                                          .orElseThrow();

        var legnagyobbTeliFizetett = Arrays.stream(fordulok)
                                           .max(Comparator.comparingInt(k -> k.telitalalatosSzelvenyNyeremenye))
                                           .orElseThrow();

        System.out.println("6. Feladat:");
        System.out.println("    Legnagyobb:" + legnagyobbTeliFizetett);
        System.out.println("    Legkisebb:" + legkisebbTeliFizetett);

        var voltEDontetlen = Arrays.stream(fordulok)
                                   .anyMatch(k -> !new EredmenyElemzo(k.eredmenyek).nemvoltDontetlenMerkozes());

        System.out.println("8. Feladat: " + (voltEDontetlen ? "Volt döntetlen" : "Nem volt döntetlen"));
    }
}