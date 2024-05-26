import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Hianyzasok {

    public static void main(String[] args) throws IOException {
        var hianyzasok = new ArrayList<Hianyzas>();
        var honap = 0;
        var nap = 0;

        for(var line : Files.readAllLines(Path.of("naplo.txt"))) {
            var split = line.split(" ");

            if(split[0].equals("#")) {
                honap = Integer.parseInt(split[1]);
                nap = Integer.parseInt(split[2]);
            }else{
                hianyzasok.add(new Hianyzas(split[0] + ' ' +  split[1], split[2], honap, nap));
            }
        }

        System.out.println("2. Feladat: Hiányzások száma: " + hianyzasok.size());

        var igazoltHianyzasok = 0;
        var igazolatlanHianyzasok = 0;

        for(var hiany : hianyzasok) {
            for(var i = 0; i < hiany.orak.length(); ++i) {
                var ora = hiany.orak.charAt(i);

                if(ora == 'X') {
                    ++igazoltHianyzasok;
                }else if(ora == 'I') {
                    ++igazolatlanHianyzasok;
                }
            }
        }

        System.out.println("3. Feladat: Igazolt hiányzások: " + igazoltHianyzasok + ", igazolatlanok: " + igazolatlanHianyzasok);
        System.out.println("5. Feladat:");

        var beHonap = Integer.parseInt(System.console().readLine("Írj be egy hónap számot: "));
        var beNap = Integer.parseInt(System.console().readLine("Írj be egy nap számot: "));

        System.out.println("Azon a napon: " + hetnapja(beHonap, beNap) + " volt");
        System.out.println("6. Feladat:");

        var beTanNap = System.console().readLine("Írj be egy nap nevet! ");
        var beOraszam = Integer.parseInt(System.console().readLine("Írj be egy óraszámot! ")) - 1;
        var hianyzottak = 0;

        for(var hiany : hianyzasok) {
            if(beTanNap.equals(hetnapja(hiany.honap, hiany.nap))) {
                var ora = hiany.orak.charAt(beOraszam);

                if(ora == 'X' || ora == 'I') {
                    ++hianyzottak;
                }
            }
        }

        System.out.println("Ekkor " + hianyzottak + "-an hiányoztak");
        System.out.println("7. Feladat: ");

        var hianyzasMap = new HashMap<String, Integer>();
        for(var hiany : hianyzasok) {
            var hianyzottak = 0;

            for(var i = 0; i < hiany.orak.length(); ++i) {
                var ora = hiany.orak.charAt(i);

                if(ora == 'X' || ora == 'I') {
                    ++hianyzottak;
                }
            }

            hianyzasMap.put(hiany.nev, hianyzasMap.getOrDefault(hiany.nev, 0) + hianyzottak);
        }

        var legtobbHianyzas = -1;
        for(var v : hianyzasMap.values()) {
            if(v > legtobbHianyzas) {
                legtobbHianyzas = v;
            }
        }

        for(var e : hianyzasMap.entrySet()) {
            if(e.getValue() == legtobbHianyzas) {
                System.out.print(e.getKey() + ' ');
            }
        }
    }

    public static final String[] napnev = { "vasarnap", "hetfo", "kedd", "szerda", "csutortok", "pentek", "szombat" };
    public static final int[] napszam = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 335 };

    public static String hetnapja(int honap, int nap) {
        return napnev[(napszam[honap - 1] + nap) % 7];
    }
}