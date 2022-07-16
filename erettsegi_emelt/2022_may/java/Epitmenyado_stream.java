import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Epitmenyado_stream {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("utca.txt"));
        var firstLineSplit = Arrays.stream(lines.get(0).split(" ")).mapToInt(Integer::parseInt).toArray();
        var fizetendoAdokSavonkent = Map.of("A", firstLineSplit[0], "B", firstLineSplit[1], "C", firstLineSplit[2]);
        var telkek = lines.stream()
                          .skip(1)
                          .map(k -> k.split(" "))
                          .map(Telek::new)
                          .toArray(Telek[]::new);

        System.out.println("2. Feladat: Telkek száma: " + telkek.length);
        System.out.println("3. Feladat: Írj be 1 adószámot!");

        try(var console = new Scanner(System.in)) {
            var bekertAdoszam = console.nextInt();
            var bekertTelkei = Arrays.stream(telkek)
                                     .filter(k -> k.adoszam == bekertAdoszam)
                                     .toArray(Telek[]::new);

            var kiirando = bekertTelkei.length == 0 ? "Nem szerepel az adatállományban"
                                                    : Arrays.stream(bekertTelkei)
                                                            .map(k -> k.utcaNev + " " + k.hazSzam)
                                                            .collect(Collectors.joining("\n"));
            System.out.println(kiirando);
        }

        System.out.println("5. Feladat:");

        Arrays.stream(telkek)
              .collect(Collectors.groupingBy(k -> k.adosav))
              .forEach((sav, telkekSavhoz) -> System.out.println(sav + " sáv: " + telkekSavhoz.size() + " telek, " +
                                                                 "adó: " + telkekSavhoz.stream().mapToInt(m -> ado(m, fizetendoAdokSavonkent)).sum()));

        System.out.println("6. Feladat: Több sávba sorolt utcák:");

        Arrays.stream(telkek)
              .collect(Collectors.groupingBy(k -> k.utcaNev, Collectors.mapping(k -> k.adosav, Collectors.toSet())))
              .entrySet().stream()
              .filter(k -> k.getValue().size() > 1)
              .forEach(k -> System.out.println(k.getKey()));

        var fileba = Arrays.stream(telkek)
                           .collect(Collectors.groupingBy(k -> k.adoszam, Collectors.summingInt(k -> ado(k, fizetendoAdokSavonkent))))
                           .entrySet().stream()
                           .map(k -> k.getKey() + " " + k.getValue())
                           .collect(Collectors.toList());

        Files.write(Path.of("fizetendo.txt"), fileba);
    }

    static int ado(Telek telek, Map<String, Integer> fizetendoAdokSavonkent) {
        var mennyiseg = (int) fizetendoAdokSavonkent.get(telek.adosav) * telek.terulet;

        return mennyiseg < 10000 ? 0 : mennyiseg;
    }
}