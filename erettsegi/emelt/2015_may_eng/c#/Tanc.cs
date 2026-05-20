public class Tanc {

    public readonly string category;
    public readonly string woman;
    public readonly string man;

    public Tanc(string[] lines, int index) {
        this.category = lines[index];
        this.woman = lines[index + 1];
        this.man = lines[index + 2];
    }
}