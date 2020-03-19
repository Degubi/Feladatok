public class Hegy{
    
    public final String nev;
    public final String hegyseg;
    public final int magassag;
    
    public Hegy(String line) {
        var split = line.split(";");
        
        nev = split[0];
        hegyseg = split[1];
        magassag = Integer.parseInt(split[2]);
    }
}