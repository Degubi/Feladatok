import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Kektura_stream{
    
    public static void main(String[] args) throws IOException{
        var lines = Files.readAllLines(Path.of("kektura.csv"));
        var szint = Integer.parseInt(lines.get(0));
        var szakaszok = lines.stream()
                             .skip(1)
                             .map(Szakasz::new)
                             .toArray(Szakasz[]::new);
        
        System.out.println("3.Feladat: Szakaszok száma: " + szakaszok.length + " db");
        System.out.println("4.Feladat: Teljes hossz: " + Arrays.stream(szakaszok).mapToDouble(k -> k.hossz).sum() + " km");
        
        Arrays.stream(szakaszok)
              .min(Comparator.comparingDouble(k -> k.hossz))
              .ifPresent(k -> System.out.println("5.Feladat: Legrövidebb szakasz adatai: " + k));
        
        System.out.println("7.Feladat: Hiányos állomásnevek:");
        var hianyosok = Arrays.stream(szakaszok).filter(Szakasz::hianyosNev).toArray(Szakasz[]::new);
        
        if(hianyosok.length == 0) {
            System.out.println("Nincs hiányos állomásnév");
        }else{
            Arrays.stream(hianyosok)
                  .map(k -> k.vegPont)
                  .forEach(System.out::println);
        }
        
        Arrays.stream(szakaszok)
              .max(Comparator.comparingInt(k -> k.magasraa(szint)))
              .ifPresent(k -> System.out.printf("8.Feladat: A túra legmagasabban pont neve: %s, magassága: %dm\n", k.vegPont, k.magasraa(szint)));
        
        var fileba = Arrays.stream(szakaszok)
                           .map(szakasz -> szakasz + (szakasz.hianyosNev() ? " pecsetelohely" : ""))
                           .collect(Collectors.joining("\n"));
        
        Files.writeString(Path.of("kektura2.csv"), szint + "\n" + fileba);
    }
}