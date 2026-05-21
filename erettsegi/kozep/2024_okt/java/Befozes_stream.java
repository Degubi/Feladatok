import module java.base;

public class Befozes_stream {

    public static void main(String[] args) throws Exception {
        var urtartalmak = Arrays.stream(Files.readString(Path.of("uvegek.txt")).trim().split(", "))
                                .mapToInt(Integer::parseInt)
                                .toArray();

        var bekertUrtartalom = Integer.parseInt(System.console().readLine("2. Feladat: Mari néni lekvárja (dl): "));

        IntStream.range(0, urtartalmak.length)
                 .boxed()
                 .max(Comparator.comparingInt(i -> urtartalmak[i]))
                 .ifPresent(i -> System.out.println("3. Feladat: A legnagyobb üveg: " + urtartalmak[i] + " dl és " + (i + 1) + ". a sorban"));

        var elegVanE = bekertUrtartalom <= Arrays.stream(urtartalmak).sum();

        System.out.println("4. Feladat: " + (elegVanE ? "Elegendő üveg volt" : "Maradt lekvár"));
    }
}