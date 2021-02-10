public class Fordulo {

    public final int ev;
    public final int het;
    public final int fordulo;
    public final int telitalalatosSzelvenyDbSzam;
    public final int telitalalatosSzelvenyNyeremenye;
    public final String eredmenyek;

    public Fordulo(String sor) {
        var split = sor.split(";");

        ev = Integer.parseInt(split[0]);
        het = Integer.parseInt(split[1]);
        fordulo = Integer.parseInt(split[2]);
        telitalalatosSzelvenyDbSzam = Integer.parseInt(split[3]);
        telitalalatosSzelvenyNyeremenye = Integer.parseInt(split[4]);
        eredmenyek = split[5];
    }

    @Override
    public String toString() {
        return "\n" +
               "    Év: " + ev + "\n" +
               "    Hét: " + het + "\n" +
               "    Forduló: " + fordulo + "\n" +
               "    Telitalalat: " + telitalalatosSzelvenyDbSzam + " db\n" +
               "    Nyeremeny: " + telitalalatosSzelvenyNyeremenye + " Ft\n" +
               "    Eredmények: " + eredmenyek + "\n";
    }
}