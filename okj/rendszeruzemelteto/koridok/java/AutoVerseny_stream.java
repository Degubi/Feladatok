import java.io.*;
import java.nio.file.*;
import java.util.*;

public class AutoVerseny_stream {

    public static void main(String[] args) throws IOException {
        var versenyek = Files.lines(Path.of("autoverseny.csv"))
                             .skip(1)
                             .map(Verseny::new)
                             .toArray(Verseny[]::new);

        System.out.println("3. Feladat: Adatsorok száma: " + versenyek.length);

        Arrays.stream(versenyek)
              .filter(k -> k.versenyzo.equals("Fürge Ferenc"))
              .filter(k -> k.palya.equals("Gran Prix Circuit"))
              .filter(k -> k.kor == 3)
              .findFirst()
              .ifPresent(k -> System.out.println("4. Feladat: " + k.korido.toSecondOfDay() + " mp"));

        System.out.println("5. Felatad: Írj be egy nevet!");

        try(var input = new Scanner(System.in)) {
            var beNev = input.nextLine();

            Arrays.stream(versenyek)
                  .filter(k -> k.versenyzo.equals(beNev))
                  .min(Comparator.comparing(k -> k.korido))
                  .ifPresentOrElse(k -> System.out.println("6. Feladat: " + k.korido), () -> System.out.println("6. Feladat: Nincs ilyen versenyző"));
        }
    }
}