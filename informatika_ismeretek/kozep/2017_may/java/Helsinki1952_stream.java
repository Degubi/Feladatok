import static java.nio.charset.StandardCharsets.*;

import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helsinki1952_stream {

    public static void main(String[] args) throws Exception {
        var helyezesek = Files.lines(Paths.get("helsinki.txt"), ISO_8859_1)
                              .map(Helyezes::new)
                              .toArray(Helyezes[]::new);

        System.out.println("3.Feladat: Pontszerző helyezések száma: " + helyezesek.length);

        var helyezesCsoportok = Arrays.stream(helyezesek)
                                      .collect(Collectors.groupingBy(k -> k.helyezes, Collectors.counting()));

        var aranyak = helyezesCsoportok.get(1);
        var ezustok = helyezesCsoportok.get(2);
        var bronzok = helyezesCsoportok.get(3);

        System.out.printf("4.Feladat: Aranyak: %d, ezüstök: %d, bronzok: %d, összesen: %d\n", aranyak, ezustok, bronzok, (aranyak + ezustok + bronzok));
        System.out.println("5.Feladat: Pontok száma: " + Arrays.stream(helyezesek).mapToInt(Helyezes::pontCalc).sum());

        var sportagCsoportok = Arrays.stream(helyezesek)
                                     .filter(k -> k.helyezes <= 3)
                                     .collect(Collectors.groupingBy(k -> k.sportag, Collectors.counting()));

        var uszas = sportagCsoportok.get("uszas");
        var torna = sportagCsoportok.get("torna");

        System.out.println("6.Feladat");
        System.out.println(uszas == torna ? "Egyenlőek" : (torna > uszas) ? "Torna volt több" : "úszás volt több");

        var fileba = Arrays.stream(helyezesek)
                           .map(k -> k.helyezes + " " + k.sportolokSzama + " " + k.pontCalc() + " " + k.sportag.replace("kajakkenu", "kajak-kenu") + " " + k.versenyszam)
                           .collect(Collectors.toList());

        Files.write(Path.of("helsinki2.txt"), fileba);
        System.out.println("8. Feladat");

        Arrays.stream(helyezesek)
              .max(Comparator.comparingInt(k -> k.sportolokSzama))
              .ifPresent(k -> System.out.printf("Helyezés: %d, sportág: %s, szám: %s, sportolók: %d\n", k.helyezes, k.sportag, k.versenyszam, k.sportolokSzama));
    }
}