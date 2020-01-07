import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Utca {

    public static void main(String[] args) throws IOException {
        var sorok = Files.readAllLines(Paths.get("kerites.txt"));
        var telkek = new ArrayList<Telek>();
        
        var parosHazszam = 2;
        var paratlanHazszam = 1;
        
        for(var sor : sorok) {
            var split = sor.split(" ");
            var parose = Integer.parseInt(split[0]) == 0;
            
            if(parose) {
                telkek.add(new Telek(true, parosHazszam, Integer.parseInt(split[1]), split[2].charAt(0)));
                parosHazszam += 2;
            }else{
                telkek.add(new Telek(false, paratlanHazszam, Integer.parseInt(split[1]), split[2].charAt(0)));
                paratlanHazszam += 2;
            }
        }
        
        System.out.println("2.Feladat");
        System.out.println("Eladott telkek száma: " + telkek.size());
        
        var utolsoTelek = telkek.get(telkek.size() - 1);
        
        System.out.println("3.Feladat");
        System.out.println("Az utolsó telek: " + (utolsoTelek.parosE ? "Páros" : "Páratlan"));
        System.out.println("Az utolsó telek házszáma: " + utolsoTelek.hazszam);
        System.out.println("4.Feladat");
        
        for(var i = 0; i < telkek.size(); ++i){
            var telek = telkek.get(i);
            var kovetkezo = telkek.get(i + 1);
            
            if(!telek.parosE && telek.keritesSzine != ':' && telek.keritesSzine != '#' && !kovetkezo.parosE && kovetkezo.keritesSzine == telek.keritesSzine) {
                System.out.println("Talált házszám: " + telek.hazszam);
                break;
            }
        }
        
        System.out.println("5. Feladat");
        System.out.println("Írd be 1 telek számát!");
        
        try(var input = new Scanner(System.in)){
            var beolvasottTelekSzam = input.nextInt();
            
            for(int i = 0; i < telkek.size(); ++i){
                var jelenlegiTelek = telkek.get(i);
                
                if(jelenlegiTelek.hazszam == beolvasottTelekSzam) {
                    System.out.println("Kerítés színe: " + (jelenlegiTelek.keritesSzine == ':' ? "Nem készült el" : jelenlegiTelek.keritesSzine == '#' ? "Festetlen" : jelenlegiTelek.keritesSzine));
                    
                    var balSzomszed = telkek.get(i - 1);
                    var jobbSzomszed = telkek.get(i + 1);
                    
                    for(char generalt = 'A'; ; ++generalt) {
                        if(balSzomszed.keritesSzine != generalt && jobbSzomszed.keritesSzine != generalt && jelenlegiTelek.keritesSzine != generalt) {
                            System.out.println("Az új szín lehet: " + generalt);
                            break;
                        }
                    }
                    break;
                }
            }
        }
        
        try(var output = new PrintWriter("utcakep.txt")){
            for(var telek : telkek) {
                if(!telek.parosE) {
                    for(int k = 0; k < telek.szelesseg; ++k) {
                        output.print(telek.keritesSzine);
                    }
                }
            }
            
            output.println();
            
            for(var telek : telkek) {
                if(!telek.parosE) {
                    output.print(telek.hazszam);
                    
                    int szokozSzam = telek.szelesseg - Integer.toString(telek.hazszam).length(); 

                    for(int k = 0; k < szokozSzam; ++k) {
                        output.print(' ');
                    }
                }
            }
        }
    }
    
    public static class Telek{
        public final boolean parosE;
        public final int szelesseg, hazszam;
        public final char keritesSzine;
        
        public Telek(boolean parosE, int hazszam, int szelesseg, char keritesSzine) {
            this.parosE = parosE;
            this.szelesseg = szelesseg;
            this.keritesSzine = keritesSzine;
            this.hazszam = hazszam;
        }
    }
}