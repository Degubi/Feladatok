public class Dij{
    
    public final int evszam;
    public final String tipus;
    public final String keresztNev;
    public final String vezetekNev;
    
    public Dij(String sor) {
        var split = sor.split(";");
        
        evszam = Integer.parseInt(split[0]);
        tipus = split[1];
        keresztNev = split[2];
        vezetekNev = split.length == 4 ? split[3] : "";
    }
}