import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class VizibicikliKolcsonzo {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("kolcsonzesek.txt"));
        var kolcsonzesek = new ArrayList<Kolcsonzes>();

        for(var i = 1; i < lines.size(); ++i) {
            kolcsonzesek.add(new Kolcsonzes(lines.get(i)));
        }

        System.out.println("5. Feladat: Kölcsönzések száma: " + kolcsonzesek.size());

        try(var console = new Scanner(System.in)) {
            System.out.println("Írj be 1 nevet!");

            var bekertNev = console.nextLine();
            var bekertNevhezTartozoKolcsonzesek = new ArrayList<Kolcsonzes>();

            for(var kolcsonzes : kolcsonzesek) {
                if(kolcsonzes.nev.equals(bekertNev)) {
                    bekertNevhezTartozoKolcsonzesek.add(kolcsonzes);
                }
            }

            if(bekertNevhezTartozoKolcsonzesek.isEmpty()) {
                System.out.println("Nem volt ilyen nevű kölcsönző");
            }else{
                for(var kolcsonzes : bekertNevhezTartozoKolcsonzesek) {
                    System.out.println(kolcsonzes.elvitelIdopont + "-" + kolcsonzes.visszahozatalIdopont);
                }
            }

            System.out.println("Adj meg 1 időpontot! (óra:perc)");

            var bekertIdopontParts = console.nextLine().split(":");
            var bekertIdopont = LocalTime.of(Integer.parseInt(bekertIdopontParts[0]), Integer.parseInt(bekertIdopontParts[1]));

            System.out.println("7. Feladat:");

            for(var kolcsonzes : kolcsonzesek) {
                if(bekertIdopont.isAfter(kolcsonzes.elvitelIdopont) && bekertIdopont.isBefore(kolcsonzes.visszahozatalIdopont)) {
                    System.out.println("    " + kolcsonzes.elvitelIdopont + "-" + kolcsonzes.visszahozatalIdopont + ": " + kolcsonzes.nev);
                }
            }
        }

        var totalStartedHours = 0;
        for(var kolcsonzes : kolcsonzesek) {
            var minutesBetweenStartEnd = Duration.between(kolcsonzes.elvitelIdopont, kolcsonzes.visszahozatalIdopont)
                                                 .toMinutes();

            totalStartedHours += (int) Math.ceil(minutesBetweenStartEnd / 30.0);
        }

        System.out.println("8. Feladat: A bevétel " + (totalStartedHours * 2400) + " Ft");

        var pressFToPayRespect = new ArrayList<String>();
        for(var kolcsonzes : kolcsonzesek) {
            if(kolcsonzes.jarmuAzonosito.equals("F")) {
                pressFToPayRespect.add(kolcsonzes.elvitelIdopont + "-" + kolcsonzes.visszahozatalIdopont + ": " + kolcsonzes.nev);
            }
        }

        Files.write(Path.of("F.txt"), pressFToPayRespect);
        System.out.println("10. Feladat:");

        var azonositoStat = new HashMap<String, Integer>();
        for(var kolcsonzes : kolcsonzesek) {
            var key = kolcsonzes.jarmuAzonosito;

            azonositoStat.put(key, azonositoStat.getOrDefault(key, 0) + 1);
        }

        for(var entry : azonositoStat.entrySet()) {
            System.out.println("    " + entry.getKey() + " - " + entry.getValue());
        }
    }
}