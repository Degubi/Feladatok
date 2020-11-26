public class Fuvar {
    public readonly int nap;
    public readonly int sorszam;
    public readonly int tavolsag;

    public Fuvar(string data) {
        var split = data.Split(' ');

        nap = int.Parse(split[0]);
        sorszam = int.Parse(split[1]);
        tavolsag = int.Parse(split[2]);
    }
}