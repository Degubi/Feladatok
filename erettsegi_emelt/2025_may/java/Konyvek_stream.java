import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Konyvek_stream {

    public static void main(String[] args) throws IOException {
        var books = Files.lines(Path.of("kiadas.txt"))
                         .map(k -> k.split(";"))
                         .map(k -> new Book(Integer.parseInt(k[0]), Integer.parseInt(k[1]), k[2], k[3], Integer.parseInt(k[4])))
                         .toArray(Book[]::new);

        var inputWriterName = System.console().readLine("2. Feladat: Írd be egy szerző nevét! ");
        var inputWriterNameCount = Arrays.stream(books)
                                         .filter(k -> k.description.contains(inputWriterName))
                                         .count();

        System.out.println(inputWriterNameCount == 0 ? "Nem adtak ki." : inputWriterNameCount + " alkalommal adtak ki.");

        var maxQuantitySold = Arrays.stream(books)
                                    .collect(Collectors.groupingBy(Book::quantitySold, TreeMap::new, Collectors.counting()))
                                    .lastEntry();

        System.out.println("3. Feladat: Legtöbb eladott mennyiség: " + maxQuantitySold.getKey() + ", előfordulások száma: " + maxQuantitySold.getValue());

        Arrays.stream(books)
              .filter(k -> k.origin.equals("kf") && k.quantitySold >= 40_000)
              .findFirst()
              .ifPresent(k -> System.out.println("4. Feladat: " + k.year + "/" + k.quarter + ". " + k.description));

        System.out.printf("5. Feladat:\n%5s%20s%20s%20s%20s\n", "Év", "Magyar kiadás", "Magyar példányszám", "Külföldi kiadás", "Külföldi példányszám");

        var stats = Arrays.stream(books)
                          .collect(Collectors.groupingBy(Book::year, Collectors.groupingBy(Book::origin, Collectors.teeing(Collectors.counting(), Collectors.summingInt(Book::quantitySold), Task5Stat::new))));

        stats.forEach((year, stat) -> {
            var maStat = stat.get("ma");
            var kfStat = stat.get("kf");

            System.out.printf("%5s%20s%20s%20s%20s\n", year, maStat.totalCount, maStat.totalQuantitySold, kfStat.totalCount, kfStat.totalQuantitySold);
        });

        var htmlTablePre = "<table><tr><th>Év</th><th>Magyar kiadás</th><th>Magyar példányszám</th><th>Külföldikiadás</th><th>Külföldi példányszám</th></tr>";
        var htmlTablePost = "</table>";
        var htmlTableContent = stats.entrySet().stream()
                                    .map(k -> {
                                        var maStat = k.getValue().get("ma");
                                        var kfStat = k.getValue().get("kf");

                                        return "<tr>" + td(k.getKey()) + td(maStat.totalCount) + td(maStat.totalQuantitySold) + td(kfStat.totalCount) + td(kfStat.totalQuantitySold) + "</tr>";
                                    })
                                    .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("tabla.html"), htmlTablePre + htmlTableContent + htmlTablePost);

        System.out.println("6. Feladat:");

        Arrays.stream(books)
              .collect(Collectors.groupingBy(Book::description, Collectors.mapping(Book::quantitySold, Collectors.toList())))
              .entrySet().stream()
              .filter(k -> {
                  var quantities = k.getValue();

                  return quantities.stream()
                                   .filter(q -> q > quantities.get(0))
                                   .count() >= 2;
              })
             .forEach(k -> System.out.println(k.getKey()));
    }

    static String td(Object value) {
        return "<td>" + value + "</td>";
    }

    record Book(int year, int quarter, String origin, String description, int quantitySold) {}
    record Task5Stat(long totalCount, int totalQuantitySold) {}
}
