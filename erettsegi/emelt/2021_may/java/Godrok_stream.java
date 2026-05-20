import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Godrok_stream {

    public static void main(String[] args) throws IOException {
        var melysegek = Files.lines(Path.of("melyseg.txt"))
                             .mapToInt(Integer::parseInt)
                             .toArray();

        System.out.println("1. Feladat: " + melysegek.length);

        var bekertTavolsagIndexe = Integer.parseInt(System.console().readLine("2. Feladat: Írjon be egy távolságértéket: ")) - 1;
        var melysegABekertHelyen = melysegek[bekertTavolsagIndexe];

        System.out.println("A felszín " + melysegABekertHelyen + "m mélyen van");

        var erintetlenFeluletSzam = Arrays.stream(melysegek)
                                          .filter(k -> k == 0)
                                          .count();

        System.out.printf("3. Feladat: Érintetlen felszín: %.2f%%\n", (float) erintetlenFeluletSzam / melysegek.length * 100);

        var godorKezdoZaroIndexek = IntStream.range(0, melysegek.length - 1)
                                             .filter(i -> (melysegek[i] == 0 && melysegek[i + 1] != 0) || (melysegek[i] != 0 && melysegek[i + 1] == 0))
                                             .map(k -> k + 1)
                                             .toArray();

        var godrok = IntStream.iterate(0, k -> k < godorKezdoZaroIndexek.length, k -> k + 2)
                              .mapToObj(i -> Arrays.copyOfRange(melysegek, godorKezdoZaroIndexek[i], godorKezdoZaroIndexek[i + 1]))
                              .toArray(int[][]::new);

        var godrokFileba = Arrays.stream(godrok)
                                 .map(k -> Arrays.stream(k).mapToObj(Integer::toString).collect(Collectors.joining(" ")))
                                 .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("godrok.txt"), godrokFileba);

        System.out.println("5. Feladat: Gödrök száma: " + godrok.length);

        if(melysegABekertHelyen == 0) {
            System.out.println("6. Feladat: Az adott helyen nincs gödör");
        }else{
            var bekertGodorIndex = IntStream.iterate(0, k -> k < godorKezdoZaroIndexek.length, k -> k + 2)
                                            .filter(i -> bekertTavolsagIndexe >= godorKezdoZaroIndexek[i] && bekertTavolsagIndexe <= godorKezdoZaroIndexek[i + 1])
                                            .findFirst()
                                            .orElseThrow() / 2;

            var bekertHelyKezdoGodorTavolsag = godorKezdoZaroIndexek[bekertGodorIndex] + 1;
            var bekertHelyZaroGodorTavolsag = godorKezdoZaroIndexek[bekertGodorIndex + 1];

            System.out.println("    a) Gödör kezdete: " + bekertHelyKezdoGodorTavolsag + "m, vége: " + bekertHelyZaroGodorTavolsag + "m");

            var aGodor = godrok[bekertGodorIndex];
            var legmelyebbPontIndex = IntStream.range(0, aGodor.length)
                                               .boxed()
                                               .max(Comparator.comparingInt(i -> aGodor[i]))
                                               .orElseThrow()
                                               .intValue();

            var balSzeltolLegnagyobbigNo = IntStream.range(0, legmelyebbPontIndex - 1)
                                                    .allMatch(i -> aGodor[i] <= aGodor[i + 1]);

            var legnagyobbtolJobbSzeligCsokken = IntStream.range(legmelyebbPontIndex + 1, aGodor.length - 1)
                                                          .allMatch(i -> aGodor[i] >= aGodor[i + 1]);

            System.out.println("    b) " + (balSzeltolLegnagyobbigNo && legnagyobbtolJobbSzeligCsokken ? "Folyamatosan Mélyül" : "Nem mélyül folyamatosan"));
            System.out.println("    c) Legnagyobb méység: " + aGodor[legmelyebbPontIndex] + "m");

            var terfogat = Arrays.stream(aGodor).sum() * 10;
            var vizmennyiseg = terfogat - 10 * (bekertHelyZaroGodorTavolsag - bekertHelyKezdoGodorTavolsag + 1);

            System.out.println("    d) Térfogat: " + terfogat + "m^3");
            System.out.println("    e) Vízmennyiség: " + vizmennyiseg + "m^3");
        }
    }
}