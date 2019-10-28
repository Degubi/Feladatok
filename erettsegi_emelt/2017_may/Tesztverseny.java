import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Tesztverseny {
    
    public static void main(String[] args) throws IOException{
        var input = new Scanner(System.in);
        var lines = Files.readAllLines(Paths.get("valaszok.txt"));
        
        var megoldasok = lines.get(0).toCharArray();
        var versenyzok = new ArrayList<Versenyzo>();
        for(var k = 1; k < lines.size(); ++k) {
            versenyzok.add(new Versenyzo(lines.get(k)));
        }
        
        System.out.println("2. feladat: A vetélkedõn " + versenyzok.size() + " versenyzõ indult.\nÍrj be 1 ID-t!");
        
        var readID = input.nextLine();
        for(var mindenki : versenyzok){
            if(mindenki.nev.equals(readID)){
                System.out.println("3. feladat: A versenyzõ azonosítója = " + readID + "\n" + String.valueOf(mindenki.valaszok) + " (a versenyzõ válaszai)");
                System.out.println("4. feladat:\n" + String.valueOf(megoldasok) + " (a helyes megoldás)");
                
                for(var k = 0; k < megoldasok.length; ++k) {
                    if(mindenki.valaszok[k] == megoldasok[k]) {
                        System.out.print("+");
                    }else{
                        System.out.print(" ");
                    }
                }
                System.out.println(" (a versenyzõ helyes válaszai)");
            }
        }
        
        System.out.println("Írd be 1 feladat sorszámát!");
        int readIndex = input.nextInt() - 1;
        int good = 0;
        
        for(var mindenki : versenyzok) {
            if(mindenki.valaszok[readIndex] == megoldasok[readIndex]) {
                ++good;
            }
        }
        input.close();
        
        System.out.println("5. feladat: A feladat sorszáma = " + (readIndex + 1));
        String percent = String.valueOf(((float)good * 100 / versenyzok.size())).substring(0, 5);
        System.out.println("A feladatra " + good + " fõ, a versenyzõk " + percent + "%-a adott helyes választ.");
        
        try(var output = new PrintWriter("pontok.txt")){
            for(var mindenki : versenyzok) {
                int points = 0;
                for(int k = 0; k < megoldasok.length; ++k) {
                    if(mindenki.valaszok[k] == megoldasok[k]) {
                        if(k <= 4) {
                            points += 3;
                        }else if(k >= 5 && k <= 9) {
                            points += 4;
                        }else if(k >= 10 && k <= 12) {
                            points += 5;
                        }else{
                            points += 6;
                        }
                    }
                }
                mindenki.pontok = points;
                output.println(mindenki.nev + " " + points);
            }
        }
        versenyzok.sort(Comparator.comparingInt((Versenyzo k) -> k.pontok).reversed());
        
        System.out.println("7. feladat: A verseny legjobbjai:");
        for(int k = 1, index = 0; k < 4; ++k, ++index) {
            var versenyzo = versenyzok.get(index);
            System.out.println(k + ". díj " + versenyzo);
            
            for(int v = index + 1; versenyzok.get(v).pontok == versenyzo.pontok; ++v) {
                System.out.println(k + ". díj "+ versenyzok.get(index));
                ++index;
            }
        }
    }
    
    static class Versenyzo{
        String nev;
        char[] valaszok;
        int pontok;
        
        public Versenyzo(String data) {
            String[] split = data.split(" ");
            nev = split[0];
            valaszok = split[1].toCharArray();
        }
        
        @Override
        public String toString() {
            return pontok + ": " + nev;
        }
    }
}