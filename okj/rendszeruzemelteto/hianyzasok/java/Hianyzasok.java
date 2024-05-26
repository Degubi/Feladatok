import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Hianyzasok {

    public static void main(String[] args) throws IOException {
        var hianyzasok = new ArrayList<Hianyzas>();
        var lines = Files.readAllLines(Path.of("szeptember.csv"));

        for(var i = 1; i < lines.size(); i++) {
            hianyzasok.add(new Hianyzas(lines.get(i)));
        }

        var totalHianyzottOrak = 0;
        for(var e : hianyzasok) {
            totalHianyzottOrak += e.mulasztottOrak;
        }

        System.out.println("2. Feladat: Hiányzott órák: " + totalHianyzottOrak);

        var bekertNap = Integer.parseInt(System.console().readLine("3. Feladat:\nÍrj be egy napot(1-30): "));
        var bekertNev = System.console().readLine("Írj be egy nevet: ");

        var hianyzottE = false;
        for(var hianyzas : hianyzasok) {
            if(hianyzas.nev.equals(bekertNev)) {
                hianyzottE = true;
                break;
            }
        }

        System.out.println("4. Feladat: " + bekertNev + (hianyzottE ? " hianyzott" : " nem hianyzott"));
        System.out.println("5. Feladat:");

        var azonANaponHianyoztak = new ArrayList<Hianyzas>();
        for(var hianyzas : hianyzasok) {
            if(bekertNap >= hianyzas.elsoNap && bekertNap <= hianyzas.utolsoNap) {
                azonANaponHianyoztak.add(hianyzas);
            }
        }

        if(azonANaponHianyoztak.size() == 0) {
            System.out.println("Nem volt hiányzó");
        }else{
            for(var hiany : azonANaponHianyoztak) {
                System.out.println(hiany.nev + " " + hiany.osztaly);
            }
        }

        var hianyzasokStat = new HashMap<String, Integer>();
        for(var hianyzas : hianyzasok) {
            hianyzasokStat.put(hianyzas.osztaly, hianyzasokStat.getOrDefault(hianyzas.osztaly, 0) + hianyzas.mulasztottOrak);
        }

        try(var file = new PrintWriter("osszesites.csv")) {
            for(var k : hianyzasokStat.entrySet()) {
                file.println(k.getKey() + ";" + k.getValue());
            }
        }
    }
}