public class Verseny {

    public final int ev;
    public final int indulasokSzama;
    public final int nyeresekSzama;
    public final int dobogosokSzama;
    public final int elsoHelyrolIndulasokSzama;
    public final int leggyorsabbKorokSzama;

    public Verseny(String line) {
        var split = line.split("\t");

        ev = Integer.parseInt(split[0]);
        indulasokSzama = Integer.parseInt(split[1]);
        nyeresekSzama = Integer.parseInt(split[2]);
        dobogosokSzama = Integer.parseInt(split[3]);
        elsoHelyrolIndulasokSzama = Integer.parseInt(split[4]);
        leggyorsabbKorokSzama = Integer.parseInt(split[5]);
    }
}