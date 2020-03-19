public class Fuvar{
    
    public final int nap;
    public final int sorszam;
    public final int tavolsag;
    
    public Fuvar(String line) {
        var split = line.split(" ");
        
        nap = Integer.parseInt(split[0]);
        sorszam = Integer.parseInt(split[1]);
        tavolsag = Integer.parseInt(split[2]);
    }
}