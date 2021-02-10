public class Szakasz{
    
    public final String induloPont, vegPont;
    public final int emelkedesek, lejtesek;
    public final double hossz;
    public final boolean pecsetelo;
    
    public Szakasz(String data) {
        var split = data.split(";");
        
        induloPont = split[0];
        vegPont = split[1];
        hossz = Double.parseDouble(split[2].replace(',', '.'));
        emelkedesek = Integer.parseInt(split[3]);
        lejtesek = Integer.parseInt(split[4]);
        pecsetelo = split[5].charAt(0) == 'i';
    }
    
    public boolean hianyosNev() {
        return pecsetelo && !vegPont.contains("pecsetelohely");
    }
    
    public int magasraa(int alap) {
        return alap + (emelkedesek - lejtesek);
    }
    
    @Override
    public String toString() {
        return "Kezdet: " + induloPont + ", vég: " + vegPont + ", távolság: " + hossz + " km";
    }
}