import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HegyekMo {
    public static void main(String[] args) throws IOException{
        var sorok = Files.readAllLines(Path.of("hegyekMo.txt"));
        var hegyek = new ArrayList<Hegy>();
        
        for(var i = 1; i < sorok.size(); ++i) {
            hegyek.add(new Hegy(sorok.get(i)));
        }
        
        System.out.println("3. Feladat: Hegyek száma: " + hegyek.size() + " db");
        
        var osszMagassag = 0D;
        for(var hegy : hegyek) {
            osszMagassag += hegy.magassag;
        }
        
        System.out.println("4. Feladat: Átlagmagasság: " + (osszMagassag / hegyek.size()) + " m");
        
        Hegy legmagasabbHegy = null;
        var legnagyobbMagassag = 0;
        for(int i = 0; i < hegyek.size(); i++) {
            var hegy = hegyek.get(i);
            
            if(hegy.magassag > legnagyobbMagassag) {
                legnagyobbMagassag = hegy.magassag;
                legmagasabbHegy = hegy;
            }
        }
        
        System.out.println("5. Feladat: Legmagasabb hegy: " + legmagasabbHegy.hegyseg + "-ben a " + legmagasabbHegy.nev + ", magassága: " + legmagasabbHegy.magassag + " m");
        
        try(var input = new Scanner(System.in)){
            System.out.println("6. Feladat: Írj be egy magasságot!");
            var beMagassag = input.nextInt();
            
            var vanMagasabb = false;
            for(var hegy : hegyek) {
                if(hegy.hegyseg.equals("Börzsöny") && hegy.magassag > beMagassag) {
                    vanMagasabb = true;
                    break;
                }
            }
            
            if(vanMagasabb) {
                System.out.println("Van magasabb hegység ennél a Börzsönyben");
            }else {
                System.out.println("Nincs magasabb hegység ennél a Börzsönyben");
            }
        }
        
        var konvertaltLab3000 = 3000D / 3.280839895D;
        var magasakSzama = 0;
        
        for(var hegy : hegyek) {
            if(hegy.magassag > konvertaltLab3000) {
                ++magasakSzama;
            }
        }
        
        System.out.println("7. Feladat: 3000 lábnál magasabbak száma: " + magasakSzama);
        System.out.println("8. Feladat: Hegység stat");
        
        var hegysegStat = new HashMap<String, Integer>();
        for(var hegy : hegyek) {
            hegysegStat.put(hegy.hegyseg, hegysegStat.getOrDefault(hegy.hegyseg, 0) + 1);
        }
        
        for(var entry : hegysegStat.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        
        try(var output = new PrintWriter("bukk-videk.txt")){
            output.println("Hegycsúcs neve;Magasság láb");
            
            for(var hegy : hegyek) {
                if(hegy.hegyseg.equals("Bükk-vidék")) {
                    var formazott = String.format("%s;%.2f", hegy.nev, hegy.magassag * 3.280839895D)
                                          .replace(',', '.');
                    
                    output.println(formazott);
                }
            }
        }
    }
}