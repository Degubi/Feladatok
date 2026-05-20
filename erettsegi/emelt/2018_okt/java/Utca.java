import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Utca {

    public static void main(String[] args) throws IOException {
        var telkek = new ArrayList<Telek>();
        var hazszamSzamlalok = new int[] { 2, 1 };

        for(var line : Files.readAllLines(Path.of("kerites.txt"))) {
            var split = line.split(" ");
            var hazszamSzamlaloIndex = Integer.parseInt(split[0]);

            telkek.add(new Telek(hazszamSzamlalok[hazszamSzamlaloIndex], Integer.parseInt(split[1]), split[2].charAt(0)));
            hazszamSzamlalok[hazszamSzamlaloIndex] += 2;
        }

        var utolsoTelek = telkek.get(telkek.size() - 1);

        System.out.println("2. Feladat: Eladott telkek száma: " + telkek.size());
        System.out.println("3. Feladat: Az utolsó telek: " + (utolsoTelek.parosE ? "Páros" : "Páratlan") + ", házszáma: " + utolsoTelek.hazszam);

        for(var i = 0; i < telkek.size() - 1; ++i){
            var telek = telkek.get(i);
            var kovetkezo = telkek.get(i + 1);

            if(!telek.parosE && telek.keritesSzine != ':' && telek.keritesSzine != '#' && !kovetkezo.parosE && kovetkezo.keritesSzine == telek.keritesSzine) {
                System.out.println("4. Feladat: Talált házszám: " + telek.hazszam);
                break;
            }
        }

        var beolvasottTelekSzam = Integer.parseInt(System.console().readLine("5. Feladat: Írd be 1 telek számát: "));

        for(var i = 0; i < telkek.size(); ++i){
            var jelenlegiTelek = telkek.get(i);

            if(jelenlegiTelek.hazszam == beolvasottTelekSzam) {
                System.out.println("Kerítés színe: " + (jelenlegiTelek.keritesSzine == ':' ? "Nem készült el" : jelenlegiTelek.keritesSzine == '#' ? "Festetlen" : jelenlegiTelek.keritesSzine));

                var balSzomszed = telkek.get(i - 1);
                var jobbSzomszed = telkek.get(i + 1);

                for(var generalt = 'A'; ; ++generalt) {
                    if(balSzomszed.keritesSzine != generalt && jobbSzomszed.keritesSzine != generalt && jelenlegiTelek.keritesSzine != generalt) {
                        System.out.println("Az új szín lehet: " + generalt);
                        break;
                    }
                }

                break;
            }
        }

        try(var output = new PrintWriter("utcakep.txt")) {
            for(var telek : telkek) {
                if(!telek.parosE) {
                    output.print(Character.toString(telek.keritesSzine).repeat(telek.szelesseg));
                }
            }

            output.println();

            for(var telek : telkek) {
                if(!telek.parosE) {
                    var hazszamString = Integer.toString(telek.hazszam);

                    output.print(hazszamString + " ".repeat(telek.szelesseg - hazszamString.length()));
                }
            }
        }
    }
}