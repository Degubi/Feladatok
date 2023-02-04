import java.util.function.*;

import javax.swing.*;

public class SuperBowlGUI {

    public static void main(String[] args) {
        var romaiTextField = newTextField(40, 50, true);
        var arabTextField  = newTextField(300, 50, false);
        var switchFieldsButton = newButton("--->", 170, 40, b -> handleSwitchFields(romaiTextField, arabTextField, b));

        var frame = new JFrame("Átváltó");
        frame.setLayout(null);
        frame.setSize(425, 200);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(newLabel("Római szám [I-X]:", 20, 20));
        frame.add(newLabel("Arab szám [1-10]:", 280, 20));
        frame.add(romaiTextField);
        frame.add(arabTextField);
        frame.add(switchFieldsButton);
        frame.add(newButton("Átvált", 170, 100, e -> handleConversion(romaiTextField, arabTextField)));
        frame.setVisible(true);
    }

    private static JLabel newLabel(String text, int x, int y) {
        var label = new JLabel(text);
        label.setBounds(x, y, 200, 30);
        return label;
    }

    private static JTextField newTextField(int x, int y, boolean enabled) {
        var field = new JTextField();
        field.setBounds(x, y, 75, 30);
        field.setEnabled(enabled);
        return field;
    }

    private static JButton newButton(String text, int x, int y, Consumer<JButton> onClick) {
        var button = new JButton(text);
        button.setBounds(x, y, 80, 30);
        button.addActionListener(e -> onClick.accept(button));
        return button;
    }

    private static void handleSwitchFields(JTextField romaiTextField, JTextField arabTextField, JButton switchFieldsButton) {
        var wasRomaiInput = romaiTextField.isEnabled();

        romaiTextField.setEnabled(!wasRomaiInput);
        romaiTextField.setText("");
        arabTextField.setEnabled(wasRomaiInput);
        arabTextField.setText("");
        switchFieldsButton.setText(wasRomaiInput ? "<---" : "--->");
    }

    private static void handleConversion(JTextField romaiTextField, JTextField arabTextField) {
        if(romaiTextField.isEnabled()) {
            var arabSzamErtek = convertToArab(romaiTextField.getText());

            arabTextField.setText(arabSzamErtek == null ? "Hiba!" : arabSzamErtek);
        }else{
            var romaiSzamErtek = convertToRoman(arabTextField.getText());

            romaiTextField.setText(romaiSzamErtek == null ? "Hiba!" : romaiSzamErtek);
        }
    }

    private static String convertToRoman(String arabNumber) {
        return switch(arabNumber) {
            case "1"  -> "I";
            case "2"  -> "II";
            case "3"  -> "III";
            case "4"  -> "IV";
            case "5"  -> "V";
            case "6"  -> "VI";
            case "7"  -> "VII";
            case "8"  -> "VIII";
            case "9"  -> "IX";
            case "10" -> "X";
            default   -> null;
        };
    }

    private static String convertToArab(String romanNumber) {
        return switch(romanNumber) {
            case "I"    -> "1";
            case "II"   -> "2";
            case "III"  -> "3";
            case "IV"   -> "4";
            case "V"    -> "5";
            case "VI"   -> "6";
            case "VII"  -> "7";
            case "VIII" -> "8";
            case "IX"   -> "9";
            case "X"    -> "10";
            default     -> null;
        };
    }
}