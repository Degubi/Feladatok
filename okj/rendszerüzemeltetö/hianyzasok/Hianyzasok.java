import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Hianyzasok {

    public static void main(String[] args) throws IOException {
        var hianyzasok = new ArrayList<Hianyzas>();
        var lines = Files.readAllLines(Path.of("szeptember.csv"));
        
        for(var i = 1; i < lines.size(); i++) {
            hianyzasok.add(new Hianyzas(lines.get(i)));
        }
        
        var orakSum = 0;
        for(var e : hianyzasok) {
            orakSum += e.mulasztottOrak;
        }
        
        System.out.println("2. Feladat: Hianyzott orak: " + orakSum);
        System.out.println("3. Feladat: Írj be egy napot(1-30) és egy nevet!");
        
        try(var input = new Scanner(System.in)){
            var bekertNap = input.nextInt();
            input.nextLine();
            var bekertNev = input.nextLine();
            
            System.out.println("4. Feladat");
            
            var hianyzottE = false;
            for(var e : hianyzasok) {
                if(e.nev.equals(bekertNev)) {
                    hianyzottE = true;
                    break;
                }
            }
            
            System.out.println(bekertNev + (hianyzottE ? " hianyzott" : " nem hianyzott"));
            
            System.out.println("5. Feladat");
            
            var azonANaponHianyoztak = new ArrayList<Hianyzas>();
            for(var e : hianyzasok) {
                if(bekertNap >= e.elsoNap && bekertNap <= e.utolsoNap) {
                    azonANaponHianyoztak.add(e);
                }
            }
            
            if(azonANaponHianyoztak.size() == 0) {
                System.out.println("Nem volt hiányzó");
            }else {
                for(var hiany : azonANaponHianyoztak) {
                    System.out.println(hiany.nev + " " + hiany.osztaly);
                }
            }
        }
        
        var hianyzasokStat = new HashMap<String, Integer>();
        
        for(var e : hianyzasok) {
            var osztaly = e.osztaly;
            
            if(hianyzasokStat.containsKey(osztaly)) {
                hianyzasokStat.put(osztaly, hianyzasokStat.get(osztaly) + e.mulasztottOrak);
            }else {
                hianyzasokStat.put(osztaly, e.mulasztottOrak);
            }
        }
        
        try(var file = new PrintWriter("osszesites.csv")){
            for(var k : hianyzasokStat.entrySet()) {
                file.println(k.getKey() + ";" + k.getValue());
            }
        }
    }
    
    public static class Hianyzas{
        public final String nev;
        public final String osztaly;
        public final int elsoNap;
        public final int utolsoNap;
        public final int mulasztottOrak;
        
        public Hianyzas(String sor) {
            var split = sor.split(";");
            
            this.nev = split[0];
            this.osztaly = split[1];
            this.elsoNap = Integer.parseInt(split[2]);
            this.utolsoNap = Integer.parseInt(split[3]);
            this.mulasztottOrak = Integer.parseInt(split[4]);
        }
    }
}