import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Cegesauto {

    public static void main(String[] args) throws IOException {
        var sorok = Files.readAllLines(Path.of("autok.txt"));
        var autok = new ArrayList<Auto>();

        for(var sor : sorok) {
            autok.add(new Auto(sor));
        }

        for(var i = autok.size() - 1; i >= 0; --i) {
            var jelenlegi = autok.get(i);

            if(jelenlegi.elvitel) {
                System.out.println("2. Feladat: Utoljára elvitt autó: " + jelenlegi.nap + ". nap, rendszam: " + jelenlegi.rendszam);
                break;
            }
        }

        autok.sort(Comparator.comparing(k -> k.rendszam));
        System.out.println("3. Feladat: Írj be egy napot!");

        try(var input = new Scanner(System.in)){
            var beNap = input.nextInt();
            var visszahozottAutokSzama = 0;

            for(var auto : autok) {
                if(auto.nap == beNap) {
                    System.out.println(auto.idopont + " " + auto.rendszam + " " + auto.szemelyAzonosito + (auto.elvitel ? " ki" : " be"));
                }

                if(!auto.elvitel) {
                    ++visszahozottAutokSzama;
                }
            }

            System.out.println("4. Feladat: Nem visszahozott autók száma: " + (autok.size() - visszahozottAutokSzama * 2));
            System.out.println("5. Feladat");

            var rendszamok = new TreeSet<String>();

            for(var auto : autok) {
                rendszamok.add(auto.rendszam);
            }

            for(var rendszam : rendszamok) {
                var maxKm = Integer.MIN_VALUE;
                var minKm = Integer.MAX_VALUE;

                for(var auto : autok) {
                    if(auto.rendszam.equals(rendszam)) {
                        var km = auto.km;

                        if(km > maxKm) {
                            maxKm = km;
                        }
                        if(km < minKm) {
                            minKm = km;
                        }
                    }
                }

                System.out.println(rendszam + ": " + (maxKm - minKm) + " km");
            }

            var maxAuto = autok.get(0);
            var maxTav = 0;

            for(var i = 1; i < autok.size(); ++i) {
                var jelen = autok.get(i);
                var elozo = autok.get(i - 1);

                if(jelen.rendszam.equals(elozo.rendszam) && !jelen.elvitel) {
                    var dist = jelen.km - elozo.km;

                    if(dist > maxTav) {
                        maxTav = dist;
                        maxAuto = jelen;
                    }
                }
            }

            System.out.println("6. Feladat: Leghosszabb út: " + maxTav + " km, személy: " + maxAuto.szemelyAzonosito);
            System.out.println("7. Feladat: Írj be egy rendszámot!");
            var beRendszam = input.next();

            try(var file = new PrintWriter(beRendszam + "_menetlevel.txt")){
                for(var auto : autok) {
                    if(auto.rendszam.equals(beRendszam)) {
                        file.print(auto.toFileInfo());
                    }
                }
            }
        }
    }
}