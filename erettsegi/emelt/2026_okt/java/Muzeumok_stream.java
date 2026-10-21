import module java.base;

public class Muzeumok_stream {

    public static void main(String[] args) throws Exception {
        var muzdatLines = Files.readAllLines(Path.of("muzdat.txt"));
        var arak = muzdatLines.getFirst().split(" ");

        var teljesKastelyjegyAr = Integer.parseInt(arak[0]);
        var teljesKiallitohelyAr = Integer.parseInt(arak[1]);
        var teljesNapijegyAr = Integer.parseInt(arak[2]);
        var muzeumokNevei = Files.readAllLines(Path.of("muznev.txt")).getFirst().replace("\"", "").split(", ");
        var kastelyNyitas = LocalTime.of(10, 0);
        var kastelyZaras = LocalTime.of(18, 0);
        var belepesek = muzdatLines.stream()
                                   .skip(1)
                                   .map(k -> k.split(" "))
                                   .map(k -> new Belepes(LocalTime.parse(k[0]), k[1], Integer.parseInt(k[2]), k[3]))
                                   .toArray(Belepes[]::new);

        System.out.println("2. Feladat: Az adott napon " + belepesek.length + " látogató volt");
        System.out.println("3. Feladat:");
        System.out.println("Az első látogató " + belepesek[0].idobelyeg + "-kor lépett a(z) " + muzeumokNevei[belepesek[0].helyszinAzonosito] + " helyszínre");
        System.out.println("Az utolsó látogató " + belepesek[belepesek.length - 1].idobelyeg + "-kor lépett a(z) " + muzeumokNevei[belepesek[belepesek.length - 1].helyszinAzonosito] + " helyszínre");

        var bekertJegySorszam = System.console().readLine("4. Feladat: Kérem a jegy sorszámát! ");

        Arrays.stream(belepesek)
              .filter(k -> k.azonosito.equals(bekertJegySorszam))
              .forEach(k -> System.out.println(k.idobelyeg + " " + muzeumokNevei[k.helyszinAzonosito]));

        var utolsoJegyvasarlas = Arrays.stream(belepesek)
                                       .filter(k -> !k.jegyTipus.equals("-"))
                                       .reduce((k, l) -> l)
                                       .orElseThrow();

        var idoZarasig = Duration.between(utolsoJegyvasarlas.idobelyeg, kastelyZaras);

        System.out.println("Az utolsó vásárló " + idoZarasig.toHoursPart() + ":" + idoZarasig.toMinutesPart() + " perccel a zárás előtt vett jegyet");

        var teljesBevetel = Arrays.stream(belepesek)
                                  .mapToInt(k -> (k.jegyTipus.equals("-") ? 0 :
                                                  k.jegyTipus.endsWith("N") ? teljesNapijegyAr :
                                                  k.helyszinAzonosito == 0 ? teljesKastelyjegyAr : teljesKiallitohelyAr
                                                 ) / (k.jegyTipus.startsWith("K") ? 2 : 1))
                                  .sum();

        System.out.println("6. Feladat: A teljes bevétel " + teljesBevetel + " Ft volt");

        var napijegyVsNemNapijegy = Arrays.stream(belepesek)
                                          .filter(k -> k.jegyTipus.contains("N"))
                                          .collect(Collectors.partitioningBy(k -> k.helyszinAzonosito == 0, Collectors.counting()));

        System.out.println("7. Feladat: A napijegyet váltók általában" + (napijegyVsNemNapijegy.get(true) > napijegyVsNemNapijegy.get(false) ? "" : " nem") + " a Kastélyban kezdtek");

        var orankentiStat = Arrays.stream(belepesek)
                                  .filter(k -> k.helyszinAzonosito == 0)
                                  .collect(Collectors.groupingBy(k -> (int) Duration.between(kastelyNyitas, k.idobelyeg).toHours() + kastelyNyitas.getHour(), Collectors.counting()));

        var fileba = IntStream.range(10, 18)
                              .mapToObj(k -> k + "\t" + orankentiStat.getOrDefault(k, 0L))
                              .collect(Collectors.joining("\n"));

        Files.writeString(Path.of("stat.txt"), "óra\tfő\n" + fileba);

        System.out.println("9. Feladat: Jutalmat kapnak\nidőpont\tjegy\tkiállítás");

        Arrays.stream(belepesek)
              .collect(Collectors.groupingBy(Belepes::azonosito))
              .entrySet().stream()
              .filter(k -> k.getValue().stream().anyMatch(j -> j.jegyTipus.endsWith("N")) && k.getValue().size() == muzeumokNevei.length)
              .map(k -> k.getValue().getLast())
              .sorted(Comparator.comparing(Belepes::idobelyeg))
              .forEach(k -> System.out.println(k.idobelyeg + "\t" + k.azonosito + "\t" + muzeumokNevei[k.helyszinAzonosito]));
    }

    record Belepes(LocalTime idobelyeg, String azonosito, int helyszinAzonosito, String jegyTipus) {}
}