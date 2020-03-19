import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;

public class Morze {
    
    //A mintában 49 morze abc karaktert ír, de a fájlban csak 43 van
    public static void main(String[] args) throws IOException {
        var abcLines = Files.readAllLines(Path.of("morzeabc.txt"), StandardCharsets.ISO_8859_1);
        var betuToMorze = new HashMap<String, String>();
        var morzeToBetu = new HashMap<String, String>();

        for(var i = 1; i < abcLines.size(); ++i) {
            var split = abcLines.get(i).split("\t");
            betuToMorze.put(split[0], split[1]);
            morzeToBetu.put(split[1], split[0]);
        }
        
        System.out.println("3. Feladat: Karakterek száma: " + betuToMorze.size());
        System.out.println("4. Feladat: Írjon be 1 karaktert!");
        
        try(var input = new Scanner(System.in)){
            var bekert = input.nextLine();
            
            if(betuToMorze.containsKey(bekert)){
                System.out.println("A " + bekert + " karakter kódja: " + betuToMorze.get(bekert));
            }else{
                System.out.println("Nem található a kódtárban ilyen karakter!");
            }
        }
        
        var morzeLines = Files.readAllLines(Path.of("morze.txt"));
        var idezetek = new ArrayList<Idezet>();
        
        for(var line : morzeLines) {
            idezetek.add(new Idezet(line, morzeToBetu));
        }
        
        System.out.println("7. Feladat: Első idézet szerzője: " + idezetek.get(0).szerzo);
        
        var leghosszabbIdezet = idezetek.get(0);
        for(var idezet : idezetek) {
            if(idezet.uzenet.length() > leghosszabbIdezet.uzenet.length()) {
                leghosszabbIdezet = idezet;
            }
        }
        
        System.out.println("8. Feladat: Leghosszab idézet: " + leghosszabbIdezet.szerzo + ": " + leghosszabbIdezet.uzenet);
        System.out.println("9. Feladat: Arisztotelés idézetei: ");
        
        for(var idezet : idezetek) {
            if(idezet.szerzo.equals("ARISZTOTELÉSZ")) {
                System.out.println("\t- " + idezet.uzenet);
            }
        }
        
        try(var forditas = new PrintWriter("forditas.txt")){
            for(var idezet : idezetek) {
                forditas.println(idezet.szerzo + ':' + idezet.uzenet);
            }
        }
    }
    
    public static String morze2Szoveg(String uzenet, Map<String, String> abc) {
        var forditott = new StringBuilder();
        
        for(var szavak : uzenet.split("       ")) {
            for(var betuk : szavak.split("   ")) {
                forditott.append(abc.get(betuk));
            }
            
            forditott.append(' ');
        }
        
        return forditott.deleteCharAt(forditott.length() - 1).toString();
    }
    
    public static class Idezet{
        public final String szerzo;
        public final String uzenet;
        
        public Idezet(String sor, Map<String, String> abc) {
            var split = sor.split(";");
            
            this.szerzo = morze2Szoveg(split[0], abc);
            this.uzenet = morze2Szoveg(split[1], abc);
        }
    }
}