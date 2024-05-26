import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Cbradio_stream {

    public static void main(String[] args) throws IOException {
        var bejegyzesek = Files.lines(Path.of("cb.txt"))
                               .skip(1)
                               .map(Bejegyzes::new)
                               .toArray(Bejegyzes[]::new);

        var voltE4AdastIndito = Arrays.stream(bejegyzesek).anyMatch(k -> k.adasok == 4);

        System.out.println("3. Feladat: Bejegyzések száma: " + bejegyzesek.length);
        System.out.println("4. Feladat: " + (voltE4AdastIndito ? "Volt" : "Nem volt") + " 4 adást indító sofőr");

        var bekertNev = System.console().readLine("5. Feladat: Írj be egy nevet: ");
        var bekertHasznalatok = Arrays.stream(bejegyzesek)
                                        .filter(k -> k.nev.equals(bekertNev))
                                        .mapToInt(k -> k.adasok)
                                        .sum();
        if(bekertHasznalatok > 0) {
            System.out.println(bekertNev + " " + bekertHasznalatok + "x használta a rádiót");
        }else{
            System.out.println("Nincs ilyen nevű sofőr!");
        }

        var fileContent = Arrays.stream(bejegyzesek)
                                .map(k -> atszamolPercre(k.ora, k.perc) + ";" + k.nev + ";" + k.adasok)
                                .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("cb2.txt"), "Kezdes;Nev;AdasDb\n" + fileContent);

        var soforokSzama = Arrays.stream(bejegyzesek)
                                 .map(k -> k.nev)
                                 .distinct()
                                 .count();

        System.out.println("8. Feladat: Sofőrök száma: " + soforokSzama);

        Arrays.stream(bejegyzesek)
              .collect(Collectors.groupingBy(k -> k.nev, Collectors.summingInt(k -> k.adasok)))
              .entrySet().stream()
              .max(Entry.comparingByValue())
              .ifPresent(k -> System.out.printf("9. Feladat: Legtöbb adás sofőre: %s: %d db\n", k.getKey(), k.getValue()));
    }

    public static int atszamolPercre(int ora, int perc) {
        return ora * 60 + perc;
    }
}