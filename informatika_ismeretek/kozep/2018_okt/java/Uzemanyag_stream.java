import java.io.*;
import java.nio.file.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

public class Uzemanyag_stream {

    public static void main(String[] args) throws IOException {
        var valtozasok = Files.lines(Path.of("uzemanyag.txt"))
                              .map(Arvaltozas::new)
                              .toArray(Arvaltozas[]::new);

        System.out.println("3. Feladat: Változások száma: " + valtozasok.length);

        var legkisebbKul = Arrays.stream(valtozasok)
                                 .min(Comparator.comparingInt(k -> Math.max(k.benzinAr, k.gazolajAr) - Math.min(k.benzinAr, k.gazolajAr)))
                                 .map(k -> Math.max(k.benzinAr, k.gazolajAr) - Math.min(k.benzinAr, k.gazolajAr))
                                 .orElseThrow();

        System.out.println("4. Feladat: Legkisebb különbség: " + legkisebbKul);

        var legkisebbDarab = Arrays.stream(valtozasok)
                                   .filter(k -> Math.max(k.benzinAr, k.gazolajAr) - Math.min(k.benzinAr, k.gazolajAr) == legkisebbKul)
                                   .count();

        System.out.println("5. Feladat: Előfordulás: " + legkisebbDarab);

        var volteSzokoev = Arrays.stream(valtozasok).anyMatch(k -> k.valtozasDatuma.isLeapYear());
        System.out.println("6. Feladat: Volt szökőév: " + (volteSzokoev ? "igen" : "nem"));

        var fileba = Arrays.stream(valtozasok)
                           .map(k -> String.format("%s;%.2f;%.2f", k.valtozasDatuma.toString().replace('-', '.'), k.benzinAr / 307.7F, k.gazolajAr / 307.7F))
                           .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("euro.txt"), fileba);
        System.out.println("8. Feladat:");

        var bekertEvszam = IntStream.generate(() -> Integer.parseInt(System.console().readLine("Írj be 1 évszámot (2010 < evszam < 2016): ")))
                                    .dropWhile(k -> k <= 2011 || k >= 2016)
                                    .findFirst()
                                    .orElseThrow();

        var bekertEviValtozasok = Arrays.stream(valtozasok)
                                        .filter(k -> k.valtozasDatuma.getYear() == bekertEvszam)
                                        .toArray(Arvaltozas[]::new);

        IntStream.range(0, bekertEviValtozasok.length - 1)
                 .map(i -> idokulonbseg(bekertEviValtozasok[i], bekertEviValtozasok[i + 1]))
                 .max()
                 .ifPresent(kul -> System.out.println("10. Feladat: " + bekertEvszam + " leghosszabb időszaka: " + kul + " nap volt."));
    }

    public static int idokulonbseg(Arvaltozas kezdet, Arvaltozas veg) {
        return (int) ChronoUnit.DAYS.between(kezdet.valtozasDatuma, veg.valtozasDatuma);
    }
}