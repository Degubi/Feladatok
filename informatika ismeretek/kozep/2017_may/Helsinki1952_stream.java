import static java.nio.charset.StandardCharsets.*;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helsinki1952_stream {
    
    public static void main(String[] args) throws Exception{
        var helyezesek = Files.lines(Paths.get("helsinki.txt"), ISO_8859_1)
                              .map(Helyezes::new)
                              .toArray(Helyezes[]::new);
        
        System.out.println("3.Feladat: Pontszerz? helyez?sek sz?ma: " + helyezesek.length);
        
        var helyezesCsoportok = Arrays.stream(helyezesek)
                                      .collect(Collectors.groupingBy(k -> k.helyezes, Collectors.counting()));
        
        var aranyak = helyezesCsoportok.get(1);
        var ezustok = helyezesCsoportok.get(2);
        var bronzok = helyezesCsoportok.get(3);
        
        System.out.printf("4.Feladat: Aranyak: %d, ez?st?k: %d, bronzok: %d, ?sszesen: %d\n", aranyak, ezustok, bronzok, (aranyak + ezustok + bronzok));
        System.out.println("5.Feladat: Pontok sz?ma: " + Arrays.stream(helyezesek).mapToInt(Helyezes::pontCalc).sum());
        
        var sportagCsoportok = Arrays.stream(helyezesek)
                                     .filter(k -> k.helyezes <= 3)
                                     .collect(Collectors.groupingBy(k -> k.sportag, Collectors.counting()));
        
        var uszas = sportagCsoportok.get("uszas");
        var torna = sportagCsoportok.get("torna");
        
        System.out.println("6.Feladat");
        System.out.println(uszas == torna ? "Egyenl?ek" : (torna > uszas) ? "Torna volt t?bb" : "?sz?s volt t?bb");
        
        var fileba = Arrays.stream(helyezesek)
                           .map(k -> k.helyezes + " " + k.sportolokSzama + " " + k.pontCalc() + " " + k.sportag.replace("kajakkenu", "kajak-kenu") + " " + k.versenyszam)
                           .collect(Collectors.toList());
        
        Files.write(Path.of("helsinki2.txt"), fileba);
        System.out.println("8. Feladat");
        
        Arrays.stream(helyezesek)
              .max(Comparator.comparingInt(k -> k.sportolokSzama))
              .ifPresent(k -> System.out.printf("Helyez?s: %d, sport?g: %s, sz?m: %s, sportol?k: %d\n", k.helyezes, k.sportag, k.versenyszam, k.sportolokSzama));
    }
    
    public static class Helyezes{
        public final int helyezes;
        public final int sportolokSzama;
        public final String sportag;
        public final String versenyszam;
        
        public Helyezes(String line) {
            var split = line.split(" ");
            
            helyezes = Integer.parseInt(split[0]);
            sportolokSzama = Integer.parseInt(split[1]);
            sportag = split[2];
            versenyszam = split[3];
        }
        
        public int pontCalc() { return helyezes == 1 ? 7 : 7 - helyezes; }
    }
}