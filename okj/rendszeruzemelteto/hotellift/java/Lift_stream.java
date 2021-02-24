import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Lift_stream {
    public static void main(String[] args) throws IOException {
        var hasznalatok = Files.lines(Path.of("lift.txt"))
                               .map(Hasznalat::new)
                               .toArray(Hasznalat[]::new);

        System.out.println("3. Feladat: Lift alkalmak száma: " + hasznalatok.length);
        System.out.println("4. Feladat: A korszak " + hasznalatok[0].idopont + " től " + hasznalatok[hasznalatok.length - 1].idopont + "-ig tartott");

        Arrays.stream(hasznalatok)
              .max(Comparator.comparingInt(k -> k.celSzint))
              .ifPresent(k -> System.out.println("5. Feladat: Max célszint: " + k.celSzint));

        System.out.println("6. Feladat: Írj be egy kártyaszámot és egy célszintet");
        try(var input = new Scanner(System.in)){
            var beKartya = parseOrDefault(input.nextLine(), 5);
            var beCelszint = parseOrDefault(input.nextLine(), 5);
            var utaztakE = Arrays.stream(hasznalatok)
                                 .anyMatch(k -> k.kartyaSorszam == beKartya && k.celSzint == beCelszint);

            System.out.println("7. Feladat: A " + beKartya + " kártyával" + (utaztakE ? "" : " nem") + " utaztak a " + beCelszint + ". emeletre");
        }

        System.out.println("8. Feladat");
        Arrays.stream(hasznalatok)
              .collect(Collectors.groupingBy(k -> k.idopont, Collectors.counting()))
              .forEach((ido, db) -> System.out.println(ido + " - " + db + "x"));
    }

    public static int parseOrDefault(String num, int defaultVal) {
        try {
            return Integer.parseInt(num);
        }catch(NumberFormatException e) {
            return defaultVal;
        }
    }
}