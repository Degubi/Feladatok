import java.io.*;
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

        System.out.println("2. feladat: A vetélkedőn " + versenyzok.size() + " versenyző indult.\nÍrj be 1 ID-t!");

        var readID = input.nextLine();
        for(var mindenki : versenyzok){
            if(mindenki.nev.equals(readID)){
                System.out.println("3. feladat: A versenyző azonosítója = " + readID + "\n" + String.valueOf(mindenki.valaszok) + " (a versenyző válaszai)");
                System.out.println("4. feladat:\n" + String.valueOf(megoldasok) + " (a helyes megoldás)");

                for(var k = 0; k < megoldasok.length; ++k) {
                    if(mindenki.valaszok[k] == megoldasok[k]) {
                        System.out.print("+");
                    }else{
                        System.out.print(" ");
                    }
                }
                System.out.println(" (a versenyző helyes válaszai)");
            }
        }

        System.out.println("Írd be 1 feladat sorszámát!");
        var readIndex = input.nextInt() - 1;
        var good = 0;

        for(var mindenki : versenyzok) {
            if(mindenki.valaszok[readIndex] == megoldasok[readIndex]) {
                ++good;
            }
        }
        input.close();

        System.out.println("5. feladat: A feladat sorszáma = " + (readIndex + 1));
        String percent = String.valueOf(((float)good * 100 / versenyzok.size())).substring(0, 5);
        System.out.println("A feladatra " + good + " fő, a versenyzők " + percent + "%-a adott helyes választ.");

        try(var output = new PrintWriter("pontok.txt")){
            for(var mindenki : versenyzok) {
                var points = 0;

                for(var k = 0; k < megoldasok.length; ++k) {
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

        var haromLegtobbPontszam = new ArrayList<Integer>();
        for(var versenyzo : versenyzok) {
            if(!haromLegtobbPontszam.contains(versenyzo.pontok)) {
                haromLegtobbPontszam.add(versenyzo.pontok);
            }

            if(haromLegtobbPontszam.size() == 3) {
                break;
            }
        }

        for(var versenyzo : versenyzok) {
            var pontIndex = haromLegtobbPontszam.indexOf(versenyzo.pontok);

            if(pontIndex == -1) {
                break;
            }

            System.out.println((pontIndex + 1) + ". díj " + versenyzo);
        }
    }

    public static class Versenyzo {

        public final String nev;
        public final char[] valaszok;
        public int pontok;

        public Versenyzo(String data) {
            var split = data.split(" ");

            nev = split[0];
            valaszok = split[1].toCharArray();
        }

        @Override
        public String toString() {
            return pontok + ": " + nev;
        }
    }
}