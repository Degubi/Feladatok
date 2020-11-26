public class Szavazat {
    public readonly int kerSzam;
    public readonly int szavazatok;
    public readonly string nev;
    public readonly string part;

    public Szavazat(string data) {
        var split = data.Split(' ');

        kerSzam = int.Parse(split[0]);
        szavazatok = int.Parse(split[1]);
        nev = split[2] + ' ' + split[3];
        part = split[4] == "-" ? "független" : split[4];
    }
}