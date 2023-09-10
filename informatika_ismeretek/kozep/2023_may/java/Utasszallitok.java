import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Utasszallitok {

    public static void main(String[] args) throws IOException {
        var repulogepek = new ArrayList<Utasszallito>();
        var lines = Files.readAllLines(Path.of("utasszallitok.txt"));

        for(var i = 1; i < lines.size(); ++i) {
            repulogepek.add(new Utasszallito(lines.get(i)));
        }

        System.out.println("4. Feladat: Repülőgépek száma: " + repulogepek.size());

        var boeingGepekSzama = 0;
        for(var gep : repulogepek) {
            if(gep.tipus.startsWith("Boeing")) {
                ++boeingGepekSzama;
            }
        }

        System.out.println("5. Feladat: Boeing gépek száma: " + boeingGepekSzama);

        var legtobbUtastSzallithatoGep = repulogepek.get(0);
        for(var gep : repulogepek) {
            if(gep.maxSzallithatoUtasokSzama > legtobbUtastSzallithatoGep.maxSzallithatoUtasokSzama) {
                legtobbUtastSzallithatoGep = gep;
            }
        }

        System.out.println("6. Feladat: \n" +
                           "    Típus: " + legtobbUtastSzallithatoGep.tipus + "\n" +
                           "    Első felszállás: " + legtobbUtastSzallithatoGep.elsoRepulesEve + "\n" +
                           "    Utasok: " + legtobbUtastSzallithatoGep.minSzallithatoUtasokSzama + "-" + legtobbUtastSzallithatoGep.maxSzallithatoUtasokSzama + "\n" +
                           "    Személyzet: " + legtobbUtastSzallithatoGep.minSzemelyzetSzama + "-" + legtobbUtastSzallithatoGep.maxSzemeyzetSzama + "\n" +
                           "    Sebesség: " + legtobbUtastSzallithatoGep.sebessegKategoria.utazosebesseg);

        var megtalalhatoKategoriak = new HashSet<String>();
        for(var gep : repulogepek) {
            megtalalhatoKategoriak.add(gep.sebessegKategoria.getKategorianev());
        }

        var nemTalalhatoKategoriak = new ArrayList<String>();
        for(var kategoriaNev : Sebessegkategoria.KATEGORIAK) {
            if(!megtalalhatoKategoriak.contains(kategoriaNev)) {
                nemTalalhatoKategoriak.add(kategoriaNev);
            }
        }

        System.out.println("7. Feladat: " + (nemTalalhatoKategoriak.size() == 0 ? "Minden sebességkategóriából van repülőgéptípus!" : String.join(" ", nemTalalhatoKategoriak)));

        var sorokFileba = new ArrayList<String>();
        for(var gep : repulogepek) {
            sorokFileba.add(Utasszallito.reformatUtassallitoData(gep));
        }

        Files.writeString(Path.of("utasszallitok_new.txt"), "típus;év;utas;személyzet;utazósebesség;felszállótömeg;fesztáv\n" + String.join("\n", sorokFileba));
    }
}