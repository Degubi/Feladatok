import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Futar_stream{
    
    public static void main(String[] args) throws IOException{
        var fuvarLista = Files.lines(Paths.get("tavok.txt")).map(Fuvar::new).toArray(Fuvar[]::new);
        
        System.out.println("2. Feladat: A hét legelső útja km-ben: " + fuvarLista[0].tavolsag + " km");
        System.out.println("3. Feladat: A hét utolsó útja km-ben: " + fuvarLista[fuvarLista.length - 1].tavolsag + " km");
    
        IntStream.rangeClosed(1, 7)
                 .filter(day -> Arrays.stream(fuvarLista)
                                      .mapToInt(k -> k.nap)
                                      .distinct()
                                      .noneMatch(k -> k == day))
                 .forEach(day -> System.out.println("A " + day + ". nap szabadnap volt"));
        
        System.out.println("5. Feladat: Legtöbb fuvarú nap: " + Arrays.stream(fuvarLista)
                                      .max(Comparator.comparingInt(k -> k.tavolsag))
                                      .get().nap);
        
        System.out.println("6. Feladat");
        IntStream.rangeClosed(1, 7)
                 .forEach(day -> System.out.println("A " + day + ". nap távja: " + 
                          Arrays.stream(fuvarLista)
                                 .filter(k -> k.nap == day)
                                 .mapToInt(k -> k.tavolsag)
                                 .sum()));
        
        try(var input = new Scanner(System.in)){
            System.out.println("7.Feladat: Írj be 1 távolságot!");
            int readKm = input.nextInt();
            System.out.println(readKm + " km esetén fizetendő: " + calcPrice(readKm));
        }
        
        try(var output = new PrintWriter("dijazas.txt")){
            Arrays.stream(fuvarLista).forEach(fuvar -> output.println(fuvar.nap + ". nap " + fuvar.sorszam + ". fuvar: " + calcPrice(fuvar.tavolsag) + "FT"));
        }
        System.out.println("9. Feladat: Az egész heti fizetés: " + Arrays.stream(fuvarLista).mapToInt(k -> calcPrice(k.tavolsag)).sum());
    }
    
    public static int calcPrice(int distance) {
        if(distance <= 2) {
            return 500;
        }else if(distance <= 5) {
            return 700;
        }else if(distance <= 10) {
            return 900;
        }else if(distance <= 20) {
            return 1400;
        }
        return 2000;
    }
    
    public static class Fuvar{
        public final int nap, sorszam, tavolsag;
        
        public Fuvar(String line) {
            var split = line.split(" ");
            
            nap = Integer.parseInt(split[0]);
            sorszam = Integer.parseInt(split[1]);
            tavolsag = Integer.parseInt(split[2]);
        }
    }
}