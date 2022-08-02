import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Telekocsi {

    public static void main(String[] args) throws IOException {
        var autokLines = Files.readAllLines(Path.of("autok.csv"), StandardCharsets.ISO_8859_1);
        var autok = new ArrayList<Auto>();

        for(var i = 1; i < autokLines.size(); ++i) {
            autok.add(new Auto(autokLines.get(i)));
        }

        System.out.println("2. Feladat: Hirdetesek szama: " + autok.size());

        var bpToMiskolc = 0;
        for(var auto : autok) {
            if(auto.indulas.equals("Budapest") && auto.cel.equals("Miskolc")) {
                bpToMiskolc += auto.ferohely;
            }
        }

        System.out.println("3. Feladat: BP to Miskolc hely: " + bpToMiskolc);

        var ferohelyStat = new HashMap<String, Integer>();
        for(var auto : autok) {
            var key = auto.indulas + '-' + auto.cel;

            ferohelyStat.put(key, ferohelyStat.getOrDefault(key, 0) + auto.ferohely);
        }

        var maxFerohelyEntry = ferohelyStat.entrySet().iterator().next();
        for(var e : ferohelyStat.entrySet()) {
            if(e.getValue() > maxFerohelyEntry.getValue()) {
                maxFerohelyEntry = e;
            }
        }

        System.out.println("4. Feladat: " + maxFerohelyEntry.getKey() + ": " + maxFerohelyEntry.getValue() + " hely");
        System.out.println("5. Feladat");

        var igenyekLines = Files.readAllLines(Path.of("igenyek.csv"), StandardCharsets.ISO_8859_1);
        var igenyek = new ArrayList<Igeny>();

        for(var i = 1; i < igenyekLines.size(); ++i) {
            igenyek.add(new Igeny(igenyekLines.get(i)));
        }

        var fileba = new ArrayList<String>();
        for(var igeny : igenyek) {
            var autoIgenyre = autotKeresIgenyre(igeny, autok);

            if(autoIgenyre != null) {
                fileba.add(igeny.azonosito + ": Rendszam: " + autoIgenyre.rendszam + ", Telefonszam: " + autoIgenyre.telefonszam);

                System.out.println(igeny.azonosito + " -> " + autoIgenyre.rendszam);
            }else{
                fileba.add(igeny.azonosito + ": " + "Sajnos nem sikerült autót találni");
            }
        }

        Files.write(Path.of("utazasuzenetek.txt"), fileba);
    }

    static Auto autotKeresIgenyre(Igeny igeny, ArrayList<Auto> autok) {
        for(var auto : autok) {
            if(auto.indulas.equals(igeny.indulas) && auto.cel.equals(igeny.cel) && auto.ferohely >= igeny.szemelyek) {
                return auto;
            }
        }

        return null;
    }
}