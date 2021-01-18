import java.awt.event.*;
import javax.swing.*;

public class SudokuGUI {

    public static void main(String[] args) {
        var content = new JPanel(null);
        var dimensionField = newTextField("4", 220, 15, 40, false);

        content.add(newLabel("Új feladvány mérete:", 15, 15));
        content.add(newButton("-", 150, 15, 50, e -> handleMinusDimensionButtonPress(dimensionField)));
        content.add(newButton("+", 280, 15, 50, e -> handlePlusDimensionButtonPress(dimensionField)));
        content.add(dimensionField);

        var userTextInput = newTextField(null, 15, 90, 480, true);
        var lengthLabel = newLabel("Hossz: 0", 15, 120);

        content.add(newLabel("Kezdőállapot:", 15, 60));
        content.add(userTextInput);
        content.add(lengthLabel);
        content.add(newButton("Ellenőrzés", 395, 125, 100, e -> handleCheckButtonPress(dimensionField, userTextInput)));

        userTextInput.addCaretListener(e -> lengthLabel.setText("Hossz: " + userTextInput.getText().length()));

        var frame = new JFrame("Sudoku-ellenőrző");
        frame.setSize(540, 210);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(content);
        frame.setVisible(true);
    }


    public static void handleMinusDimensionButtonPress(JTextField dimensionField) {
        var oldValue = Integer.parseInt(dimensionField.getText());
        var newValue = Math.max(oldValue - 1, 4);

        dimensionField.setText(Integer.toString(newValue));
    }

    public static void handlePlusDimensionButtonPress(JTextField dimensionField) {
        var oldValue = Integer.parseInt(dimensionField.getText());
        var newValue = Math.min(oldValue + 1, 9);

        dimensionField.setText(Integer.toString(newValue));
    }

    public static void handleCheckButtonPress(JTextField dimensionField, JTextField userTextInput) {
        var expectedDimension = Integer.parseInt(dimensionField.getText());
        var userInputTextLength = userTextInput.getText().length();
        var expectedLength = expectedDimension * expectedDimension;

        if(userInputTextLength == expectedLength) {
            JOptionPane.showMessageDialog(null, "A feladvány megfelelő hosszúságú!");
        }else{
            var smaller = Math.min(expectedLength, userInputTextLength);
            var larger = Math.max(expectedLength, userInputTextLength);
            var argumentPart = larger == expectedLength ? "rövid: kell még " : "hosszú: törlendő ";

            JOptionPane.showMessageDialog(null, "A feladvány + " + argumentPart + (larger - smaller) + "számjegy!");
        }
    }



    public static JLabel newLabel(String text, int x, int y) {
        var label = new JLabel(text);
        label.setBounds(x, y, text.length() * 7, 30);
        return label;
    }

    public static JButton newButton(String text, int x, int y, int width, ActionListener listener) {
        var butt = new JButton(text);
        butt.setBounds(x, y, width, 30);
        butt.addActionListener(listener);
        return butt;
    }

    public static JTextField newTextField(String text, int x, int y, int width, boolean editable) {
        var field = new JTextField(text);
        field.setBounds(x, y, width, 30);
        field.setEditable(editable);
        return field;
    }
}