import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Helyjegy {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("eladott.txt"));
        var firstLineSplit = lines.get(0).split(" ");

        var vonalHossz = Integer.parseInt(firstLineSplit[1]);
        var arPer10km = Integer.parseInt(firstLineSplit[2]);
        var utasok = new ArrayList<Utas>();

        for(var k = 1; k < lines.size(); ++k) {
            utasok.add(new Utas(lines.get(k), k));
        }

        var utolso = utasok.get(utasok.size() - 1);
        System.out.println("2.Feladat: Utolsó utas ülése: " + utolso.ules + " utazott távolság: " + (utolso.leszallasKm - utolso.felszallasKm));
        System.out.println("3.Feladat");

        var osszesPenz = 0;
        for(var utas : utasok) {
            osszesPenz += Utas.getAr(utas, arPer10km);

            if((utas.leszallasKm - utas.felszallasKm) == vonalHossz) {
                System.out.print(utas.sorszam + " ");
            }
        }

        System.out.println("\n4.Feladat: Összes bevétel: " + osszesPenz);

        var utolsoElottiMegalloKm = 0;
        for(var utas : utasok) {
            if(utas.leszallasKm > utolsoElottiMegalloKm && utas.leszallasKm != vonalHossz) {
                utolsoElottiMegalloKm = utas.leszallasKm;
            }
        }

        var utoljaraFelszallok = 0;
        var utoljaraLeszallok = 0;
        for(var utas : utasok) {
            if(utas.felszallasKm == utolsoElottiMegalloKm) {
                ++utoljaraFelszallok;
            }

            if(utas.leszallasKm == utolsoElottiMegalloKm) {
                ++utoljaraLeszallok;
            }
        }

        System.out.println("5.Feladat: Utolsó megállónál felszállók: " + utoljaraFelszallok + ", leszállók: " + utoljaraLeszallok);

        var allomasok = new HashSet<Integer>();
        for(var utas : utasok) {
            allomasok.add(utas.felszallasKm);
            allomasok.add(utas.leszallasKm);
        }

        System.out.println("6.Feladat: Megállók száma: " + (allomasok.size() - 2));

        try(var output = new PrintWriter("kihol.txt")) {
            var bekertKm = Integer.parseInt(System.console().readLine("Írj be 1 km számot: "));

            for(var k = 1; k <= 48; ++k) {
                var currentUtas = (Utas) null;

                for(var utas : utasok) {
                    if(utas.ules == k && (utas.felszallasKm == bekertKm || utas.leszallasKm == bekertKm)) {
                        currentUtas = utas;
                    }
                }

                output.println(k + ". ülés: " + (currentUtas == null ? "üres" : (currentUtas.sorszam + ". utas")));
            }
        }
    }
}