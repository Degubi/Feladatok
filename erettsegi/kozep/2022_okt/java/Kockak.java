import module java.base;

public class Kockak {

    public static void main(String[] args) {
        var random = new Random();
        var alkalmakSzama = Integer.parseInt(System.console().readLine("Hány alkalommal legyen feldobás? "));
        var korok = new ArrayList<Kor>();

        while(korok.size() != alkalmakSzama) {
            var kor = kortGeneral(random);

            if(!korok.contains(kor)) {
                korok.add(kor);
            }
        }

        var anniNyertKorok = 0;
        var panniNyertKorok = 0;

        for(var kor : korok) {
            var anniNyert = kor.osszeg < 10;

            System.out.println("Dobás: " + kor.elso + " + " + kor.masodik + " + " + kor.harmadik + " = " + kor.osszeg + ", nyert: " + (anniNyert ? "Anni" : "Panni"));

            if(anniNyert) {
                ++anniNyertKorok;
            }else{
                ++panniNyertKorok;
            }
        }

        System.out.println("A játékok során " + anniNyertKorok + " alkalommal nyert Anni, " + panniNyertKorok + " alkalommal pedig Panni");
    }

    static Kor kortGeneral(Random random) {
        var elso = dobastGeneral(random);
        var masodik = dobastGeneral(random);
        var harmadik = dobastGeneral(random);

        return new Kor(elso, masodik, harmadik, elso + masodik + harmadik);
    }

    static int dobastGeneral(Random random) {
        return 1 + random.nextInt(6);
    }

    record Kor(int elso, int masodik, int harmadik, int osszeg) {}
}
