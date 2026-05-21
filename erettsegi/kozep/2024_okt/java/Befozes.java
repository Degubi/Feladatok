import module java.base;

public class Befozes {

    public static void main(String[] args) throws Exception {
        var urtartalmakSzovegek = Files.readString(Path.of("uvegek.txt")).trim().split(", ");
        var urtartalmak = new int[urtartalmakSzovegek.length];

        for(var i = 0; i < urtartalmak.length; ++i) {
            urtartalmak[i] = Integer.parseInt(urtartalmakSzovegek[i]);
        }

        var bekertUrtartalom = Integer.parseInt(System.console().readLine("2. Feladat: Mari néni lekvárja (dl): "));
        var legnagyobbUvegIndex = 0;

        for(var i = 0; i < urtartalmak.length; ++i) {
            if(urtartalmak[i] > urtartalmak[legnagyobbUvegIndex]) {
                legnagyobbUvegIndex = i;
            }
        }

        System.out.println("3. Feladat: A legnagyobb üveg: " + urtartalmak[legnagyobbUvegIndex] + " dl és " + (legnagyobbUvegIndex + 1) + ". a sorban");

        var urtartalomOssz = 0;
        for(var urtartalom : urtartalmak) {
            urtartalomOssz += urtartalom;
        }

        System.out.println("4. Feladat: " + (bekertUrtartalom <= urtartalomOssz ? "Elegendő üveg volt" : "Maradt lekvár"));
    }
}
