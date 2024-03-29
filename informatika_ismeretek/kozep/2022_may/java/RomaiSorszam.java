import java.util.*;

public class RomaiSorszam {
    private String romaiSsz;

    public void setRomaiSsz(String ujErtek) {
        this.romaiSsz = ujErtek;
    }

    private static Map<Character, Integer> rómaiMap = new HashMap<Character, Integer>() {{
        put('I', 1);
        put('V', 5);
        put('X', 10);
        put('L', 50);
        put('C', 100);
        put('D', 500);
        put('M', 1000);
    }};

    public String getArabSsz() {
        int ertek = 0;
        String romaiSzam = romaiSsz.replace(".", "");
        for (int i = 0; i < romaiSzam.length(); i++) {
            if (i + 1 < romaiSzam.length() &&
                    rómaiMap.get(romaiSzam.charAt(i)) < rómaiMap.get(romaiSzam.charAt(i + 1))) {
                ertek -= rómaiMap.get(romaiSzam.charAt(i));
            } else {
                ertek += rómaiMap.get(romaiSzam.charAt(i));
            }
        }
        return ertek + ".";
    }

    public RomaiSorszam(String romaiSsz) {
        this.romaiSsz = romaiSsz.toUpperCase();
    }
}
