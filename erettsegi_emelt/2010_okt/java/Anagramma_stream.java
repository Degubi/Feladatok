import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Anagramma_stream {

    public static void main(String[] args) throws IOException {
        System.out.println("1. Feladat: Írjon be 1 szöveget!");

        var console = new Scanner(System.in);
        var bekertSzoveg = console.nextLine();
        var bekertSzovegKarakterek = bekertSzoveg.chars()
                                                 .distinct()
                                                 .sorted()
                                                 .toArray();

        System.out.println("Karakterek száma: " + bekertSzovegKarakterek.length + " (" + new String(bekertSzovegKarakterek, 0, bekertSzovegKarakterek.length) + ")");

        var szotarSzavak = Files.readAllLines(Path.of("szotar.txt"));
        var szotarSzavakRendezve = szotarSzavak.stream()
                                               .map(k -> new String(k.chars().sorted().toArray(), 0, k.length()))
                                               .collect(Collectors.toList());

        Files.write(Path.of("abc.txt"), szotarSzavakRendezve);
        System.out.println("4. Feladat: Írjon be 2 szót!");

        var elsoSzo = console.next();
        var masodikSzo = console.next();

        System.out.println(isAnagramm(elsoSzo, masodikSzo) ? "Anagramma" : "Nem anagramma");
        System.out.println("5. Feladat: Írjon be 1 szót!");

        var bekertSzo = console.next();
        var bekertSzoAnagrammai = szotarSzavak.stream()
                                              .filter(k -> isAnagramm(k, bekertSzo))
                                              .toArray(String[]::new);

        System.out.println(bekertSzoAnagrammai.length == 0 ? "Nincs a szótárban anagramma" : "    " + String.join("\n    ", bekertSzoAnagrammai));
        System.out.println("6. Feladat:");
        console.close();

        var legosszabbSzoHossza = szotarSzavak.stream()
                                              .mapToInt(String::length)
                                              .max()
                                              .orElse(0);

        var leghosszabbSzavak = szotarSzavak.stream()
                                            .filter(k -> k.length() == legosszabbSzoHossza)
                                            .collect(Collectors.toList());

        streamAnagramGroups(leghosszabbSzavak)
        .map(k -> k[0] + ": \n" + Arrays.stream(k, 1, k.length).map(n -> "    " + n).collect(Collectors.joining("\n")) + '\n')
        .forEach(System.out::println);

        var fileba = streamAnagramGroups(szotarSzavak)
                    .collect(Collectors.groupingBy(k -> k[0].length()))
                    .entrySet().stream()
                    .sorted(Entry.comparingByKey())
                    .map(Entry::getValue)
                    .map(k -> k.stream().map(n -> String.join(" ", n)).collect(Collectors.joining("\n")) + '\n')
                    .collect(Collectors.toList());

        Files.write(Path.of("rendezve.txt"), fileba);
    }

    static Stream<String[]> streamAnagramGroups(List<String> words) {
        return words.stream()
                    .map(k -> new String(k.chars().sorted().toArray(), 0, k.length()))
                    .distinct()
                    .map(k -> words.stream().filter(n -> isAnagramm(k, n)).toArray(String[]::new));
    }

    static boolean isAnagramm(String word1, String word2) {
        var firstWordLetters = word1.chars().sorted().toArray();
        var secondWordLetters = word2.chars().sorted().toArray();

        return Arrays.equals(firstWordLetters, secondWordLetters);
    }
}