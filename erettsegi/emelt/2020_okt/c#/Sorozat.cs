using System;
using System.Globalization;

public class Sorozat {
    public static readonly DateTime HIANYZO_DATUM = DateTime.MinValue;

    public readonly DateTime adasbaKerulesiDatum;
    public readonly string cim;
    public readonly int evadokSzama;
    public readonly int epizodokSzama;
    public readonly int epizodonkentiHossz;
    public readonly bool lattaEMarAKeszito;

    public Sorozat(string[] lines, int index) {
        var datumStr = lines[index];
        var epizodInfoSplit = lines[index + 2].Split('x');

        adasbaKerulesiDatum = datumStr == "NI" ? HIANYZO_DATUM : DateTime.ParseExact(datumStr, "yyyy.MM.dd", CultureInfo.InvariantCulture);
        cim = lines[index + 1];
        evadokSzama = int.Parse(epizodInfoSplit[0]);
        epizodokSzama = int.Parse(epizodInfoSplit[1]);
        epizodonkentiHossz = int.Parse(lines[index + 3]);
        lattaEMarAKeszito = int.Parse(lines[index + 4]) == 1;
    }
}