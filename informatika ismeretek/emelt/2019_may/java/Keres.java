public class Keres {
    
    public final String cim;
    public final String datumIdo;
    public final String keres;
    public final String httpKod;
    public final String meret;
    
    public Keres(String line) {
        var split = line.split("\\*");
        var utolsoSzokozIndex = split[3].indexOf(' ');
        
        cim = split[0];
        datumIdo = split[1];
        keres = split[2];
        httpKod = split[3].substring(0, utolsoSzokozIndex);
        meret = split[3].substring(utolsoSzokozIndex + 1);
    }
    
    public int byteMeret() {
        return meret.equals("-") ? 0 : Integer.parseInt(meret);
    }
    
    public boolean domain() {
        return Character.isLetter(cim.charAt(cim.length() - 1));
    }
}