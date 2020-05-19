import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Metjelentes_stream {

    public static void main(String[] args) throws IOException {
        var adatok = Files.lines(Path.of("tavirathu13.txt"))
                          .map(IdojarasAdat::new)
                          .toArray(IdojarasAdat[]::new);
        
        System.out.println("2. Feladat: Írj be 1 városkódot!");
        try(var console = new Scanner(System.in)) {
            var bekertKod = console.nextLine();
            
            Arrays.stream(adatok)
                  .filter(k -> k.telepules.equals(bekertKod))
                  .reduce((k, l) -> l)
                  .ifPresent(k -> System.out.println("Utolsó mérés időpontja: " + k.ido));
        }
        
        System.out.println("3. Feladat:");
        var homersekletComparator = Comparator.comparingInt((IdojarasAdat k) -> k.homerseklet);
        
        Arrays.stream(adatok)
              .min(homersekletComparator)
              .ifPresent(k -> System.out.println("Legkisebb hőmérséklet: " + k.telepules + " " + k.ido + " " + k.homerseklet + " fok"));
        
        Arrays.stream(adatok)
              .max(homersekletComparator)
              .ifPresent(k -> System.out.println("Legnagyobb hőmérséklet: " + k.telepules + " " + k.ido + " " + k.homerseklet + " fok"));
        
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
        
        var orankentiHomersekletGyujto = Collectors.groupingBy((IdojarasAdat k) -> k.ido.getHour(),
                                                               Collectors.mapping((IdojarasAdat k) -> (double) k.homerseklet, Collectors.toList()));
        
        var telepulesenkentiOrakhozTartozoHomersekletek = Arrays.stream(adatok)
                                                                .collect(Collectors.groupingBy(k -> k.telepules, orankentiHomersekletGyujto));
        
        telepulesenkentiOrakhozTartozoHomersekletek.entrySet().stream()
                                                   .map(k -> telepuleshezKiiratandohomersekletAdat(k.getKey(), k.getValue()))
                                                   .forEach(System.out::println);
        System.out.println("6. Feladat:");
        Arrays.stream(adatok)
              .collect(Collectors.groupingBy(k -> k.telepules))
              .forEach(Metjelentes_stream::telepulesAdataiFajlba);
    }

    public static String telepuleshezKiiratandohomersekletAdat(String telepules, Map<Integer, List<Double>> orankentiHomersekletek) {
        var ingadozashozStat = orankentiHomersekletek.values().stream()
                                                     .flatMap(List::stream)
                                                     .mapToDouble(Double::doubleValue)
                                                     .summaryStatistics();
        
        var ingadozas = ingadozashozStat.getMax() - ingadozashozStat.getMin();
        
        if(orankentiHomersekletek.size() < 24) {
            return telepules + ": NA; Ingadozás: " + (int) ingadozas;
        }
        
        var kerekitettKozep = (int) Math.ceil(ingadozashozStat.getAverage());
        
        return telepules + ": Középhőmérséklet: " + kerekitettKozep + "; Ingadozás: " + (int) ingadozas;
    }
    
    public static void telepulesAdataiFajlba(String telepules, List<IdojarasAdat> adat) {
        var adatSorok = adat.stream()
                            .map(k -> k.ido + " " + ("#".repeat(k.szelErosseg)))
                            .collect(Collectors.joining("\n"));
        
        var filebaIrando = telepules + '\n' + adatSorok;
        
        try {
            Files.writeString(Path.of(telepules + ".txt"), filebaIrando);
        } catch (IOException e) {}
    }
}