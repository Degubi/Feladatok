import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Tanciskola_stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("tancrend.txt"));
        var tancok = IntStream.iterate(0, k -> k + 3)
                              .limit(lines.size() / 3)
                              .mapToObj(i -> new Tanc(lines, i))
                              .toArray(Tanc[]::new);

        System.out.println("Első tánc neve: " + tancok[0].category + ", az utolsóé: " + tancok[tancok.length - 1].category);

        var szambazokSzama = Arrays.stream(tancok)
                                   .filter(k -> k.category.equals("samba"))
                                   .count();

        System.out.println("Összesen " + szambazokSzama + "-an szambásztak");

        var vilmaKategoriai = Arrays.stream(tancok)
                                    .filter(k -> k.woman.equals("Vilma"))
                                    .map(k -> k.category)
                                    .distinct()
                                    .collect(Collectors.joining(", "));

        System.out.println("Vilma által táncolt kategóriák: " + vilmaKategoriai);
        System.out.println("Írj be 1 kategóriát!");

        try(var input = new Scanner(System.in)){
            var readCat = input.nextLine();

            Arrays.stream(tancok)
                  .filter(k -> k.woman.equals("Vilma") && k.category.equals(readCat))
                  .findFirst()
                  .ifPresentOrElse(k -> System.out.println("Vilma vele táncolt " + readCat + "-t: " + k.man + "-val"),
                                  () -> System.out.println("Vilma senkivel sem táncolt " + readCat + "-t"));
        }

        var lanyokToTancalkalmak = Arrays.stream(tancok)
                                         .collect(Collectors.groupingBy(k -> k.woman, Collectors.counting()));

        var fiukToTancalkalmak = Arrays.stream(tancok)
                                       .collect(Collectors.groupingBy(k -> k.man, Collectors.counting()));

        Files.writeString(Path.of("szereplok.txt"), "Lányok: " + String.join(", ", lanyokToTancalkalmak.keySet()) +
                                                    "\nFiúk: " + String.join(", ", fiukToTancalkalmak.keySet()));

        var grillMax = lanyokToTancalkalmak.values().stream().mapToLong(k -> k).max().orElseThrow();
        var boiMax = fiukToTancalkalmak.values().stream().mapToLong(k -> k).max().orElseThrow();

        var popularGrills = lanyokToTancalkalmak.entrySet().stream()
                                                .filter(k -> k.getValue() == grillMax)
                                                .map(Entry::getKey)
                                                .collect(Collectors.joining(", "));

        var popularBois = fiukToTancalkalmak.entrySet().stream()
                                            .filter(k -> k.getValue() == boiMax)
                                            .map(Entry::getKey)
                                            .collect(Collectors.joining(", "));

        System.out.println("A legtöbbet táncolt lányok: " + popularGrills);
        System.out.println("A legtöbbet táncolt fiúk: " + popularBois);
    }
}