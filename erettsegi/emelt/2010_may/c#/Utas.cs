public class Utas {

    public readonly int sorszam;
    public readonly int ules;
    public readonly int felszallasKm;
    public readonly int leszallasKm;

    public Utas(string line, int sorsz) {
        var split = line.Split(' ');

        sorszam = sorsz;
        ules = int.Parse(split[0]);
        felszallasKm = int.Parse(split[1]);
        leszallasKm = int.Parse(split[2]);
    }

    public static int getAr(Utas utas, int arPer10km) {
        var tav = utas.leszallasKm - utas.felszallasKm;
        var utolsoSzamjegy = tav % 10;
        var tizesek = tav / 10 + ((utolsoSzamjegy == 3 || utolsoSzamjegy == 4 || utolsoSzamjegy == 8 || utolsoSzamjegy == 9) ? 1 : 0);

        return arPer10km * tizesek;
    }
}