public class Versenyzo {

    public readonly string nev;
    public readonly int rajtszam;
    public readonly string kategoria;
    public readonly string ido;
    public readonly int befejezesSzazalek;
    
    public Versenyzo(string line) {
        var split = line.Split(';');
        
        nev = split[0];
        rajtszam = int.Parse(split[1]);
        kategoria = split[2];
        ido = split[3];
        befejezesSzazalek = int.Parse(split[4]);
    }
    
    public double idoOraban() {
        var daraboltIdo = ido.Split(':');
        var ora = double.Parse(daraboltIdo[0]);
        var perc = double.Parse(daraboltIdo[1]);
        var mp = double.Parse(daraboltIdo[2]);
        
        return ora + (perc / 60D) + (mp / 3600D);
    }
}