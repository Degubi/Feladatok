using System;

public class Kolcsonzes {

    public readonly string nev;
    public readonly string jarmuAzonosito;
    public readonly TimeSpan elvitelIdopont;
    public readonly TimeSpan visszahozatalIdopont;

    public Kolcsonzes(string line) {
        var split = line.Split(';');

        nev = split[0];
        jarmuAzonosito = split[1];
        elvitelIdopont = new TimeSpan(int.Parse(split[2]), int.Parse(split[3]), 0);
        visszahozatalIdopont = new TimeSpan(int.Parse(split[4]), int.Parse(split[5]), 0);
    }
}