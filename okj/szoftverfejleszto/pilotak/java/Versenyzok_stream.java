import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Versenyzok_stream {

    public static void main(String[] args) throws IOException {
        var pilotak = Files.lines(Path.of("pilotak.csv"))
                           .skip(1)
                           .map(Pilota::new)
                           .toArray(Pilota[]::new);

        System.out.println("3. Feladat: Adatsorok száma: " + pilotak.length);
        System.out.println("4. Feladat: Utolsó pilóta neve: " + pilotak[pilotak.length - 1].nev);
        System.out.println("5. Feladat:");

        Arrays.stream(pilotak)
              .filter(k -> k.szuletesiDatum.getYear() >= 1800 && k.szuletesiDatum.getYear() <= 1900)
              .forEach(k -> System.out.println("    " + k.nev + " (" + k.szuletesiDatum + ")"));

        Arrays.stream(pilotak)
              .filter(k -> k.rajtszam != Pilota.URES_RAJTSZAM)
              .min(Comparator.comparingInt(k -> k.rajtszam))
              .ifPresent(k -> System.out.println("6. Feladat: " + k.nemzetiseg));

        System.out.print("7. Feladat: ");

        Arrays.stream(pilotak)
              .filter(k -> k.rajtszam != Pilota.URES_RAJTSZAM)
              .collect(Collectors.groupingBy(k -> k.rajtszam, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 1)
              .forEach(k -> System.out.print(k.getKey() + " "));
    }
}