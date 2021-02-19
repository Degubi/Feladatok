public class Bejegyzes {

    public readonly int ora;
    public readonly int perc;
    public readonly int adasok;
    public readonly string nev;

    public Bejegyzes(string line) {
        var split = line.Split(';');

        ora = int.Parse(split[0]);
        perc = int.Parse(split[1]);
        adasok = int.Parse(split[2]);
        nev = split[3];
    }
}