public class Utas {

    public final int sorszam;
    public final int ules;
    public final int felszallasKm;
    public final int leszallasKm;

    public Utas(String line, int sorsz) {
        var split = line.split(" ");

        sorszam = sorsz;
        ules = Integer.parseInt(split[0]);
        felszallasKm = Integer.parseInt(split[1]);
        leszallasKm = Integer.parseInt(split[2]);
    }

    public static int getAr(Utas utas, int arPer10km) {
        var tav = utas.leszallasKm - utas.felszallasKm;
        var utolsoSzamjegy = tav % 10;
        var tizesek = tav / 10 + ((utolsoSzamjegy == 3 || utolsoSzamjegy == 4 || utolsoSzamjegy == 8 || utolsoSzamjegy == 9) ? 1 : 0);

        return arPer10km * tizesek;
    }
}