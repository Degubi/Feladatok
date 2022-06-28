import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Kutyak_stream {

    // A KutyaFajtak.csv 388. sorában hibás adat van, ám az eredeti név nem kell sehol a feladatban, ezért azt el se tároljuk
    public static void main(String[] args) throws IOException {
        var nevLookup = Files.lines(Path.of("KutyaNevek.csv"))
                             .skip(1)
                             .map(k -> k.split(";"))
                             .collect(Collectors.toMap(k -> Integer.parseInt(k[0]), k -> k[1]));

        var fajtaLookup = Files.lines(Path.of("KutyaFajtak.csv"))
                               .skip(1)
                               .map(k -> k.split(";"))
                               .collect(Collectors.toMap(k -> Integer.parseInt(k[0]), k -> k[1]));

        var kutyik = Files.lines(Path.of("Kutyak.csv"))
                          .skip(1)
                          .map(k -> new Kutya(k.split(";")))
                          .toArray(Kutya[]::new);

        System.out.println("3. Feladat: Kutyanevek száma: " + nevLookup.size());

        Arrays.stream(kutyik)
              .mapToInt(k -> k.eletkor)
              .average()
              .ifPresent(atlag -> System.out.printf("6. Feladat: Átlag életkor: %.2f év\n", atlag));

        Arrays.stream(kutyik)
              .max(Comparator.comparingInt(k -> k.eletkor))
              .ifPresent(k -> System.out.println("7. Feladat: Legidősebb kutya neve + fajtája: " + nevLookup.get(k.nevId) + ": " + fajtaLookup.get(k.fajtaId)));

        System.out.println("8. Feladat: 2018 január 10-én vizsgált kutyik:");
        Arrays.stream(kutyik)
              .filter(k -> k.ellenorzes.getYear() == 2018 && k.ellenorzes.getMonth() == Month.JANUARY && k.ellenorzes.getDayOfMonth() == 10)
              .collect(Collectors.groupingBy(k -> fajtaLookup.get(k.fajtaId), Collectors.counting()))
              .forEach((fajta, db) -> System.out.println(fajta + ": " + db + " kutya"));

        Arrays.stream(kutyik)
              .collect(Collectors.groupingBy(k -> k.ellenorzes, Collectors.counting()))
              .entrySet().stream()
              .max(Entry.comparingByValue())
              .ifPresent(k -> System.out.println("9. Feladat: Legjobban leterhelt nap: " + k.getKey() + ": " + k.getValue() + " db kutya"));

        var fileba = Arrays.stream(kutyik)
                           .collect(Collectors.groupingBy(k -> nevLookup.get(k.nevId), Collectors.counting()))
                           .entrySet().stream()
                           .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                           .map(k -> k.getKey() + ";" + k.getValue())
                           .collect(Collectors.toList());

        Files.write(Path.of("nevstatisztika.txt"), fileba);
    }
}