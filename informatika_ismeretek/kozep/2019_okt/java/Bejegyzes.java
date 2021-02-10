public class Bejegyzes{
    
    public final int ora;
    public final int perc;
    public final int adasok;
    public final String nev;
    
    public Bejegyzes(String line) {
        var split = line.split(";");
        
        ora = Integer.parseInt(split[0]);
        perc = Integer.parseInt(split[1]);
        adasok = Integer.parseInt(split[2]);
        nev = split[3];
    }
}