import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

public class Txt2Srt_stream{
    
    public static void main(String[] args) throws IOException{
        var file = Files.readAllLines(Path.of("feliratok.txt"));
        var feliratok = IntStream.iterate(0, k -> k < file.size(), k -> k + 2)
                                 .mapToObj(i -> new IdozitettFelirat(file.get(i), file.get(i + 1)))
                                 .toArray(IdozitettFelirat[]::new);

        System.out.println("5. feladat: A feliratok száma: " + feliratok.length);
        Arrays.stream(feliratok)
              .max(Comparator.comparingInt(k -> k.szavakSzama()))
              .ifPresent(talalt -> System.out.println("7. feladat: A legtöbb szóból álló felirat: " + talalt.felirat));
        
        var toFile = IntStream.range(0, feliratok.length)
                              .mapToObj(i -> (i + 1) + "\n" + feliratok[i].strIdozites() + "\n" + feliratok[i].felirat)
                              .collect(Collectors.joining("\n\n"));
        
        Files.writeString(Path.of("felirat.srt"), toFile);
    }
    
    public static class IdozitettFelirat{
        public final String idozites;
        public final String felirat;

        public IdozitettFelirat(String idoz, String felir){
            idozites = idoz;
            felirat = felir;
        }

        public int szavakSzama(){
            return felirat.chars().filter(k -> k == ' ').map(k -> 1).sum() + 1;
        }
        
        private static String str_idoforma(String ido){
            return LocalTime.parse(ido, DateTimeFormatter.ISO_LOCAL_TIME.withResolverStyle(ResolverStyle.LENIENT))
                            .format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        }

        public String strIdozites(){
            var split = idozites.split(" - ");
            return str_idoforma("00:" + split[0]) + "--> " + str_idoforma("00:" + split[1]);
        }
    }
}