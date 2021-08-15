public class Dij {

    public readonly int ev;
    public readonly string tipus;
    public readonly string keresztNev;
    public readonly string vezetekNev;
    
    public Dij(string sor) {
        var split = sor.Split(';');
        
        ev = int.Parse(split[0]);
        tipus = split[1];
        keresztNev = split[2];
        vezetekNev = split.Length == 4 ? split[3] : "";
    }
}