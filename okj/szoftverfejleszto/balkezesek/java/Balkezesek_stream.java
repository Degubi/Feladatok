import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Balkezesek_stream {

    public static void main(String[] args) throws IOException {
        var jatekosok = Files.lines(Path.of("balkezesek.csv"), StandardCharsets.ISO_8859_1)
                             .skip(1)
                             .map(Jatekos::new)
                             .toArray(Jatekos[]::new);
        
        System.out.println("3. Feladat: Adatsorok száma: " + jatekosok.length);
        System.out.println("4. Feladat:");
        
        Arrays.stream(jatekosok)
              .filter(k -> k.befejezes.getYear() == 1999 && k.befejezes.getMonth() == Month.OCTOBER)
              .forEach(k -> System.out.printf("   %s, %.1f cm\n", k.nev, (k.magassag * 2.54D)));
        
        System.out.println("5. Feladat: Írj be 1 évszámot! (1990 <= évszám <= 1999)");
        try(var console = new Scanner(System.in)){
            var bekertEvszam = IntStream.generate(console::nextInt)
                                        .dropWhile(k -> {
                                            var drop = k < 1990 || k > 1999;
                                            if(drop) {
                                                System.out.println("Hibás adat, kérek egy 1990 és 1999 közötti évszámot");
                                            }
                                            return drop;
                                        })
                                        .findFirst()
                                        .orElse(1995);
            
            Arrays.stream(jatekosok)
                  .filter(k -> k.kezdes.getYear() == bekertEvszam)
                  .mapToInt(k -> k.suly)
                  .average()
                  .ifPresent(k -> System.out.printf("6. Feladat: %.2f", k));
        }
    }
}