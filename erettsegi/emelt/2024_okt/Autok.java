import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import java.time.*;

public class Autok {

    record Jel(String rendszam, LocalTime idopont, int sebesseg) {}

    public static void main(String[] args) throws Exception {
        var jelek = Files.lines(Path.of("jeladas.txt"))
                         .map(k -> k.split("\t"))
                         .map(k -> new Jel(k[0], LocalTime.of(Integer.parseInt(k[1]), Integer.parseInt(k[2]), 0), Integer.parseInt(k[3])))
                         .toArray(Jel[]::new);

        var utolsoJeladas = jelek[jelek.length - 1];
        System.out.println("2. Feladat: Utolsó jeladás időpontja: " + utolsoJeladas.idopont + ", rendszám: " + utolsoJeladas.rendszam);

        var elsoRendszam = jelek[0].rendszam;
        var elsoRendszamIdopontjai = Arrays.stream(jelek)
                                           .filter(k -> k.rendszam.equals(elsoRendszam))
                                           .map(k -> k.idopont.getHour() + ":" + k.idopont.getMinute())
                                           .collect(Collectors.joining(" "));

        System.out.println("3. Feladat: Első jeladó rendszáma: " + elsoRendszam + ", időpontjai: " + elsoRendszamIdopontjai);

        var bekertOra = Integer.parseInt(System.console().readLine("4. Feladat: Írj be egy óra értéket! "));
        var bekertPerc = Integer.parseInt(System.console().readLine("Írj be egy perc értéket! "));
        var bekertIdopontbanMennyiseg = Arrays.stream(jelek)
                                              .filter(k -> k.idopont.getHour() == bekertOra && k.idopont.getMinute() == bekertPerc)
                                              .count();

        System.out.println("4. Feladat: Bekért időpontban jelek száma: " + bekertIdopontbanMennyiseg);

        var legmagasabbSebesseg = Arrays.stream(jelek)
                                        .mapToInt(Jel::sebesseg)
                                        .max()
                                        .orElseThrow();

        var nagyonGyorsak = Arrays.stream(jelek)
                                  .filter(k -> k.sebesseg == legmagasabbSebesseg)
                                  .map(Jel::rendszam)
                                  .collect(Collectors.joining(" "));

        System.out.println("5. Feladat: Legnagyobb sebesség: " + legmagasabbSebesseg + "km/h, rendszámok: " + nagyonGyorsak);

        var bekertRendszam = System.console().readLine("6. Feladat: Írj be egy rendszámot! ");
        var bekertRendszamJelek = Arrays.stream(jelek)
                                        .filter(k -> k.rendszam.equals(bekertRendszam))
                                        .toArray(Jel[]::new);

        if(bekertRendszamJelek.length == 0) {
            System.out.println("Nem található ilyen rendszám!");
        }else{
            IntStream.range(0, bekertRendszamJelek.length)
                     .boxed()
                     .reduce(0.0, (total, i) -> {
                         var start = bekertRendszamJelek[(i == 0 ? 1 : i) - 1].idopont;
                         var end = bekertRendszamJelek[i].idopont;
                         var hours = Duration.between(start, end).toMinutes() / 60.0;
                         var distanceSoFar = total + hours * bekertRendszamJelek[i].sebesseg;

                         System.out.println(end + ": " + Math.round(distanceSoFar * 10) / 10.0);
                         return distanceSoFar;
                      }, Double::sum);
        }

        var byJelIdopont = Comparator.comparing(Jel::idopont);
        var jarmuStatok = Arrays.stream(jelek)
                                .collect(Collectors.groupingBy(Jel::rendszam, Collectors.teeing(Collectors.minBy(byJelIdopont), Collectors.maxBy(byJelIdopont),
                                                                                               (v1, v2) -> new LocalTime[] { v1.orElseThrow().idopont, v2.orElseThrow().idopont } )));

        var jarmuStatokFileba = jarmuStatok.entrySet().stream()
                                           .map(k -> k.getKey() + " " + k.getValue()[0].getHour() + " " + k.getValue()[0].getMinute() + " " + k.getValue()[1].getHour() + " " + k.getValue()[1].getMinute())
                                           .collect(Collectors.toList());

        Files.write(Path.of("ido.txt"), jarmuStatokFileba);
    }
}
