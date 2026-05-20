import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class RGB_stream {

    public static void main(String[] args) throws IOException {
        var pixelek2D = Files.lines(Path.of("kep.txt"))
                             .map(RGB_stream::kepSortBeolvas)
                             .toArray(Color[][]::new);

        System.out.println("2. Feladat: Kérem egy képpont adatait!");

        var bekertSorIndex = Integer.parseInt(System.console().readLine("Sor: ")) - 1;
        var bekertOszlopIndex = Integer.parseInt(System.console().readLine("Oszlop: ")) - 1;

        System.out.println("Képpont színe: " + pixelek2D[bekertSorIndex][bekertOszlopIndex]);

        var pixelek1D = Arrays.stream(pixelek2D)
                              .flatMap(Arrays::stream)
                              .toArray(Color[]::new);

        var vilagosKeppontokSzama = Arrays.stream(pixelek1D)
                                          .filter(k -> k.sum() > 600)
                                          .count();

        System.out.println("3. Feladat: Világos képpontok száma: " + vilagosKeppontokSzama);

        var legsotetebbKeppontErtek = Arrays.stream(pixelek1D)
                                            .mapToInt(Color::sum)
                                            .min()
                                            .orElseThrow();

        System.out.println("4. Feladat: Legsötétebb pont RGB összege: " + legsotetebbKeppontErtek);

        Arrays.stream(pixelek1D)
              .filter(k -> k.sum() == legsotetebbKeppontErtek)
              .forEach(System.out::println);

        var hatarOszlopIndexek = IntStream.range(0, pixelek2D.length)
                                          .filter(i -> hatar(i, 10, pixelek2D))
                                          .toArray();

        System.out.println("6. Feladat: Felhő legfelső sora: " + (hatarOszlopIndexek[0] + 1) + ", utolsó sora: " + (hatarOszlopIndexek[hatarOszlopIndexek.length - 1] + 1));
    }

    private static Color[] kepSortBeolvas(String line) {
        var split = line.split(" ");

        return IntStream.iterate(0, k -> k < split.length, k -> k + 3)
                        .mapToObj(i -> new Color(Integer.parseInt(split[i]), Integer.parseInt(split[i + 1]), Integer.parseInt(split[i + 2])))
                        .toArray(Color[]::new);
    }

    private static boolean hatar(int sorSzam, int elteres, Color[][] pixelek2D) {
        var sor = pixelek2D[sorSzam];

        return IntStream.range(0, sor.length - 1)
                        .anyMatch(i -> Math.abs(sor[i].blue - sor[i + 1].blue) > elteres);
    }
}