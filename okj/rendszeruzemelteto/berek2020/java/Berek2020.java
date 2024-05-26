import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Berek2020 {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("berek2020.txt"));
        var dolgozok = new ArrayList<Dolgozo>();

        for(var i = 1; i < lines.size(); ++i) {
            dolgozok.add(new Dolgozo(lines.get(i)));
        }

        System.out.println("3. Feladat: Dolgozók száma: " + dolgozok.size());

        var totalBer = 0;
        for(var dolgozo : dolgozok) {
            totalBer += dolgozo.munkaBer;
        }

        System.out.printf("4. Feladat: Átlagbér: %.2f\n", (float) totalBer / dolgozok.size() / 1000);

        var bekertReszleg = System.console().readLine("5. Feladat: Írjon be 1 részleg nevet: ");
        var legtobbBeresDolgozo = (Dolgozo) null;
        var legtobbBer = 0;

        for(var dolgozo : dolgozok) {
            if(dolgozo.munkaReszleg.equals(bekertReszleg) && dolgozo.munkaBer > legtobbBer) {
                legtobbBeresDolgozo = dolgozo;
                legtobbBer = dolgozo.munkaBer;
            }
        }

        System.out.print("6. Feladat: ");

        if(legtobbBeresDolgozo == null) {
            System.out.println("A megadott részleg nem létezik a cégnél!");
        }else{
            System.out.println(legtobbBeresDolgozo.nev + " (" + legtobbBeresDolgozo.munkabaLepesEv + ") : " + legtobbBeresDolgozo.munkaBer + " FT");
        }

        System.out.println("7. Feladat:");

        var reszlegStat = new HashMap<String, Integer>();
        for(var dolgozo : dolgozok) {
            reszlegStat.put(dolgozo.munkaReszleg, reszlegStat.getOrDefault(dolgozo.munkaReszleg, 0) + 1);
        }

        for(var statEntry : reszlegStat.entrySet()) {
            System.out.println("    " + statEntry.getKey() + " - " + statEntry.getValue() + " fő");
        }
    }
}