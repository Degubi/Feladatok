import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Snooker {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("snooker.txt"), StandardCharsets.ISO_8859_1);
        var versenyzok = new ArrayList<Versenyzo>();
        
        for(var i = 1; i < lines.size(); ++i) {
            versenyzok.add(new Versenyzo(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Versenyzők száma: " + versenyzok.size());
        
        var bevetelAtlag = 0;
        for(var vers : versenyzok) {
            bevetelAtlag += vers.nyeremeny;
        }
        
        System.out.printf("4. Feladat: Átlag kereset: %.2f\n", (double) (bevetelAtlag / versenyzok.size()));
        
        var kicikinai = versenyzok.get(0);
        for(var vers : versenyzok) {
            if(vers.orszag.equals("Kína") && vers.nyeremeny > kicikinai.nyeremeny) {
                kicikinai = vers;
            }
        }
        
        System.out.println("5. Feladat: Legjobban kereső kínai versenyző:\n" +
                           "    Helyezés: " + kicikinai.helyezes + "\n" +
                           "    Ország: Kína\n" +
                           "    Nyeremény: " + (kicikinai.nyeremeny * 380) + " FT");
        
        var vanENorveg = false;
        for(var vers : versenyzok) {
            if(vers.orszag.equals("Norvégia")) {
                vanENorveg = true;
                break;
            }
        }
        
        System.out.println("6. Feladat: " + (vanENorveg ? "Van" : "Nincs") + " norvég játékos");
        
        var orszagStat = new HashMap<String, Integer>();
        for(var vers : versenyzok) {
            var orszag = vers.orszag;
            
            orszagStat.put(orszag, orszagStat.getOrDefault(orszag, 0) + 1);
        }
        
        System.out.println("7. Feladat: Statisztika:");
        for(var entry : orszagStat.entrySet()) {
            if(entry.getValue() > 4) {
                System.out.println(entry.getKey() + ": " + entry.getValue() + " fő");
            }
        }
    }
}