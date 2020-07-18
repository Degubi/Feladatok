public class Eredmeny {
    
    public readonly string csapat;
    public readonly int helyezes;
    public readonly int valtozas;
    public readonly int pontszam;
    
    public Eredmeny(string line) {
        var split = line.Split(';');
        
        csapat = split[0];
        helyezes = int.Parse(split[1]);
        valtozas = int.Parse(split[2]);
        pontszam = int.Parse(split[3]);
    }
}