import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.Map.*;
import java.util.stream.*;

public class Helsinki2017_stream {

    public static void main(String[] args) throws IOException {
        var versenyzok = Files.lines(Path.of("rovidprogram.csv")).skip(1).map(Versenyzo::new).toArray(Versenyzo[]::new);
        var dontosok = Files.lines(Path.of("donto.csv")).skip(1).map(Versenyzo::new).toArray(Versenyzo[]::new);

        System.out.println("2. Feladat: Versenyzők száma: " + versenyzok.length);

        var bejutottEMagyar = Arrays.stream(dontosok).anyMatch(k -> k.orszag.equals("HUN"));
        System.out.print("3. Feladat: A magyar versenyző " + (bejutottEMagyar ? "bejutott" : "nem jutott be"));

        try(var input = new Scanner(System.in)){
            System.out.println("5. Feladat: Kérem 1 versenyző nevét!");

            var bekertNev = input.nextLine();

            System.out.println("6. Feladat: " + bekertNev + " pontszáma: " + osszPontszam(bekertNev, versenyzok, dontosok));
        }

        System.out.println("7. Feladat");
        Arrays.stream(dontosok)
              .collect(Collectors.groupingBy(k -> k.orszag, Collectors.counting()))
              .entrySet().stream()
              .filter(k -> k.getValue() > 1)
              .forEach(k -> System.out.println(k.getKey() + ": " + k.getValue() + " db versenyző"));


        //TODO: Ebbe bele kell még számolni a versenyzok tömb pontjait
        var raw = Arrays.stream(dontosok)
                        .collect(Collectors.toMap(k -> k, Versenyzo::sumPont))
                        .entrySet().stream()
                        .sorted(Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(k -> ";" + k.getKey().nev + ";" + k.getKey().orszag + ";" + k.getValue() + "\n")
                        .toArray(String[]::new);

        var fileba = IntStream.range(0, raw.length)
                              .mapToObj(k -> (k + 1) + raw[k])
                              .collect(Collectors.joining());

        Files.writeString(Path.of("vegeredmeny.csv"), fileba);
    }

    public static double osszPontszam(String nev, Versenyzo[] versenyzok, Versenyzo[] dontosok) {
        var versenyPont = Arrays.stream(versenyzok).filter(k -> k.nev.equals(nev)).mapToDouble(Versenyzo::sumPont).sum();
        var dontosPont = Arrays.stream(dontosok).filter(k -> k.nev.equals(nev)).mapToDouble(Versenyzo::sumPont).sum();

        return versenyPont + dontosPont;
    }
}