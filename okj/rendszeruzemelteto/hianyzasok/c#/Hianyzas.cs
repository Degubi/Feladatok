public class Hianyzas {

    public readonly string nev;
    public readonly string osztaly;
    public readonly int elsoNap;
    public readonly int utolsoNap;
    public readonly int mulasztottOrak;

    public Hianyzas(string sor) {
        var split = sor.Split(';');

        this.nev = split[0];
        this.osztaly = split[1];
        this.elsoNap = int.Parse(split[2]);
        this.utolsoNap = int.Parse(split[3]);
        this.mulasztottOrak = int.Parse(split[4]);
    }
}