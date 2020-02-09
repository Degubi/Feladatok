import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class Fuvar {
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("fuvar.csv"));
        var fuvarok = new ArrayList<FuvarAdat>();
        
        for(var i = 1; i < lines.size(); ++i) {
            fuvarok.add(new FuvarAdat(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Fuvarok száma: " + fuvarok.size());
        
        var szurtDb = 0;
        var szurtDij = 0F;
        
        for(var fuvar : fuvarok) {
            if(fuvar.azonosito == 6185) {
                ++szurtDb;
                szurtDij += fuvar.dij;
            }
        }
        
        System.out.println("4. Feladat: " + szurtDb + " db fuvarra: " + szurtDij);
        System.out.println("5. Feladat:");
        
        var modokToDB = new HashMap<String, Integer>();
        for(var fuvar : fuvarok) {
            var mod = fuvar.fizetesMod;
            
            if(modokToDB.containsKey(mod)) {
                modokToDB.put(mod, modokToDB.get(mod) + 1);
            }else {
                modokToDB.put(mod, 1);
            }
        }
        
        for(var entry : modokToDB.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " db");
        }
        
        var tavolsagSum = 0F;
        for(var fuvar : fuvarok) {
            tavolsagSum += fuvar.tavolsag;
        }
        
        System.out.printf("6. Feladat: %.2f km\n", tavolsagSum * 1.6D);
        
        var legnagyobbByIdotartam = fuvarok.get(0);
        for(var fuvar : fuvarok) {
            if(fuvar.idotartam > legnagyobbByIdotartam.idotartam) {
                legnagyobbByIdotartam = fuvar;
            }
        }
        
        System.out.printf("7. Feladat: %d mp, azonosito: %d, távolság: %.2f km, díj: %.2f$\n", legnagyobbByIdotartam.idotartam, legnagyobbByIdotartam.azonosito, legnagyobbByIdotartam.tavolsag, legnagyobbByIdotartam.dij);
    
        var header = "taxi_id;indulas;idotartam;tavolsag;viteldij;borravalo;fizetes_modja";
        var szurtFuvarok = new ArrayList<FuvarAdat>();
        
        for(var e : fuvarok) {
            if(e.idotartam > 0 && e.dij > 0F && e.tavolsag == 0F) {
                szurtFuvarok.add(e);
            }
        }
        
        szurtFuvarok.sort(Comparator.comparing(k -> k.indulas));
        
        try(var file = new PrintWriter("hibak.txt")){
            file.println(header);
            
            for(var e : szurtFuvarok) {
                file.println(e.azonosito + ";" + e.indulas + ";" + e.idotartam + ";" + e.tavolsag + ";" + e.dij + ";" + e.borravalo + ";" + e.fizetesMod);
            }
        }
    }
    
    public static class FuvarAdat{
        public final int azonosito;
        public final LocalDateTime indulas;
        public final int idotartam;
        public final float tavolsag;
        public final float dij;
        public final float borravalo;
        public final String fizetesMod;
        
        public FuvarAdat(String line) {
            var split = line.split(";");
            
            azonosito = Integer.parseInt(split[0]);
            indulas = LocalDateTime.parse(split[1].replace(' ', 'T'));
            idotartam = Integer.parseInt(split[2]);
            tavolsag = Float.parseFloat(split[3].replace(',', '.'));
            dij = Float.parseFloat(split[4].replace(',', '.'));
            borravalo = Float.parseFloat(split[5].replace(',', '.'));
            fizetesMod = split[6];
        }
    }
}