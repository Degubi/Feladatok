import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Otszaz {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Paths.get("penztar.txt"));
        var vasarlasok = new ArrayList<List<String>>();
       
        var toAdd = new ArrayList<String>();
        for(var sor : lines) {
            if(sor.equals("F")) {
                vasarlasok.add(new ArrayList<>(toAdd));
                toAdd.clear();
            }else{
                toAdd.add(sor);
            }
        }
        
        System.out.println("Vásárlások száma: " + vasarlasok.size());
        System.out.println("Elsö vásárlásnál vett dolgok száma: " + vasarlasok.get(0).size());
        
        var input = new Scanner(System.in);
        System.out.println("Írj be 1 sorszámot");
        var beSorszam = input.nextInt();

        System.out.println("Írj be 1 árut");
        var beAru = input.next();

        System.out.println("Írj be 1 mennyiséget");
        var beDBszam = input.nextInt();
        input.close();
        
        int osszesVetel = 0, utolsoSorszam = 0;
        
        for(var k = 0; k < vasarlasok.size(); ++k) {
            for(var cuccok : vasarlasok.get(k)) {
                if(cuccok.equals(beAru)) {
                    ++osszesVetel;
                    utolsoSorszam = k;
                    
                    if(osszesVetel == 1) {
                        System.out.println("Először a " + (k + 1) + ". vásárlásnál vettek " + beAru + "-t");
                    }
                }
            }
        }
       
        System.out.println("Utoljára a " + (utolsoSorszam + 1) + ". vásárlásnál vettek " + beAru + "-t");
        System.out.println("Összesen " + osszesVetel + "-szor vettek " + beAru + "-t");
        System.out.println(beDBszam + " db esetén a fizetendő: " + ertek(beDBszam));
        System.out.println("A " + beSorszam + ". vásárláskor vásárolt dolgok: ");
        
        for(var statok : stat(vasarlasok.get(beSorszam - 1)).entrySet()) {
            System.out.println(statok.getKey() + "-ből: " + statok.getValue() + " db");
        }
       
        try(var output = new PrintWriter("osszeg.txt")){
            for(var k = 0; k < vasarlasok.size(); ++k) {
                var irasDarab = 0;
                var statisztika = stat(vasarlasok.get(k));
                
                for(var entries : statisztika.entrySet()) {
                    irasDarab += ertek(entries.getValue());
                }
                output.println((k + 1) + ": " + irasDarab);
            }
        }
    }
    
    public static int ertek(int dbSzam) {
        return dbSzam == 1 ? 500 : dbSzam == 2 ? 950 : 1350 + (400 * (dbSzam - 3));
    }
    
    public static Map<String, Integer> stat(List<String> vasarlas){
        var freqMap = new HashMap<String, Integer>();
        
        for(var cucc : vasarlas) {
            if(!freqMap.containsKey(cucc)) {
                freqMap.put(cucc, Collections.frequency(vasarlas, cucc));
            }
        }
        
        return freqMap;
    }
}