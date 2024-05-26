import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Kemia {

    public static void main(String[] args) throws IOException {
        var elemek = new ArrayList<Elem>();

        for(var line : Files.readAllLines(Path.of("felfedezesek.csv"), StandardCharsets.ISO_8859_1)) {
            elemek.add(new Elem(line.split(";")));
        }

        System.out.println("3. Feladat: Elemek száma: " + elemek.size());

        var okoriElemekSzama = 0;
        for(var elem : elemek) {
            if(elem.ev == Elem.OKOR_EV) {
                ++okoriElemekSzama;
            }
        }

        System.out.println("4. Feladat: Ókori elemek száma: " + okoriElemekSzama);
        System.out.println("5. Feladat:");

        bekeresLoop:
        while(true) {
            var bekertVegyjel = System.console().readLine("Írj be egy vegyjelet! (1-2 karakter): ");

            if(bekertVegyjel.length() == 1 || bekertVegyjel.length() == 2) {
                for(var i = 0; i < bekertVegyjel.length(); ++i) {
                    if(!Character.isLetter(bekertVegyjel.charAt(i))) {
                        continue bekeresLoop;
                    }
                }

                System.out.println("6. Feladat:");

                var bekertElem = (Elem) null;
                for(var elem : elemek) {
                    if(elem.vegyjel.equalsIgnoreCase(bekertVegyjel)) {
                        bekertElem = elem;
                        break;
                    }
                }

                if(bekertElem == null) {
                    System.out.println("Nincs ilyen elem eltárolva!");
                }else{
                    System.out.println(bekertElem.vegyjel + ": " + bekertElem.nev + ", rsz.: " + bekertElem.rendszam + ", év: " + bekertElem.ev + ", felf.: " + bekertElem.felfedezo);
                }

                break bekeresLoop;
            }
        }

        var leghosszabbIdoDiff = 0;
        for(var i = 0; i < elemek.size() - 1; ++i) {
            var elem1 = elemek.get(i);
            var elem2 = elemek.get(i + 1);

            if(elem1.ev != Elem.OKOR_EV && elem2.ev != Elem.OKOR_EV) {
                var evDiff = elem2.ev - elem1.ev;

                if(evDiff > leghosszabbIdoDiff) {
                    leghosszabbIdoDiff = evDiff;
                }
            }
        }

        System.out.println("7. Feladat: Leghoszabb idő: " + leghosszabbIdoDiff + " év");
        System.out.println("8. Feladat");

        var evDbSzamok = new HashMap<Integer, Integer>();
        for(var elem : elemek) {
            if(elem.ev != Elem.OKOR_EV) {
                evDbSzamok.put(elem.ev, evDbSzamok.getOrDefault(elem.ev, 0) + 1);
            }
        }

        for(var evStat : evDbSzamok.entrySet()) {
            if(evStat.getValue() > 3) {
                System.out.println(evStat.getKey() + ": " + evStat.getValue() + " db");
            }
        }
    }
}