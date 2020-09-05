import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class Kosar2004 {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("eredmenyek.csv"), StandardCharsets.ISO_8859_1);
        var eredmenyek = new ArrayList<Eredmeny>();

        for(var i = 1; i < lines.size(); ++i) {
            eredmenyek.add(new Eredmeny(lines.get(i)));
        }

        var hazaiMadridDb = 0;
        var idegenMadridDb = 0;

        for(var eredmeny : eredmenyek) {
            if(eredmeny.hazaiCsapat.equals("Real Madrid")) {
                ++hazaiMadridDb;
            }

            if(eredmeny.idegenCsapat.equals("Real Madrid")) {
                ++idegenMadridDb;
            }
        }

        System.out.printf("3. Feladat: Hazai: %d, idegen: %d\n", hazaiMadridDb, idegenMadridDb);

        var volteDontetlen = false;
        for(var eredmeny : eredmenyek) {
            if(eredmeny.hazaiPont == eredmeny.idegenPont) {
                volteDontetlen = true;
                break;
            }
        }

        System.out.println("4. Feladat: Volt e döntetlen: " + (volteDontetlen ? "igen" : "nem"));

        var barcelonaNeve = "";
        for(var eredmeny : eredmenyek) {
            if(eredmeny.hazaiCsapat.contains("Barcelona")) {
                barcelonaNeve = eredmeny.hazaiCsapat;
                break;
            }

            if(eredmeny.idegenCsapat.contains("Barcelona")) {
                barcelonaNeve = eredmeny.idegenCsapat;
                break;
            }
        }

        System.out.println("5. Feladat: Barcelona csapat neve: " + barcelonaNeve);
        System.out.println("6. Feladat:");

        var hatosFeladatIdopont = LocalDate.of(2004, Month.NOVEMBER, 21);
        for(var eredmeny : eredmenyek) {
            if(eredmeny.idopont.equals(hatosFeladatIdopont)) {
                System.out.printf("    %s - %s (%d:%d)\n", eredmeny.hazaiCsapat, eredmeny.idegenCsapat, eredmeny.hazaiPont, eredmeny.idegenPont);
            }
        }

        System.out.println("7. Feladat:");

        var helszinenkentiMerkozesszamok = new HashMap<String, Integer>();
        for(var eredmeny : eredmenyek) {
            var helyszin = eredmeny.helyszin;

            helszinenkentiMerkozesszamok.put(helyszin, helszinenkentiMerkozesszamok.getOrDefault(helyszin, 0) + 1);
        }

        for(var entry : helszinenkentiMerkozesszamok.entrySet()) {
            if(entry.getValue() > 20) {
                System.out.println("    " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}