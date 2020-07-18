import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FifaVilagRanglista_stream {
    
    public static void main(String[] args) throws IOException {
        var eredmenyek = Files.lines(Path.of("fifa.txt"), StandardCharsets.ISO_8859_1)
                              .skip(1)
                              .map(Eredmeny::new)
                              .toArray(Eredmeny[]::new);
        
        System.out.println("3. Feladat: Csapatok száma: " + eredmenyek.length);
        
        Arrays.stream(eredmenyek)
              .mapToInt(k -> k.pontszam)
              .average()
              .ifPresent(k -> System.out.printf("4. Feladat: Átlagpontszám: %.2f\n", k));
        
        Arrays.stream(eredmenyek)
              .max(Comparator.comparingInt(k -> k.valtozas))
              .ifPresent(k -> System.out.println("5. Feladat: Legtöbbet javító csapat: " + k.csapat +
                                                 ", helyezés: " + k.helyezes + ", pontszam: " + k.pontszam));
        
        var moKiirando = Arrays.stream(eredmenyek)
                               .filter(k -> k.csapat.equals("Magyarország"))
                               .findFirst()
                               .map(k -> "6. Feladat: Csapatok között van Magyarország")
                               .orElse("6. Feladat: Csapatok között nincs Magyarország");
        
        System.out.println(moKiirando);
        System.out.println("7. Feladat:");
        
        Arrays.stream(eredmenyek)
              .collect(Collectors.groupingBy(k -> k.valtozas, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 1)
              .forEach(e -> System.out.println("    " + e.getKey() + " helyet változott: " + e.getValue() + " csapat"));
    }
}