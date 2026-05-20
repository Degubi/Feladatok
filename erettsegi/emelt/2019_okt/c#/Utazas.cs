using System;
using System.Globalization;

public class Utazas {

    public readonly int megalloSorszama;
    public readonly DateTime felszallas;
    public readonly string kartyaAzonosito;
    public readonly string tipus;
    public readonly int jegyszam;
    public readonly DateTime ervenyesseg;

    public Utazas(string line) {
        var split = line.Split(' ');

        megalloSorszama = int.Parse(split[0]);
        felszallas = DateTime.ParseExact(split[1], "yyyyMMdd-HHmm", CultureInfo.InvariantCulture);
        kartyaAzonosito = split[2];
        tipus = split[3];

        if(split[4].Length <= 2) {
            jegyszam = int.Parse(split[4]);
            ervenyesseg = DateTime.MinValue;
        }else{
            jegyszam = -1;
            ervenyesseg = DateTime.ParseExact(split[4], "yyyyMMdd", CultureInfo.InvariantCulture);
        }
    }

    public bool ErvenytelenE() => jegyszam == 0 || (ervenyesseg != DateTime.MinValue && felszallas > ervenyesseg);
    public bool ErvenyesE() => !ErvenytelenE();
}