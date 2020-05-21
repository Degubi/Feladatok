import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Metjelentes {

    public static void main(String[] args) throws IOException {
        var adatok = new ArrayList<IdojarasAdat>();
        for(var line : Files.readAllLines(Path.of("tavirathu13.txt"))) {
            adatok.add(new IdojarasAdat(line));
        }
        
        System.out.println("2. Feladat: Írj be egy városkódot!");
        try(var console = new Scanner(System.in)) {
            var bekertKod = console.nextLine();
            
            for(var i = adatok.size() - 1; i >= 0; --i) {
                var jelenlegi = adatok.get(i);
                
                if(jelenlegi.telepules.equals(bekertKod)) {
                    System.out.println("Utolsó mérés időpontja: " + jelenlegi.ido);
                    break;
                }
            }
        }
        
        System.out.println("3. Feladat:");
        var minHomersekletes = adatok.get(0);
        var maxHomersekletes = adatok.get(0);
        
        for(var adat : adatok) {
            if(adat.homerseklet > maxHomersekletes.homerseklet) {
                maxHomersekletes = adat;
            }
            
            if(adat.homerseklet < minHomersekletes.homerseklet) {
                minHomersekletes = adat;
            }
        }
        
        System.out.println("Legalacsonyabb hőmérséklet: "  + minHomersekletes.telepules + " " + minHomersekletes.ido + " " + minHomersekletes.homerseklet + " fok");
        System.out.println("Legmagasabb hőmérséklet: " + maxHomersekletes.telepules + " " + maxHomersekletes.ido + " " + maxHomersekletes.homerseklet + " fok");
        System.out.println("4. Feladat:");
        
        var szelcsendek = new ArrayList<IdojarasAdat>();
        for(var adat : adatok) {
            if(adat.szelIrany.equals("000") && adat.szelErosseg == 0) {
                szelcsendek.add(adat);
            }
        }
        
        if(szelcsendek.size() == 0){
            System.out.println("Nem volt szélcsend a mérések idején.");
        }else{
            for(var csendes : szelcsendek) {
                System.out.println(csendes.telepules + ": " + csendes.ido);
            }
        }
        
        System.out.println("5. Feladat:");
        
        var adatokTelepulesenkent = new HashMap<String, ArrayList<IdojarasAdat>>();
        for(var adat : adatok) {
            var telepules = adat.telepules;
            var telepulesAdatai = adatokTelepulesenkent.get(telepules);
            
            if(telepulesAdatai == null) {
                telepulesAdatai = new ArrayList<>();
                adatokTelepulesenkent.put(telepules, telepulesAdatai);
            }
            
            telepulesAdatai.add(adat);
        }
        
        for(var epicStat : adatokTelepulesenkent.entrySet()) {
            var telepules = epicStat.getKey();
            var telepuleshezTartozoAdatok = epicStat.getValue();
            var egyediAdattalRendelkezoOrak = new HashSet<Integer>();
            var legkisebbHomerseklet = 2000;
            var legnagyobbHomerseklet = -2000;
            var osszHomerseklet = 0;
            var homersekletekSzama = 0;
            
            for(var adat : telepuleshezTartozoAdatok) {
                var homerseklet = adat.homerseklet;
                
                if(homerseklet < legkisebbHomerseklet) {
                    legkisebbHomerseklet = homerseklet;
                }
                    
                if(homerseklet > legnagyobbHomerseklet) {
                    legnagyobbHomerseklet = homerseklet;
                }
                
                osszHomerseklet += homerseklet;
                ++homersekletekSzama;
                egyediAdattalRendelkezoOrak.add(adat.ido.getHour());
            }
            
            var ingadozas = legnagyobbHomerseklet - legkisebbHomerseklet;
            
            if(egyediAdattalRendelkezoOrak.size() == 24) {
                var kozep = ((double) osszHomerseklet) / homersekletekSzama;
                var kerekitettKozep = (int) Math.ceil(kozep);
                
                System.out.println(telepules + ": Középhőmérséklet: " + kerekitettKozep + "; Ingadozás: " + ingadozas);
            }else{
                System.out.println(telepules + ": NA; Ingadozás: " + ingadozas);
            }
        }
        
        System.out.println("6. Feladat:");
        
        for(var epicStat : adatokTelepulesenkent.entrySet()) {
            var telepules = epicStat.getKey();
            var telepuleshezTartozoAdatok = epicStat.getValue();
            
            try(var file = new PrintWriter(telepules + ".txt")) {
                file.println(telepules);
                
                for(var adat : telepuleshezTartozoAdatok) {
                    file.println(adat.ido + " " + ("#".repeat(adat.szelErosseg)));
                }
            }
        }
    }
}