public class Verseny {

    public readonly int ev;
    public readonly int indulasokSzama;
    public readonly int nyeresekSzama;
    public readonly int dobogosokSzama;
    public readonly int elsoHelyrolIndulasokSzama;
    public readonly int leggyorsabbKorokSzama;

    public Verseny(string line) {
        var split = line.Split('\t');

        ev = int.Parse(split[0]);
        indulasokSzama = int.Parse(split[1]);
        nyeresekSzama = int.Parse(split[2]);
        dobogosokSzama = int.Parse(split[3]);
        elsoHelyrolIndulasokSzama = int.Parse(split[4]);
        leggyorsabbKorokSzama = int.Parse(split[5]);
    }
}