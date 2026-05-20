import java.util.*;

public final class Tanc {

    public final String category;
    public final String woman;
    public final String man;

    public Tanc(List<String> lines, int index) {
        this.category = lines.get(index);
        this.woman = lines.get(index + 1);
        this.man = lines.get(index + 2);
    }
}