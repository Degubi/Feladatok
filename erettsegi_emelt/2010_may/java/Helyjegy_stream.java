import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Helyjegy_stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("eladott.txt"));
        var firstLineSplit = lines.get(0).split(" ");

        var vonalHossz = Integer.parseInt(firstLineSplit[1]);
        var arPer10km = Integer.parseInt(firstLineSplit[2]);
        var utasok = IntStream.range(1, lines.size())
                              .mapToObj(i -> new Utas(lines.get(i), i))
                              .toArray(Utas[]::new);

        var utolso = utasok[utasok.length - 1];

        System.out.println("2.Feladat: Utolsó utas ülése: " + utolso.ules + " utazott távolság: " + (utolso.leszallasKm - utolso.felszallasKm));
        System.out.println("3.Feladat:");

        Arrays.stream(utasok)
              .filter(k -> (k.leszallasKm - k.felszallasKm) == vonalHossz)
              .forEach(k -> System.out.print(k.sorszam + " "));

        System.out.println("\n4.Feladat: Összes bevétel: " + Arrays.stream(utasok).mapToInt(k -> Utas.getAr(k, arPer10km)).sum());

        var utolsoElottiMegalloKm = Arrays.stream(utasok)
                                          .mapToInt(k -> k.leszallasKm)
                                          .filter(k -> k != vonalHossz)
                                          .max()
                                          .orElseThrow();

        var utoljaraFelszallok = Arrays.stream(utasok).filter(k -> k.felszallasKm == utolsoElottiMegalloKm).count();
        var utoljaraLeszallok  = Arrays.stream(utasok).filter(k -> k.leszallasKm == utolsoElottiMegalloKm).count();

        System.out.println("5.Feladat: Utolsó megállónál felszállók: " + utoljaraFelszallok + ", leszállók: " + utoljaraLeszallok);

        var megallokSzama = IntStream.concat(Arrays.stream(utasok).mapToInt(k -> k.leszallasKm), Arrays.stream(utasok).mapToInt(k -> k.felszallasKm))
                                     .distinct()
                                     .count() - 2;

        System.out.println("6.Feladat: Megállók száma: " + megallokSzama);

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
                                         .filter(k -> k.felszallasKm == bekertKm || k.leszallasKm == bekertKm)
                                         .findFirst()
                                         .map(k -> k.sorszam + ". utas")
                                         .orElse("üres");
    }
}