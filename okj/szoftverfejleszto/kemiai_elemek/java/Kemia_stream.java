import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Kemia_stream {

    public static void main(String[] args) throws IOException {
        var elemek = Files.lines(Path.of("felfedezesek.csv"), StandardCharsets.ISO_8859_1)
                          .skip(1)
                          .map(Elem::new)
                          .toArray(Elem[]::new);

        System.out.println("3. Feladat: Elemek száma: " + elemek.length);
        System.out.println("4. Feladat: Ókori elemek száma: " + Arrays.stream(elemek).filter(k -> k.okor).count());
        System.out.println("5. Feladat:\nÍrj be egy vegyjelet! (1-2 karakter)");

        try(var input = new Scanner(System.in)){
            var bekert = Stream.generate(input::nextLine)
                               .peek(k -> System.out.println("Írj be egy vegyjelet! (1-2 karakter)"))
                               .dropWhile(Kemia_stream::filterConsoleInput)
                               .findFirst()
                               .orElseThrow();

            System.out.println("6. Feladat:");
            Arrays.stream(elemek)
                  .filter(k -> k.vegyjel.equalsIgnoreCase(bekert))
                  .findFirst()
                  .ifPresentOrElse(k -> System.out.println(k.vegyjel + ": " + k.nev + ", rsz.: " + k.rendszam + ", év: " + k.ev + ", felf.: " + k.felfedezo),
                                  () -> System.out.println("Nincs ilyen elem eltárolva!"));
        }

        IntStream.range(0, elemek.length - 1)
                 .filter(i -> elemek[i].ev != 0 && elemek[i + 1].ev != 0)
                 .map(i -> elemek[i + 1].ev - elemek[i].ev)
                 .max()
                 .ifPresent(k -> System.out.println("7. Feladat: Leghoszabb idő: " + k + " év"));

        System.out.println("8. Feladat");
        Arrays.stream(elemek)
              .collect(Collectors.groupingBy(k -> k.ev, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 3)
              .filter(k -> k.getKey() != -1)
              .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " db"));
    }

    private static boolean filterConsoleInput(String text) {
         var len = text.length();
         return !(len == 1 && Character.isLetter(text.charAt(0))) || (len == 2 && Character.isLetter(text.charAt(0)) && Character.isLetter(text.charAt(1)));
    }
}