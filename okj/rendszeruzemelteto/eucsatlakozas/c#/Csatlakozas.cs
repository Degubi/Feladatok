using System;

public class Csatlakozas {

    public readonly string orszag;
    public readonly DateTime datum;

    public Csatlakozas(string line) {
        var split = line.Split(';');

        orszag = split[0];
        datum = DateTime.Parse(split[1]);
    }
}