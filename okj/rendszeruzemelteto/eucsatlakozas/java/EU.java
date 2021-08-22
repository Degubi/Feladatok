import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class EU {

    public static void main(String[] args) throws IOException {
        var csatlakozasok = new ArrayList<Csatlakozas>();
        var lines = Files.readAllLines(Path.of("EUcsatlakozas.txt"), StandardCharsets.ISO_8859_1);

        for(var line : lines) {
            csatlakozasok.add(new Csatlakozas(line));
        }

        System.out.println("3. Feladat: 2018-ig csatlakozott országok száma: " + csatlakozasok.size());

        var tagallamok2007ben = 0;
        for(var csati : csatlakozasok) {
            if(csati.datum.getYear() == 2007) {
                ++tagallamok2007ben;
            }
        }

        System.out.println("4. Feladat: 2007-ben csatlakozott országok száma: " + tagallamok2007ben);

        for(var csati : csatlakozasok) {
            if(csati.orszag.equals("Magyarország")) {
                System.out.println("5. Feladat: Magyarország csatlakozása: " + csati.datum);
                break;
            }
        }

        var voltEMajusban = false;
        for(var csati : csatlakozasok) {
            if(csati.datum.getMonth() == Month.MAY) {
                voltEMajusban = true;
                break;
            }
        }

        System.out.println("6. Feladat: " + (voltEMajusban ? "Volt" : "Nem volt") + " májusban csatlakozás");

        var utolso = csatlakozasok.get(0);
        for(var csati : csatlakozasok) {
            if(csati.datum.isAfter(utolso.datum)) {
                utolso = csati;
            }
        }

        System.out.println("7. Feladat: Utoljára csatlakozott: " + utolso.orszag);
        System.out.println("8. Feladat:");

        var stat = new HashMap<Integer, Integer>();
        for(var csati : csatlakozasok) {
            var ev = csati.datum.getYear();

            stat.put(ev, stat.getOrDefault(ev, 0) + 1);
        }

        for(var entry : stat.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue() + " db ország");
        }
    }
}