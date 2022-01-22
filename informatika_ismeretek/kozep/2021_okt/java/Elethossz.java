public class Elethossz {
    private int Tol;
    private int Ig;

    public int elethosszEvekben() {
        return Tol == -1 || Ig == -1 ? -1 : Ig - Tol;
    }

    public boolean ismertElethossz() {
        return elethosszEvekben() != -1;
    }

    public Elethossz(String szuletesHalalozas) {
        String[] m = szuletesHalalozas.split("-");
        try {
            Tol = Integer.parseInt(m[0]);
        } catch (Exception e) {
            Tol = -1;
        }
        try {
            Ig = Integer.parseInt(m[1]);
        } catch (Exception e) {
            Ig = -1;
        }
    }
}