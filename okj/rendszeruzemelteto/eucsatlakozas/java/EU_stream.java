import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class EU_stream {

    public static void main(String[] args) throws IOException {
        var csatlakozasok = Files.lines(Path.of("EUcsatlakozas.txt"), StandardCharsets.ISO_8859_1)
                                 .map(Csatlakozas::new)
                                 .toArray(Csatlakozas[]::new);

        System.out.println("3. Feladat: 2018-ig EU államok száma: " + csatlakozasok.length);

        var csatlakozott2007 = Arrays.stream(csatlakozasok)
                                     .filter(k -> k.datum.getYear() == 2007)
                                     .count();

        System.out.println("4. Feladat: 2007-ben csatlakozott országok száma: " + csatlakozott2007);

        var magyarorszagCsat = Arrays.stream(csatlakozasok)
                                     .filter(k -> k.orszag.equals("Magyarország"))
                                     .findFirst()
                                     .orElseThrow();

        System.out.println("5. Feladat: Magyarország csatlakozása: " + magyarorszagCsat.datum);

        var voltEMajusban = Arrays.stream(csatlakozasok).anyMatch(k -> k.datum.getMonth() == Month.MAY);

        System.out.println("6. Feladat: " + (voltEMajusban ? "Volt" : "Nem volt") + " májusban csatlakozás");

        Arrays.stream(csatlakozasok)
              .max(Comparator.comparing(k -> k.datum))
              .ifPresent(k -> System.out.println("7. Feladat: Utoljára csatlakozott: " + k.orszag));

        System.out.println("8. Feladat:");

        Arrays.stream(csatlakozasok)
              .collect(Collectors.groupingBy(k -> k.datum.getYear(), Collectors.counting()))
              .forEach((ev, db) -> System.out.println(ev + " - " + db + " db ország"));
    }
}