import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helyjegy_stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("eladott.txt"));
        var firstSplit = lines.get(0).split(" ");

        var utHossz = Integer.parseInt(firstSplit[1]);
        var ar = Integer.parseInt(firstSplit[2]);  //10 km-ként
        var utasok = IntStream.range(1, lines.size())
                              .mapToObj(i -> new Utas(lines.get(i), i))
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

        try(var console = new Scanner(System.in)) {
            System.out.println("Írj be 1 km számot!");

            var bekertKm = console.nextInt();
            var fileba = IntStream.rangeClosed(1, 48)
                                  .mapToObj(i -> getUlesStatus(i, bekertKm, utasok))
                                  .collect(Collectors.toList());

            Files.write(Path.of("kihol.txt"), fileba);
        }
    }

    static String getUlesStatus(int ules, int bekertKm, Utas[] utasok) {
        return ules + ". ülés: " + Arrays.stream(utasok)
                                         .filter(k -> k.ules == ules)
                                         .filter(k -> k.start == bekertKm || k.end == bekertKm)
                                         .findFirst()
                                         .map(k -> k.sorszam + ". utas")
                                         .orElse("üres");
    }
}