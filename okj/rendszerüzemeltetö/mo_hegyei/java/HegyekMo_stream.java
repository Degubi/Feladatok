import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class HegyekMo_stream {
    public static void main(String[] args) throws IOException{
        var hegyek = Files.lines(Path.of("hegyekMo.txt"))
                          .skip(1)
                          .map(Hegy::new)
                          .toArray(Hegy[]::new);
        
        System.out.println("3. Feladat: Hegyek száma: " + hegyek.length + " db");
        
        Arrays.stream(hegyek)
              .mapToInt(k -> k.magassag)
              .average()
              .ifPresent(k -> System.out.println("4. Feladat: Átlagmagasság: " + k + " m"));
        
        Arrays.stream(hegyek)
              .max(Comparator.comparingInt(k -> k.magassag))
              .ifPresent(k -> System.out.println("5. Feladat: Legmagasabb hegy: " + k.hegyseg + "-ben a " + k.nev + ", magassága: " + k.magassag + " m"));
        
        try(var input = new Scanner(System.in)){
            System.out.println("6. Feladat: Írj be egy magasságot!");
            var beMagassag = input.nextInt();
            
            Arrays.stream(hegyek)
                  .filter(k -> k.hegyseg.equals("Börzsöny"))
                  .filter(k -> k.magassag > beMagassag)
                  .findFirst()
                  .ifPresentOrElse(k -> System.out.println("Van magasabb hegység ennél a Börzsönyben"),
                                    () -> System.out.println("Nincs magasabb hegység ennél a Börzsönyben"));
        }
        
        var konvertaltLab3000 = 3000D / 3.280839895D;
        var magasakSzama = Arrays.stream(hegyek).filter(k -> k.magassag > konvertaltLab3000).count();
        System.out.println("7. Feladat: 3000 lábnál magasabbak száma: " + magasakSzama);
        System.out.println("8. Feladat: Hegység stat");
        
        Arrays.stream(hegyek)
              .collect(Collectors.groupingBy(k -> k.hegyseg, Collectors.counting()))
              .forEach((hegyseg, db) -> System.out.println(hegyseg + ": " + db));
        
        var fileAdat = Arrays.stream(hegyek)
                             .filter(k -> k.hegyseg.equals("Bükk-vidék"))
                             .map(k -> String.format("%s;%.2f", k.nev, k.magassag * 3.280839895D))
                             .map(k -> k.replace(',', '.'))
                             .collect(Collectors.joining("\n"));
        
        Files.writeString(Path.of("bukk-videk.txt"), "Hegycsúcs neve;Magasság láb\n" + fileAdat);
    }
}