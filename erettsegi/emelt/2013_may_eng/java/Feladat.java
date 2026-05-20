public class Feladat{

    public final String kerdes;
    public final String temakor;
    public final int pont;
    public final int valasz;

    public Feladat(String firstLine, String secondLine) {
        var secondLineSplit = secondLine.split(" ");

        kerdes = firstLine;
        valasz = Integer.parseInt(secondLineSplit[0]);
        pont = Integer.parseInt(secondLineSplit[1]);
        temakor = secondLineSplit[2];
    }
}