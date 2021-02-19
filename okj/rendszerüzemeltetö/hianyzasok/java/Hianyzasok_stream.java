import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Hianyzasok_stream {

    public static void main(String[] args) throws IOException {
        var hianyzasok = Files.lines(Path.of("szeptember.csv"))
                              .skip(1)
                              .map(Hianyzas::new)
                              .toArray(Hianyzas[]::new);

        var totalHianyzottOrak = Arrays.stream(hianyzasok)
                                       .mapToInt(k -> k.mulasztottOrak)
                                       .sum();

        System.out.println("2. Feladat: Hianyzott orak: " + totalHianyzottOrak);
        System.out.println("3. Feladat: Írj be egy napot(1-30) és egy nevet!");

        try(var input = new Scanner(System.in)) {
            var bekertNap = input.nextInt();
            input.nextLine();
            var bekertNev = input.nextLine();

            var bekertHianyzottE = Arrays.stream(hianyzasok).anyMatch(k -> k.nev.equals(bekertNev));

            System.out.println("4. Feladat: " + bekertNev + (bekertHianyzottE ? " hiányzott" : " nem hiányzott"));
            System.out.println("5. Feladat");

            var azonANaponHianyoztak = Arrays.stream(hianyzasok)
                                             .filter(k -> bekertNap >= k.elsoNap && bekertNap <= k.utolsoNap)
                                             .toArray(Hianyzas[]::new);

            if(azonANaponHianyoztak.length == 0) {
                System.out.println("Nem volt hiányzó");
            }else {
                Arrays.stream(azonANaponHianyoztak).forEach(hiany -> System.out.println(hiany.nev + " " + hiany.osztaly));
            }
        }

        var hianyzasokStat = Arrays.stream(hianyzasok)
                                   .collect(Collectors.groupingBy(k -> k.osztaly, Collectors.summingInt(k -> k.mulasztottOrak)))
                                   .entrySet().stream()
                                   .map(k -> k.getKey() + ";" + k.getValue())
                                   .collect(Collectors.toList());

        Files.write(Path.of("osszesites.csv"), hianyzasokStat);
    }
}