import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Utca_stream {

    public static void main(String[] args) throws IOException {
        var hazszamSzamlalok = new int[] { 2, 1 };
        var telkek = Files.lines(Path.of("kerites.txt"))
                          .map(k -> k.split(" "))
                          .map(k -> {
                              var hazszamSzamlaloIndex = Integer.parseInt(k[0]);
                              var hazszam = hazszamSzamlalok[hazszamSzamlaloIndex];

                              hazszamSzamlalok[hazszamSzamlaloIndex] += 2;
                              return new Telek(hazszam, Integer.parseInt(k[1]), k[2].charAt(0));
                          })
                          .toArray(Telek[]::new);

        var utolsoTelek = telkek[telkek.length - 1];

        System.out.println("2. Feladat: Eladott telkek száma: " + telkek.length);
        System.out.println("3. Feladat: Az utolsó telek: " + (utolsoTelek.parosE ? "Páros" : "Páratlan") + ", házszáma: " + utolsoTelek.hazszam);

        IntStream.range(0, telkek.length - 1)
                 .filter(i -> {
                     var telek = telkek[i];
                     var kovetkezo = telkek[i + 1];

                     return !telek.parosE && telek.keritesSzine != ':' && telek.keritesSzine != '#' && !kovetkezo.parosE && kovetkezo.keritesSzine == telek.keritesSzine;
                 })
                 .mapToObj(i -> telkek[i])
                 .findFirst()
                 .ifPresent(k -> System.out.println("4. Feladat: Talált házszám: " + k.hazszam));

        System.out.println("5. Feladat: Írd be 1 telek számát!");

        try(var console = new Scanner(System.in)) {
            var bekertTelekSzam = console.nextInt();
            var bekertTelekIndexe = IntStream.range(0, telkek.length)
                                             .filter(i -> telkek[i].hazszam == bekertTelekSzam)
                                             .findFirst()
                                             .orElseThrow();

            var bekertTelek = telkek[bekertTelekIndexe];

            System.out.println("Kerítés színe: " + (bekertTelek.keritesSzine == ':' ? "Nem készült el" :
                               bekertTelek.keritesSzine == '#' ? "Festetlen" : bekertTelek.keritesSzine));

            IntStream.rangeClosed('A', 'Z')
                     .filter(c -> telkek[bekertTelekIndexe - 1].keritesSzine != c && telkek[bekertTelekIndexe + 1].keritesSzine != c && bekertTelek.keritesSzine != c)
                     .findFirst()
                     .ifPresent(c -> System.out.println("Az új szín lehet: " + (char) c));
        }

        var elsoSor = Arrays.stream(telkek)
                            .filter(k -> !k.parosE)
                            .map(k -> Character.toString(k.keritesSzine).repeat(k.szelesseg))
                            .collect(Collectors.joining());

        var masodikSor = Arrays.stream(telkek)
                               .filter(k -> !k.parosE)
                               .map(k -> {
                                   var hazszamString = Integer.toString(k.hazszam);

                                   return hazszamString + " ".repeat(k.szelesseg - hazszamString.length());
                               })
                               .collect(Collectors.joining());

        Files.writeString(Path.of("utcakep.txt"), elsoSor + '\n' + masodikSor);
    }
}