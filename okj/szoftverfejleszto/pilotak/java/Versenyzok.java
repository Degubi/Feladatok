import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Versenyzok {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("pilotak.csv"));
        var pilotak = new ArrayList<Pilota>();

        for(var i = 1; i < lines.size(); ++i) {
            pilotak.add(new Pilota(lines.get(i)));
        }

        System.out.println("3. Feladat: Adatsorok száma: " + pilotak.size());
        System.out.println("4. Feladat: Utolsó pilóta neve: " + pilotak.get(pilotak.size() - 1).nev);
        System.out.println("5. Feladat:");

        for(var pilota : pilotak) {
            var szuletesiEv = pilota.szuletesiDatum.getYear();

            if(szuletesiEv >= 1800 && szuletesiEv <= 1900) {
                System.out.println("    " + pilota.nev + " (" + pilota.szuletesiDatum + ")");
            }
        }

        var legkisebbRajtszamuPilota = pilotak.get(0);
        for(var pilota : pilotak) {
            if(pilota.rajtszam != Pilota.URES_RAJTSZAM && pilota.rajtszam < legkisebbRajtszamuPilota.rajtszam) {
                legkisebbRajtszamuPilota = pilota;
            }
        }

        System.out.println("6. Feladat: " + legkisebbRajtszamuPilota.nemzetiseg);
        System.out.print("7. Feladat: ");

        var rajtszamStat = new HashMap<Integer, Integer>();
        for(var pilota : pilotak) {
            var rajtszam = pilota.rajtszam;

            if(rajtszam != Pilota.URES_RAJTSZAM) {
                rajtszamStat.put(rajtszam, rajtszamStat.getOrDefault(rajtszam, 0) + 1);
            }
        }

        for(var rajtszamStatEntry : rajtszamStat.entrySet()) {
            if(rajtszamStatEntry.getValue() > 1) {
                System.out.print(rajtszamStatEntry.getKey() + " ");
            }
        }
    }
}