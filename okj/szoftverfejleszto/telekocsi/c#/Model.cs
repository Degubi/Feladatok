public class Auto {

    public readonly string indulas;
    public readonly string cel;
    public readonly string rendszam;
    public readonly string telefonszam;
    public readonly int ferohely;

    public Auto(string sor) {
        var split = sor.Split(';');

        indulas = split[0];
        cel = split[1];
        rendszam = split[2];
        telefonszam = split[3];
        ferohely = int.Parse(split[4]);
    }
}

public class Igeny {

    public readonly string azonosito;
    public readonly string indulas;
    public readonly string cel;
    public readonly int szemelyek;

    public Igeny(string sor) {
        var split = sor.Split(';');

        azonosito = split[0];
        indulas = split[1];
        cel = split[2];
        szemelyek = int.Parse(split[3]);
    }
}