using System;

public class Kifejezes {

    public readonly int ElsoOperandus;
    public readonly string Operator;
    public readonly int MasodikOperandus;
    public readonly string TeljesKifejezes;

    public Kifejezes(string line) {
        var split = line.Split(' ');

        this.ElsoOperandus = int.Parse(split[0]);
        this.Operator = split[1];
        this.MasodikOperandus = int.Parse(split[2]);
        this.TeljesKifejezes = line;
    }

    public string kiertekel() {
        checked {
            try {
                switch(Operator) {
                    case "+": return (ElsoOperandus + MasodikOperandus).ToString();
                    case "-": return (ElsoOperandus - MasodikOperandus).ToString();
                    case "*": return (ElsoOperandus * MasodikOperandus).ToString();
                    case "/": return ((double) ElsoOperandus / (double) MasodikOperandus).ToString();
                    case "div": return (ElsoOperandus / MasodikOperandus).ToString();
                    case "mod": return (ElsoOperandus % MasodikOperandus).ToString();
                    default: return "Hibás operátor!";
                }
            }catch (ArithmeticException _) {
                return "Egyéb hiba!";
            }
        }
    }
}