import module java.base;

public class Letra_stream {

    public static void main(String[] args) throws Exception {
        var dobasok = Arrays.stream(Files.readString(Path.of("dobasok.txt")).trim().split(", "))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        var lepesek = Stream.iterate(new LepesAllapot(0, 0, false), k -> k.dobasI == dobasok.length ? null : lepestAlkalmaz(k, dobasok[k.dobasI]))
                            .skip(1)
                            .takeWhile(Objects::nonNull)
                            .toArray(LepesAllapot[]::new);

        var kiirando = Arrays.stream(lepesek)
                             .map(k -> Integer.toString(k.ertek))
                             .collect(Collectors.joining(" "));

        System.out.println("2. Feladat: " + kiirando);

        var visszalepesekSzama = Arrays.stream(lepesek)
                                       .filter(LepesAllapot::visszalepes)
                                       .count();

        System.out.println("3. Feladat: A játék során " + visszalepesekSzama + " alkalommal lépett létrára");
        System.out.println("4. Feladat: " + (lepesek[lepesek.length - 1].ertek > 45 ? "A játékot befejezte" : "A játékot abbahagyta"));
    }

    static LepesAllapot lepestAlkalmaz(LepesAllapot elozoAllapot, int dobottErtek) {
        var osszeg = elozoAllapot.ertek + dobottErtek;
        var visszalepes = osszeg % 10 == 0;

        return new LepesAllapot(elozoAllapot.dobasI + 1, visszalepes ? osszeg - 3 : osszeg, visszalepes);
    }

    record LepesAllapot(int dobasI, int ertek, boolean visszalepes) {}
}