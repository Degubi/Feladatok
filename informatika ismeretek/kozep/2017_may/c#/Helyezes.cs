public struct Helyezes {
    public int helyezes, sportolokSzama;
    public string sportag, versenyszam;

    public Helyezes(string line) {
        var split = line.Split(' ');

        helyezes = int.Parse(split[0]);
        sportolokSzama = int.Parse(split[1]);
        sportag = split[2];
        versenyszam = split[3];
    }

    public int PontCalc() => helyezes == 1 ? 7 : 7 - helyezes;
}