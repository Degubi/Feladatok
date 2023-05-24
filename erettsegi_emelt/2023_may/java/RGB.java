import java.io.*;
import java.nio.file.*;
import java.util.*;

public class RGB {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("kep.txt"));
        var pixelek = new Color[360][640];

        for(var oszlop = 0; oszlop < 360; ++oszlop) {
            var lineSplit = lines.get(oszlop).split(" ");
            var splitIndex = 0;

            for(var sor = 0; sor < 640; ++sor) {
                pixelek[oszlop][sor] = new Color(Integer.parseInt(lineSplit[splitIndex]), Integer.parseInt(lineSplit[splitIndex + 1]), Integer.parseInt(lineSplit[splitIndex + 2]));
                splitIndex += 3;
            }
        }

        try(var console = new Scanner(System.in)) {
            System.out.println("2. Feladat: Kérem egy képpont adatait!");
            System.out.println("Sor:");

            var bekertSorIndex = console.nextInt() - 1;

            System.out.println("Oszlop:");

            var bekertOszlopIndex = console.nextInt() - 1;

            System.out.println("Képpont színe: " + pixelek[bekertSorIndex][bekertOszlopIndex]);
        }

        var vilagosKeppontokSzama = 0;
        var legsotetebbKeppontErteke = 255 * 3;

        for(var sor : pixelek) {
            for(var oszlop : sor) {
                var szinSum = oszlop.sum();

                if(szinSum > 600) {
                    ++vilagosKeppontokSzama;
                }

                if(szinSum < legsotetebbKeppontErteke) {
                    legsotetebbKeppontErteke = szinSum;
                }
            }
        }

        System.out.println("3. Feladat: Világos képpontok száma: " + vilagosKeppontokSzama);
        System.out.println("4. Feladat: Legsötétebb képpont értéke: " + legsotetebbKeppontErteke);

        for(var sor : pixelek) {
            for(var oszlop : sor) {
                if(oszlop.sum() == legsotetebbKeppontErteke) {
                    System.out.println(oszlop);
                }
            }
        }

        System.out.println("6. Feladat:");

        for(var i = 0; i < 640; ++i) {
            if(hatar(i, 10, pixelek)) {
                System.out.println("Felhő legfelső sora: " + (i + 1));
                break;
            }
        }

        for(var i = 639; i >= 0; --i) {
            if(hatar(i, 10, pixelek)) {
                System.out.println("Felhő legalsó sora: " + (i + 1));
                break;
            }
        }
    }

    private static boolean hatar(int sorSzam, int elteres, Color[][] pixelek) {
        var sor = pixelek[sorSzam];

        for(var i = 0; i < 639; ++i) {
            if(Math.abs(sor[i].blue - sor[i + 1].blue) > elteres) {
                return true;
            }
        }

        return false;
    }
}