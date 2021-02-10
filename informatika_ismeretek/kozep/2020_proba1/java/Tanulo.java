public class Tanulo {
    
    public final int kezdesEve;
    public final String osztaly;
    public final String nev;
    
    public Tanulo(String line) {
        var split = line.split(";");
        
        kezdesEve = Integer.parseInt(split[0]);
        osztaly = split[1];
        nev = split[2];
    }
}