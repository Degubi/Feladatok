public class Keres {
    
    public final String cim;
    public final String datumIdo;
    public final String keres;
    public final String httpKod;
    public final String meret;
    
    public Keres(String line) {
        var firstSplit = line.split("\\*");
        var utolsoSzokozIndex = firstSplit[3].indexOf(' ');
        
        cim = firstSplit[0];
        datumIdo = firstSplit[1];
        keres = firstSplit[2];
        httpKod = firstSplit[3].substring(0, utolsoSzokozIndex);
        meret = firstSplit[3].substring(utolsoSzokozIndex + 1);
    }
    
    public int byteMeret() {
        return meret.equals("-") ? 0 : Integer.parseInt(meret);
    }
    
    public boolean domain() {
        return Character.isLetter(cim.charAt(cim.length() - 1));
    }
}