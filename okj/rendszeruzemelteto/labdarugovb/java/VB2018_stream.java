import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class VB2018_stream {

    public static void main(String[] args) throws IOException {
        var helyszinek = Files.lines(Path.of("vb2018.txt"), StandardCharsets.ISO_8859_1)
                              .skip(1)
                              .map(Helyszin::new)
                              .toArray(Helyszin[]::new);

        System.out.println("3. Feladat: Stadionok száma: " + helyszinek.length);

        Arrays.stream(helyszinek)
              .min(Comparator.comparingInt(k -> k.ferohely))
              .ifPresent(k -> System.out.println("4. Feladat: Varos: " + k.varos + ", nev1: " + k.nev1 + ", nev2: " + k.nev2 + ", ferohely: " + k.ferohely));

        Arrays.stream(helyszinek)
              .mapToInt(k -> k.ferohely)
              .average()
              .ifPresent(atlag -> System.out.printf("5. Feladat: Ferohelyek atlaga: %.1f\n", atlag));

        var nevesStadionDb = Arrays.stream(helyszinek)
                                   .filter(k -> !k.nev2.equals("n.a."))
                                   .count();

        System.out.println("6. Feladat: Alternativ neves stadionok: " + nevesStadionDb);
        System.out.println("7. Feladat:");

        var bekertNev = Stream.generate(() -> System.console().readLine("Kérem 1 város nevét: "))
                              .dropWhile(k -> k.length() < 3)
                              .findFirst()
                              .orElseThrow();

        Arrays.stream(helyszinek)
              .filter(k -> k.varos.equalsIgnoreCase(bekertNev))
              .findFirst()
              .ifPresentOrElse(k -> System.out.println("8. Feladat: Volt " + bekertNev + "-ban merkozes"),
                              () -> System.out.println("8. Feladat: Nem volt " + bekertNev + "-ban merkozes"));

        var varosokSzama = Arrays.stream(helyszinek)
                                 .map(k -> k.varos)
                                 .distinct()
                                 .count();

        System.out.println("9. Feladat: Varosok szama: " + varosokSzama);
    }
}