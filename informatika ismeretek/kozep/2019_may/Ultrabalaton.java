import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Ultrabalaton {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("ub2017egyeni.txt"));
        var versenyzok = new ArrayList<Versenyzo>();
        
        for(var i = 1; i < lines.size(); ++i) {
            versenyzok.add(new Versenyzo(lines.get(i)));
        }
        
        System.out.println("3. Feladat: Egyéni indulók: " + versenyzok.size());
        
        var celbaertNoiVersenyzok = 0;
        for(var versenyzo : versenyzok) {
            if(versenyzo.befejezesSzazalek == 100 && versenyzo.kategoria.equals("Noi")) {
                ++celbaertNoiVersenyzok;
            }
        }
        
        System.out.println("4. Feladat: Célbaért női versenyzők: " + celbaertNoiVersenyzok);
        System.out.println("5. Feladat: Írd be egy versenyző nevét!");
        
        try(var console = new Scanner(System.in)){
            var bekertNev = console.nextLine();
            var indultEBekert = false;
            var bekertSzazaleka = -1;
            
            for(var versenyzo : versenyzok) {
                if(versenyzo.nev.equals(bekertNev)) {
                    indultEBekert = true;
                    bekertSzazaleka = versenyzo.befejezesSzazalek;
                    break;
                }
            }
            
            if(indultEBekert) {
                System.out.println("Indult egyéniben? Igen");
                System.out.println("Teljesítette a távot? " + (bekertSzazaleka == 100 ? "Igen" : "Nem"));
            }else {
                System.out.println("Indult egyéniben? Nem");
            }
        }
        
        var ferfiAtlagIdo = 0D;
        var ferfiakSzama = 0;
        
        for(var versenyzo : versenyzok) {
            if(versenyzo.befejezesSzazalek == 100 && versenyzo.kategoria.equals("Ferfi")) {
                ++ferfiakSzama;
                ferfiAtlagIdo += versenyzo.idoOraban();
            }
        }
        
        System.out.println("7. Feladat: Átlagos idő: " + (ferfiAtlagIdo / ferfiakSzama));
        
        var ferfiGyoztes = versenyzok.get(0);
        var noiGyoztes = versenyzok.get(0);
        
        for(var versenyzo : versenyzok) {
            if(versenyzo.befejezesSzazalek == 100) {
                if(versenyzo.kategoria.equals("Noi")) {
                    if(versenyzo.idoOraban() < noiGyoztes.idoOraban()) {
                        noiGyoztes = versenyzo;
                    }
                }else {
                    if(versenyzo.idoOraban() < ferfiGyoztes.idoOraban()) {
                        ferfiGyoztes = versenyzo;
                    }
                }
            }
        }
        
        System.out.printf("Nők: %s (%d) - %s\n", noiGyoztes.nev, noiGyoztes.rajtszam, noiGyoztes.ido);
        System.out.printf("Férfiak: %s (%d) - %s\n", ferfiGyoztes.nev, ferfiGyoztes.rajtszam, ferfiGyoztes.ido);
    }
    
    public static class Versenyzo {
        public final String nev;
        public final int rajtszam;
        public final String kategoria;
        public final String ido;
        public final int befejezesSzazalek;
        
        public Versenyzo(String line) {
            var split = line.split(";");
            
            nev = split[0];
            rajtszam = Integer.parseInt(split[1]);
            kategoria = split[2];
            ido = split[3];
            befejezesSzazalek = Integer.parseInt(split[4]);
        }
        
        public double idoOraban() {
            var daraboltIdo = ido.split(":");
            var ora = Double.parseDouble(daraboltIdo[0]);
            var perc = Double.parseDouble(daraboltIdo[1]);
            var mp = Double.parseDouble(daraboltIdo[2]);
            
            return ora + (perc / 60D) + (mp / 3600D);
        }
    }
}