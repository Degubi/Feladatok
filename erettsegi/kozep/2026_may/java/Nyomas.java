import module java.base;

public class Nyomas {

    public static void main(String[] args) throws Exception {
        var meresDarabok = Files.readString(Path.of("nyomas.txt")).trim().split(", ");
        var meresek = new int[meresDarabok.length];

        for(var i = 0; i < meresek.length; ++i) {
            meresek[i] = Integer.parseInt(meresDarabok[i]);
        }

        var legkisebbIndex = 0;
        for(var i = 0; i < meresek.length; ++i) {
            if(meresek[i] < meresek[legkisebbIndex]) {
                legkisebbIndex = i;
            }
        }

        System.out.println("A legkisebb mért érték: " + meresek[legkisebbIndex] + ", sorszáma: " + (legkisebbIndex + 1));

        var bekertErtek = Integer.parseInt(System.console().readLine("Minél kisebb értékeket keres? (egész szám) "));
        var kisebbErtekekSzama = 0;
        for(var meres : meresek) {
            if(meres < bekertErtek) {
                ++kisebbErtekekSzama;
            }
        }

        System.out.println(bekertErtek + " alatti mérések száma: " + kisebbErtekekSzama);

        var legnagyobbCsokkenes = 0;
        for(var i = 0; i < meresek.length - 1; ++i) {
            var kulonbseg = meresek[i] - meresek[i + 1];

            if(kulonbseg > legnagyobbCsokkenes) {
                legnagyobbCsokkenes = kulonbseg;
            }
        }

        System.out.println("A két mérés közötti legnagyobb csökkenés: " + legnagyobbCsokkenes);
    }
}
