public class Dolgozo {

    public readonly string nev;
    public readonly string nem;
    public readonly string munkaReszleg;
    public readonly int munkabaLepesEv;
    public readonly int munkaBer;

    public Dolgozo(string line) {
        var split = line.Split(';');

        nev = split[0];
        nem = split[1];
        munkaReszleg = split[2];
        munkabaLepesEv = int.Parse(split[3]);
        munkaBer = int.Parse(split[4]);
    }
}