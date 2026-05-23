import module java.base;

public class Letra {

    public static void main(String[] args) throws Exception {
        var dobasok = Files.readString(Path.of("dobasok.txt")).trim().split(", ");
        var lepesek = new ArrayList<LepesAllapot>();

        for(var dobas : dobasok) {
            var dobasErtek = Integer.parseInt(dobas);
            var elozoLepes = lepesek.isEmpty() ? new LepesAllapot(0, 0, false) : lepesek.getLast();

            lepesek.add(lepestAlkalmaz(elozoLepes, dobasErtek));
        }

        System.out.print("2. Feladat: ");

        for(var lepes : lepesek) {
            System.out.print(lepes.ertek + " ");
        }

        System.out.println();

        var visszalepesekSzama = 0;
        for(var lepes : lepesek) {
            if(lepes.visszalepes) {
                ++visszalepesekSzama;
            }
        }

        System.out.println("3. Feladat: A játék során " + visszalepesekSzama + " alkalommal lépett létrára");
        System.out.println("4. Feladat: " + (lepesek.getLast().ertek > 45 ? "A játékot befejezte" : "A játékot abbahagyta"));
    }

    static LepesAllapot lepestAlkalmaz(LepesAllapot elozoAllapot, int dobottErtek) {
        var osszeg = elozoAllapot.ertek + dobottErtek;
        var visszalepes = osszeg % 10 == 0;

        return new LepesAllapot(elozoAllapot.dobasI + 1, visszalepes ? osszeg - 3 : osszeg, visszalepes);
    }

    record LepesAllapot(int dobasI, int ertek, boolean visszalepes) {}
}
