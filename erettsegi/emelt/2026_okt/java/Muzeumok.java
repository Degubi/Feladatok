import module java.base;

public class Muzeumok {

    public static void main(String[] args) throws Exception {
        var muzdatLines = Files.readAllLines(Path.of("muzdat.txt"));
        var arak = muzdatLines.getFirst().split(" ");

        var teljesKastelyjegyAr = Integer.parseInt(arak[0]);
        var teljesKiallitohelyAr = Integer.parseInt(arak[1]);
        var teljesNapijegyAr = Integer.parseInt(arak[2]);
        var muzeumokNevei = Files.readAllLines(Path.of("muznev.txt")).getFirst().replace("\"", "").split(", ");
        var kastelyNyitas = LocalTime.of(10, 0);
        var kastelyZaras = LocalTime.of(18, 0);
        var belepesek = new ArrayList<Belepes>();

        for(var line : muzdatLines.subList(1, muzdatLines.size())) {
            var split = line.split(" ");

            belepesek.add(new Belepes(LocalTime.parse(split[0]), split[1], Integer.parseInt(split[2]), split[3]));
        }

        System.out.println("2. Feladat: Az adott napon " + belepesek.size() + " látogató volt");
        System.out.println("3. Feladat:");
        System.out.println("Az első látogató " + belepesek.getFirst().idobelyeg + "-kor lépett a(z) " + muzeumokNevei[belepesek.getFirst().helyszinAzonosito] + " helyszínre");
        System.out.println("Az utolsó látogató " + belepesek.getLast().idobelyeg + "-kor lépett a(z) " + muzeumokNevei[belepesek.getLast().helyszinAzonosito] + " helyszínre");

        var bekertJegySorszam = System.console().readLine("4. Feladat: Kérem a jegy sorszámát! ");

        for(var belepes : belepesek) {
            if(belepes.azonosito.equals(bekertJegySorszam)) {
                System.out.println(belepes.idobelyeg + " " + muzeumokNevei[belepes.helyszinAzonosito]);
            }
        }

        var utolsoJegyvasarlas = (Belepes) null;
        for(var belepes : belepesek.reversed()) {
            if(!belepes.jegyTipus.equals("-")) {
                utolsoJegyvasarlas = belepes;
                break;
            }
         }

        var idoZarasig = Duration.between(utolsoJegyvasarlas.idobelyeg, kastelyZaras);

        System.out.println("Az utolsó vásárló " + idoZarasig.toHoursPart() + ":" + idoZarasig.toMinutesPart() + " perccel a zárás előtt vett jegyet");

        var teljesBevetel = 0;
        for(var belepes : belepesek) {
            teljesBevetel += (belepes.jegyTipus.equals("-") ? 0 :
                              belepes.jegyTipus.endsWith("N") ? teljesNapijegyAr :
                              belepes.helyszinAzonosito == 0 ? teljesKastelyjegyAr : teljesKiallitohelyAr
                             ) / (belepes.jegyTipus.startsWith("K") ? 2 : 1);
        }

        System.out.println("6. Feladat: A teljes bevétel " + teljesBevetel + " Ft volt");

        var napijegyek = 0;
        var nemNapijegyek = 0;
        for(var belepes : belepesek) {
            if(belepes.jegyTipus.endsWith("N")) {
                if(belepes.helyszinAzonosito == 0) {
                    ++napijegyek;
                }else{
                    ++nemNapijegyek;
                }
            }
        }

        System.out.println("7. Feladat: A napijegyet váltók általában" + (napijegyek > nemNapijegyek ? "" : " nem") + " a Kastélyban kezdtek");

        var orankentiStat = new HashMap<Integer, Integer>();
        for(var belepes : belepesek) {
            if(belepes.helyszinAzonosito == 0) {
                var ora = (int) Duration.between(kastelyNyitas, belepes.idobelyeg).toHours() + kastelyNyitas.getHour();

                orankentiStat.put(ora, orankentiStat.getOrDefault(ora, 0) + 1);
            }
        }

        try(var output = Files.newBufferedWriter(Path.of("stat.txt"))) {
            output.write("óra\tfő\n");

            for(var k = 10; k < 18; ++k) {
                output.write(k + "\t" + orankentiStat.getOrDefault(k, 0) + "\n");
            }
        }

        System.out.println("9. Feladat: Jutalmat kapnak\nidőpont\tjegy\tkiállítás");

        var azonositonkentiCsoportok = new HashMap<String, ArrayList<Belepes>>();
        for(var belepes : belepesek) {
            var csoport = azonositonkentiCsoportok.getOrDefault(belepes.azonosito, new ArrayList<>());
            csoport.add(belepes);

            azonositonkentiCsoportok.put(belepes.azonosito, csoport);
        }

        var napijegyCsoportokUtolsoi = new ArrayList<Belepes>();

        for(var csoport : azonositonkentiCsoportok.values()) {
            if(csoport.size() == muzeumokNevei.length) {
                for(var csoportJegyek : csoport) {
                    if(csoportJegyek.jegyTipus.endsWith("N")) {
                        napijegyCsoportokUtolsoi.add(csoport.getLast());
                        break;
                    }
                }
            }
        }

        napijegyCsoportokUtolsoi.sort(Comparator.comparing(Belepes::idobelyeg));

        for(var utolsoNapijegyek : napijegyCsoportokUtolsoi) {
            System.out.println(utolsoNapijegyek.idobelyeg + "\t" + utolsoNapijegyek.azonosito + "\t" + muzeumokNevei[utolsoNapijegyek.helyszinAzonosito]);
        }
    }

    record Belepes(LocalTime idobelyeg, String azonosito, int helyszinAzonosito, String jegyTipus) {}
}
