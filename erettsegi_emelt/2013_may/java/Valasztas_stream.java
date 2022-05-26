import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Valasztas_stream {

    public static void main(String[] args) throws IOException{
        var szavazatok = Files.lines(Path.of("szavazatok.txt")).map(Szavazat::new).toArray(Szavazat[]::new);
        var kepviselokSzama = Arrays.stream(szavazatok).map(k -> k.nev).distinct().count();

        System.out.println("2.Feladat: Választáson indult képviselők száma: " + kepviselokSzama);

        try(var console = new Scanner(System.in)) {
            System.out.println("3.Feladat: Írd be 1 képviselő Első nevét");
            var firstName = console.nextLine();

            System.out.println("Írd be 1 képviselő Második nevét");
            var lastName = console.nextLine();

            Arrays.stream(szavazatok)
                  .filter(k -> k.nev.equals(firstName + " " + lastName))
                  .findAny()
                  .ifPresentOrElse(l -> System.out.println("A jelöltre jött szavazatok száma: " + l.szavazottSzam),
                                  () -> System.out.println("Nem volt ilyen jelölt!"));
        }

        var szavazatokSzama = Arrays.stream(szavazatok).mapToInt(k -> k.szavazottSzam).sum();
        System.out.printf("4.Feladat: A választáson " + szavazatokSzama +
                          " szavaztak, ami százalékban az összesnek a %.2f %%-a.\n", ((float) szavazatokSzama / 12_345) * 100);

        System.out.println("5.Feladat");
        Arrays.stream(szavazatok)
              .collect(Collectors.groupingBy(k -> k.part, Collectors.summingInt(l -> l.szavazottSzam)))
              .forEach((key, value) -> System.out.printf(key + "-ra szavazottak száma: %.2f %%.\n", ((float) value / szavazatokSzama) * 100));

        System.out.println("6.Feladat");
        var legtobbSzavazat = Arrays.stream(szavazatok)
                                    .max(Comparator.comparingInt(k -> k.szavazottSzam))
                                    .orElseThrow()
                                    .szavazottSzam;
        Arrays.stream(szavazatok)
              .filter(k -> k.szavazottSzam == legtobbSzavazat)
              .forEach(k -> System.out.println(k.nev + ", támogató párt neve: " + k.part));

        var fileba = Arrays.stream(szavazatok)
                           .collect(Collectors.groupingBy(k -> k.part, Collectors.maxBy(Comparator.comparingInt(l -> l.szavazottSzam))))
                           .entrySet().stream()
                           .sorted(Comparator.comparingInt(l -> l.getValue().get().kerSzam))
                           .map(entry -> entry.getValue().get().kerSzam + " " + entry.getValue().get().nev + " " + entry.getKey())
                           .collect(Collectors.toList());

        Files.write(Path.of("kepviselok.txt"), fileba);
    }
}