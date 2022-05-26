import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Radio {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("veetel.txt"));
        var feljegyzesek = new ArrayList<Feljegyzes>();

        for(var k = 0; k < lines.size(); k += 2) {
            feljegyzesek.add(new Feljegyzes(lines.get(k).split(" "), lines.get(k + 1)));
        }

        System.out.println("2.Feladat\nElső feljegyző: " + feljegyzesek.get(0).radioAmator + ", utolsó: " + feljegyzesek.get(feljegyzesek.size() - 1).radioAmator);
        System.out.println("3.Feladat");

        for(var feljegyzes : feljegyzesek) {
            if(feljegyzes.adat.contains("farkas")) {
                System.out.println("Nap: " + feljegyzes.nap + ", feljegyzo: " + feljegyzes.radioAmator);
            }
        }

        System.out.println("4.Feladat");
        for(var k = 1; k < 12; ++k) {
            var napiSzam = 0;

            for(var feljegyzes : feljegyzesek) {
                if(feljegyzes.nap == k) {
                    ++napiSzam;
                }
            }
            System.out.println(k + ". napon levő feljegyzések száma: " + napiSzam);
        }

        try(var output = new PrintWriter("adaas.txt")){
            for(var k = 1; k < 12; ++k) {
                char[] felj = null;

                for(var feljegyzes : feljegyzesek) {
                    if(feljegyzes.nap == k) {
                        if(felj == null) {
                            felj = feljegyzes.adat.toCharArray();
                        }else {
                            for(int charIndex = 0; charIndex < felj.length; ++charIndex) {
                                var jelenlegiChar = feljegyzes.adat.charAt(charIndex);

                                if(felj[charIndex] == '#' && Character.isLetter(jelenlegiChar)) {
                                    felj[charIndex] = jelenlegiChar;
                                }
                            }
                        }
                    }
                }
                output.println(new String(felj));
            }
        }

        System.out.println("7.Feladat\nÍrj be 1 napot (1-11) és 1 megfigyelő sorszámát!");
        try(var console = new Scanner(System.in)) {
            var readNap = console.nextInt();
            var readMegfigyelo = console.nextInt();
            var egyedszam = 0;
            var voltIlyen = false;

            for(var feljegyzes : feljegyzesek) {
                if(feljegyzes.nap == readNap && feljegyzes.radioAmator == readMegfigyelo) {
                    voltIlyen = true;
                    egyedszam += feljegyzes.gyerekekSzama;
                    egyedszam += feljegyzes.szulokSzama;
                }
            }

            if(voltIlyen) {
                if(egyedszam == 0) {
                    System.out.println("Nem határozható meg");
                }else {
                    System.out.println(egyedszam);
                }
            }else {
                System.out.println("Nem volt ilyen feljegyzés");
            }
        }
    }

    public static boolean szame(String szo) {
        var aze = true;

        for(var i = 1; i < szo.length(); ++i) {
            var jelenlegi = szo.charAt(i);

            if(jelenlegi < '0' || jelenlegi > '9') {
                aze = false;
            }
        }

        return aze;
    }
}