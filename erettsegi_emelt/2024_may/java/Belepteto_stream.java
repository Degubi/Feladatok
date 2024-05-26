import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Belepteto_stream {

    record Mozgas(String azonosito, LocalTime idopont, int tipus) {}


    public static void main(String[] args) throws Exception {
        var mozgasok = Files.lines(Path.of("bedat.txt"))
                            .map(k -> k.split(" "))
                            .map(k -> new Mozgas(k[0], LocalTime.parse(k[1]), Integer.parseInt(k[2])))
                            .toArray(Mozgas[]::new);

        System.out.println("2. Feladat: Első belépő: " + mozgasok[0].idopont() + ", utolsó kilépő: " + mozgasok[mozgasok.length - 1].idopont());

        var kesoKezdet = LocalTime.of(7, 50);
        var kesoZaras = LocalTime.of(8, 15);
        var kesok = Arrays.stream(mozgasok)
                          .filter(k -> k.idopont().isAfter(kesoKezdet) && k.idopont().isBefore(kesoZaras))
                          .map(k -> k.idopont() + " " + k.azonosito())
                          .collect(Collectors.toList());

        Files.write(Path.of("kesok.txt"), kesok);

        var menzasokSzama = Arrays.stream(mozgasok)
                                  .filter(k -> k.tipus() == 3)
                                  .count();

        System.out.println("4. Feladat: Menzások száma: " + menzasokSzama);

        var kolcsonzesekSzama = Arrays.stream(mozgasok)
                                      .filter(k -> k.tipus() == 4)
                                      .map(Mozgas::azonosito)
                                      .distinct()
                                      .count();

        System.out.println("5. Feladat: Könyvtári kölcsönzések száma: " + kolcsonzesekSzama +
                           ".\n" + (kolcsonzesekSzama > menzasokSzama ? "Többen voltak" : "Nem voltak többen") + ", mint a menzán");

        var szunetKezdete = LocalTime.of(10, 45);
        var kapuZaras = LocalTime.of(10, 50);
        var szunetVege = LocalTime.of(11, 0);

        var szunetbeIsmertKilepok = Arrays.stream(mozgasok)
                                          .filter(k -> k.idopont().isAfter(szunetKezdete) && k.idopont().isBefore(szunetVege))
                                          .filter(k -> k.tipus() == 2)
                                          .map(Mozgas::azonosito)
                                          .collect(Collectors.toList());

        var szunetElottMarBelepok = Arrays.stream(mozgasok)
                                          .filter(k -> k.idopont().isBefore(szunetKezdete))
                                          .map(Mozgas::azonosito)
                                          .collect(Collectors.toList());

        var szunetVegeigBelepok = Arrays.stream(mozgasok)
                                        .filter(k -> k.idopont().isAfter(kapuZaras) && k.idopont().isBefore(szunetVege))
                                        .filter(k -> k.tipus() == 1)
                                        .map(Mozgas::azonosito)
                                        .filter(k -> !szunetbeIsmertKilepok.contains(k) && szunetElottMarBelepok.contains(k))
                                        .collect(Collectors.joining(" "));

        System.out.println("6. Feladat: " + szunetVegeigBelepok);

        var bekertAzonosito = System.console().readLine("7. Feladat: Kérem egy tanuló azonosítóját! ");
        var bekertTanuloMozgasai = Arrays.stream(mozgasok)
                                         .filter(k -> k.azonosito().equals(bekertAzonosito))
                                         .toArray(Mozgas[]::new);

        var elteltIdo = Duration.between(bekertTanuloMozgasai[0].idopont(), bekertTanuloMozgasai[bekertTanuloMozgasai.length - 1].idopont());

        System.out.println("Eltelt idő: " + elteltIdo.toHoursPart() + " óra " + elteltIdo.toMinutesPart() + " perc");
    }
}