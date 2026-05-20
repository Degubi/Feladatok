import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class Nyomas_stream {

    public static void main(String[] args) throws Exception {
        var meresek = Arrays.stream(Files.readString(Path.of("nyomas.txt")).split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        var legkisebbIndex = IntStream.range(0, meresek.length)
                                      .boxed()
                                      .min(Comparator.comparingInt(i -> meresek[i]))
                                      .orElseThrow();

        System.out.println("A legkisebb mért érték: " + meresek[legkisebbIndex] + ", sorszáma: " + (legkisebbIndex + 1));

        var bekertErtek = Integer.parseInt(System.console().readLine("Minél kisebb értékeket keres? (egész szám) "));
        var kisebbErtekekSzama = Arrays.stream(meresek)
                                       .filter(k -> k < bekertErtek)
                                       .count();

        System.out.println(bekertErtek + " alatti mérések száma: " + kisebbErtekekSzama);

        var legnagyobbCsokkenes = Arrays.stream(meresek)
                                        .boxed()
                                        .gather(Gatherers.windowSliding(2))
                                        .mapToInt(k -> k.getFirst() - k.getLast())
                                        .max()
                                        .orElseThrow();

        System.out.println("A két mérés közötti legnagyobb csökkenés: " + legnagyobbCsokkenes);
    }
}
