public class Feladat{
    
    public final String kerdes, temakor;
    public final int pont, valasz;
    
    public Feladat(String quest, int answ, int points, String cat) {
        kerdes = quest;
        valasz = answ;
        pont = points;
        temakor = cat;
    }
}