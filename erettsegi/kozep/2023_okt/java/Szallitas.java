import module java.base;

public class Szallitas {

    public static void main(String[] args) throws Exception {
        var tomegDarabok = Files.readString(Path.of("tomeg.txt")).trim().split(", ");
        var tomegek = new int[tomegDarabok.length];

        for(var i = 0; i < tomegek.length; ++i) {
            tomegek[i] = Integer.parseInt(tomegDarabok[i]);
        }

        var ossztomeg = 0;
        for(var tomeg : tomegek) {
            ossztomeg += tomeg;
        }

        System.out.println("2. Feladat: A tárgyak tömegének összege: " + ossztomeg + "kg");

        var dobozok = new ArrayList<Integer>();
        dobozok.add(0);

        for(var tomeg : tomegek) {
            var utolsoDoboz = dobozok.getLast();
            var ujTomeg = utolsoDoboz + tomeg;

            if(ujTomeg <= 20) {
                dobozok.set(dobozok.size() - 1, ujTomeg);
            }else{
                dobozok.add(tomeg);
            }
        }

        var kiirniLista = new String[dobozok.size()];
        for(var i = 0; i < dobozok.size(); ++i) {
            kiirniLista[i] = dobozok.get(i).toString();
        }

        System.out.println("3. Feladat: A dobozok tartalmának tömege (kg): " + String.join(" ", kiirniLista) + "\nSzükséges dobozok száma: " + dobozok.size());
    }
}
