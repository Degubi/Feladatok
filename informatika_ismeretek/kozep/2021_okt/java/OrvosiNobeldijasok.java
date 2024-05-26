import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class OrvosiNobeldijasok {

    public static void main(String[] args) throws IOException {
        var dijak = Files.lines(Path.of("orvosi_nobeldijak.txt"))
                         .skip(1)
                         .map(Nobeldij::new)
                         .toArray(Nobeldij[]::new);

        System.out.println("3. Feladat: Adatsorok száma: " + dijak.length);

        Arrays.stream(dijak)
              .mapToInt(k -> k.ev)
              .max()
              .ifPresent(k -> System.out.println("4. Feladat: Utolsó év: " + k));

        var orszagonkentiStat = Arrays.stream(dijak).collect(Collectors.groupingBy(k -> k.orszagkod));
        var bekertKod = System.console().readLine("5. Feladat: Írja be 1 ország kódját: ");
        var bekertOrszagbanKiosztottak = orszagonkentiStat.get(bekertKod);
        var kiirando = switch(bekertOrszagbanKiosztottak.size()) {
            case 0  -> "A megadott országból nem volt díjazott!";
            case 1  -> "Név: " + bekertOrszagbanKiosztottak.get(0).nev + ", év: " + bekertOrszagbanKiosztottak.get(0).ev;
            default -> "A megadott országból " + bekertOrszagbanKiosztottak.size() + " fő díjazott volt!";
        };

        System.out.println(kiirando);
        System.out.println("6. Feladat:");

        orszagonkentiStat.entrySet().stream()
                         .filter(k -> k.getValue().size() > 5)
                         .forEach(k -> System.out.println("    " + k.getKey() + " - " + k.getValue().size()));

        Arrays.stream(dijak)
              .mapToInt(k -> k.elethossz.elethosszEvekben())
              .filter(k -> k != -1)
              .average()
              .ifPresent(k -> System.out.printf("7. Feladat: Átlag: %.1f\n", k));
    }
}