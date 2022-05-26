import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Futar_stream{

    public static void main(String[] args) throws IOException{
        var fuvarLista = Files.lines(Path.of("tavok.txt")).map(Fuvar::new).toArray(Fuvar[]::new);

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
                 .mapToObj(day -> "A " + day + ". nap távja: " + Arrays.stream(fuvarLista)
                                                                       .filter(k -> k.nap == day)
                                                                       .mapToInt(k -> k.tavolsag)
                                                                       .sum())
                 .forEach(System.out::println);

        try(var console = new Scanner(System.in)){
            System.out.println("7.Feladat: Írj be 1 távolságot!");

            var readKm = console.nextInt();

            System.out.println(readKm + " km esetén fizetendő: " + calcPrice(readKm));
        }

        var fileba = Arrays.stream(fuvarLista)
                           .map(fuvar -> fuvar.nap + ". nap " + fuvar.sorszam + ". fuvar: " + calcPrice(fuvar.tavolsag) + "FT")
                           .collect(Collectors.toList());

        Files.write(Path.of("dijazas.txt"), fileba);

        System.out.println("9. Feladat: Az egész heti fizetés: " + Arrays.stream(fuvarLista).mapToInt(k -> calcPrice(k.tavolsag)).sum());
    }

    public static int calcPrice(int distance) {
        return distance <= 2  ? 500 :
               distance <= 5  ? 700 :
               distance <= 10 ? 900 :
               distance <= 20 ? 1400 : 2000;
    }
}