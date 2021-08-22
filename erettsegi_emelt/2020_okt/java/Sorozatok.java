import java.io.*;
import java.nio.file.*;
import java.time.*;
import java.util.*;

public class Sorozatok {

    public static void main(String[] args) throws IOException {
        var lines = Files.readAllLines(Path.of("lista.txt"));
        var sorozatok = new ArrayList<Sorozat>();

        for(var i = 0; i < lines.size(); i += 5) {
            sorozatok.add(new Sorozat(lines, i));
        }

        var ismertDatumuakSzama = 0;
        var keszitoAltalLatottakSzama = 0;
        var osszesElpazaroltPerc = 0;

        for(var sorozat : sorozatok) {
            if(sorozat.adasbaKerulesiDatum != Sorozat.HIANYZO_DATUM) {
                ++ismertDatumuakSzama;
            }

            if(sorozat.lattaEMarAKeszito) {
                ++keszitoAltalLatottakSzama;
                osszesElpazaroltPerc += sorozat.epizodonkentiHossz;
            }
        }

        var elpazaroltIdoStat = Duration.ofMinutes(osszesElpazaroltPerc);

        System.out.println("2. Feladat: " + ismertDatumuakSzama + " db ismert dátumú epizód van");
        System.out.printf("3. Feladat: Látottak százaléka: %.2f%%\n", ((float) keszitoAltalLatottakSzama / sorozatok.size() * 100));
        System.out.printf("4. Feladat: Eltöltött idő: %d nap, %d óra és %d perc\n", elpazaroltIdoStat.toDaysPart(), elpazaroltIdoStat.toHoursPart(), elpazaroltIdoStat.toMinutesPart());
        System.out.println("5. Feladat: Írj be 1 dátumot! (éééé.hh.nn)");

        try(var input = new Scanner(System.in)) {
            var bekertDatum = LocalDate.parse(input.nextLine().replace('.', '-'));

            for(var sorozat : sorozatok) {
                if(sorozat.adasbaKerulesiDatum != Sorozat.HIANYZO_DATUM && !sorozat.lattaEMarAKeszito) {
                   if(sorozat.adasbaKerulesiDatum.isBefore(bekertDatum) || sorozat.adasbaKerulesiDatum.isEqual(bekertDatum)) {
                        System.out.println(sorozat.evadokSzama + "x" + sorozat.epizodokSzama + "\t" + sorozat.cim);
                    }
                }
            }

            System.out.println("7. Feladat: Add meg 1 hét napját! (h, k, sze, cs, p, szo, v)");

            var bekertNap = input.nextLine();
            var bekertNapraEsok = new ArrayList<String>();

            for(var sorozat : sorozatok) {
                if(sorozat.adasbaKerulesiDatum != Sorozat.HIANYZO_DATUM) {
                    var adasbaKerulesNapja = hetnapja(sorozat.adasbaKerulesiDatum.getYear(), sorozat.adasbaKerulesiDatum.getMonthValue(), sorozat.adasbaKerulesiDatum.getDayOfMonth());

                    if(bekertNap.equals(adasbaKerulesNapja) && !bekertNapraEsok.contains(sorozat.cim)) {
                        bekertNapraEsok.add(sorozat.cim);
                    }
                }
            }

            var kiirando = bekertNapraEsok.size() == 0 ? "Az adott napon nem kerül adásba sorozat."
                                                       : String.join("\n", bekertNapraEsok);
            System.out.println(kiirando);
        }

        var vetitesiIdoSorozatonkent = new HashMap<String, Integer>();
        var epizodokSzamaSorozatonkent = new HashMap<String, Integer>();
        for(var sorozat : sorozatok) {
            var cim = sorozat.cim;

            vetitesiIdoSorozatonkent.put(cim, vetitesiIdoSorozatonkent.getOrDefault(cim, 0) + sorozat.epizodonkentiHossz);
            epizodokSzamaSorozatonkent.put(cim, epizodokSzamaSorozatonkent.getOrDefault(cim, 0) + 1);
        }

        try(var output = new PrintWriter("summa.txt")) {
            for(var sorozatCimek : vetitesiIdoSorozatonkent.keySet()) {
                output.println(sorozatCimek + " " + vetitesiIdoSorozatonkent.get(sorozatCimek) + " " + epizodokSzamaSorozatonkent.get(sorozatCimek));
            }
        }
    }


    public static final String[] napok = { "v", "h", "k", "sz", "cs", "p", "szo" };
    public static final int[] honapok = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };

    public static String hetnapja(int ev, int ho, int nap) {
        ev = ho < 3 ? ev - 1 : ev;

        return napok[(ev + ev / 4 - ev / 100 + ev / 400 + honapok[ho - 1] + nap) % 7];
    }
}