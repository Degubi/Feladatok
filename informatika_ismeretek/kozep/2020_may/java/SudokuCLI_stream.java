import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class SudokuCLI_stream {

    public static void main(String[] args) throws IOException {
        var feladvanyok = Files.lines(Path.of("feladvanyok.txt"))
                               .map(Feladvany::new)
                               .toArray(Feladvany[]::new);

        System.out.println("3. Feladat: Feladványok száma: " + feladvanyok.length);
        System.out.println("4. Feladat:");

        var bekertMeret = IntStream.generate(() -> Integer.parseInt(System.console().readLine("Kérem 1 feladvány méretét: ")))
                                   .filter(k -> k >= 4 && k <= 9)
                                   .findFirst()
                                   .orElseThrow();

        var bekertMeretuek = Arrays.stream(feladvanyok)
                                   .filter(k -> k.meret == bekertMeret)
                                   .toArray(Feladvany[]::new);

        var kivalasztott = bekertMeretuek[new Random().nextInt(bekertMeretuek.length)];

        System.out.println("Ebből a méretből " + bekertMeretuek.length + " db van");
        System.out.println("5. Feladat: " + kivalasztott.feladvanyTeljes);

        var kitoltottCellakSzama = kivalasztott.feladvanyTeljes.chars()
                                               .filter(k -> k != '0')
                                               .count();

        System.out.printf("6. Feladat: A feladvány kitöltöttsége: %.0f%%\n", ((float) kitoltottCellakSzama / kivalasztott.feladvanyTeljes.length() * 100));
        System.out.println("7. Feladat:");
        kivalasztott.kirajzol();

        var bekertMeretuekFeladvanyai = Arrays.stream(bekertMeretuek)
                                              .map(k -> k.feladvanyTeljes)
                                              .collect(Collectors.toList());

        Files.write(Path.of("sudoku" + bekertMeret + ".txt"), bekertMeretuekFeladvanyai);
    }
}