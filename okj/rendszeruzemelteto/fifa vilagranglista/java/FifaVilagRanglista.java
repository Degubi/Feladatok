import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class FifaVilagRanglista {
    
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("fifa.txt"), StandardCharsets.ISO_8859_1);
        var eredmenyek = new ArrayList<Eredmeny>();
        
        for(var i = 1; i < lines.size(); ++i) {
            eredmenyek.add(new Eredmeny(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Csapatok száma: " + eredmenyek.size());
        
        var eredmenyTotal = 0F;
        for(var eredmeny : eredmenyek) {
            eredmenyTotal += eredmeny.pontszam;
        }
        
        System.out.printf("4. Feladat: Átlagpontszám: %.2f\n", eredmenyTotal / eredmenyek.size());
        
        var legtobbetJavito = eredmenyek.get(0);
        for(var eredmeny : eredmenyek) {
            if(eredmeny.valtozas > legtobbetJavito.valtozas) {
                legtobbetJavito = eredmeny;
            }
        }
        
        System.out.println("5. Feladat: Legtöbbet javító csapat: " + legtobbetJavito.csapat +
                           ", helyezés: " + legtobbetJavito.helyezes + ", pontszam: " + legtobbetJavito.pontszam);
        
        var volteMo = false;
        for(var eredmeny : eredmenyek) {
            if(eredmeny.csapat.equals("Magyarország")) {
                volteMo = true;
                break;
            }
        }
        
        if(volteMo) {
            System.out.println("6. Feladat: Csapatok között van Magyarország");
        }else{
            System.out.println("6. Feladat: Csapatok között nincs Magyarország");
        }
                               
        System.out.println("7. Feladat:");
        
        var stat = new HashMap<Integer, Integer>();
        for(var eredmeny : eredmenyek) {
            stat.put(eredmeny.valtozas, stat.getOrDefault(eredmeny.valtozas, 0) + 1);
        }
        
        for(var entry : stat.entrySet()) {
            if(entry.getKey() > 1) {
                System.out.println("    " + entry.getKey() + " helyet változott: " + entry.getValue() + " csapat");
            }
        }
    }
}