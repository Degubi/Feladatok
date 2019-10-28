import static java.util.Comparator.*;

import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.Map.*;
import java.util.function.*;
import java.util.stream.*;

public class Kutyak_stream {

    //A KutyaFajtak.csv 388. sorában hibás adat van
    public static void main(String[] args) throws IOException {
        var nevek = readFile("KutyaNevek.csv", KutyaNev::new, KutyaNev[]::new);
        var fajtak = readFile("KutyaFajtak.csv", KutyaFajta::new, KutyaFajta[]::new);
        var kutyik = readFile("Kutyak.csv", Kutya::new, Kutya[]::new);
        
        System.out.println("3. Feladat: Kutyanevek száma: " + nevek.length);
        System.out.print("6. Feladat: Átlag életkor: ");
        
        Arrays.stream(kutyik)
              .mapToInt(k -> k.eletkor)
              .average()
              .ifPresent(atlag -> System.out.printf("%.2f év\n", atlag));
        
        var legidosebb = Arrays.stream(kutyik).max(comparingInt(k -> k.eletkor)).orElseThrow();
        System.out.print("7. Feladat: Legidõsebb kutya neve & fajtája: ");
        System.out.println(KutyaNev.getNevByID(nevek, legidosebb.nevId) + ", " + KutyaFajta.getFajtaByID(fajtak, legidosebb.fajtaId));
        
        System.out.println("8. Feladat: 2018 január 10-én vizsgált kutyik:");
        Arrays.stream(kutyik)
              .filter(k -> k.ellenorzes.getYear() == 2018)
              .filter(k -> k.ellenorzes.getMonth() == Month.JANUARY)
              .filter(k -> k.ellenorzes.getDayOfMonth() == 10)
              .collect(Collectors.groupingBy(k -> k.fajtaId, Collectors.counting()))
              .forEach((fajtaId, db) -> System.out.println(KutyaFajta.getFajtaByID(fajtak, fajtaId) + ": " + db + " kutya"));
        
        System.out.print("9. Feladat: Legjobban leterhelt nap: ");
        Arrays.stream(kutyik)
              .collect(Collectors.groupingBy(k -> k.ellenorzes, Collectors.counting()))
              .entrySet().stream()
              .max(Entry.comparingByValue())
              .ifPresent(k -> System.out.println(k.getKey() + ": " + k.getValue() + " db kutya"));
        
        var fileba = Arrays.stream(kutyik)
                           .map(k -> k.nevId)
                           .collect(Collectors.toMap(k -> KutyaNev.getNevByID(nevek, k), k -> 1, Integer::sum))
                           .entrySet().stream()
                           .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                           .map(k -> k.getKey() + ";" + k.getValue())
                           .collect(Collectors.joining("\n"));
        
        Files.writeString(Path.of("nevstatisztika.txt"), fileba);
    }
    
    
    private static<T> T[] readFile(String fileName, Function<String, T> type, IntFunction<T[]> array) throws IOException {
        return Files.lines(Path.of(fileName)).skip(1).map(type).toArray(array);
    }
    
    
    public static class KutyaNev{
        public final int id;
        public final String nev;
        
        public KutyaNev(String line) {
            var split = line.split(";");
            
            id = Integer.parseInt(split[0]);
            nev = split[1];
        }
        
        public static String getNevByID(KutyaNev[] nevek, int id) {
            return Arrays.stream(nevek).filter(k -> k.id == id).map(k -> k.nev).findFirst().orElseThrow();
        }
    }
    
    public static class KutyaFajta{
        public final int id;
        public final String nev;
        public final String eredetiNev;
        
        public KutyaFajta(String line) {
            var split = line.split(";");
            
            if(split.length == 2) {
                System.out.println(line);
            }
            
            id = Integer.parseInt(split[0]);
            nev = split[1];
            eredetiNev = split[2];
        }
        
        public static String getFajtaByID(KutyaFajta[] fajtak, int id) {
            return Arrays.stream(fajtak).filter(k -> k.id == id).map(k -> k.nev).findFirst().orElseThrow();
        }
    }
    
    public static class Kutya{
        private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        
        public final int id;
        public final int fajtaId;
        public final int nevId;
        public final int eletkor;
        public final LocalDate ellenorzes;
        
        public Kutya(String line) {
            var split = line.split(";");
            
            id = Integer.parseInt(split[0]);
            fajtaId = Integer.parseInt(split[1]);
            nevId = Integer.parseInt(split[2]);
            eletkor = Integer.parseInt(split[3]);
            ellenorzes = LocalDate.parse(split[4], timeFormat);
        }
    }
}