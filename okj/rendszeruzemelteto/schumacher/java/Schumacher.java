import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Schumacher {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("schumacher.csv"));
        var eredmenyek = new ArrayList<Eredmeny>();

        for(var i = 1; i < lines.size(); ++i) {
            eredmenyek.add(new Eredmeny(lines.get(i)));
        }

        System.out.println("3. Feladat: Adatsorok száma: " + eredmenyek.size());
        System.out.println("4. Feladat:");

        for(var eredmeny : eredmenyek) {
            if(eredmeny.dijNev.equals("Hungarian Grand Prix") && eredmeny.helyezes != 0) {
                System.out.println("    " + eredmeny.datum + ": " + eredmeny.helyezes + ". hely");
            }
        }

        var celbaeresStat = new HashMap<String, Integer>();
        for(var eredmeny : eredmenyek) {
            var key = eredmeny.vegeredmenyStatusz;

            if(eredmeny.helyezes == 0) {
                celbaeresStat.put(key, celbaeresStat.getOrDefault(key, 0) + 1);
            }
        }

        System.out.println("5. Feladat:");

        for(var celbaeres : celbaeresStat.entrySet()) {
            if(celbaeres.getValue() > 2) {
                System.out.println("    " + celbaeres.getKey() + ": " + celbaeres.getValue());
            }
        }
    }
}