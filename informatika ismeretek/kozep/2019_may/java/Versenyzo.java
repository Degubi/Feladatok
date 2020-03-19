public class Versenyzo {
    
    public final String nev;
    public final int rajtszam;
    public final String kategoria;
    public final String ido;
    public final int befejezesSzazalek;
    
    public Versenyzo(String line) {
        var split = line.split(";");
        
        nev = split[0];
        rajtszam = Integer.parseInt(split[1]);
        kategoria = split[2];
        ido = split[3];
        befejezesSzazalek = Integer.parseInt(split[4]);
    }
    
    public double idoOraban() {
        var daraboltIdo = ido.split(":");
        var ora = Double.parseDouble(daraboltIdo[0]);
        var perc = Double.parseDouble(daraboltIdo[1]);
        var mp = Double.parseDouble(daraboltIdo[2]);
        
        return ora + (perc / 60D) + (mp / 3600D);
    }
}