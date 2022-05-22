public class Telek {

    public readonly int adoszam;
    public readonly string utcaNev;
    public readonly string hazSzam;
    public readonly string adosav;
    public readonly int terulet;

    public Telek(string[] data) {
        adoszam = int.Parse(data[0]);
        utcaNev = data[1];
        hazSzam = data[2];
        adosav = data[3];
        terulet = int.Parse(data[4]);
    }
}