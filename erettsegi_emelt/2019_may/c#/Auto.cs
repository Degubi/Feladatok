public struct Auto {
    public readonly int nap;
    public readonly string idopont;
    public readonly string rendszam;
    public readonly int szemelyAzonosito;
    public readonly int km;
    public readonly bool elvitel;

    public Auto(string line) {
        var split = line.Split(' ');
    
        nap = int.Parse(split[0]);
        idopont = split[1];
        rendszam = split[2];
        szemelyAzonosito = int.Parse(split[3]);
        km = int.Parse(split[4]);
        elvitel = split[5][0] == '0';
    }
}