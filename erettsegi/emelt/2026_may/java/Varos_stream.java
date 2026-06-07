import module java.base;

public class Varos_stream {

    public static void main(String[] args) throws Exception {
        var autoAzonosito = System.console().readLine("1. Feladat: Kérem adja meg az autó azonosítóját! ");
        var sebessegValtozasok = Stream.concat(
            Stream.of(new SebessegValtozas(0, 0, 0)),
            Files.lines(Path.of(autoAzonosito + ".txt"))
                 .map(k -> k.split("\t"))
                 .map(k -> new SebessegValtozas(Integer.parseInt(k[0]), Integer.parseInt(k[1]), Integer.parseInt(k[2])))
        ).toArray(SebessegValtozas[]::new);

        System.out.println("2. Feladat: A " + sebessegValtozasok[1].kezdetiIdo + ". mp-ben indult el, a megfigyelés végén " + sebessegValtozasok[sebessegValtozasok.length - 1].sebessegAVegen + " m/s sebességgel haladt");

        var atlepteE = Arrays.stream(sebessegValtozasok)
                             .anyMatch(k -> k.sebessegAVegen > 14);

        System.out.println("3. Feladat: Az autó " + (atlepteE ? "átlépte" : "nem lépte át") + " a sebességhatárt");

        var leghosszabbAlltValtozasParos = Arrays.stream(sebessegValtozasok)
                                                 .gather(Gatherers.windowSliding(2))
                                                 .filter(k -> k.getFirst().sebessegAVegen == 0)
                                                 .max((k1, k2) -> Integer.compare(k1.getLast().kezdetiIdo - k1.getFirst().zaroIdo, k2.getLast().kezdetiIdo - k2.getFirst().zaroIdo))
                                                 .orElseThrow();

        System.out.println("4. Feladat: Leghosszabbatt állt idő " + leghosszabbAlltValtozasParos.getFirst().zaroIdo + " és " + leghosszabbAlltValtozasParos.getLast().kezdetiIdo + " mp között volt");

        var vizsgalandoIdopont = Integer.parseInt(System.console().readLine("5. Feladat: Mikor vizsgáljuk a sebességet? "));
        var vizsgalandoI = IntStream.range(0, sebessegValtozasok.length - 1)
                                    .filter(i -> egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok[i]) ||
                                                 ketValtozasKozottiIdoE(vizsgalandoIdopont, sebessegValtozasok[i], sebessegValtozasok[i + 1]) ||
                                                 egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok[i + 1])
                                    ).findFirst()
                                    .orElseThrow();

        var sebessegAVizsgaltIdopontban = egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok[vizsgalandoI]) ? sebessegAdottIdopontban(vizsgalandoIdopont, vizsgalandoI, sebessegValtozasok) :
                                          egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok[vizsgalandoI + 1]) ? sebessegAdottIdopontban(vizsgalandoIdopont, vizsgalandoI + 1, sebessegValtozasok)
                                                                                                                                 : sebessegValtozasok[vizsgalandoI].sebessegAVegen;
        System.out.println("Az autó sebessége ekkor " + sebessegAVizsgaltIdopontban + "m/s volt");

        var osszesMegtettUt = Arrays.stream(sebessegValtozasok)
                                    .gather(Gatherers.windowSliding(2))
                                    .mapToDouble(k -> valtozasonBelulMegtettUt(k.getFirst(), k.getLast()) + valtozasKozottMegtettUt(k.getFirst(), k.getLast()))
                                    .sum();

        System.out.println("6. Feladat: A megtett út: " + osszesMegtettUt + "m");

        var fileba = Arrays.stream(sebessegValtozasok)
                           .gather(Gatherers.windowSliding(2))
                           .flatMap(k -> Stream.of(k.getLast().kezdetiIdo + "\t" + k.getFirst().sebessegAVegen, k.getLast().zaroIdo + "\t" + k.getLast().sebessegAVegen))
                           .collect(Collectors.toList());

        Files.write(Path.of("v" + autoAzonosito + ".txt"), fileba);

        // 8. Feladathoz nem vagyok hajlandó excelezni, sorry.
    }

    static boolean egyAdottValtozasonBeluliIdoE(int idopont, SebessegValtozas valtozas) {
        return idopont >= valtozas.kezdetiIdo && idopont <= valtozas.zaroIdo;
    }

    static boolean ketValtozasKozottiIdoE(int idopont, SebessegValtozas korabbiValtozas, SebessegValtozas kesobbiValtozas) {
        return idopont >= korabbiValtozas.zaroIdo && idopont <= kesobbiValtozas.kezdetiIdo;
    }

    static float sebessegAdottIdopontban(int t, int vizsgalandoI, SebessegValtozas[] sebessegValtozasok) {
        var valtozas1 = sebessegValtozasok[vizsgalandoI - 1];
        var valtozas2 = sebessegValtozasok[vizsgalandoI];
        var v1 = (float) valtozas1.sebessegAVegen;
        var v2 = (float) valtozas2.sebessegAVegen;
        var t1 = (float) valtozas2.kezdetiIdo;
        var t2 = (float) valtozas2.zaroIdo;

        return v1 + ((v2 - v1) / (t2 - t1)) * (t - t1);
    }

    static float valtozasonBelulMegtettUt(SebessegValtozas valtozas1, SebessegValtozas valtozas2) {
        var v1 = (float) valtozas1.sebessegAVegen;
        var v2 = (float) valtozas2.sebessegAVegen;
        var t1 = (float) valtozas2.kezdetiIdo;
        var t2 = (float) valtozas2.zaroIdo;

        return (v1 + v2) / 2 * (t2 - t1);
    }

    static float valtozasKozottMegtettUt(SebessegValtozas valtozas1, SebessegValtozas valtozas2) {
        return valtozas1.sebessegAVegen * (valtozas2.kezdetiIdo - valtozas1.zaroIdo);
    }

    record SebessegValtozas(int kezdetiIdo, int zaroIdo, int sebessegAVegen) {}
}