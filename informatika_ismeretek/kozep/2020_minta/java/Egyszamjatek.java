import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Egyszamjatek {

    public static void main(String[] args) throws IOException {
        var jatekosok = new HashMap<String, int[]>();
        for(var line : Files.readAllLines(Path.of("egyszamjatek1.txt"))) {
            var split = line.split(" ");
            var tippek = new int[split.length - 1];

            for(var i = 1; i < split.length; ++i) {
                tippek[i - 1] = Integer.parseInt(split[i]);
            }

            jatekosok.put(split[0], tippek);
        }

        System.out.println("3. Feladat: Játékosok száma: " + jatekosok.size());

        var beForduloSzam = Integer.parseInt(System.console().readLine("4. Feladat: Írj be egy forduló számot: ")) - 1;
        var forduloTotal = 0F;

        for(var tippek : jatekosok.values()) {
            forduloTotal += tippek[beForduloSzam];
        }

        System.out.printf("5. Feladat: Tippek átlaga: %.2f", forduloTotal / jatekosok.size());
    }
}