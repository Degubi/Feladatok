import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nasa {

    public static void main(String[] args) throws IOException {
        var keresek = new ArrayList<Keres>();
        for(var line : Files.readAllLines(Path.of("NASAlog.txt"))) {
            keresek.add(new Keres(line));
        }
        
        System.out.println("5. Feladat: Kérések száma: " + keresek.size());
        
        var osszmeret = 0;
        for(var keres : keresek) {
            osszmeret += keres.byteMeret();
        }
        
        System.out.println("6. Feladat: Összméret: " + osszmeret + " byte");
        
        var domainesek = 0;
        for(var keres : keresek) {
            if(keres.domain()){
                ++domainesek;
            }
        }
        
        System.out.printf("8. Feladat: Domaines kérések: %.2f%%\n", ((float) domainesek) / keresek.size() * 100);
        System.out.println("9. Feladat:");
        
        var stat = new HashMap<String, Integer>();
        for(var keres : keresek) {
            stat.put(keres.httpKod, stat.getOrDefault(keres.httpKod, 0) + 1);
        }
        
        for(var entry : stat.entrySet()) {
            System.out.println("    " + entry.getKey() + ": " + entry.getValue() + " db");
        }
    }
}