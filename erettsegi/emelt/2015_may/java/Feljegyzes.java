public class Feljegyzes {

    public final int nap;
    public final int radioAmator;
    public int szulokSzama;
    public int gyerekekSzama;
    public final String adat;

    public Feljegyzes(String[] data1, String data2) {
        nap = Integer.parseInt(data1[0]);
        radioAmator = Integer.parseInt(data1[1]);

        if(data2.contains("/")) {
            var first = data2.charAt(0);
            var second = data2.charAt(2);

            if(first != '#') {
                szulokSzama = Character.getNumericValue(first);
            }
            if(second != '#') {
                gyerekekSzama = Character.getNumericValue(second);  //TODO: Erre nem teljesen emlékszem... nemhiszem hogy jó
            }

            adat = data2.substring(3);
        }else {
            adat = data2;
            gyerekekSzama = 0;
            szulokSzama = 0;
        }
    }
}