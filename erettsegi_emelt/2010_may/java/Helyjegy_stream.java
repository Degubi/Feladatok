import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helyjegy_stream {
    
    public static void main(String[] args) throws IOException {
        var file = Files.readAllLines(Path.of("eladott.txt"));
        var firstSplit = file.get(0).split(" ");
        
        var utHossz = Integer.parseInt(firstSplit[1]);
        var ar = Integer.parseInt(firstSplit[2]);  //10 km-ként
        var utasok = IntStream.range(0, file.size())
                              .mapToObj(i -> new Utas(file.get(i), i))
                              .toArray(Utas[]::new);
        
        var utolso = utasok[utasok.length - 1];
        System.out.println("2.Feladat: Utolsó utas ülése: " + utolso.ules + " utazott távolság: " + utolso.getTavolsag());
        System.out.println("3.Feladat");
        
        Arrays.stream(utasok)
              .filter(k -> k.getTavolsag() == utHossz)
              .forEach(k -> System.out.print(k.sorszam + " "));
        
        System.out.println("\n4.Feladat");
        System.out.println("Összes bevétel: " + Arrays.stream(utasok).mapToInt(k -> k.getAr(ar)).sum());
        
        var uccso = Arrays.stream(utasok)
                          .mapToInt(k -> k.end)
                          .filter(k -> k != utHossz)
                          .max()
                          .orElseThrow();
        
        var felszallok = Arrays.stream(utasok).filter(k -> k.start == uccso).count();
        var leszallok = Arrays.stream(utasok).filter(k -> k.end == uccso).count();

        System.out.println("5.Feladat: Utolsó megállónál felszállók: " + felszallok + ", leszállók: " + leszallok);
        
        var allomasok = IntStream.concat(Arrays.stream(utasok).mapToInt(k -> k.end), Arrays.stream(utasok).mapToInt(k -> k.start))
                                 .distinct()
                                 .toArray();
        
        System.out.println("6.Feladat: Megállók száma: " + (allomasok.length - 2));
        
        try(var output = new PrintWriter("kihol.txt"); 
            var input = new Scanner(System.in)){
            
            System.out.println("Írj be 1 km számot!");
            var readTav = input.nextInt();
            
            IntStream.rangeClosed(1, 48).forEach(index -> {
                         System.out.println(index + ". ülés");
                         
                         Arrays.stream(utasok)
                               .filter(k -> k.ules == index)
                               .filter(k -> k.start == readTav || k.end == readTav)
                               .findFirst()
                               .ifPresentOrElse(utas -> System.out.println(utas.sorszam + ". utas"), 
                                                  () -> System.out.println("üres"));
                         });
        }
    }
}