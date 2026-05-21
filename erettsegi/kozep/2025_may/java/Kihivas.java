public class Kihivas {

    public static void main(String[] args) {
        var aktivitas = System.console().readLine("1. Feladat: Adja meg az aktivitását: ");
        var teljesTavolsag = 0;

        for(var i = 0; i < aktivitas.length(); ++i) {
            teljesTavolsag += switch(aktivitas.charAt(i)) {
                case 'U', 'G' -> 1;
                case 'F' -> 2;
                case 'K' -> 10;
                default -> 0;
            };
        }

        var voltEMindenAktivitas = aktivitas.contains("U") && aktivitas.contains("G") && aktivitas.contains("F") && aktivitas.contains("K");
        var teljesVegsoTavolsag = teljesTavolsag + (voltEMindenAktivitas ? 10 : 0);

        System.out.println("2. Feladat: Az elért távolság: " + teljesTavolsag);
        System.out.println("3. Feladat: " + (voltEMindenAktivitas ? "Bravó! Jutalma még 10 km." : "Nem jár jutalom."));
        System.out.println("4. Feladat: Eredménye: " + teljesVegsoTavolsag + "km. " + (teljesVegsoTavolsag >= 40 ? "Gratulálok, kihívás teljesítve!" : "Legközelebb sikerül!"));
    }
}
