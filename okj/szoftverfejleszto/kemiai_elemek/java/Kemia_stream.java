import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Kemia_stream {

    public static void main(String[] args) throws IOException {
        var elemek = Files.lines(Path.of("felfedezesek.csv"), StandardCharsets.ISO_8859_1)
                          .skip(1)
                          .map(k -> new Elem(k.split(";")))
                          .toArray(Elem[]::new);

        System.out.println("3. Feladat: Elemek száma: " + elemek.length);
        System.out.println("4. Feladat: Ókori elemek száma: " + Arrays.stream(elemek).filter(k -> k.ev == Elem.OKOR_EV).count());
        System.out.println("5. Feladat:");

        var bekertVegyjel = Stream.generate(() -> System.console().readLine("Írj be egy vegyjelet! (1-2 karakter): "))
                                  .filter(k -> (k.length() == 1 || k.length() == 2) && k.chars().allMatch(Character::isLetter))
                                  .findFirst()
                                  .orElseThrow();

        System.out.println("6. Feladat:");
        Arrays.stream(elemek)
              .filter(k -> k.vegyjel.equalsIgnoreCase(bekertVegyjel))
              .findFirst()
              .ifPresentOrElse(k -> System.out.println(k.vegyjel + ": " + k.nev + ", rsz.: " + k.rendszam + ", év: " + k.ev + ", felf.: " + k.felfedezo),
                              () -> System.out.println("Nincs ilyen elem eltárolva!"));

        IntStream.range(0, elemek.length - 1)
                 .filter(i -> elemek[i].ev != Elem.OKOR_EV && elemek[i + 1].ev != Elem.OKOR_EV)
                 .map(i -> elemek[i + 1].ev - elemek[i].ev)
                 .max()
                 .ifPresent(k -> System.out.println("7. Feladat: Leghoszabb idő: " + k + " év"));

        System.out.println("8. Feladat");
        Arrays.stream(elemek)
              .filter(k -> k.ev != Elem.OKOR_EV)
              .collect(Collectors.groupingBy(k -> k.ev, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 3)
              .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " db"));
    }
}