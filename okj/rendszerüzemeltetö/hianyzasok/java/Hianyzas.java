public class Hianyzas{
    
    public final String nev;
    public final String osztaly;
    public final int elsoNap;
    public final int utolsoNap;
    public final int mulasztottOrak;
    
    public Hianyzas(String sor) {
        var split = sor.split(";");
        
        this.nev = split[0];
        this.osztaly = split[1];
        this.elsoNap = Integer.parseInt(split[2]);
        this.utolsoNap = Integer.parseInt(split[3]);
        this.mulasztottOrak = Integer.parseInt(split[4]);
    }
}