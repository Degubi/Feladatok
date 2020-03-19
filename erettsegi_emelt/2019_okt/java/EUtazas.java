import java.io.*;
import java.nio.file.*;
import java.time.temporal.*;
import java.util.*;

public class EUtazas {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("utasadat.txt"));
        var utazasok = new ArrayList<Utazas>();
        
        for(var line : lines) {
            utazasok.add(new Utazas(line));
        }
        
        System.out.println("2. Feladat: " + utazasok.size() + " db utas akart felszállni");
        
        var ervenytelenek = 0;
        for(var utazas : utazasok) {
            if(utazas.ervenytelenE()) {
                ++ervenytelenek;
            }
        }
        
        System.out.println("3. Feladat: " + ervenytelenek + " utas nem szállhatott fel");
        
        var megalloStat = new HashMap<Integer, Integer>();
        for(var utazas : utazasok) {
            var sorszam = utazas.megalloSorszama;
            megalloStat.put(sorszam, megalloStat.getOrDefault(sorszam, 0) + 1);
        }
        
        var legtobbMegalloEntry = megalloStat.entrySet().iterator().next();
        for(var entry : megalloStat.entrySet()) {
            if(legtobbMegalloEntry.getValue() < entry.getValue()) {
                legtobbMegalloEntry = entry;
            }
        }
        
        System.out.println("4. Feladat: legtöbb utas (" + legtobbMegalloEntry.getValue() + " fő) a " + 
                                                          legtobbMegalloEntry.getKey() + "-as megállóban próbált felszállni");
        
        var tipusStat = new HashMap<String, Integer>();
        for(var utazas : utazasok) {
            if(utazas.ervenyesE()) {
                var tipus = utazas.tipus;
                tipusStat.put(tipus, tipusStat.getOrDefault(tipus, 0) + 1);
            }
        }
        
        System.out.println("5. Feladat");
        System.out.println("Ingyenes utasok: " + (tipusStat.get("NYP") + tipusStat.get("RVS") + tipusStat.get("GYK")));
        System.out.println("Kedvezményes utasok: " + (tipusStat.get("TAB") + tipusStat.get("NYB")));
        
        try(var output = new PrintWriter("figyelmeztetes.txt")){
            for(var utazas : utazasok) {
                if(utazas.ervenyesseg != null) {
                    var napKulonbseg = ChronoUnit.DAYS.between(utazas.felszallas.toLocalDate(), utazas.ervenyesseg);
                    
                    if(napKulonbseg >= 0 && napKulonbseg <= 3) {
                        output.println(utazas.kartyaAzonosito + " " + utazas.ervenyesseg);
                    }
                }
            }
        }
    }
    
    public static int napokszama(int e1, int h1, int n1, int e2, int h2, int n2) {
        //Normál esetben nem módosítanák soha bemeneti paramétert, de a feladat kérte
        h1 = (h1 + 9) % 12;
        h2 = (h2 + 9) % 12;
        e1 = e1 - h1 / 10;
        e2 = e2 - h2 / 10;
        
        var d1 = 365 * e1 + e1 / 4 - e1 / 100 + e1 / 400 + (h1 * 306 + 5) / 10 + n1 - 1;
        var d2 = 365 * e2 + e2 / 4 - e2 / 100 + e2 / 400 + (h2 * 306 + 5) / 10 + n2 - 1;
        return d2 - d1;
    }
}