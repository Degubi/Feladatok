import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Futar{

    public static void main(String[] args) throws IOException{
        var fuvarLista = new ArrayList<Fuvar>();
        for(var line : Files.readAllLines(Path.of("tavok.txt"))) {
            fuvarLista.add(new Fuvar(line));
        }

        System.out.println("2. Feladat: A hét legelső útja km-ben: " + fuvarLista.get(0).tavolsag + " km");
        System.out.println("3. Feladat: A hét utolsó útja km-ben: " + fuvarLista.get(fuvarLista.size() - 1).tavolsag + " km");

        var napok = new HashSet<Integer>();
        for(var fuvar : fuvarLista) {
            napok.add(fuvar.nap);
        }

        System.out.println("4. Feladat");
        for(var k = 1; k <= 7; ++k) {
            if(!napok.contains(k)) {
                System.out.println("A hét " + k + ". napján volt 1 szabadnap");
            }
        }

        var legtobb = new ArrayList<Fuvar>();
        var masodikLegtobb = new ArrayList<Fuvar>();

        for(var k = 1; k <= 7; ++k) {
            for(var fuvar : fuvarLista) {
                if(fuvar.nap == k) {
                    masodikLegtobb.add(fuvar);
                }
            }

            if(masodikLegtobb.size() > legtobb.size()) {
                legtobb = new ArrayList<>(masodikLegtobb);
            }
            masodikLegtobb.clear();
        }

        System.out.println("5. Feladat: Legtöbb fuvarú nap: " + legtobb.get(0).nap);
        System.out.println("6. Feladat");

        for(var k = 1; k <= 7; ++k) {
            var napiKm = 0;

            for(var fuvar : fuvarLista) {
                if(fuvar.nap == k) {
                    napiKm += fuvar.tavolsag;
                }
            }

            System.out.println("A " + k + ". nap: " + napiKm + " km");
        }

        var bekertKm = Integer.parseInt(System.console().readLine("7.Feladat: Írj be 1 távolságot: "));
        System.out.println(bekertKm + " km esetén fizetendő: " + calcPrice(bekertKm));

        var allPrice = 0;
        try(var output = new PrintWriter("dijazas.txt")) {
            for(var fuvar : fuvarLista) {
                int fuvarAr = calcPrice(fuvar.tavolsag);

                allPrice += fuvarAr;
                output.println(fuvar.nap + ". nap " + fuvar.sorszam + ". fuvar: " + fuvarAr + "FT");
            }
        }

        System.out.println("9. Feladat: Az egész heti fizetés: " + allPrice);
    }

    public static int calcPrice(int distance) {
        return distance <= 2  ? 500 :
               distance <= 5  ? 700 :
               distance <= 10 ? 900 :
               distance <= 20 ? 1400 : 2000;
    }
}