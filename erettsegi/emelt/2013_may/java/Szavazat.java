public class Szavazat {

    public final int kerSzam, szavazottSzam;
    public final String nev, part;

    public Szavazat(String data) {
        var split = data.split(" ");

        kerSzam = Integer.parseInt(split[0]);
        szavazottSzam = Integer.parseInt(split[1]);
        nev = split[2] + " " + split[3];
        part = split[4].equals("-") ? "Független" : split[4];
    }
}