public class EredmenyElemzo {
    private final String eredmenyek;

    public EredmenyElemzo(String eredmenyek) {
        this.eredmenyek = eredmenyek;
    }

    private int dontetlenekSzama() {
        return megszamol('X');
    }

    private int megszamol(char kimenet) {
        return (int) eredmenyek.chars().filter(ch -> ch == kimenet).count();
    }

    public boolean nemvoltDontetlenMerkozes() {
        return dontetlenekSzama() == 0;
    }
}