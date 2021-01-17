public class Kifejezes {

    public final int elsoOperandus;
    public final String operator;
    public final int masodikOperandus;
    public final String teljesKifejezes;

    public Kifejezes(String line) {
        var split = line.split(" ");

        this.elsoOperandus = Integer.parseInt(split[0]);
        this.operator = split[1];
        this.masodikOperandus = Integer.parseInt(split[2]);
        this.teljesKifejezes = line;
    }

    // A Math.exact fv-ek exception-t dobnak overflow esetén
    public String kiertekel() {
        try {
            return String.valueOf(
                    switch(operator) {
                        case "+" -> Math.addExact(elsoOperandus, masodikOperandus);
                        case "-" -> Math.subtractExact(elsoOperandus, masodikOperandus);
                        case "*" -> Math.multiplyExact(elsoOperandus, masodikOperandus);
                        case "/" -> ((double) elsoOperandus) / ((double) masodikOperandus);
                        case "div" -> elsoOperandus / masodikOperandus;
                        case "mod" -> elsoOperandus % masodikOperandus;
                        default -> "Hibás operátor!";
                    });
        }catch (ArithmeticException e) {
            return "Egyéb hiba!";
        }
    }
}