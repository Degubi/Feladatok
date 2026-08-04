import module java.base;

public class Szamlalas {

    public static void main(String[] args) throws Exception {
        var meresekSzovegek = Files.readString(Path.of("meres.txt")).trim().split(", ");
        var meresek = new int[meresekSzovegek.length];

        for(var i = 0; i < meresek.length; ++i) {
            meresek[i] = Integer.parseInt(meresekSzovegek[i]);
        }

        var osszesKerekparos = 0;
        for(var meres : meresek) {
            osszesKerekparos += Math.max(0, meres);
        }

        System.out.println("2. Feladat: Összesen " + osszesKerekparos + " kerékpárost számoltak");
        System.out.println("3. Feladat: Óránkénti mérések:");

        for(int i = 0, ora = 6; i < meresek.length; i += 4, ++ora) {
            var osszSzam = Math.max(0, meresek[i]) + Math.max(0, meresek[i + 1]) + Math.max(0, meresek[i + 2]) + Math.max(0, meresek[i + 3]);

            System.out.println(ora + " órától " + osszSzam + " kerékpáros");
        }

        var maxI = 0;
        for(var i = 0; i < meresek.length; ++i) {
            if(meresek[i] > meresek[maxI]) {
                maxI = i;
            }
        }

        System.out.println("Az áthaladók maximális száma: " + meresek[maxI] + ", időpontja: " + idotFormaz(maxI));
    }

    static String idotFormaz(int meresI) {
        var percek = 6 * 60 + 15 + (meresI * 15);

        return (percek / 60) + ":" + (percek % 60);
    }
}
