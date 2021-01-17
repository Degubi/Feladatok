import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Operatorok_stream {

    public static void main(String[] args) throws IOException {
        var kifejezesek = Files.lines(Path.of("kifejezesek.txt"), StandardCharsets.ISO_8859_1)  // Kell encoding, mert különben elhal az egész... ??
                               .map(Kifejezes::new)
                               .toArray(Kifejezes[]::new);

        System.out.println("2. Feladat: Kifejezések száma: " + kifejezesek.length);

        var operatoronkentiDbSzam = Arrays.stream(kifejezesek)
                                          .collect(Collectors.groupingBy(k -> k.operator, Collectors.counting()));

        System.out.println("3. Feladat: Maradékos osztások száma: " + operatoronkentiDbSzam.get("mod"));

        var vanETizzelOszthatoOperandusu = Arrays.stream(kifejezesek)
                                                 .anyMatch(k -> k.elsoOperandus % 10 == 0 && k.masodikOperandus % 10 == 0);

        System.out.println("4. Feladat: " + (vanETizzelOszthatoOperandusu ? "Van" : "Nincs") + " ilyen kifejezés");
        System.out.println("5. Feladat: \n" +
                           "    'mod' -> " + operatoronkentiDbSzam.get("mod") + " db\n" +
                           "      '/' -> " + operatoronkentiDbSzam.get("/") + " db\n" +
                           "    'div' -> " + operatoronkentiDbSzam.get("div") + " db\n" +
                           "      '-' -> " + operatoronkentiDbSzam.get("-") + " db\n" +
                           "      '*' -> " + operatoronkentiDbSzam.get("*") + " db\n" +
                           "      '+' -> " + operatoronkentiDbSzam.get("+") + " db");

        try(var input = new Scanner(System.in)) {
            Stream.generate(() -> kifejezestBeker(input))
                  .takeWhile(k -> !k.equals("vége"))
                  .forEach(k -> System.out.println(k + " = " + new Kifejezes(k).kiertekel()));
        }

        var kiertekeltSorok = Arrays.stream(kifejezesek)
                                    .map(k -> k.teljesKifejezes + " = " + k.kiertekel())
                                    .collect(Collectors.toList());

        Files.write(Path.of("eredmenyek.txt"), kiertekeltSorok);
    }

    public static String kifejezestBeker(Scanner input) {
        System.out.println("Kérek egy kifejezést!");
        return input.nextLine();
    }
}