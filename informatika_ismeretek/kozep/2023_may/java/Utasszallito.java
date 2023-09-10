public class Utasszallito {

    public final String tipus;
    public final int elsoRepulesEve;
    public final int minSzallithatoUtasokSzama;
    public final int maxSzallithatoUtasokSzama;
    public final int minSzemelyzetSzama;
    public final int maxSzemeyzetSzama;
    public final Sebessegkategoria sebessegKategoria;
    public final int felszalloTomeg;
    public final float fesztav;

    public Utasszallito(String line) {
        var split = line.split(";");
        var szallithatoUtasokSzamaSplit = split[2].split("-");
        var szemelyzetSzamaSplit = split[3].split("-");

        tipus = split[0];
        elsoRepulesEve = Integer.parseInt(split[1]);
        minSzallithatoUtasokSzama = Integer.parseInt(szallithatoUtasokSzamaSplit[0]);
        maxSzallithatoUtasokSzama = Integer.parseInt(szallithatoUtasokSzamaSplit[szallithatoUtasokSzamaSplit.length - 1]);
        minSzemelyzetSzama = Integer.parseInt(szemelyzetSzamaSplit[0]);
        maxSzemeyzetSzama = Integer.parseInt(szemelyzetSzamaSplit[szemelyzetSzamaSplit.length - 1]);
        sebessegKategoria = new Sebessegkategoria(Integer.parseInt(split[4]));
        felszalloTomeg = Integer.parseInt(split[5]);
        fesztav = Float.parseFloat(split[6].replace(',', '.'));
    }

    public static String reformatUtassallitoData(Utasszallito szallito) {
        return szallito.tipus + ";" +
               szallito.elsoRepulesEve + ";" +
               szallito.maxSzallithatoUtasokSzama + ";" +
               szallito.maxSzemeyzetSzama + ";" +
               szallito.sebessegKategoria.utazosebesseg + ";" +
               Math.round((float) szallito.felszalloTomeg / 1000F) + ";" +
               Math.round((float) szallito.fesztav * 3.2808F);
    }
}