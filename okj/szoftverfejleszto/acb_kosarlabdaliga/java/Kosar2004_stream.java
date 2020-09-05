import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Kosar2004_stream {

    public static void main(String[] args) throws IOException {
        var eredmenyek = Files.lines(Path.of("eredmenyek.csv"), StandardCharsets.ISO_8859_1)
                              .skip(1)
                              .map(Eredmeny::new)
                              .toArray(Eredmeny[]::new);

        var hazaiMadridDb = Arrays.stream(eredmenyek)
                                  .filter(k -> k.hazaiCsapat.equals("Real Madrid"))
                                  .count();

        var idegenMadridDb = Arrays.stream(eredmenyek)
                                   .filter(k -> k.idegenCsapat.equals("Real Madrid"))
                                   .count();

        System.out.printf("3. Feladat: Hazai: %d, idegen: %d\n", hazaiMadridDb, idegenMadridDb);

        var volteDontetlen = Arrays.stream(eredmenyek)
                                   .anyMatch(k -> k.hazaiPont == k.idegenPont);

        System.out.println("4. Feladat: Volt e döntetlen: " + (volteDontetlen ? "igen" : "nem"));

        Arrays.stream(eredmenyek)
              .filter(k -> k.hazaiCsapat.contains("Barcelona") || k.idegenCsapat.contains("Barcelona"))
              .findFirst()
              .ifPresent(k -> System.out.println("5. Feladat: Barcelona csapat neve: " + (k.hazaiCsapat.contains("Barcelona") ? k.hazaiCsapat : k.idegenCsapat)));

        var hatosFeladatIdopont = LocalDate.of(2004, Month.NOVEMBER, 21);

        System.out.println("6. Feladat:");

        Arrays.stream(eredmenyek)
              .filter(k -> k.idopont.equals(hatosFeladatIdopont))
              .forEach(k -> System.out.printf("    %s - %s (%d:%d)\n", k.hazaiCsapat, k.idegenCsapat, k.hazaiPont, k.idegenPont));

        System.out.println("7. Feladat:");
        Arrays.stream(eredmenyek)
              .map(k -> k.helyszin)
              .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 20)
              .forEach(e -> System.out.println("    " + e.getKey() + ": " + e.getValue()));
    }
}