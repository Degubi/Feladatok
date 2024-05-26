import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class KarakterDekodolo_stream {

    public static void main(String[] args) throws IOException {
        var bankKarakterek = readKarakterFile("bank.txt");

        System.out.println("5. Feladat: Karakterek száma: " + bankKarakterek.length);
        System.out.println("6. Feladat:");

        var bekertBetu = Stream.generate(() -> System.console().readLine("Kérek egy angol nagybetűt: "))
                               .dropWhile(k -> k.length() != 1 || !Character.isUpperCase(k.charAt(0)))
                               .findFirst()
                               .orElseThrow();

        Arrays.stream(bankKarakterek)
              .filter(k -> k.karakter.equals(bekertBetu))
              .findFirst()
              .ifPresentOrElse(k -> System.out.println("7. Feladat: \n" + k.formatMatrix()),
                              () -> System.out.println("7. Feladat: Nincs ilyen karakter a bankban!"));

        var dekodolKarakterek = readKarakterFile("dekodol.txt");
        var dekodoltSzoveg = Arrays.stream(dekodolKarakterek)
                                   .map(k -> Arrays.stream(bankKarakterek)
                                                   .filter(n -> k.felismer(n))
                                                   .findFirst()
                                                   .map(n -> n.karakter)
                                                   .orElse("?"))
                                   .collect(Collectors.joining());

        System.out.println("9. Feladat: " + dekodoltSzoveg);
    }

    private static Karakter[] readKarakterFile(String filePath) throws IOException {
        var lines = Files.readAllLines(Path.of(filePath));
        var karakterLineIndices = IntStream.range(0, lines.size())
                                           .filter(i -> lines.get(i).length() == 1)
                                           .toArray();

        return IntStream.range(0, karakterLineIndices.length)
                        .mapToObj(i -> {
                            var karakterLineIndex = karakterLineIndices[i];
                            var matrixEndIndex = i == karakterLineIndices.length - 1 ? lines.size() : karakterLineIndices[i + 1];

                            return new Karakter(lines.get(karakterLineIndex), lines.subList(karakterLineIndex + 1, matrixEndIndex));
                        })
                        .toArray(Karakter[]::new);
    }
}