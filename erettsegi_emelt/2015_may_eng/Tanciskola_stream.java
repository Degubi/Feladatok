import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Tanciskola_stream {
    
    public static void main(String... args) throws IOException {
        var file = Files.readAllLines(Paths.get("tancrend.txt"));
        var tancok = IntStream.iterate(0, k -> k + 3)
                              .limit(file.size() / 3)
                              .mapToObj(k -> new Tanc(file.get(k), file.get(k + 1), file.get(k + 2)))
                              .toArray(Tanc[]::new);
        
        System.out.println("Első tánc neve: " + tancok[0].category + ", az utolsóé: " + tancok[tancok.length - 1].category);
        
        System.out.println("Összesen " + Stream.of(tancok)
                                               .filter(k -> k.category.equals("samba"))
                                               .count() + "-an szambásztak");
        
        System.out.println("Vilma által táncolt kategóriák: " + Stream.of(tancok)
                            .filter(k -> k.woman.equals("Vilma"))
                            .map(k -> k.category)
                            .distinct()
                            .collect(Collectors.toList()));
        
        System.out.println("Írj be 1 kategóriát!");
        try(var input = new Scanner(System.in)){
            String readCat = input.nextLine();
        
            Stream.of(tancok)
                  .filter(k -> k.woman.equals("Vilma"))
                  .filter(k -> k.category.equals(readCat))
                  .findFirst()
                  .ifPresentOrElse(k -> System.out.println("Vilma vele táncolt " + readCat + "-t: " + k.man + "-val"),
                          () -> System.out.println("Vilma senkivel sem táncolt " + readCat + "-t"));
        }
        
        var lanyok = Stream.of(tancok)
                           .map(k -> k.woman)
                           .distinct()
                           .map(name -> new Szereplo(name, tancok, a -> a.woman.equals(name)))
                           .sorted(Szereplo.byAlkalmak)
                           .toArray(Szereplo[]::new);
        
        var fiuk = Stream.of(tancok)
                            .map(k -> k.man)
                            .distinct()
                            .map(name -> new Szereplo(name, tancok, a -> a.man.equals(name)))
                            .sorted(Szereplo.byAlkalmak)
                            .toArray(Szereplo[]::new);
        
        try(var output = new PrintWriter("szereplok.txt")){
            output.print("Lányok: ");
            output.print(Stream.of(lanyok)
                               .map(k -> k.name)
                               .collect(Collectors.joining(", ")));
            
            output.print("\nFiúk: ");
            output.print(Stream.of(fiuk)
                               .map(k -> k.name)
                               .collect(Collectors.joining(", ")));
        }
        System.out.print("A legtöbbet táncolt lányok: " + Stream.of(lanyok)
                                    .filter(k -> k.alkalmak == lanyok[0].alkalmak)
                                    .map(k -> k.name)
                                    .collect(Collectors.joining(" ")));
        
        System.out.println();
        System.out.print("A legtöbbet táncolt fiúk: " + Stream.of(fiuk)
                                  .filter(k -> k.alkalmak == fiuk[0].alkalmak)
                                  .map(k -> k.name)
                                  .collect(Collectors.joining(" ")));
    }
    
    public static class Tanc{
        public final String category, woman, man;
        
        public Tanc(String cat, String wom, String ma) {
            category = cat;
            woman = wom;
            man = ma;
        }
    }
    
    public static class Szereplo{
        public static final Comparator<Szereplo> byAlkalmak = Comparator.<Szereplo>comparingInt(k -> k.alkalmak).reversed();
        
        public final String name;
        public final int alkalmak;
        
        public Szereplo(String neim, Tanc[] tancok, Predicate<Tanc> filter) {
            name = neim;
            alkalmak = Stream.of(tancok).filter(filter).mapToInt(k -> 1).sum();
        }
    }
}