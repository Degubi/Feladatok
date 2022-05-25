import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Otszaz {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("penztar.txt"));
        var vasarlasok = new ArrayList<HashMap<String, Integer>>();
        var itemBuffer = new ArrayList<String>();

        for(var line : lines) {
            if(line.equals("F")) {
                var itemFreqMap = new HashMap<String, Integer>();

                for(var item : itemBuffer) {
                    itemFreqMap.put(item, itemFreqMap.getOrDefault(item, 0) + 1);
                }

                vasarlasok.add(itemFreqMap);
                itemBuffer.clear();
            }else{
                itemBuffer.add(line);
            }
        }

        System.out.println("2. Feladat: Vásárlások száma: " + vasarlasok.size());
        System.out.println("3. Feladat: Elsö vásárlásnál vett dolgok száma: " + vasarlasok.get(0).size());

        var console = new Scanner(System.in);
        System.out.println("4. Feladat:");
        System.out.println("Írj be 1 sorszámot");
        var bekertSorszam = console.nextInt();

        System.out.println("Írj be 1 árut");
        var bekertAru = console.next();

        System.out.println("Írj be 1 mennyiséget");
        var bekertDBszam = console.nextInt();
        console.close();

        var bekertAruOsszDbSzam = 0;
        var bekertAruUtolsoVasarlasIndex = 0;

        for(var k = 0; k < vasarlasok.size(); ++k) {
            for(var item : vasarlasok.get(k).keySet()) {
                if(item.equals(bekertAru)) {
                    ++bekertAruOsszDbSzam;
                    bekertAruUtolsoVasarlasIndex = k;

                    if(bekertAruOsszDbSzam == 1) {
                        System.out.println("Először a " + (k + 1) + ". vásárlásnál vettek " + bekertAru + "-t");
                    }
                }
            }
        }

        System.out.println("5. Feladat:");
        System.out.println("Utoljára a " + (bekertAruUtolsoVasarlasIndex + 1) + ". vásárlásnál vettek " + bekertAru + "-t");
        System.out.println("Összesen " + bekertAruOsszDbSzam + "-szor vettek " + bekertAru + "-t");
        System.out.println("6. Feladat: " + bekertDBszam + " db esetén a fizetendő: " + ertek(bekertDBszam));
        System.out.println("7. Feladat: A " + bekertSorszam + ". vásárláskor vásárolt dolgok: ");

        for(var bekertVasarlasItem : vasarlasok.get(bekertSorszam - 1).entrySet()) {
            System.out.println(bekertVasarlasItem.getKey() + "-ből: " + bekertVasarlasItem.getValue() + " db");
        }

        try(var output = new PrintWriter("osszeg.txt")) {
            for(var i = 0; i < vasarlasok.size(); ++i) {
                var irasDarab = 0;

                for(var dbSzam : vasarlasok.get(i).values()) {
                    irasDarab += ertek(dbSzam);
                }

                output.println((i + 1) + ": " + irasDarab);
            }
        }
    }

    public static int ertek(int dbSzam) {
        return dbSzam == 1 ? 500 : dbSzam == 2 ? 950 : 1350 + (400 * (dbSzam - 3));
    }
}