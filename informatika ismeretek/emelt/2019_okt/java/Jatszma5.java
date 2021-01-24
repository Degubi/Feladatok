import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Jatszma5 {

    public static void main(String[] args) throws IOException {
        var menetek = Files.lines(Path.of("labdamenetek5.txt"))
                           .toArray(String[]::new);

        System.out.println("3. Feladat: Menetek száma: " + menetek.length);

        var adogatoDB = Arrays.stream(menetek)
                               .filter(k -> k.equals("A"))
                               .count();

        System.out.printf("4. Feladat: Adogató: %.10f%%\n", ((double) adogatoDB / menetek.length * 100));

        var leghosszabbSorozat = Arrays.stream(menetek)
                                       .reduce(new int[2], Jatszma5::leghosszabbSorozatAllapotAccumulator, (l, r) -> l)[1];

        System.out.println("5. Feladat: Leghosszabb sorozat: " + leghosszabbSorozat);

        var probaJatek = new Jatek("Mahut", "Isner", "FAFAA");
        probaJatek.hozzaad('A');

        System.out.println("7. Feladat: Állás: " + probaJatek.getAllas() + ", befejeződött: " + (probaJatek.jatekVege() ? "igen" : "nem"));

        // A 8-9. feladatot felfogni se bírtam, hogy mit szeretett volna...
    }

    public static int[] leghosszabbSorozatAllapotAccumulator(int[] accumulator, String element) {
        return new int[] {
             element.equals("A") ? accumulator[0] + 1 : 0,
             Math.max(accumulator[0], accumulator[1])
        };
    }

    /* 5. Feladat Normál megoldáshoz
    public static int leghosszabbSorozatotKeres(String[] menetek) {
        var count = 0;
        var result = 0;

        for(var element : menetek) {
            count = element.equals("A") ? count + 1 : 0;
            result = Math.max(result, count);
        }

        return result;
    }*/
}