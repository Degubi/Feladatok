import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Ultrabalaton_stream {

    public static void main(String[] args) throws IOException {
        var versenyzok = Files.lines(Path.of("ub2017egyeni.txt"))
                              .skip(1)
                              .map(Versenyzo::new)
                              .toArray(Versenyzo[]::new);
        
        System.out.println("3. Feladat: Egyéni indulók: " + versenyzok.length);
        
        var celbeerkezok = Arrays.stream(versenyzok)
                                 .filter(k -> k.befejezesSzazalek == 100)
                                 .toArray(Versenyzo[]::new);
        
        var celbaertNoiIndulok = Arrays.stream(celbeerkezok)
                                       .filter(k -> k.kategoria.equals("Noi"))
                                       .count();
        
        System.out.println("4. Feladat: Célbaért női indulók: " + celbaertNoiIndulok);
        System.out.println("5. Feladat: Írd be egy versenyző nevét!");
        
        try(var console = new Scanner(System.in)){
            var bekertNev = console.nextLine();
            
            Arrays.stream(versenyzok)
                  .filter(k -> k.nev.equals(bekertNev))
                  .findFirst()
                  .ifPresentOrElse(bekert -> {
                      System.out.println("Indult egyéniben? Igen");
                      System.out.println("Teljesítette a távot? " + (bekert.befejezesSzazalek == 100 ? "Igen" : "Nem"));
                  }, () -> System.out.println("Indult egyéniben? Nem"));
        }
        
        Arrays.stream(celbeerkezok)
              .filter(k -> k.kategoria.equals("Ferfi"))
              .mapToDouble(Versenyzo::idoOraban)
              .average()
              .ifPresent(atlag -> System.out.println("7. Feladat: Átlagos idő: " + atlag + " óra"));
        
        Arrays.stream(celbeerkezok)
              .filter(k -> k.kategoria.equals("Noi"))
              .min(Comparator.comparingDouble(Versenyzo::idoOraban))
              .ifPresent(k -> System.out.printf("Nők: %s (%d) - %s\n", k.nev, k.rajtszam, k.ido));
        
        Arrays.stream(celbeerkezok)
              .filter(k -> k.kategoria.equals("Ferfi"))
              .min(Comparator.comparingDouble(Versenyzo::idoOraban))
              .ifPresent(k -> System.out.printf("Férfiak: %s (%d) - %s\n", k.nev, k.rajtszam, k.ido));
    }
}