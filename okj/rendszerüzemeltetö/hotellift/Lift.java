import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class Lift {
    public static void main(String[] args) throws IOException {
        var hasznalatok = new ArrayList<Hasznalat>();
        var lines = Files.readAllLines(Path.of("lift.txt"));
        
        for(var line : lines) {
            hasznalatok.add(new Hasznalat(line));
        }
        
        System.out.println("3. Feladat: Lift alkalmak száma: " + hasznalatok.size());
        System.out.println("4. Feladat: A korszak " + hasznalatok.get(0).idopont + " től " + hasznalatok.get(hasznalatok.size() - 1).idopont + "-ig tartott");
        
        var maxSzint = -1;
        for(var e : hasznalatok) {
            if(e.celSzint > maxSzint) {
                maxSzint = e.celSzint;
            }
        }
        
        System.out.println("5. Feladat: Max célszint: " + maxSzint);
        
        System.out.println("6. Feladat: Írj be egy kártyaszámot és egy célszintet");
        try(var input = new Scanner(System.in)){
            var beKartya = parseOrDefault(input.nextLine(), 5);
            var beCelszint = parseOrDefault(input.nextLine(), 5);
            
            var kieg = "nem";
            
            for(var e : hasznalatok) {
                if(e.kartyaSorszam == beKartya && e.celSzint == beCelszint) {
                    kieg = "";
                    break;
                }
            }
            
            System.out.println("7. Feladat: A " + beKartya + " kártyával " + kieg + " utaztak a " + beCelszint + ". emeletre");
        }
        
        System.out.println("8. Feladat");
        
        var hasznalatStat = new HashMap<LocalDate, Integer>();
        for(var e : hasznalatok) {
            var idopont = e.idopont;
            
            if(hasznalatStat.containsKey(idopont)) {
                hasznalatStat.replace(idopont, hasznalatStat.get(idopont) + 1);
            }else{
                hasznalatStat.put(idopont, 1);
            }
        }
        
        for(var stat : hasznalatStat.entrySet()) {
            System.out.println(stat.getKey() + " - " + stat.getValue() + "x");
        }
    }
    
    static int parseOrDefault(String num, int defaultVal) {
        try {
            return Integer.parseInt(num);
        }catch(NumberFormatException e) {
            return defaultVal;
        }
    }
    
    public static class Hasznalat{
        private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("uuuu.MM.dd.");
        
        public final LocalDate idopont;
        public final int kartyaSorszam;
        public final int induloSzint;
        public final int celSzint;
        
        public Hasznalat(String sor) {
            var split = sor.split(" ");
            
            idopont = LocalDate.parse(split[0], timeFormat);
            kartyaSorszam = Integer.parseInt(split[1]);
            induloSzint = Integer.parseInt(split[2]);
            celSzint = Integer.parseInt(split[3]);
        }
    }
}