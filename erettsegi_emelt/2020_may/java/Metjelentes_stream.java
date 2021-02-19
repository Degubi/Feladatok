import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Metjelentes_stream {

    public static void main(String[] args) throws IOException {
        var adatok = Files.lines(Path.of("tavirathu13.txt"))
                          .map(IdojarasAdat::new)
                          .toArray(IdojarasAdat[]::new);

        System.out.println("2. Feladat: Írj be egy városkódot!");
        try(var console = new Scanner(System.in)) {
            var bekertKod = console.nextLine();

            Arrays.stream(adatok)
                  .filter(k -> k.telepules.equals(bekertKod))
                  .reduce((k, l) -> l)
                  .ifPresent(k -> System.out.println("Utolsó mérés időpontja: " + k.ido));
        }

        System.out.println("3. Feladat:");

        var rendezettAdatok = Arrays.stream(adatok)
                                    .sorted(Comparator.comparingInt(k -> k.homerseklet))
                                    .toArray(IdojarasAdat[]::new);

        var legalacsonyabbAdat = rendezettAdatok[0];
        var legmagasabbAdat = rendezettAdatok[rendezettAdatok.length - 1];

        System.out.println("Legalacsonyabb hőmérséklet: " + legalacsonyabbAdat.telepules + " " + legalacsonyabbAdat.ido + " " + legalacsonyabbAdat.homerseklet + " fok");
        System.out.println("Legmagasabb hőmérséklet: " + legmagasabbAdat.telepules + " " + legmagasabbAdat.ido + " " + legmagasabbAdat.homerseklet + " fok");

        System.out.println("4. Feladat:");
        var szelcsendek = Arrays.stream(adatok)
                                .filter(k -> k.szelIrany.equals("000") && k.szelErosseg == 0)
                                .toArray(IdojarasAdat[]::new);

        if(szelcsendek.length == 0){
            System.out.println("Nem volt szélcsend a mérések idején.");
        }else{
            Arrays.stream(szelcsendek)
                  .forEach(k -> System.out.println(k.telepules + ": " + k.ido));
        }

        System.out.println("5. Feladat:");

        var adatokTelepulesenkent = Arrays.stream(adatok)
                                          .collect(Collectors.groupingBy(k -> k.telepules));

        adatokTelepulesenkent.entrySet().stream()
                             .map(k -> telepuleshezTartozoKiiratandoHomersekletAdat(k.getKey(), k.getValue()))
                             .forEach(System.out::println);

        adatokTelepulesenkent.forEach(Metjelentes_stream::telepulesAdataiFajlba);
    }

    public static String telepuleshezTartozoKiiratandoHomersekletAdat(String telepules, List<IdojarasAdat> adatok) {
        var homersekletStat = adatok.stream()
                                    .mapToDouble(k -> k.homerseklet)
                                    .summaryStatistics();

        var ingadozas = homersekletStat.getMax() - homersekletStat.getMin();
        var mindenOraraVanAdat = adatok.stream()
                                       .mapToInt(k -> k.ido.getHour())
                                       .distinct()
                                       .count() == 24;
        if(mindenOraraVanAdat) {
            var kerekitettKozep = (int) Math.ceil(homersekletStat.getAverage());
            return telepules + ": Középhőmérséklet: " + kerekitettKozep + "; Ingadozás: " + (int) ingadozas;
        }

        return telepules + ": NA; Ingadozás: " + (int) ingadozas;
    }

    public static void telepulesAdataiFajlba(String telepules, List<IdojarasAdat> adatok) {
        var adatSorok = adatok.stream()
                              .map(k -> k.ido + " " + ("#".repeat(k.szelErosseg)))
                              .collect(Collectors.joining("\n"));
        try {
            Files.writeString(Path.of(telepules + ".txt"), telepules + '\n' + adatSorok);
        } catch (IOException e) {}
    }
}