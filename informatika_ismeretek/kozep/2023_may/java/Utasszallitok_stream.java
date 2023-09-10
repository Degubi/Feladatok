import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Utasszallitok_stream {

    public static void main(String[] args) throws IOException {
        var repulogepek = Files.lines(Path.of("utasszallitok.txt"))
                               .skip(1)
                               .map(Utasszallito::new)
                               .toArray(Utasszallito[]::new);

        System.out.println("4. Feladat: Repülőgépek száma: " + repulogepek.length);

        var boeingGepekSzama = Arrays.stream(repulogepek)
                                     .filter(k -> k.tipus.startsWith("Boeing"))
                                     .count();

        System.out.println("5. Feladat: Boeing gépek száma: " + boeingGepekSzama);

        Arrays.stream(repulogepek)
              .max(Comparator.comparingInt(k -> k.maxSzallithatoUtasokSzama))
              .ifPresent(k -> System.out.println("6. Feladat: \n" +
                                                 "    Típus: " + k.tipus + "\n" +
                                                 "    Első felszállás: " + k.elsoRepulesEve + "\n" +
                                                 "    Utasok: " + k.minSzallithatoUtasokSzama + "-" + k.maxSzallithatoUtasokSzama + "\n" +
                                                 "    Személyzet: " + k.minSzemelyzetSzama + "-" + k.maxSzemeyzetSzama + "\n" +
                                                 "    Sebesség: " + k.sebessegKategoria.utazosebesseg));

        var megtalalhatoKategoriak = Arrays.stream(repulogepek)
                                           .map(k -> k.sebessegKategoria.getKategorianev())
                                           .collect(Collectors.toSet());

        var nemTalalhatoKategoriak = Arrays.stream(Sebessegkategoria.KATEGORIAK)
                                           .filter(k -> !megtalalhatoKategoriak.contains(k))
                                           .toArray(String[]::new);

        System.out.println("7. Feladat: " + (nemTalalhatoKategoriak.length == 0 ? "Minden sebességkategóriából van repülőgéptípus!" : String.join(" ", nemTalalhatoKategoriak)));

        var fileba = Arrays.stream(repulogepek)
                           .map(Utasszallito::reformatUtassallitoData)
                           .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("utasszallitok_new.txt"), "típus;év;utas;személyzet;utazósebesség;felszállótömeg;fesztáv\n" + fileba);
    }
}