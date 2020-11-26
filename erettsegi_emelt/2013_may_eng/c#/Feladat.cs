public class Feladat{
    public readonly string kerdes;
    public readonly string temakor;
    public readonly int pont;
    public readonly int valasz;

    public Feladat(string quest, int answ, int points, string cat) {
        kerdes = quest;
        valasz = answ;
        pont = points;
        temakor = cat;
    }
}