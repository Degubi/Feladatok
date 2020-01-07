import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.Map.*;

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
        h1 = (h1 + 9) % 12;
        h2 = (h2 + 9) % 12;
        e1 = e1 - h1 / 10;
        e2 = e2 - h2 / 10;
        
        var d1 = 365 * e1 + e1 / 4 - e1 / 100 + e1 / 400 + (h1 * 306 + 5) / 10 + n1 - 1;
        var d2 = 365 * e2 + e2 / 4 - e2 / 100 + e2 / 400 + (h2 * 306 + 5) / 10 + n2 - 1;
        return d2 - d1;
    }
    
    public static class Utazas{
        public static final DateTimeFormatter felszallasFormatum = DateTimeFormatter.ofPattern("uuuuMMdd-HHmm");
        public static final DateTimeFormatter ervenyessegFormatum = DateTimeFormatter.ofPattern("uuuuMMdd");

        public final int megalloSorszama;
        public final LocalDateTime felszallas;
        public final String kartyaAzonosito;
        public final String tipus;
        public final int jegyszam;
        public final LocalDate ervenyesseg;
        
        public Utazas(String line) {
            var split = line.split(" ");
            
            megalloSorszama = Integer.parseInt(split[0]);
            felszallas = LocalDateTime.parse(split[1], felszallasFormatum);
            kartyaAzonosito = split[2];
            tipus = split[3];
            
            if(split[4].length() <= 2) {
                jegyszam = Integer.parseInt(split[4]);
                ervenyesseg = null;
            }else{
                jegyszam = -1;
                ervenyesseg = LocalDate.parse(split[4], ervenyessegFormatum);
            }
        }
        
        public boolean ervenytelenE() { return jegyszam == 0 || (ervenyesseg != null && felszallas.toLocalDate().isAfter(ervenyesseg)); }
        public boolean ervenyesE() { return !ervenytelenE(); }
    }
}