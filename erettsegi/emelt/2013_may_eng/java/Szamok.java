import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Szamok {

    public static void main(String[] args) throws IOException {
        var rand = new Random();
        var feladatok = new ArrayList<Feladat>();
        var minden = Files.readAllLines(Path.of("felszam.txt"), StandardCharsets.ISO_8859_1);

        for(var k = 0; k < minden.size() - 1; k += 2) {
            feladatok.add(new Feladat(minden.get(k), minden.get(k + 1)));
        }

        System.out.println(feladatok.size() + " Feladat van a fájlban!");

        var temakorok = new ArrayList<String>();
        var matekCounters = new int[4];

        for(var all : feladatok) {
            if(!temakorok.contains(all.temakor))
                temakorok.add(all.temakor);
            if(all.temakor.equals("matematika")) {
                ++matekCounters[0];
                if(all.pont == 1) {
                    ++matekCounters[1];
                }else if(all.pont == 2) {
                    ++matekCounters[2];
                }else {
                    ++matekCounters[3];
                }
            }
        }

        System.out.println("Az adatfajlban " + matekCounters[0] + " matematika feladat van,\n1 pontot er: " + matekCounters[1] + "" +
                           " feladat, 2 pontot er " + matekCounters[2] + " feladat, 3 pontot er " + matekCounters[3] + " feladat. ");

        feladatok.sort(new Comparator<Feladat>() {
            @Override
            public int compare(Feladat o1, Feladat o2) {
                return Integer.compare(o1.valasz, o2.valasz);
            }
        });

        System.out.println("A legkisebb válaszú feladat: " + feladatok.get(0).valasz + ", a legnagyobb: " + feladatok.get(feladatok.size() - 1).valasz);
        System.out.println("Előforduló témakörök: " + temakorok);

        var bekertTemakor = System.console().readLine("Írj be 1 témakört: ");
        var categorizált = new ArrayList<Feladat>();

        for(var all : feladatok) {
            if(all.temakor.equals(bekertTemakor)) {
                categorizált.add(all);
            }
        }

        var chosen = categorizált.get(rand.nextInt(categorizált.size()));

        if(Integer.parseInt(System.console().readLine(chosen.kerdes)) == chosen.valasz) {
            System.out.println("Kapott pontszám: " + chosen.pont);
        }else{
            System.out.println("Rossz válasz, 0 pont...\nA helyes válasz: " + chosen.valasz);
        }

        var generalt = new ArrayList<Feladat>();
        for(var k = 0; k < 10; ++k) {
            var randomFeladat = feladatok.get(rand.nextInt(feladatok.size()));

            if(generalt.contains(randomFeladat)) {
                --k;
            }else{
                generalt.add(randomFeladat);
            }
        }

        var osszpont = 0;
        try(var output = new PrintWriter("tesztfel.txt")) {
            for(var toPrint : generalt) {
                osszpont += toPrint.pont;
                output.println(toPrint.pont + " " + toPrint.kerdes);
            }

            output.print(osszpont);
        }
    }
}