import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Hianyzasok_stream {

    public static void main(String[] args) throws IOException {
        var splitLines = Files.lines(Path.of("naplo.txt"))
                              .map(k -> k.split(" "))
                              .toArray(String[][]::new);

        var napSorIndexek = IntStream.range(0, splitLines.length)
                                     .filter(i -> splitLines[i][0].equals("#"))
                                     .toArray();

        var hianyzasok = IntStream.range(0, napSorIndexek.length)
                                  .boxed()
                                  .flatMap(i -> collectHianyzasokBetweenDays(splitLines, napSorIndexek, i))
                                  .toArray(Hianyzas[]::new);

        System.out.println("2. Feladat: Hiányzások száma: " + hianyzasok.length);

        var igazoltak = Arrays.stream(hianyzasok)
                              .map(k -> k.orak)
                              .mapToLong(k -> k.chars().filter(l -> l == 'X').count())
                              .sum();

        var igazolatlanok = Arrays.stream(hianyzasok)
                                  .map(k -> k.orak)
                                  .mapToLong(k -> k.chars().filter(l -> l == 'I').count())
                                  .sum();

        System.out.println("3. Feladat: Igazolt hiányzások: " + igazoltak + ", igazolatlanok: " + igazolatlanok);
        System.out.println("5. Feladat:");

        var beHonap = Integer.parseInt(System.console().readLine("Írj be egy hónap számot: "));
        var beNap = Integer.parseInt(System.console().readLine("Írj be egy nap számot: "));

        System.out.println("Azon a napon: " + hetnapja(beHonap, beNap) + " volt");
        System.out.println("6. Feladat:");

        var beTanNap = System.console().readLine("Írj be egy nap nevet! ");
        var beOraszam = Integer.parseInt(System.console().readLine("Írj be egy óraszámot! ")) - 1;
        var hianyzasokSzama = Arrays.stream(hianyzasok)
                                    .filter(k -> beTanNap.equals(hetnapja(k.honap, k.nap)))
                                    .map(k -> k.orak.charAt(beOraszam))
                                    .filter(k -> k == 'X' || k == 'I')
                                    .count();

        System.out.println("Ekkor " + hianyzasokSzama + "-an hiányoztak");
        System.out.println("7. Feladat: ");

        var hianyzasMap = Arrays.stream(hianyzasok)
                                .collect(Collectors.groupingBy(k -> k.nev,
                                         Collectors.summingLong(k -> k.orak.chars().filter(l -> l == 'X' || l == 'I').count())));

        var legtobbHianyzas = hianyzasMap.values().stream()
                                         .mapToLong(k -> k)
                                         .max()
                                         .orElseThrow();
        hianyzasMap.entrySet().stream()
                   .filter(k -> k.getValue() == legtobbHianyzas)
                   .forEach(k -> System.out.print(k.getKey() + ' '));
    }


    public static final String[] napnev = { "vasarnap", "hetfo", "kedd", "szerda", "csutortok", "pentek", "szombat" };
    public static final int[] napszam = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 335 };

    public static String hetnapja(int honap, int nap) {
        return napnev[(napszam[honap - 1] + nap) % 7];
    }

    public static Stream<Hianyzas> collectHianyzasokBetweenDays(String[][] splitLines, int[] napSorIndexek, int napSorIndexIndex) {
        var napKezdetIndex = napSorIndexek[napSorIndexIndex];
        var nextNapSorIndex = napSorIndexIndex + 1;
        var napZaroIndex = nextNapSorIndex < napSorIndexek.length ? napSorIndexek[napSorIndexIndex + 1] : splitLines.length;
        var napAdat = splitLines[napKezdetIndex];
        var honap = Integer.parseInt(napAdat[1]);
        var nap = Integer.parseInt(napAdat[2]);
        return IntStream.range(napKezdetIndex + 1, napZaroIndex)
                         .mapToObj(k -> splitLines[k])
                         .map(k -> new Hianyzas(k[0] + ' ' + k[1], k[2], honap, nap));
    }
}