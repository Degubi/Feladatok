import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Telekocsi_stream {

    public static void main(String[] args) throws IOException {
        var autok = Files.lines(Path.of("autok.csv"), StandardCharsets.ISO_8859_1)
                         .skip(1)
                         .map(Auto::new)
                         .toArray(Auto[]::new);

        System.out.println("2. Feladat: Hirdetesek szama: " + autok.length);

        var bpToMiskolc = Arrays.stream(autok)
                                .filter(k -> k.indulas.equals("Budapest") && k.cel.equals("Miskolc"))
                                .mapToInt(k -> k.ferohely)
                                .sum();

        System.out.println("3. Feladat: BP to Miskolc hely: " + bpToMiskolc);

        Arrays.stream(autok)
              .collect(Collectors.groupingBy(k -> k.indulas + '-' + k.cel, Collectors.summingInt(k -> k.ferohely)))
              .entrySet().stream()
              .max(Entry.comparingByValue())
              .ifPresent(k -> System.out.println("4. Feladat: " + k.getKey() + ": " + k.getValue() + " hely"));

        System.out.println("5. Feladat");

        var igenyek = Files.lines(Path.of("igenyek.csv"), StandardCharsets.ISO_8859_1)
                           .skip(1)
                           .map(Igeny::new)
                           .toArray(Igeny[]::new);

        Arrays.stream(igenyek)
              .forEach(igeny -> Telekocsi_stream.autotKeresIgenyre(igeny, autok)
                                                .ifPresent(k -> System.out.println(igeny.azonosito + " -> " + k.rendszam)));
        var fileba = Arrays.stream(igenyek)
                           .map(igeny -> Telekocsi_stream.autotKeresIgenyre(igeny, autok)
                                                         .map(k -> igeny.azonosito + ": Rendszam: " + k.rendszam + ", Telefonszam: " + k.telefonszam)
                                                         .orElse(igeny.azonosito + ": " + "Sajnos nem sikerült autót találni"))
                           .collect(Collectors.toList());

        Files.write(Path.of("utazasuzenetek.txt"), fileba);
    }

    public static Optional<Auto> autotKeresIgenyre(Igeny igeny, Auto[] autok) {
        return Arrays.stream(autok)
                     .filter(k -> k.indulas.equals(igeny.indulas) && k.cel.equals(igeny.cel) && k.ferohely >= igeny.szemelyek)
                     .findFirst();
    }
}