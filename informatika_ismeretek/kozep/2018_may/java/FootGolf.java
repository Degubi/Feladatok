import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FootGolf {

    public static void main(String[] args) throws Exception{
        var sorok = Files.readAllLines(Path.of("fob2016.txt"));
        var versenyzok = new ArrayList<Versenyzo>();

        for(var sor : sorok) {
            versenyzok.add(new Versenyzo(sor));
        }

        System.out.println("3.Feladat: Versenyzők száma: " + versenyzok.size());

        var noiVersenyzok = 0;
        for(var versenyzo : versenyzok) {
            if(versenyzo.kategoria.contains("Noi")) {
                ++noiVersenyzok;
            }
        }

        System.out.printf("4.Feladat: Női versenyzők aránya: %.2f%%\n", noiVersenyzok / (float)versenyzok.size() * 100);

        var legtobbNoi = versenyzok.get(0);
        for(var vers : versenyzok) {
            if(vers.kategoria.contains("Noi") && vers.osszPont() > legtobbNoi.osszPont()) {
                legtobbNoi = vers;
            }
        }

        System.out.println("6.Feladat: Női versenyző: " + "Név: " + legtobbNoi.nev + ", Egyesület: "
                            + legtobbNoi.versenyEgyesulet + ", pontok: " + legtobbNoi.osszPont());

        try(var output = new PrintWriter("osszpontFF.txt")) {
            for(var vers : versenyzok) {
                output.println(vers);
            }
        }

        var stat = new HashMap<String, Integer>();
        for(var vers : versenyzok) {
            if(!vers.versenyEgyesulet.equals("n.a.")) {
                var jelenes = 0;

                for(var ver : versenyzok) {
                    if(ver.versenyEgyesulet.equals(vers.versenyEgyesulet)) {
                        ++jelenes;
                    }
                }

                if(jelenes > 2) {
                    stat.put(vers.versenyEgyesulet, jelenes);
                }
            }
        }

        System.out.println("8.Feladat: " + stat);
    }

    public static class Versenyzo {

        public final String nev, kategoria, versenyEgyesulet;
        public final int[] pontok = new int[8];

        public Versenyzo(String line) {
            var split = line.split(";");

            nev = split[0];
            kategoria = split[1];
            versenyEgyesulet = split[2];

            for(int k = 3; k < 11; ++k) {
                pontok[k - 3] = Integer.parseInt(split[k]);
            }

            Arrays.sort(pontok);
        }

        public int osszPont() {
            var pont = 0;

            for(var k = 2; k < 8; ++k) {
                pont += pontok[k];
            }

            return pont + (pontok[0] != 0 ? 10 : 0) + (pontok[1] != 0 ? 10 : 0);
        }

        @Override
        public String toString() {
            return nev + " " + osszPont();
        }
    }
}