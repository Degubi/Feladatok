import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Morze_stream {

    //A mintában 49 morze abc karaktert ír, de a fájlban csak 43 van
    public static void main(String[] args) throws IOException {
        var betuToMorze = Files.lines(Path.of("morzeabc.txt"), StandardCharsets.ISO_8859_1)
                               .skip(1)
                               .map(k -> k.split("\t"))
                               .collect(Collectors.toMap(k -> k[0], k -> k[1]));

        System.out.println("3. Feladat: Karakterek száma: " + betuToMorze.size());

        var bekertKarakter = System.console().readLine("4. Feladat: Írjon be 1 karaktert: ");
        if(betuToMorze.containsKey(bekertKarakter)){
            System.out.println("A " + bekertKarakter + " karakter kódja: " + betuToMorze.get(bekertKarakter));
        }else{
            System.out.println("Nem található a kódtárban ilyen karakter!");
        }

        var morzeToBetu = betuToMorze.entrySet().stream().collect(Collectors.toMap(Entry::getValue, Entry::getKey));
        var idezetek = Files.lines(Path.of("morze.txt"))
                            .map(k -> new Idezet(k, morzeToBetu))
                            .toArray(Idezet[]::new);

        System.out.println("7. Feladat: Első idézet szerzője: " + idezetek[0].szerzo);
        Arrays.stream(idezetek)
              .max(Comparator.comparingInt(k -> k.uzenet.length()))
              .ifPresent(k -> System.out.println("8. Feladat: Leghosszab idézet: " + k.szerzo + ": " + k.uzenet));

        System.out.println("9. Feladat: Arisztotelés idézetei: ");
        Arrays.stream(idezetek)
              .filter(k -> k.szerzo.equals("ARISZTOTELÉSZ"))
              .forEach(k -> System.out.println("\t- " + k.uzenet));

        var fileba = Arrays.stream(idezetek)
                           .map(k -> k.szerzo + ':' + k.uzenet)
                           .collect(Collectors.toList());

        Files.write(Path.of("forditas.txt"), fileba);
    }
}