import module java.base;

public class Kitalalo {

    public static void main(String[] args) throws Exception {
        var szavak = Files.readString(Path.of("szavak.txt")).trim().replace("\"", "").split(", ");
        var random = new Random();
        var kivalasztottSzo = szavak[random.nextInt(szavak.length)];
        var probakSzama = 1;

        while(true) {
            var tipp = System.console().readLine("Kérem a tippet: ");

            if(tipp.equals("stop")) {
                break;
            }else{
                System.out.print("Az eredmény: ");

                var eltalaltKarakterek = 0;
                for(var i = 0; i < 6; ++i) {
                    var eredetiKarakter = kivalasztottSzo.charAt(i);
                    var eltalaltaE = eredetiKarakter == tipp.charAt(i);

                    System.out.print(eltalaltaE ? eredetiKarakter : '.');

                    if(eltalaltaE) {
                        ++eltalaltKarakterek;
                    }
                }

                System.out.println();

                if(eltalaltKarakterek == 6) {
                    System.out.println("\n" + probakSzama + " tippeléssel sikerült kitalálni");
                    break;
                }else{
                    ++probakSzama;
                }
            }
        }
    }
}