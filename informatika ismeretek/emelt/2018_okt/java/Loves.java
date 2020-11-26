public class Loves {
    public final String jatekosNeve;
    public final float lovesX, lovesY;
    public final int lovesSorszam;

    public Loves(String sor, int sorszam) {
        var split = sor.split(";");

        jatekosNeve = split[0];
        lovesX = Float.parseFloat(split[1].replace(',', '.'));
        lovesY = Float.parseFloat(split[2].replace(',', '.'));
        lovesSorszam = sorszam;
    }

    public double tavolsag(float kozeppontX, float kozeppontY) {
        var dx = kozeppontX - lovesX;
        var dy = kozeppontY - lovesY;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double pontszam(float kozeppontX, float kozeppontY) {
        var local = 10D - tavolsag(kozeppontX, kozeppontY);
        return local < 0 ? 0D : Math.round(local * 100D) / 100D;
    }
}