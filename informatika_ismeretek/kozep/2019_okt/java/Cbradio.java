import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Cbradio {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("cb.txt"));
        var bejegyzesek = new ArrayList<Bejegyzes>();
        
        for(var i = 1; i < lines.size(); i++) {
            bejegyzesek.add(new Bejegyzes(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Bejegyzések száma: " + bejegyzesek.size());
        
        var voltE4Adasos = false;
        for(var bejegyzes : bejegyzesek) {
            if(bejegyzes.adasok == 4) {
                voltE4Adasos = true;
                break;
            }
        }
        
        if(voltE4Adasos) {
            System.out.println("4. Feladat: Volt 4 adást indító sofőr");
        }else {
            System.out.println("4. Feladat: Nem volt 4 adást indító sofőr");
        }
        
        System.out.println("5. Feladat: Írj be egy nevet");
        try(var console = new Scanner(System.in)){
            var bekertNev = console.nextLine();
            var bekertHasznalatok = 0;
            
            for(var bejegyzes : bejegyzesek) {
                if(bejegyzes.nev.equals(bekertNev)) {
                    bekertHasznalatok += bejegyzes.adasok;
                }
            }
            
            if(bekertHasznalatok > 0) {
                System.out.println(bekertNev + " " + bekertHasznalatok + "x használta a rádiót");
            }else{
                System.out.println("Nincs ilyen nevű sofőr!");
            }
        }
        
        try(var file = new PrintWriter("cb2.txt")){
            file.println("Kezdes;Nev;AdasDb");
            
            for(var bejegyzes : bejegyzesek) {
                file.println(atszamolPercre(bejegyzes.ora, bejegyzes.perc) + ";" + bejegyzes.nev + ";" + bejegyzes.adasok);
            }
        }
        
        var egyediSoforok = new HashSet<String>();
        for(var bejegyzes : bejegyzesek) {
            egyediSoforok.add(bejegyzes.nev);
        }
        
        System.out.println("8. Feladat: Sofőrök száma: " + egyediSoforok.size());
        
        var soforokAdasszamokkal = new HashMap<String, Integer>();
        for(var bejegyzes : bejegyzesek) {
            var soforNeve = bejegyzes.nev;
            
            soforokAdasszamokkal.put(soforNeve, soforokAdasszamokkal.getOrDefault(soforNeve, 0) + bejegyzes.adasok);
        }
        
        //Elég ronda módszer az első elem lekérésére, de kell valami amihez képest összehasonlítani tudunk. :/
        var legtobbAdasBejegyzes = soforokAdasszamokkal.entrySet().iterator().next();
        for(var bejegyzes : soforokAdasszamokkal.entrySet()) {
            if(bejegyzes.getValue() > legtobbAdasBejegyzes.getValue()) {
                legtobbAdasBejegyzes = bejegyzes;
            }
        }
        
        System.out.println("9. Feladat: Legtöbb adást indító sofőr: " + legtobbAdasBejegyzes.getKey() + ", adások: " + legtobbAdasBejegyzes.getValue());
    }
    
    public static int atszamolPercre(int ora, int perc) {
        return ora * 60 + perc;
    }
}