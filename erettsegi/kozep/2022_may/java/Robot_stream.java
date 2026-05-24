import module java.base;

public class Robot_stream {

    public static void main(String[] args) {
        var parancsok = System.console().readLine("Kérem a robot parancsait: ");

        parancsok.chars()
                 .boxed()
                 .collect(Collectors.groupingBy(k -> k, Collectors.counting()))
                 .forEach((betu, db) -> System.out.println(Character.toString(betu) + " betűk száma: " + db));

        var vegsoPozicio = Stream.iterate(new Pozicio(0, 0, 0), k -> k.parancsI == parancsok.length() ? null : parancsotAlkalmaz(k, parancsok))
                                 .takeWhile(Objects::nonNull)
                                 .reduce((_, l) -> l)
                                 .orElseThrow();

        var xParancsBetu = vegsoPozicio.x > 0 ? "K" : "N";
        var egyszerusitettXParancsok = xParancsBetu.repeat(Math.abs(vegsoPozicio.x));
        var yParancsBetu = vegsoPozicio.y > 0 ? "E" : "D";
        var egyszerusitettYParancsok = yParancsBetu.repeat(Math.abs(vegsoPozicio.y));

        System.out.println("Egy legrövidebb út parancsszava: " + egyszerusitettXParancsok + egyszerusitettYParancsok);
    }

    static Pozicio parancsotAlkalmaz(Pozicio elozo, String parancsok) {
        var parancs = parancsok.charAt(elozo.parancsI);
        var ujX = parancs == 'N' ? elozo.x - 1 : parancs == 'K' ? elozo.x + 1 : elozo.x;
        var ujY = parancs == 'E' ? elozo.y + 1 : parancs == 'D' ? elozo.y - 1 : elozo.y;

        return new Pozicio(elozo.parancsI + 1, ujX, ujY);
    }

    record Pozicio(int parancsI, int x, int y) {}
}