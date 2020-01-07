import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class AutoVerseny_stream {
    public static void main(String[] args) throws IOException {
        var versenyek = Files.lines(Path.of("autoverseny.csv"))
                             .skip(1)
                             .map(Verseny::new)
                             .toArray(Verseny[]::new);
        
        System.out.println("3. Feladat: Adatsorok száma: " + versenyek.length);
        
        Arrays.stream(versenyek)
              .filter(k -> k.versenyzo.equals("Fürge Ferenc"))
              .filter(k -> k.palya.equals("Gran Prix Circuit"))
              .filter(k -> k.kor == 3)
              .findFirst()
              .ifPresent(k -> System.out.println("4. Feladat: " + k.korido.toSecondOfDay() + " mp"));
        
        try(var input = new Scanner(System.in)){
            System.out.println("5. Felatad: Írj be egy nevet!");
            var beNev = input.nextLine();
            
            System.out.print("6. Feladat: ");
            Arrays.stream(versenyek)
                  .filter(k -> k.versenyzo.equals(beNev))
                  .min(Comparator.comparing(k -> k.korido))
                  .ifPresentOrElse(k -> System.out.println(k.korido), () -> System.out.println("Nincs ilyen versenyző"));
        }
    }
    
    public static class Verseny{
        public final String csapat;
        public final String versenyzo;
        public final int eletkor;
        public final String palya;
        public final LocalTime korido;
        public final int kor;
        
        public Verseny(String line) {
            var split = line.split(";");
            
            csapat = split[0];
            versenyzo = split[1];
            eletkor = Integer.parseInt(split[2]);
            palya = split[3];
            korido = LocalTime.parse(split[4]);
            kor = Integer.parseInt(split[5]);
        }
    }
}