import java.io.*;
import java.nio.file.*;
import java.util.*;

public class EgySzamjatek_stream {
    
    public static void main(String[] args) throws IOException {
        var menetek = Files.lines(Path.of("egyszamjatek.txt")).map(Menet::new).toArray(Menet[]::new);
        var fordulokSzama = menetek[0].tippek.length;
        
        System.out.println("3. Feladat: J�t�kosok sz�ma: " + menetek.length);
        System.out.println("4. Feladat: Fordul�k sz�ma: " + fordulokSzama);
        
        var voltEEgyes = Arrays.stream(menetek)
                               .flatMapToInt(k -> Arrays.stream(k.tippek))
                               .anyMatch(l -> l == 1);
        
        System.out.println("5. Feladat: Volt egyes: " + (voltEEgyes ? "igen" : "nem"));
        
        Arrays.stream(menetek)
              .flatMapToInt(k -> Arrays.stream(k.tippek))
              .max()
              .ifPresent(tipp -> System.out.println("6. Feladat: Legnagyobb tipp: " + tipp));
        
        try(var console = new Scanner(System.in)){
            System.out.println("7. Feladat: �rd be egy fordul� sorsz�m�t! [1-" + fordulokSzama + "]");
            
            var bekertFordulo = console.nextInt();
            var kezeltFordulo = bekertFordulo < 1 || bekertFordulo > fordulokSzama ? 0 : bekertFordulo - 1;
            
            var legkisebbTipp = Arrays.stream(menetek)
                                      .min(Comparator.comparingInt(k -> k.tippek[kezeltFordulo]))
                                      .orElseThrow();
            
            var legkisebbErtek = legkisebbTipp.tippek[kezeltFordulo];
            var legkisebbElofordulasa = Arrays.stream(menetek)
                                              .filter(k -> k.tippek[kezeltFordulo] == legkisebbErtek)
                                              .count();
            
            if(legkisebbElofordulasa == 1) {
                System.out.println("8. Feladat: Nyertes tipp: " + legkisebbErtek);
                System.out.println("9. Feladat: Fordul� nyertese: " + legkisebbTipp.nev);
                
                var stat = "Fordul� sorsz�ma: " + (kezeltFordulo + 1) + "\n" +
                           "Nyertes tipp: " + legkisebbErtek + "\n" +
                           "Nyertes j�t�kos: " + legkisebbTipp.nev;
                
                Files.writeString(Path.of("nyertes.txt"), stat);
            }else {
                System.out.println("8. Feladat: Nem volt nyertes a megadott fordul�ban");
                System.out.println("9. Feladat: Nem volt nyertes a megadott fordul�ban");
            }
        }
    }
    
    public static class Menet{
        public final String nev;
        public final int[] tippek;
        
        public Menet(String line) {
            var split = line.split(" ");
            
            nev = split[split.length - 1];
            tippek = Arrays.stream(split, 0, split.length - 1).mapToInt(Integer::parseInt).toArray();
        }
    }
}