import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Tanciskola {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("tancrend.txt"));
        var tancok = new ArrayList<Tanc>();

        for(var i = 0; i < lines.size(); i += 3) {
            tancok.add(new Tanc(lines, i));
        }

        System.out.println("Első tánc neve: " + tancok.get(0).category + ", az utolsóé: " + tancok.get(tancok.size() - 1).category);

        var szambaCounter = 0;
        for(var dansz : tancok) {
            if(dansz.category.equals("samba")) {
                ++szambaCounter;
            }
        }

        System.out.println("Összesen " + szambaCounter + "-an szambásztak");

        var viliCat = new ArrayList<String>();
        for(var dansz : tancok) {
            if(dansz.woman.equals("Vilma") && !viliCat.contains(dansz.category)) {
                viliCat.add(dansz.category);
            }
        }

        System.out.println("Vilma által táncolt kategóriák: " + String.join(", ", viliCat));

        var readCat = System.console().readLine("Írj be 1 kategóriát: ");
        var hasPrinted = false;

        for(var dansz : tancok) {
            if(dansz.woman.equals("Vilma") && dansz.category.equals(readCat)) {
                System.out.println("Vilma a " + readCat + " kategóriában " + dansz.man + "-val táncolt");
                hasPrinted = true;
            }
        }

        if(!hasPrinted) {
            System.out.println("Vilma a " + readCat + " kategóriában nem táncolt");
        }

        var lanyokToTancalkalmak = new HashMap<String, Integer>();
        var fiukToTancalkalmak = new HashMap<String, Integer>();

        for(var dans : tancok) {
            lanyokToTancalkalmak.put(dans.woman, lanyokToTancalkalmak.getOrDefault(dans.woman, 0) + 1);
            fiukToTancalkalmak.put(dans.man, fiukToTancalkalmak.getOrDefault(dans.man, 0) + 1);
        }

        Files.writeString(Path.of("szereplok.txt"), "Lányok: " + String.join(", ", lanyokToTancalkalmak.keySet()) +
                                                    "\nFiúk: " + String.join(", ", fiukToTancalkalmak.keySet()));
        var grillMax = 0;
        for(var v : lanyokToTancalkalmak.values()) {
            if(v > grillMax) {
                grillMax = v;
            }
        }

        System.out.print("A legtöbbet táncolt lányok: ");
        for(var e : lanyokToTancalkalmak.entrySet()) {
            if(e.getValue() == grillMax) {
                System.out.print(e.getKey() + ' ');
            }
        }

        var boiMax = 0;
        for(var v : fiukToTancalkalmak.values()) {
            if(v > boiMax) {
                boiMax = v;
            }
        }

        System.out.print("\nA legtöbbet táncolt fiúk: ");
        for(var e : fiukToTancalkalmak.entrySet()) {
            if(e.getValue() == boiMax) {
                System.out.print(e.getKey() + ' ');
            }
        }
    }
}