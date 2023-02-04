import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class SuperBowl {

    public static void main(String[] args) throws IOException {
        var dontok = Files.lines(Path.of("SuperBowl.txt"))
                          .skip(1)
                          .map(Donto::new)
                          .toArray(Donto[]::new);

        System.out.println("4. Feladat: Döntők száma: " + dontok.length);

        Arrays.stream(dontok)
              .mapToInt(k -> k.gyoztesPontjai - k.vesztesPontjai)
              .average()
              .ifPresent(k -> System.out.printf("5. Feladat: Pontkülönbség: %.1f\n", k));

        Arrays.stream(dontok)
              .max(Comparator.comparingInt(k -> k.nezoszam))
              .ifPresent(k -> System.out.println("6. Feladat: Legmagasabb nézőszámú döntő: \n" +
                                                 "    Sorszám (dátum): " + k.sorszam + " (" + k.datum + ")\n" +
                                                 "    Győztes csapat: " + k.gyoztesCsapatNeve + ", szerzett pontok: " + k.gyoztesPontjai + "\n" +
                                                 "    Vesztes csapat: " + k.vesztesCsapatNeve + ", szerzett pontok: " + k.vesztesPontjai + "\n" +
                                                 "    Helyszín: " + k.stadion + "\n" +
                                                 "    Város, állam: " + k.varos + ", " + k.allam + "\n" +
                                                 "    Nézőszám: " + k.nezoszam));

        var mezonevekHeader = "Ssz;Dátum;Győztes;Eredmény;Vesztes;Nézőszám";
        var fileba = IntStream.range(0, dontok.length)
                              .mapToObj(i -> {
                                  var donto = dontok[i];
                                  var gyoztesSzereplesek = Arrays.stream(dontok, 0, i + 1)
                                                                 .filter(k -> k.gyoztesCsapatNeve.equals(donto.gyoztesCsapatNeve) || k.vesztesCsapatNeve.equals(donto.gyoztesCsapatNeve))
                                                                 .count();

                                  var vesztesSzereplesek = Arrays.stream(dontok, 0, i + 1)
                                                                 .filter(k -> k.gyoztesCsapatNeve.equals(donto.vesztesCsapatNeve) || k.vesztesCsapatNeve.equals(donto.vesztesCsapatNeve))
                                                                 .count();

                                  return donto.sorszam + ".;" + donto.datum + ".;" + donto.gyoztesCsapatNeve + " (" + gyoztesSzereplesek + ");" + donto.gyoztesPontjai + "-" + donto.vesztesPontjai + ";" + donto.vesztesCsapatNeve + " (" + vesztesSzereplesek + ");" + donto.nezoszam;
                              })
                              .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("SuperBowlNew.txt"), mezonevekHeader + '\n' + fileba);
    }
}