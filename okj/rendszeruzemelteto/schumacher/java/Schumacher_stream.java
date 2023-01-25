import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Schumacher_stream {

    public static void main(String[] args) throws IOException {
        var eredmenyek = Files.lines(Path.of("schumacher.csv"))
                              .skip(1)
                              .map(Eredmeny::new)
                              .toArray(Eredmeny[]::new);

        System.out.println("3. Feladat: Adatsorok száma: " + eredmenyek.length);
        System.out.println("4. Feladat:");

        Arrays.stream(eredmenyek)
              .filter(k -> k.dijNev.equals("Hungarian Grand Prix"))
              .filter(k -> k.helyezes != 0)
              .forEach(k -> System.out.println("    " + k.datum + ": " + k.helyezes + ". hely"));

        System.out.println("5. Feladat:");

        Arrays.stream(eredmenyek)
              .filter(k -> k.helyezes == 0)
              .collect(Collectors.groupingBy(k -> k.vegeredmenyStatusz, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 2)
              .forEach(k -> System.out.println("    " + k.getKey() + ": " + k.getValue()));
    }
}