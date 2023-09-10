public class Sebessegkategoria {
    public static final String[] KATEGORIAK = { "Alacsony sebességű", "Szubszonikus", "Transzszonikus", "Szuperszonikus" };

    public final int utazosebesseg;

    public String getKategorianev() {
        if (utazosebesseg < 500) return KATEGORIAK[0];
        else if (utazosebesseg < 1000) return KATEGORIAK[1];
        else if (utazosebesseg < 1200) return KATEGORIAK[2];
        else return KATEGORIAK[3];
    }

    public Sebessegkategoria(int utazosebesseg) {
        this.utazosebesseg = utazosebesseg;
    }
}