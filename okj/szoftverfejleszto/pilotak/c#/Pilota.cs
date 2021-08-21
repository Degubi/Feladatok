using System;

public class Pilota {
    public const int URES_RAJTSZAM = -1;

    public readonly string nev;
    public readonly DateTime szuletesiDatum;
    public readonly string nemzetiseg;
    public readonly int rajtszam;

    public Pilota(string line) {
        var split = line.Split(';');

        nev = split[0];
        szuletesiDatum = DateTime.Parse(split[1]);
        nemzetiseg = split[2];
        rajtszam = line[line.Length - 1] == ';' ? URES_RAJTSZAM : int.Parse(split[3]);
    }
}