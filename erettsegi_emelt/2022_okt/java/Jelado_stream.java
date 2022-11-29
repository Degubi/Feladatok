import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.stream.*;

public class Jelado_stream {

    public static void main(String[] args) throws IOException {
        var jelek = Files.lines(Path.of("jel.txt"))
                         .map(Jel::new)
                         .toArray(Jel[]::new);

        try(var console = new Scanner(System.in)) {
            System.out.println("2. Feladat: Írja be 1 jel sorszámát!");

            var bekertJel = jelek[console.nextInt() - 1];

            System.out.println("Bekért jel pozíciója: x=" + bekertJel.x + " y=" + bekertJel.y);
        }

        var elteltIdo = LocalTime.ofSecondOfDay(eltelt(jelek[0].idopont, jelek[jelek.length - 1].idopont));

        System.out.println("4. Feladat: Első & utolsó között eltelt idő: " + elteltIdo);

        var xStat = Arrays.stream(jelek).mapToInt(k -> k.x).summaryStatistics();
        var yStat = Arrays.stream(jelek).mapToInt(k -> k.y).summaryStatistics();

        System.out.println("5. Feladat: \n" +
                           "  Bal  alsó:  x=" + xStat.getMin() + " y=" + yStat.getMin() + "\n" +
                           "  Jobb felső: x=" + xStat.getMax() + " y=" + yStat.getMax());

        var elmozdulasOsszeg = IntStream.range(0, jelek.length - 1)
                                        .mapToDouble(i -> elmozdulastSzamol(jelek, i))
                                        .sum();

        System.out.printf("6. Feladat: Elmozdulás: %.3f\n", elmozdulasOsszeg);

        var kimaradtJelSorok = IntStream.range(0, jelek.length - 1)
                                        .mapToObj(i -> kimaradastKeres(jelek, i))
                                        .filter(Objects::nonNull)
                                        .collect(Collectors.toList());

        Files.write(Path.of("kimaradt.txt"), kimaradtJelSorok);
    }

    public static int eltelt(LocalTime time1, LocalTime time2) {
        return (int) Duration.between(time1, time2).toSeconds();
    }


    private static String kimaradastKeres(Jel[] jelek, int i) {
        var jel1 = jelek[i];
        var jel2 = jelek[i + 1];
        var elteltMpek = eltelt(jel1.idopont, jel2.idopont);
        var legnagyobbKoordinataElteres = Math.max(Math.abs(jel1.x - jel2.x), Math.abs(jel1.y - jel2.y));

        var kimaradasokSzamaMpekSzerint = (elteltMpek - 1) / 300;
        var kimaradasokSzamaKoordinataElteresSzerint = (legnagyobbKoordinataElteres - 1) / 10;

        if(kimaradasokSzamaMpekSzerint == 0 && kimaradasokSzamaKoordinataElteresSzerint == 0) {
            return null;
        }

        var okResz = kimaradasokSzamaMpekSzerint > kimaradasokSzamaKoordinataElteresSzerint ? ("időeltérés " + kimaradasokSzamaMpekSzerint)
                                                                                            : ("koordináta-eltérés " + kimaradasokSzamaKoordinataElteresSzerint);

        return jel2.idopont.getHour() + " " + jel2.idopont.getMinute() + " " + jel2.idopont.getSecond() + " " + okResz;
    }

    private static double elmozdulastSzamol(Jel[] jelek, int i) {
        var jel1 = jelek[i];
        var jel2 = jelek[i + 1];

        return Math.sqrt(Math.pow(jel1.x - jel2.x, 2) + Math.pow(jel1.y - jel2.y, 2));
    }
}