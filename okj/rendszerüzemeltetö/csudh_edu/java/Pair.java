public class Pair{
    
    public final String domain;
    public final String ip;
    
    public Pair(String line) {
        var split = line.split(";");
        
        domain = split[0];
        ip = split[1];
    }
}