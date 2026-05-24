import module java.base;

public class Robot {

    public static void main(String[] args) {
        var parancsok = System.console().readLine("Kérem a robot parancsait: ");
        var betuStatok = new HashMap<Character, Integer>();

        for(var i = 0; i < parancsok.length(); ++i) {
            var parancsBetu = parancsok.charAt(i);

            betuStatok.put(parancsBetu, betuStatok.getOrDefault(parancsBetu, 0) + 1);
        }

        for(var entry : betuStatok.entrySet()) {
            System.out.println(entry.getKey() + " betűk száma: " + entry.getValue());
        }

        var vegsoX = 0;
        var vegsoY = 0;
        for(var i = 0; i < parancsok.length(); ++i) {
            var parancs = parancsok.charAt(i);
            vegsoX = parancs == 'N' ? vegsoX - 1 : parancs == 'K' ? vegsoX + 1 : vegsoX;
            vegsoY = parancs == 'E' ? vegsoY + 1 : parancs == 'D' ? vegsoY - 1 : vegsoY;
        }

        var xParancsBetu = vegsoX > 0 ? "K" : "N";
        var egyszerusitettXParancsok = xParancsBetu.repeat(Math.abs(vegsoX));
        var yParancsBetu = vegsoY > 0 ? "E" : "D";
        var egyszerusitettYParancsok = yParancsBetu.repeat(Math.abs(vegsoY));

        System.out.println("Egy legrövidebb út parancsszava: " + egyszerusitettXParancsok + egyszerusitettYParancsok);
    }
}
