import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

@SuppressWarnings("boxing")
public class EUtazas_stream {

    public static void main(String[] args) throws IOException {
        var utazasok = Files.lines(Path.of("utasadat.txt")).map(Utazas::new).toArray(Utazas[]::new);
        
        System.out.println("2. Feladat: " + utazasok.length + " db utas akart felszállni");
        
        var ervenytelenek = Arrays.stream(utazasok)
                                  .filter(Utazas::ervenytelenE)
                                  .count();
        
        System.out.println("3. Feladat: " + ervenytelenek + " utas nem szállhatott fel");
        
        Arrays.stream(utazasok)
              .collect(Collectors.groupingBy(k -> k.megalloSorszama, Collectors.counting()))
              .entrySet().stream()
              .max(Comparator.comparing(Entry::getValue))
              .ifPresent(k -> System.out.println("4. Feladat: legtöbb utas (" + k.getValue() + " fő) a " + k.getKey() + "-as megállóban próbált felszállni"));
        
        var tipusStat = Arrays.stream(utazasok)
                              .filter(Utazas::ervenyesE)
                              .collect(Collectors.groupingBy(k -> k.tipus, Collectors.counting()));
        
        System.out.println("5. Feladat");
        System.out.println("Ingyenes utasok: " + (tipusStat.get("NYP") + tipusStat.get("RVS") + tipusStat.get("GYK")));
        System.out.println("Kedvezményes utasok: " + (tipusStat.get("TAB") + tipusStat.get("NYB")));
        
        var haromnaposok = Arrays.stream(utazasok)
                                 .filter(k -> k.ervenyesseg != null)
                                 .filter(k -> {
                                     var napKulonbseg = ChronoUnit.DAYS.between(k.felszallas.toLocalDate(), k.ervenyesseg);
                                     return napKulonbseg >= 0 && napKulonbseg <= 3;
                                 })
                                 .map(k -> k.kartyaAzonosito + " " + k.ervenyesseg)
                                 .collect(Collectors.toList());
        
        Files.write(Path.of("figyelmeztetes.txt"), haromnaposok);
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