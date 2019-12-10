import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.*;

public class Uzemanyag_stream {
    
    public static void main(String[] args) throws IOException {
        var valtozasok = Files.lines(Path.of("uzemanyag.txt")).map(Arvaltozas::new).toArray(Arvaltozas[]::new);
        
        System.out.println("3. Feladat: V�ltoz�sok sz�ma: " + valtozasok.length);
        
        var legkisebbKul = Arrays.stream(valtozasok)
                                 .min(Comparator.comparingInt(k -> Integer.max(k.benzinAr, k.gazolajAr) - Integer.min(k.benzinAr, k.gazolajAr)))
                                 .map(k -> Integer.max(k.benzinAr, k.gazolajAr) - Integer.min(k.benzinAr, k.gazolajAr))
                                 .orElseThrow();
        
        System.out.println("4. Feladat: Legkisebb k�l�nbs�g: " + legkisebbKul);
        
        var legkisebbDarab = Arrays.stream(valtozasok)
                                   .filter(k -> Integer.max(k.benzinAr, k.gazolajAr) - Integer.min(k.benzinAr, k.gazolajAr) == legkisebbKul)
                                   .count();
        
        System.out.println("5. Feladat: El�fordul�s: " + legkisebbDarab);
        
        var volteSzokoev = Arrays.stream(valtozasok).anyMatch(k -> k.valtozasDatuma.isLeapYear());
        System.out.println("6. Feladat: Volt sz�k��v: " + (volteSzokoev ? "igen" : "nem"));
        
        var fileba = Arrays.stream(valtozasok)
                           .map(k -> String.format("%s;%.2f;%.2f", k.valtozasDatuma.toString().replace('-', '.'), k.benzinAr / 307.7F, k.gazolajAr / 307.7F))
                           .collect(Collectors.joining("\n"));
        
        Files.writeString(Path.of("euro.txt"), fileba);
        
        System.out.println("8. Feladat:");
        try(var console = new Scanner(System.in)){
            var bekertEvszam = IntStream.generate(() -> evszamotBeker(console))
                                         .dropWhile(k -> k <= 2011 || k >= 2016)
                                         .findFirst()
                                         .orElseThrow();
            
            var bekertEviValtozasok = Arrays.stream(valtozasok)
                                            .filter(k -> k.valtozasDatuma.getYear() == bekertEvszam)
                                            .toArray(Arvaltozas[]::new);
            
            IntStream.range(0, bekertEviValtozasok.length - 1)
                     .map(i -> idokulonbseg(bekertEviValtozasok[i], bekertEviValtozasok[i + 1]))
                     .max()
                     .ifPresent(kul -> System.out.println("10. Feladat: " + bekertEvszam + " leghosszabb id�szaka: " + kul + " nap volt."));
        }
    }
    
    public static int idokulonbseg(Arvaltozas kezdet, Arvaltozas veg) {
        return (int) ChronoUnit.DAYS.between(kezdet.valtozasDatuma, veg.valtozasDatuma);
    }
    
    public static int evszamotBeker(Scanner input) {
        System.out.println("�rj be 1 �vsz�mot (2010 < evszam < 2016");
        return input.nextInt();
    }
    
    public static class Arvaltozas{
        public final LocalDate valtozasDatuma;
        public final int benzinAr;
        public final int gazolajAr;
        
        public Arvaltozas(String line) {
            var split = line.split(";");
            
            valtozasDatuma = LocalDate.parse(split[0].replace('.', '-'));
            benzinAr = Integer.parseInt(split[1]);
            gazolajAr = Integer.parseInt(split[2]);
        }
    }
}