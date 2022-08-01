import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class VizibicikliKolcsonzo_stream {

    public static void main(String[] args) throws IOException {
        var kolcsonzesek = Files.lines(Path.of("kolcsonzesek.txt"))
                                .skip(1)
                                .map(Kolcsonzes::new)
                                .toArray(Kolcsonzes[]::new);

        System.out.println("5. Feladat: Kölcsönzések száma: " + kolcsonzesek.length);

        try(var console = new Scanner(System.in)) {
            System.out.println("Írj be 1 nevet!");

            var bekertNev = console.nextLine();
            var bekertNevhezTartozoKolcsonzesek = Arrays.stream(kolcsonzesek)
                                                        .filter(k -> k.nev.equals(bekertNev))
                                                        .toArray(Kolcsonzes[]::new);

            var bekerthezKiirando = bekertNevhezTartozoKolcsonzesek.length == 0
                                    ? "Nem volt ilyen nevű kölcsönző"
                                    : Arrays.stream(bekertNevhezTartozoKolcsonzesek)
                                            .map(k -> k.elvitelIdopont + "-" + k.visszahozatalIdopont)
                                            .collect(Collectors.joining("\n"));

            System.out.println(bekerthezKiirando);
            System.out.println("Adj meg 1 időpontot! (óra:perc)");

            var bekertIdopontParts = console.nextLine().split(":");
            var bekertIdopont = LocalTime.of(Integer.parseInt(bekertIdopontParts[0]), Integer.parseInt(bekertIdopontParts[1]));

            System.out.println("7. Feladat:");

            Arrays.stream(kolcsonzesek)
                  .filter(k -> bekertIdopont.isAfter(k.elvitelIdopont) && bekertIdopont.isBefore(k.visszahozatalIdopont))
                  .forEach(k -> System.out.println("    " + k.elvitelIdopont + "-" + k.visszahozatalIdopont + ": " + k.nev));
        }

        var totalStartedHours = Arrays.stream(kolcsonzesek)
                                      .mapToLong(k -> Duration.between(k.elvitelIdopont, k.visszahozatalIdopont).toMinutes())
                                      .mapToInt(k -> (int) Math.ceil(k / 30.0))
                                      .sum();

        System.out.println("8. Feladat: A bevétel " + (totalStartedHours * 2400) + "Ft");

        var fileba = Arrays.stream(kolcsonzesek)
                           .filter(k -> k.jarmuAzonosito.equals("F"))
                           .map(k -> k.elvitelIdopont + "-" + k.visszahozatalIdopont + ": " + k.nev)
                           .collect(Collectors.toList());

        Files.write(Path.of("F.txt"), fileba);
        System.out.println("10. Feladat:");

        Arrays.stream(kolcsonzesek)
              .collect(Collectors.groupingBy(k -> k.jarmuAzonosito, Collectors.counting()))
              .entrySet().stream()
              .sorted(Comparator.comparing(k -> k.getKey()))
              .forEach(k -> System.out.println("    " + k.getKey() + " - " + k.getValue()));
    }
}