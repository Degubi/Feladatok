import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Tesztverseny_stream {
    
    public static void main(String[] args) throws IOException{
        var lines = Files.readAllLines(Paths.get("valaszok.txt"), StandardCharsets.UTF_8);
        
        var megoldasok = lines.get(0).toCharArray();
        var versenyzok = lines.stream()
                              .skip(1)
                              .map(k -> new Versenyzo(k, megoldasok))
                              .sorted(Comparator.comparingInt(k -> k.pontok))
                              .collect(Collectors.toList());
        
        System.out.println("2. feladat: A vetélkedőn " + versenyzok.size() + " versenyző indult.\nÍrj be 1 ID-t!");
        
        try(var input = new Scanner(System.in)){
            var readID = input.nextLine();
            var kivalasztott = versenyzok.stream()
                                         .filter(k -> k.nev.equals(readID))
                                         .findFirst()
                                         .orElseThrow();
            
            System.out.println("3. feladat: A versenyző azonosítója = " + readID + "\n" + String.valueOf(kivalasztott.valaszok) + " (a versenyző válaszai)");
            System.out.println("4. feladat:\n" + String.valueOf(megoldasok) + " (a helyes megoldás)");
            
            IntStream.range(0, megoldasok.length)
                     .mapToObj(index -> kivalasztott.valaszok[index] == megoldasok[index] ? "+" : " ")
                     .forEach(System.out::print);
            
            System.out.println(" (a versenyző helyes válaszai)\nÍrd be 1 feladat sorszámát!");
    
            var readIndex = input.nextInt() - 1;
            var good = versenyzok.stream()
                                 .filter(k -> k.valaszok[readIndex] == megoldasok[readIndex])
                                 .mapToInt(k -> 1)
                                 .sum();
            
            
            System.out.println("5. feladat: A feladat sorszáma = " + (readIndex + 1));
            String percent = String.valueOf(((float)good * 100 / versenyzok.size())).substring(0, 5);
            System.out.println("A feladatra " + good + " fő, a versenyzők " + percent + "%-a adott helyes választ.");
        }
        
        Files.write(Paths.get("pontok.txt"), versenyzok.stream().map(Versenyzo::toString).collect(Collectors.toList()));
        System.out.println("7. feladat: A verseny legjobbjai:");
        
        var pontok = versenyzok.stream()
                               .mapToInt(k -> -k.pontok)  //Negatív mapelős trükk a csökkenő sorrend miatt.. :(
                               .distinct()
                               .sorted()
                               .map(k -> -k)
                               .toArray();
        
        IntStream.rangeClosed(1, 3)
                 .forEach(index -> versenyzok.stream()
                                                  .filter(k -> k.pontok == pontok[index - 1])
                                                  .forEach(versenyzo -> System.out.println(index + ". díj: " + versenyzo)));
    }
    
    public static class Versenyzo{
        public final String nev;
        public final char[] valaszok;
        public final int pontok;
        
        public Versenyzo(String data, char[] megoldasok) {
            var split = data.split(" ");
            nev = split[0];
            valaszok = split[1].toCharArray();
            
            pontok = IntStream.range(0, megoldasok.length)
                     .filter(k -> megoldasok[k] == valaszok[k])
                     .map(Versenyzo::sumPoint)
                     .sum();
        }
        
        private static int sumPoint(int index) {
            if(index <= 4) return 3;
            if(index >= 5 && index <= 9) return 4;
            if(index >= 10 && index <= 12) return 5;
            return 6;
        }
        
        @Override
        public String toString() {
            return pontok + ": " + nev;
        }
    }
}