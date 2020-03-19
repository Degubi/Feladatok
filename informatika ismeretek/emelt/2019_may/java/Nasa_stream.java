import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nasa_stream {

    public static void main(String[] args) throws IOException {
        var keresek = Files.lines(Path.of("NASAlog.txt"))
                           .map(Keres::new)
                           .toArray(Keres[]::new);
        
        System.out.println("5. Feladat: Kérések száma: " + keresek.length);
        
        var osszmeret = Arrays.stream(keresek)
                              .mapToInt(Keres::byteMeret)
                              .sum();
        
        System.out.println("6. Feladat: Összméret: " + osszmeret + " byte");
        
        var domainesek = Arrays.stream(keresek)
                               .filter(Keres::domain)
                               .count();
        
        System.out.printf("8. Feladat: Domaines kérések: %.2f%%\n", ((float) domainesek) / keresek.length * 100);
        System.out.println("9. Feladat:");
        
        Arrays.stream(keresek)
              .map(k -> k.httpKod)
              .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
              .forEach((kod, db) -> System.out.println("    " + kod + ": " + db + " db"));
    }
}