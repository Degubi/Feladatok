import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class Lift {
    public static void main(String[] args) throws IOException {
        var hasznalatok = new ArrayList<Hasznalat>();
        var lines = Files.readAllLines(Path.of("lift.txt"));

        for(var line : lines) {
            hasznalatok.add(new Hasznalat(line));
        }

        System.out.println("3. Feladat: Lift alkalmak száma: " + hasznalatok.size());
        System.out.println("4. Feladat: A korszak " + hasznalatok.get(0).idopont + " től " + hasznalatok.get(hasznalatok.size() - 1).idopont + "-ig tartott");

        var maxSzint = -1;
        for(var e : hasznalatok) {
            if(e.celSzint > maxSzint) {
                maxSzint = e.celSzint;
            }
        }

        System.out.println("5. Feladat: Max célszint: " + maxSzint);

        var beKartya = parseIntOrDefault(System.console().readLine("Írj be egy kártyaszámot: "), 5);
        var beCelszint = parseIntOrDefault(System.console().readLine("Írj be egy célszintet: "), 5);
        var kieg = "nem";

        for(var e : hasznalatok) {
            if(e.kartyaSorszam == beKartya && e.celSzint == beCelszint) {
                kieg = "";
                break;
            }
        }

        System.out.println("7. Feladat: A " + beKartya + " kártyával " + kieg + " utaztak a " + beCelszint + ". emeletre");
        System.out.println("8. Feladat");

        var hasznalatStat = new HashMap<LocalDate, Integer>();
        for(var e : hasznalatok) {
            var idopont = e.idopont;

            if(hasznalatStat.containsKey(idopont)) {
                hasznalatStat.replace(idopont, hasznalatStat.get(idopont) + 1);
            }else{
                hasznalatStat.put(idopont, 1);
            }
        }

        for(var stat : hasznalatStat.entrySet()) {
            System.out.println(stat.getKey() + " - " + stat.getValue() + "x");
        }
    }

    public static int parseIntOrDefault(String num, int defaultVal) {
        try {
            return Integer.parseInt(num);
        }catch(NumberFormatException e) {
            return defaultVal;
        }
    }
}