import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SudokuCLI {

    public static void main(String[] args) throws IOException {
        var feladvanyok = new ArrayList<Feladvany>();

        for(var line : Files.readAllLines(Path.of("feladvanyok.txt"))) {
            feladvanyok.add(new Feladvany(line));
        }

        System.out.println("3. Feladat: Feladványok száma: " + feladvanyok.size());
        System.out.println("4. Feladat:");

        try(var input = new Scanner(System.in)) {
            var bekertMeret = meretetBeker(input);
            var bekertMeretuek = new ArrayList<Feladvany>();

            for(var feladvany : feladvanyok) {
                if(feladvany.meret == bekertMeret) {
                    bekertMeretuek.add(feladvany);
                }
            }

            var kivalasztott = bekertMeretuek.get(new Random().nextInt(bekertMeretuek.size()));

            System.out.println("Ebből a méretből " + bekertMeretuek.size() + " db van");
            System.out.println("5. Feladat: " + kivalasztott.feladvanyTeljes);

            var kitoltottCellakSzama = 0;
            for(var i = 0; i < kivalasztott.feladvanyTeljes.length(); ++i) {
                if(kivalasztott.feladvanyTeljes.charAt(i) != '0') {
                    ++kitoltottCellakSzama;
                }
            }

            System.out.printf("6. Feladat: A feladvány kitöltöttsége: %.0f%%\n", ((float) kitoltottCellakSzama / kivalasztott.feladvanyTeljes.length() * 100));
            System.out.println("7. Feladat:");
            kivalasztott.kirajzol();

            var bekertMeretuekFeladvanyai = new ArrayList<String>();
            for(var feladvany : bekertMeretuek) {
                bekertMeretuekFeladvanyai.add(feladvany.feladvanyTeljes);
            }

            Files.write(Path.of("sudoku" + bekertMeret + ".txt"), bekertMeretuekFeladvanyai);
        }
    }

    public static int meretetBeker(Scanner input) {
        while(true) {
            System.out.println("Kérem 1 feladvány méretét!");
            var bekertMeret = input.nextInt();

            if(bekertMeret >= 4 && bekertMeret <= 9) {
                return bekertMeret;
            }
        }
    }
}