import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Snooker_stream {
    
    public static void main(String[] args) throws IOException {
        var versenyzok = Files.lines(Path.of("snooker.txt"), StandardCharsets.ISO_8859_1)
                              .skip(1)
                              .map(Versenyzo::new)
                              .toArray(Versenyzo[]::new);
        
        System.out.println("3. Feladat: Versenyzők száma: " + versenyzok.length);
        
        Arrays.stream(versenyzok)
              .mapToInt(k -> k.nyeremeny)
              .average()
              .ifPresent(k -> System.out.printf("4. Feladat: Átlag kereset: %.2f\n", k));
        
        Arrays.stream(versenyzok)
              .filter(k -> k.orszag.equals("Kína"))
              .max(Comparator.comparingInt(k -> k.nyeremeny))
              .ifPresent(k -> System.out.println("5. Feladat: Legjobban kereső kínai versenyző:\n" +
                                                 "    Helyezés: " + k.helyezes + "\n" +
                                                 "    Ország: Kína\n" +
                                                 "    Nyeremény: " + (k.nyeremeny * 380) + " FT"));
        
        var voltENorveg = Arrays.stream(versenyzok).anyMatch(k -> k.orszag.equals("Norvégia"));
        System.out.println("6. Feladat: " + (voltENorveg ? "Van" : "Nincs") + " norvég játékos");
        
        Arrays.stream(versenyzok)
              .map(k -> k.orszag)
              .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 4)
              .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue() + " fő"));
    }
    
    public static class Versenyzo {
        
        public final int helyezes;
        public final String nev;
        public final String orszag;
        public final int nyeremeny;
        
        public Versenyzo(String line) {
            var split = line.split(";");
            
            helyezes = Integer.parseInt(split[0]);
            nev = split[1];
            orszag = split[2];
            nyeremeny = Integer.parseInt(split[3]);
        }
    }
}