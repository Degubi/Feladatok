import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class Jelado {

    public static void main(String[] args) throws IOException {
        var jelek = new ArrayList<Jel>();

        for(var line : Files.readAllLines(Path.of("jel.txt"))) {
            jelek.add(new Jel(line));
        }

        var bekertJel = jelek.get(Integer.parseInt(System.console().readLine("2. Feladat: Írja be 1 jel sorszámát: ")) - 1);

        System.out.println("Bekért jel pozíciója: x=" + bekertJel.x + " y=" + bekertJel.y);
        System.out.println("4. Feladat: Első & utolsó között eltelt idő: " + LocalTime.ofSecondOfDay(eltelt(jelek.get(0).idopont, jelek.get(jelek.size() - 1).idopont)));

        var minX = Integer.MAX_VALUE;
        var maxX = Integer.MIN_VALUE;
        var minY = Integer.MAX_VALUE;
        var maxY = Integer.MIN_VALUE;

        for(var jel : jelek) {
            minX = Math.min(minX, jel.x);
            maxX = Math.max(maxX, jel.x);
            minY = Math.min(minY, jel.y);
            maxY = Math.max(maxY, jel.y);
        }

        System.out.println("5. Feladat: \n" +
                           "  Bal  alsó:  x=" + minX + " y=" + minY + "\n" +
                           "  Jobb felső: x=" + maxX + " y=" + maxY);

        var elmozdulasOsszeg = 0.0;
        for(var i = 0; i < jelek.size() - 1; ++i) {
            var jel1 = jelek.get(i);
            var jel2 = jelek.get(i + 1);

            elmozdulasOsszeg += Math.sqrt(Math.pow(jel1.x - jel2.x, 2) + Math.pow(jel1.y - jel2.y, 2));
        }

        System.out.printf("6. Feladat: Elmozdulás: %.3f\n", elmozdulasOsszeg);

        var kimaradtJelSorok = new ArrayList<String>();

        for(var i = 0; i < jelek.size() - 1; ++i) {
            var jel1 = jelek.get(i);
            var jel2 = jelek.get(i + 1);
            var elteltMpek = eltelt(jel1.idopont, jel2.idopont);
            var legnagyobbKoordinataElteres = Math.max(Math.abs(jel1.x - jel2.x), Math.abs(jel1.y - jel2.y));

            var kimaradasokSzamaMpekSzerint = (elteltMpek - 1) / 300;
            var kimaradasokSzamaKoordinataElteresSzerint = (legnagyobbKoordinataElteres - 1) / 10;

            if(kimaradasokSzamaMpekSzerint == 0 && kimaradasokSzamaKoordinataElteresSzerint == 0) {
                continue;
            }

            var okResz = kimaradasokSzamaMpekSzerint > kimaradasokSzamaKoordinataElteresSzerint ? ("időeltérés " + kimaradasokSzamaMpekSzerint)
                                                                                                : ("koordináta-eltérés " + kimaradasokSzamaKoordinataElteresSzerint);

            kimaradtJelSorok.add(jel2.idopont.getHour() + " " + jel2.idopont.getMinute() + " " + jel2.idopont.getSecond() + " " + okResz);
        }

        Files.write(Path.of("kimaradt.txt"), kimaradtJelSorok);
    }

    public static int eltelt(LocalTime time1, LocalTime time2) {
        return (int) Duration.between(time1, time2).toSeconds();
    }
}