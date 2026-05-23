import module java.base;

public class Szamlalas_stream {

    public static void main(String[] args) throws Exception {
        var meresek = Arrays.stream(Files.readString(Path.of("meres.txt")).trim().split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        var osszesKerekparos = Arrays.stream(meresek)
                                     .map(Szamlalas_stream::kerekparosErtekOsszeadashoz)
                                     .sum();

        System.out.println("2. Feladat: Összesen " + osszesKerekparos + " kerékpárost számoltak");
        System.out.println("3. Feladat: Óránkénti mérések:");

        var orankentiAthaladok = Arrays.stream(meresek)
                                       .boxed()
                                       .gather(Gatherers.windowFixed(4))
                                       .mapToInt(k -> k.stream().mapToInt(Szamlalas_stream::kerekparosErtekOsszeadashoz).sum())
                                       .toArray();

        IntStream.range(0, orankentiAthaladok.length)
                 .forEach(i -> System.out.println((i + 6) + " órától " + orankentiAthaladok[i] + " kerékpáros"));

        IntStream.range(0, meresek.length)
                 .boxed()
                 .max(Comparator.comparingInt(i -> meresek[i]))
                 .ifPresent(i -> System.out.println("Az áthaladók maximális száma: " + meresek[i] + ", időpontja: " + idotFormaz(i)));
    }

    static int kerekparosErtekOsszeadashoz(int ertek) {
        return ertek == -1 ? 0 : ertek;
    }

    static String idotFormaz(int meresI) {
        var percek = 6 * 60 + 15 + (meresI * 15);

        return (percek / 60) + ":" + (percek % 60);
    }
}
