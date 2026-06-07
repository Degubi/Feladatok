import module java.base;

public class Varos {

    public static void main(String[] args) throws Exception {
        var autoAzonosito = System.console().readLine("1. Feladat: Kérem adja meg az autó azonosítóját! ");
        var autoValtozasSorok = Files.readAllLines(Path.of(autoAzonosito + ".txt"));

        var sebessegValtozasok = new ArrayList<SebessegValtozas>();
        sebessegValtozasok.add(new SebessegValtozas(0, 0, 0));

        for(var sor : autoValtozasSorok) {
            var darabolt = sor.split("\t");

            sebessegValtozasok.add(new SebessegValtozas(Integer.parseInt(darabolt[0]), Integer.parseInt(darabolt[1]), Integer.parseInt(darabolt[2])));
        }

        System.out.println("2. Feladat: A " + sebessegValtozasok.get(1).kezdetiIdo + ". mp-ben indult el, a megfigyelés végén " + sebessegValtozasok.getLast().sebessegAVegen + " m/s sebességgel haladt");

        var atlepteE = false;
        for(var valtozas : sebessegValtozasok) {
            if(valtozas.sebessegAVegen > 14) {
                atlepteE = true;
                break;
            }
        }

        System.out.println("3. Feladat: Az autó " + (atlepteE ? "átlépte" : "nem lépte át") + " a sebességhatárt");

        var leghosszabbAlltValtozas1 = (SebessegValtozas) null;
        var leghosszabbAlltValtozas2 = (SebessegValtozas) null;

        for(var i = 0; i < sebessegValtozasok.size() - 1; ++i) {
            var valtozas1 = sebessegValtozasok.get(i);
            var valtozas2 = sebessegValtozasok.get(i + 1);

            if(valtozas1.sebessegAVegen == 0) {
                var elteltIdo = valtozas2.kezdetiIdo - valtozas1.zaroIdo;
                var leghosszabbElteltIdo = leghosszabbAlltValtozas1 == null ? -1 : leghosszabbAlltValtozas2.kezdetiIdo - leghosszabbAlltValtozas1.zaroIdo;

                if(elteltIdo > leghosszabbElteltIdo) {
                    leghosszabbAlltValtozas1 = valtozas1;
                    leghosszabbAlltValtozas2 = valtozas2;
                 }
            }
        }

        System.out.println("4. Feladat: Leghosszabbatt állt idő " + leghosszabbAlltValtozas1.zaroIdo + " és " + leghosszabbAlltValtozas2.kezdetiIdo + " mp között volt");

        var vizsgalandoIdopont = Integer.parseInt(System.console().readLine("5. Feladat: Mikor vizsgáljuk a sebességet? "));
        var vizsgalandoI = -1;

        for(var i = 0; i < sebessegValtozasok.size() - 1; ++i) {
            if(egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok.get(i)) ||
               ketValtozasKozottiIdoE(vizsgalandoIdopont, sebessegValtozasok.get(i), sebessegValtozasok.get(i + 1)) ||
               egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok.get(i + 1))) {

                vizsgalandoI = i;
                break;
             }
        }

        var sebessegAVizsgaltIdopontban = egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok.get(vizsgalandoI)) ? sebessegAdottIdopontban(vizsgalandoIdopont, vizsgalandoI, sebessegValtozasok) :
                                          egyAdottValtozasonBeluliIdoE(vizsgalandoIdopont, sebessegValtozasok.get(vizsgalandoI + 1)) ? sebessegAdottIdopontban(vizsgalandoIdopont, vizsgalandoI + 1, sebessegValtozasok)
                                                                                                                                 : sebessegValtozasok.get(vizsgalandoI).sebessegAVegen;
        System.out.println("Az autó sebessége ekkor " + sebessegAVizsgaltIdopontban + "m/s volt");

        var osszesMegtettUt = 0.0F;
        for(var i = 0; i < sebessegValtozasok.size() - 1; ++i) {
            var valtozas1 = sebessegValtozasok.get(i);
            var valtozas2 = sebessegValtozasok.get(i + 1);

            osszesMegtettUt += valtozasonBelulMegtettUt(valtozas1, valtozas2) + valtozasKozottMegtettUt(valtozas1, valtozas2);
        }

        System.out.println("6. Feladat: A megtett út: " + osszesMegtettUt + "m");

        var fileba = new ArrayList<String>();
        for(var i = 0; i < sebessegValtozasok.size() - 1; ++i) {
            var valtozas1 = sebessegValtozasok.get(i);
            var valtozas2 = sebessegValtozasok.get(i + 1);

            fileba.add(valtozas2.kezdetiIdo + "\t" + valtozas1.sebessegAVegen);
            fileba.add(valtozas2.zaroIdo + "\t" + valtozas2.sebessegAVegen);
        }

        Files.write(Path.of("v" + autoAzonosito + ".txt"), fileba);

        // 8. Feladathoz nem vagyok hajlandó excelezni, sorry.
    }

    static boolean egyAdottValtozasonBeluliIdoE(int idopont, SebessegValtozas valtozas) {
        return idopont >= valtozas.kezdetiIdo && idopont <= valtozas.zaroIdo;
    }

    static boolean ketValtozasKozottiIdoE(int idopont, SebessegValtozas korabbiValtozas, SebessegValtozas kesobbiValtozas) {
        return idopont >= korabbiValtozas.zaroIdo && idopont <= kesobbiValtozas.kezdetiIdo;
    }

    static float sebessegAdottIdopontban(int t, int vizsgalandoI, ArrayList<SebessegValtozas> sebessegValtozasok) {
        var valtozas1 = sebessegValtozasok.get(vizsgalandoI - 1);
        var valtozas2 = sebessegValtozasok.get(vizsgalandoI);
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
