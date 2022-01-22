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

        System.out.println("5. Feladat: Írja be 1 ország kódját!");
        try(var input = new Scanner(System.in)) {
            var bekertKod = input.nextLine();
            var bekertOrszagbanKiosztottak = orszagonkentiStat.get(bekertKod);
            var kiosztasokSzama = bekertOrszagbanKiosztottak.size();

            var kiirando = kiosztasokSzama == 0 ? "A megadott országból nem volt díjazott!" :
                           kiosztasokSzama == 1 ? "Név: " + bekertOrszagbanKiosztottak.get(0).nev + ", év: " + bekertOrszagbanKiosztottak.get(0).ev
                                                : "A megadott országból " + kiosztasokSzama + " fő díjazott volt!";
            System.out.println(kiirando);
        }

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