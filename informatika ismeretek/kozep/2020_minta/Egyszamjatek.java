import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Egyszamjatek {

    @SuppressWarnings("boxing")
    public static void main(String[] args) throws IOException {
        var jatekosok = beolvas("egyszamjatek1.txt");
        
        System.out.println("3. Feladat: Játékosok száma: " + jatekosok.size());
        System.out.println("4. Feladat: Írj be egy forduló számot!");
        
        try(var conIn = new Scanner(System.in)){
            var beForduloSzam = conIn.nextInt() - 1;
            var forduloTotal = 0F;
            
            for(var tippek : jatekosok.values()) {
                forduloTotal += tippek[beForduloSzam];
            }
            
            System.out.printf("5. Feladat: Tippek átlaga: %.2f", forduloTotal / jatekosok.size());
        }
    }
    
    public static HashMap<String, int[]> beolvas(String file) throws IOException{
        var jatekosok = new HashMap<String, int[]>();
        
        for(var line : Files.readAllLines(Path.of(file))) {
            var split = line.split(" ");
            var tippek = new int[split.length - 1];
            
            for(var i = 1; i < split.length; ++i) {
                tippek[i - 1] = Integer.parseInt(split[i]);
            }
            
            jatekosok.put(split[0], tippek);
        }
        
        return jatekosok;
    }
}