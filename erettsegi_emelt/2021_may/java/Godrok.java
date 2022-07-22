import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Godrok {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("melyseg.txt"));
        var melysegek = new int[lines.size()];

        for(var i = 0; i < lines.size(); ++i) {
            melysegek[i] = Integer.parseInt(lines.get(i));
        }

        System.out.println("1. Feladat: " + melysegek.length);
        System.out.println("2. Feladat: Írjon be egy távolságértéket!");

        var bekertTavolsagIndexe = szamotBeker() - 1;
        var melysegABekertHelyen = melysegek[bekertTavolsagIndexe];

        System.out.println("A felszín " + melysegABekertHelyen + "m mélyen van");

        var erintetlenFeluletSzam = 0;
        for(var melyseg : melysegek) {
            if(melyseg == 0) {
                ++erintetlenFeluletSzam;
            }
        }

        System.out.printf("3. Feladat: Érintetlen felszín: %.2f%%\n", (float) erintetlenFeluletSzam / melysegek.length * 100);

        var godrok = new ArrayList<int[]>();
        var godorKezdoZaroIndexek = new ArrayList<Integer>();

        for(var i = 0; i < melysegek.length - 1; ++i) {
            var elsoMelyseg = melysegek[i];
            var masodikMelyseg = melysegek[i + 1];

            if(elsoMelyseg == 0 && masodikMelyseg != 0) {
                godorKezdoZaroIndexek.add(i + 1);
            }else if(elsoMelyseg != 0 && masodikMelyseg == 0) {
                godrok.add(Arrays.copyOfRange(melysegek, godorKezdoZaroIndexek.get(godorKezdoZaroIndexek.size() - 1), i + 1));
                godorKezdoZaroIndexek.add(i + 1);
            }
        }

        try(var output = new PrintWriter("godrok.txt")) {
            for(var godor : godrok) {
                for(var melyseg : godor) {
                    output.print(melyseg + " ");
                }

                output.println();
            }
        }

        System.out.println("5. Feladat: Gödrök száma: " + godrok.size());

        if(melysegABekertHelyen == 0) {
            System.out.println("6. Feladat: Az adott helyen nincs gödör");
        }else{
            var bekertGodorIndex = 0;
            for(var i = 0; i < godorKezdoZaroIndexek.size(); i += 2) {
                if(bekertTavolsagIndexe >= godorKezdoZaroIndexek.get(i) && bekertTavolsagIndexe <= godorKezdoZaroIndexek.get(i + 1)) {
                    bekertGodorIndex = i / 2;
                }
            }

            var bekertHelyKezdoGodorTavolsag = godorKezdoZaroIndexek.get(bekertGodorIndex).intValue() + 1;
            var bekertHelyZaroGodorTavolsag = godorKezdoZaroIndexek.get(bekertGodorIndex + 1).intValue();

            System.out.println("    a) Gödör kezdete: " + bekertHelyKezdoGodorTavolsag + "m, vége: " + bekertHelyZaroGodorTavolsag + "m");

            var aGodor = godrok.get(bekertGodorIndex);
            var legmelyebbPontIndex = 0;

            for(var i = 0; i < aGodor.length; ++i) {
                if(aGodor[i] > aGodor[legmelyebbPontIndex]) {
                    legmelyebbPontIndex = i;
                }
            }

            var balSzeltolLegnagyobbigNo = true;
            for(var i = 0; i < legmelyebbPontIndex - 1; ++i) {
                if(aGodor[i] > aGodor[i + 1]) {
                    balSzeltolLegnagyobbigNo = false;
                    break;
                }
            }

            var legnagyobbtolJobbSzeligCsokken = true;
            for(var i = legmelyebbPontIndex + 1; i < aGodor.length - 1; ++i) {
                if(aGodor[i] > aGodor[i + 1]) {
                    legnagyobbtolJobbSzeligCsokken = false;
                    break;
                }
            }

            System.out.println("    b) " + (balSzeltolLegnagyobbigNo && legnagyobbtolJobbSzeligCsokken ? "Folyamatosan Mélyül" : "Nem mélyül folyamatosan"));
            System.out.println("    c) Legnagyobb méység: " + aGodor[legmelyebbPontIndex] + "m");

            var godorMelysegekSum = 0;
            for(var melyseg : aGodor) {
                godorMelysegekSum += melyseg;
            }

            var terfogat = godorMelysegekSum * 10;
            var vizmennyiseg = terfogat - 10 * (bekertHelyZaroGodorTavolsag - bekertHelyKezdoGodorTavolsag + 1);

            System.out.println("    d) Térfogat: " + terfogat + "m^3");
            System.out.println("    e) Vízmennyiség: " + vizmennyiseg + "m^3");
        }
    }


    static int szamotBeker() {
        try(var input = new Scanner(System.in)) {
            return input.nextInt();
        }
    }
}