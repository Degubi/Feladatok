public class Versenyzo {
    
    public final int helyezes;
    public final String nev;
    public final String orszag;
    public final int nyeremeny;
    
    public Versenyzo(String line) {
        var split = line.split(";");
        
        helyezes = Integer.parseInt(split[0]);
        nev = split[1];
        orszag = split[2];
        nyeremeny = Integer.parseInt(split[3]);
    }
}