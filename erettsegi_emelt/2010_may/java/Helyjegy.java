import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Helyjegy {

    public static void main(String[] args) throws IOException {
        var file = Files.readAllLines(Path.of("eladott.txt"));
        var firstSplit = file.get(0).split(" ");

        var utHossz = Integer.parseInt(firstSplit[1]);
        var ar = Integer.parseInt(firstSplit[2]);  //10 km-ként
        var utasok = new ArrayList<Utas>();

        for(var k = 1; k < file.size(); ++k) {
            utasok.add(new Utas(file.get(k), k));
        }

        var utolso = utasok.get(utasok.size() - 1);
        System.out.println("2.Feladat: Utolsó utas ülése: " + utolso.ules + " utazott távolság: " + utolso.getTavolsag());
        System.out.println("3.Feladat");

        var osszesPenz = 0;
        for(var utas : utasok) {
            osszesPenz += utas.getAr(ar);

            if(utas.getTavolsag() == utHossz) {
                System.out.print(utas.sorszam + " ");
            }
        }

        System.out.println("\n4.Feladat: Összes bevétel: " + osszesPenz);

        var utolsoMegallo = 0;
        for(var utas : utasok) {
            if(utas.end > utolsoMegallo && utas.end != utHossz) {
                utolsoMegallo = utas.end;
            }
        }

        var felszallok = 0;
        var leszallok = 0;
        for(var utas : utasok) {
            if(utas.start == utolsoMegallo) {
                ++felszallok;
            }

            if(utas.end == utolsoMegallo) {
                ++leszallok;
            }
        }

        System.out.println("5.Feladat: Utolsó megállónál felszállók: " + felszallok + ", leszállók: " + leszallok);

        var allomasok = new HashSet<Integer>();
        for(var utas : utasok) {
            allomasok.add(utas.start);
            allomasok.add(utas.end);
        }

        System.out.println("6.Feladat: Megállók száma: " + (allomasok.size() - 2));

        try(var output = new PrintWriter("kihol.txt");
            var input = new Scanner(System.in)) {

            System.out.println("Írj be 1 km számot!");

            var readTav = input.nextInt();

            for(var k = 1; k <= 48; ++k) {
                Utas currentUtas = null;

                for(var utas : utasok) {
                    if(utas.ules == k && (utas.start == readTav || utas.end == readTav)) {
                        currentUtas = utas;
                    }
                }

                output.println(k + ". ülés: " + (currentUtas == null ? "üres" : (currentUtas.sorszam + ". utas")));
            }
        }
    }
}