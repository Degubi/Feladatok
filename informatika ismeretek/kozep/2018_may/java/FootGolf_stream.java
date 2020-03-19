import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FootGolf_stream {
    public static void main(String[] args) throws Exception{
        var versenyzok = Files.lines(Paths.get("fob2016.txt")).map(Versenyzo::new).toArray(Versenyzo[]::new);
        
        System.out.println("3.Feladat: Versenyzők száma: " + versenyzok.length);
        
        var arany = Arrays.stream(versenyzok).filter(k -> k.kategoria.contains("Noi")).count() / (float)versenyzok.length * 100;
        System.out.printf("4.Feladat: Női versenyzők aránya: %.2f%%\n", arany);
        
        Arrays.stream(versenyzok)
              .filter(k -> k.kategoria.contains("Noi"))
              .max(Comparator.comparingInt(Versenyzo::osszPont))
              .ifPresent(versenyzo -> System.out.printf("6.Feladat: Női versenyző neve: %s, egyesület: %s, pontok: %d\n", versenyzo.nev, versenyzo.versenyEgyesulet, versenyzo.osszPont()));
        
        Files.write(Path.of("osszpontFF.txt"), Arrays.stream(versenyzok).map(Versenyzo::toString).collect(Collectors.toList()));
        System.out.println("8.Feladat:");
        
        var kiirni = Arrays.stream(versenyzok)
                           .collect(Collectors.groupingBy(k -> k.versenyEgyesulet, Collectors.counting()))
                           .entrySet().stream()
                           .filter(k -> !k.getKey().equals("n.a."))
                           .filter(k -> k.getValue() > 2)
                           .collect(Collectors.toList());
        
        System.out.println(kiirni);
    }
    
    public static class Versenyzo{
        public final String nev, kategoria, versenyEgyesulet;
        public final int[] pontok;
        
        public Versenyzo(String line) {
            var split = line.split(";");
            
            nev = split[0];
            kategoria = split[1];
            versenyEgyesulet = split[2];
            pontok = IntStream.range(3, 11).map(k -> Integer.parseInt(split[k])).sorted().toArray();
        }
        
        public int osszPont() {  //5. Feladat
            return Arrays.stream(pontok, 2, 8).sum() + (pontok[0] != 0 ? 10 : 0) + (pontok[1] != 0 ? 10 : 0);
        }
        
        @Override
        public String toString() {
            return nev + " " + osszPont();
        }
    }
}