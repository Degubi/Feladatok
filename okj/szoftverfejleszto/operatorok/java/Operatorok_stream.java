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

        var operatorDbSzamok = Arrays.stream(kifejezesek)
                                     .collect(Collectors.groupingBy(k -> k.operator, Collectors.counting()));

        System.out.println("3. Feladat: Maradékos osztások száma: " + operatorDbSzamok.get("mod"));

        var vanETizzelOszthatoOperandusu = Arrays.stream(kifejezesek)
                                                 .anyMatch(k -> k.elsoOperandus % 10 == 0 && k.masodikOperandus % 10 == 0);

        System.out.println("4. Feladat: " + (vanETizzelOszthatoOperandusu ? "Van" : "Nincs") + " ilyen kifejezés");
        System.out.println("5. Feladat: \n" +
                           "    'mod' -> " + operatorDbSzamok.get("mod") + " db\n" +
                           "      '/' -> " + operatorDbSzamok.get("/") + " db\n" +
                           "    'div' -> " + operatorDbSzamok.get("div") + " db\n" +
                           "      '-' -> " + operatorDbSzamok.get("-") + " db\n" +
                           "      '*' -> " + operatorDbSzamok.get("*") + " db\n" +
                           "      '+' -> " + operatorDbSzamok.get("+") + " db");

        Stream.generate(() -> System.console().readLine("Kérek egy kifejezést: "))
              .takeWhile(k -> !k.equals("vége"))
              .forEach(k -> System.out.println(k + " = " + new Kifejezes(k).kiertekel()));

        var kiertekeltSorok = Arrays.stream(kifejezesek)
                                    .map(k -> k.teljesKifejezes + " = " + k.kiertekel())
                                    .collect(Collectors.toList());

        Files.write(Path.of("eredmenyek.txt"), kiertekeltSorok);
    }
}