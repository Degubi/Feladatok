public class Jatek {

    private final String adogatoNeve;
    private final String fogadoNeve;
    private final StringBuilder allas;

    public Jatek(String adogatoNeve, String fogadoNeve, String alapAllas) {
        this.adogatoNeve = adogatoNeve;
        this.fogadoNeve = fogadoNeve;
        this.allas = new StringBuilder(alapAllas);
    }

    public void hozzaad(char eredmeny) {
        this.allas.append(eredmeny);
    }

    public int nyertLabdamenetekSzama(char jatekos) {
        return this.allas.toString().chars()
                   .filter(k -> k == jatekos)
                   .map(i -> 1)
                   .sum();
    }

    public boolean jatekVege() {
        var nyertAdogato = this.nyertLabdamenetekSzama('A');
        var nyertFogado = this.nyertLabdamenetekSzama('F');
        var kulonbseg = Math.abs(nyertAdogato - nyertFogado);

        return (nyertAdogato >= 4 || nyertFogado >= 4) && (kulonbseg >= 2);
    }

    public String getAllas() {
        return this.allas.toString();
    }
}