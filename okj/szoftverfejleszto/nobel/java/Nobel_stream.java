import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nobel_stream {

    public static void main(String[] args) throws IOException {
        var dijak = Files.lines(Path.of("nobel.csv"))
                         .skip(1)
                         .map(Dij::new)
                         .toArray(Dij[]::new);
        
        Arrays.stream(dijak)
              .filter(k -> k.keresztNev.equals("Arthur B.") && k.vezetekNev.equals("McDonald"))
              .findFirst()
              .ifPresent(k -> System.out.println("3. Feladat: Arthur " + k.tipus + " díjat kapott"));
        
        System.out.println("4. Feladat");
        Arrays.stream(dijak)
              .filter(k -> k.evszam == 2017 && k.tipus.equals("irodalmi"))
              .forEach(k -> System.out.println("Irodalmi díjat kapott: " + k.keresztNev + " " + k.vezetekNev));
        
        System.out.println("5. Feladat");
        Arrays.stream(dijak)
              .filter(k -> k.vezetekNev.equals("") && k.evszam >= 1990)
              .forEach(k -> System.out.println(k.evszam + ": " + k.keresztNev));
        
        System.out.println("6. Feladat");
        Arrays.stream(dijak)
              .filter(k -> k.vezetekNev.contains("Curie"))
              .forEach(k -> System.out.println(k.evszam + ": " + k.keresztNev + " " + k.vezetekNev + ": " + k.tipus));
        
        System.out.println("7. Feladat");
        Arrays.stream(dijak)
              .collect(Collectors.groupingBy(k -> k.tipus, Collectors.counting()))
              .forEach((tipus, db) -> System.out.println(tipus + ": " + db + " db"));
        
        var fileba = Arrays.stream(dijak)
                           .filter(k -> k.tipus.equals("orvosi"))
                           .sorted(Comparator.comparingInt(k -> k.evszam))
                           .map(k -> k.evszam + ":" + k.keresztNev + " " + k.vezetekNev)
                           .collect(Collectors.toList());
        
        Files.write(Path.of("orvosi.txt"), fileba);
    }
}