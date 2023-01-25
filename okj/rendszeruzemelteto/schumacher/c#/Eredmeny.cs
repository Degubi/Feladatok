using System;

public class Eredmeny {

    public readonly DateTime datum;
    public readonly string dijNev;
    public readonly int helyezes;
    public readonly int befejezettKorok;
    public readonly int szerzettPontok;
    public readonly string csapat;
    public readonly string vegeredmenyStatusz;

    public Eredmeny(string line) {
        var split = line.Split(';');

        this.datum = DateTime.Parse(split[0]);
        this.dijNev = split[1];
        this.helyezes = int.Parse(split[2]);
        this.befejezettKorok = int.Parse(split[3]);
        this.szerzettPontok = int.Parse(split[4]);
        this.csapat = split[5];
        this.vegeredmenyStatusz = split[6];
    }
}