import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nyelvvizsga_stream {
    
    //Hiba: PDF-ben 2009-2017 van, txt- be 2009-2018
    public static void main(String[] args) throws IOException {
        var sikeresFile = Files.readAllLines(Path.of("sikeres.csv"), StandardCharsets.ISO_8859_1);
        var sikertelenFile = Files.readAllLines(Path.of("sikertelen.csv"), StandardCharsets.ISO_8859_1);
        var nyelvvizsgak = IntStream.range(1, sikeresFile.size())
                                    .mapToObj(i -> new Nyelvvizsga(sikeresFile.get(i), sikertelenFile.get(i)))
                                    .toArray(Nyelvvizsga[]::new);
        
        System.out.println("2. Feladat: Legnépszerűbb nyelvek");
        Arrays.stream(nyelvvizsgak)
              .sorted(Comparator.comparingInt(Nyelvvizsga::vizsgaOsszead).reversed())
              .limit(3)
              .forEach(k -> System.out.println(k.nyelv));
        
        System.out.println("3. Feladat: Írj be egy évet 2009-2018-ig!");
        try(var input = new Scanner(System.in)){
            var beEv = input.nextInt();
            
            if(beEv < 2009 || beEv > 2018) {
                return;   //Program vége
            }
            
            System.out.println("4. Feladat:");
            Arrays.stream(nyelvvizsgak)
                  .max(Comparator.comparingDouble(k -> k.getBukasAtlag(beEv)))
                  .ifPresent(k -> System.out.printf(beEv + "-ben " + k.nyelv + "-ből volt a legnagyobb bukási arány: %.2f%%\n", k.getBukasAtlag(beEv)));
            
            System.out.println("5. Feladat:");
            var nincsVizsgazo = Arrays.stream(nyelvvizsgak)
                                      .filter(k -> k.vizsgaOsszeadEvre(beEv) == 0)
                                      .map(k -> k.nyelv)
                                      .toArray(String[]::new);
            
            if(nincsVizsgazo.length == 0) {
                System.out.println("Minden nyelvből volt vizsgázó");
            }else{
                Arrays.stream(nincsVizsgazo).forEach(System.out::println);
            }
        }
        
        var stat = Arrays.stream(nyelvvizsgak)
                         .map(k -> String.format("%s;%d;%.2f", k.nyelv, k.vizsgaOsszead(), k.getTeljesBukasAtlag()))
                         .collect(Collectors.joining("\n"));
        
        Files.writeString(Path.of("osszesites.csv"), stat);
    }
    
    public static class Nyelvvizsga{
        public final String nyelv;
        public final Map<Integer, Integer> sikeresVizsgak;
        public final Map<Integer, Integer> sikertelenVizsgak;
        
        public Nyelvvizsga(String sikeresSor, String sikertelenSor) {
            var sikerSplit = sikeresSor.split(";");
            var sikertelenSplit = sikertelenSor.split(";");
            
            this.nyelv = sikerSplit[0];
            this.sikeresVizsgak = IntStream.range(0, 10)
                                           .boxed()
                                           .collect(Collectors.toMap(k -> k + 2009, k -> Integer.parseInt(sikerSplit[k + 1])));
            this.sikertelenVizsgak = IntStream.range(0, 10)
                                              .boxed()
                                              .collect(Collectors.toMap(k -> k + 2009, k -> Integer.parseInt(sikertelenSplit[k + 1])));
        }
        
        public double getBukasAtlag(int beEv) {
            double bukott = sikertelenVizsgak.get(beEv);
            double siker = sikeresVizsgak.get(beEv);
            
            if(bukott + siker == 0) {
                return 0D;  //Nullával való osztás miatt kell
            }
            return bukott / (bukott + siker) * 100D;
        }
        
        public double getTeljesBukasAtlag() {
            double osszesBukott = sikertelenVizsgak.values().stream().mapToInt(k -> k).sum();
            double osszesSiker = sikeresVizsgak.values().stream().mapToInt(k -> k).sum();
            
            if(osszesBukott + osszesSiker == 0) {
                return 0D;  //Nullával való osztás miatt kell
            }
            return osszesBukott / (osszesBukott + osszesSiker) * 100D;
        }
        
        public int vizsgaOsszeadEvre(int ev) {
            return sikeresVizsgak.get(ev) + sikertelenVizsgak.get(ev);
        }
        
        public int vizsgaOsszead() {
            return sikeresVizsgak.values().stream().mapToInt(k -> k).sum() + sikertelenVizsgak.values().stream().mapToInt(k -> k).sum();
        }
    }
}