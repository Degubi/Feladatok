import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Nobel {

    @SuppressWarnings("boxing")
    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("nobel.csv"));
        var dijak = new ArrayList<Dij>();
        
        for(var i = 1; i < lines.size(); ++i) {
            dijak.add(new Dij(lines.get(i)));
        }
        
        for(var dij : dijak) {
            if(dij.keresztNev.equals("Arthur B.") && dij.vezetekNev.equals("McDonald")) {
                System.out.println("3. Feladat: Arthur " + dij.tipus + " díjat kapott");
                break;
            }
        }
        
        System.out.println("4. Feladat:");
        for(var dij : dijak) {
            if(dij.evszam == 2017 && dij.tipus.equals("irodalmi")) {
                System.out.println("Irodalmi díjat kapott: " + dij.keresztNev + " " + dij.vezetekNev);
            }
        }
        
        System.out.println("5. Feladat:");
        for(var dij : dijak) {
            if(dij.evszam >= 1990 && dij.vezetekNev.equals("")) {
                System.out.println(dij.evszam + ": " + dij.keresztNev);
            }
        }
        
        System.out.println("6. Feladat");
        for(var dij : dijak) {
            if(dij.vezetekNev.contains("Curie")) {
                System.out.println(dij.evszam + ": " + dij.keresztNev + " " + dij.vezetekNev + ": " + dij.tipus);
            }
        }
        
        System.out.println("7. Feladat");
        var tipusSzamlalok = new HashMap<String, Integer>();
        for(var dij : dijak) {
            tipusSzamlalok.put(dij.tipus, tipusSzamlalok.getOrDefault(dij.tipus, 0) + 1);
        }
        
        for(var entry : tipusSzamlalok.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " db");
        }
        
        dijak.sort(Comparator.comparingInt(k -> k.evszam));
        
        try(var output = new PrintWriter("orvosi.txt")){
            for(var dij : dijak) {
                if(dij.tipus.equals("orvosi")) {
                    output.println(dij.evszam + ":" + dij.keresztNev + " " + dij.vezetekNev);
                }
            }
        }
    }
}