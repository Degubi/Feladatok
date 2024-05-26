import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Cegesauto_stream {

    public static void main(String[] args) throws IOException {
        var autok = Files.lines(Path.of("autok.txt")).map(Auto::new).toArray(Auto[]::new);

        Arrays.stream(autok)
              .filter(k -> k.elvitel)
              .reduce((l, r) -> r)
              .ifPresent(k -> System.out.println("2. Feladat: Utoljára elvitt autó: " + k.nap + ". nap, rendszam: " + k.rendszam));

        Arrays.sort(autok, Comparator.comparing(k -> k.rendszam));

        var beNap = Integer.parseInt(System.console().readLine("3. Feladat: Írj be egy napot!"));

        Arrays.stream(autok)
              .filter(k -> k.nap == beNap)
              .forEach(k -> System.out.println(k.idopont + " " + k.rendszam + " " + k.szemelyAzonosito + (k.elvitel ? " ki" : " be")));

        var visszahozottAutokSzama = Arrays.stream(autok).filter(k -> !k.elvitel).count();
        System.out.println("4. Feladat: Nem visszahozott autók száma: " + (autok.length - visszahozottAutokSzama * 2));
        System.out.println("5. Feladat");

        Arrays.stream(autok)
              .collect(Collectors.groupingBy(k -> k.rendszam, TreeMap::new, Collectors.summarizingInt(k -> k.km)))
              .forEach((rsz, stat) -> System.out.println(rsz + ": " + (stat.getMax() - stat.getMin()) + " km"));

        IntStream.range(1, autok.length).boxed()
                 .filter(i -> autok[i].rendszam.equals(autok[i - 1].rendszam) && !autok[i].elvitel)
                 .max(Comparator.comparingInt(k -> autok[k].km - autok[k - 1].km))
                 .ifPresent(i -> System.out.println("6. Feladat: Leghosszabb út: " + (autok[i].km - autok[i - 1].km) + " km, személy: " + autok[i].szemelyAzonosito));

        var beRendszam = System.console().readLine("7. Feladat: Írj be egy rendszámot: ");
        var fileba = Arrays.stream(autok)
                           .filter(k -> k.rendszam.equals(beRendszam))
                           .map(Auto::toFileInfo)
                           .collect(Collectors.joining());

        Files.writeString(Path.of(beRendszam + "menetlevel.txt"), fileba);
    }
}