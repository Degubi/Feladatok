import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Otszaz_stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("penztar.txt"));
        var vasarlasKezdoZaroIndexek = IntStream.range(-1, lines.size())
                                                .filter(i -> i == -1 || lines.get(i).equals("F"))
                                                .toArray();

        var vasarlasok = IntStream.range(0, vasarlasKezdoZaroIndexek.length - 1)
                                  .mapToObj(i -> lines.subList(vasarlasKezdoZaroIndexek[i] + 1, vasarlasKezdoZaroIndexek[i + 1]))
                                  .map(k -> k.stream().collect(Collectors.groupingBy(n -> n, Collectors.counting())))
                                  .collect(Collectors.toList());

        System.out.println("2. Feladat: Vásárlások száma: " + vasarlasok.size());
        System.out.println("3. Feladat: Elsö vásárlásnál vett dolgok száma: " + vasarlasok.get(0).size());
        System.out.println("4. Feladat:");

        var bekertSorszam = Integer.parseInt(System.console().readLine("Írj be 1 sorszámot: "));
        var bekertAru = System.console().readLine("Írj be 1 árut: ");
        var bekertDBszam = Integer.parseInt(System.console().readLine("Írj be 1 mennyiséget: "));

        System.out.println("5. Feladat:");

        IntStream.range(0, vasarlasok.size())
                 .filter(i -> vasarlasok.get(i).containsKey(bekertAru))
                 .findFirst()
                 .ifPresent(i -> System.out.println("Először a " + (i + 1) + ". vásárlásnál vettek " + bekertAru + "-t"));

        IntStream.iterate(vasarlasok.size() - 1, i -> i >= 0, i -> i - 1)
                 .filter(i -> vasarlasok.get(i).containsKey(bekertAru))
                 .findFirst()
                 .ifPresent(i -> System.out.println("Utoljára a " + (i + 1) + ". vásárlásnál vettek " + bekertAru + "-t"));

        var bekertAruOsszDbSzam = vasarlasok.stream()
                                            .mapToInt(k -> k.getOrDefault(bekertAru, 0L).intValue())
                                            .sum();

        System.out.println("Összesen " + bekertAruOsszDbSzam + "-szor vettek " + bekertAru + "-t");
        System.out.println("6. Feladat: " + bekertDBszam + " db esetén a fizetendő: " + ertek(bekertDBszam));
        System.out.println("7. Feladat: A " + bekertSorszam + ". vásárláskor vásárolt dolgok: ");

        vasarlasok.get(bekertSorszam - 1)
                  .forEach((item, db) -> System.out.println(item + "-ből: " + db + " db"));

        var fileba = IntStream.range(0, vasarlasok.size())
                              .mapToObj(i -> (i + 1) + ": " + calculateVasarlasOsszertek(vasarlasok.get(i)))
                              .collect(Collectors.toList());

        Files.write(Path.of("osszeg.txt"), fileba);
    }

    public static int calculateVasarlasOsszertek(Map<String, Long> vasarlasItemek) {
        return vasarlasItemek.values().stream()
                             .mapToInt(k -> ertek(k.intValue()))
                             .sum();
    }

    public static int ertek(int dbSzam) {
        return dbSzam == 1 ? 500 : dbSzam == 2 ? 950 : 1350 + (400 * (dbSzam - 3));
    }
}