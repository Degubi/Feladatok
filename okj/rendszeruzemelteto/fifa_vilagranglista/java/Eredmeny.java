public class Eredmeny {
    
    public final String csapat;
    public final int helyezes;
    public final int valtozas;
    public final int pontszam;
    
    public Eredmeny(String line) {
        var split = line.split(";");
        
        csapat = split[0];
        helyezes = Integer.parseInt(split[1]);
        valtozas = Integer.parseInt(split[2]);
        pontszam = Integer.parseInt(split[3]);
    }
}