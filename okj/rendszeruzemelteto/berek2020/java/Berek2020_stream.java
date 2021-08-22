import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Berek2020_stream {

    public static void main(String[] args) throws IOException {
        var dolgozok = Files.lines(Path.of("berek2020.txt"))
                            .skip(1)
                            .map(Dolgozo::new)
                            .toArray(Dolgozo[]::new);

        System.out.println("3. Feladat: Dolgozók száma: " + dolgozok.length);

        var atlagBer = Arrays.stream(dolgozok)
                             .mapToInt(k -> k.munkaBer)
                             .average()
                             .orElse(0);

        System.out.printf("4. Feladat: Átlagbér: %.2f\n", atlagBer / 1000);
        System.out.println("5. Feladat: Írjon be 1 részleg nevet!");
        System.out.print("6. Feladat: ");

        var bekertReszleg = readLineFromConsole();

        Arrays.stream(dolgozok)
              .filter(k -> k.munkaReszleg.equals(bekertReszleg))
              .max(Comparator.comparingInt(k -> k.munkaBer))
              .ifPresentOrElse(k -> System.out.println(k.nev + " (" + k.munkabaLepesEv + ") : " + k.munkaBer + " FT"),
                              () -> System.out.println("A megadott részleg nem létezik a cégnél!"));

        System.out.println("7. Feladat:");

        Arrays.stream(dolgozok)
              .collect(Collectors.groupingBy(k -> k.munkaReszleg, Collectors.counting()))
              .forEach((reszleg, dbSzam) -> System.out.println("    " + reszleg + " - " + dbSzam + " fő"));
    }


    static String readLineFromConsole() {
        try(var input = new Scanner(System.in)) {
            return input.nextLine();
        }
    }
}