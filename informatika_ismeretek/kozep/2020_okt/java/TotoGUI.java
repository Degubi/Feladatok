import java.util.function.*;
import javax.swing.*;
import javax.swing.event.*;

public class TotoGUI {

    public static void main(String[] args) {
        var frame = new JFrame("Totó eredmény ellenőrző");
        var panel = new JPanel(null);

        var saveButton = newButton("Eredmények mentése", 20, 220, 300);
        var characterLengthCheckBox = newCheckBox("Nem megfelelő a karakterek száma (14)", 20, 120);
        var invalidCharacterCheckBox = newCheckBox("Helytelen karakter az eredményekben ()", 20, 150);
        var inputField = newTextField("12X12X12X12X12", 20, 70, 400, k -> onInputFieldTextChange(k, characterLengthCheckBox, invalidCharacterCheckBox, saveButton));

        panel.add(newLabel("Kérem a forduló eredményeit [1, 2, X]:", 20, 20));
        panel.add(inputField);
        panel.add(characterLengthCheckBox);
        panel.add(invalidCharacterCheckBox);
        panel.add(saveButton);

        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 320);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    public static void onInputFieldTextChange(JTextField inputField, JCheckBox characterLengthCheckBox, JCheckBox invalidCharacterCheckBox, JButton saveButton) {
        var inputText = inputField.getText();
        var invalidCharacters = inputText.chars()
                                         .filter(k -> k != '1' && k != '2' && k != 'X')
                                         .mapToObj(k -> new String(Character.toChars(k)))
                                         .toArray(String[]::new);

        characterLengthCheckBox.setSelected(inputText.length() != 14);
        invalidCharacterCheckBox.setSelected(invalidCharacters.length > 0);
        invalidCharacterCheckBox.setText("Helytelen karakter az eredményekben (" + String.join(";", invalidCharacters) + ")");
        saveButton.setEnabled(!characterLengthCheckBox.isSelected() && !invalidCharacterCheckBox.isSelected());
    }



    public static JLabel newLabel(String text, int x, int y) {
        var label = new JLabel(text);
        label.setBounds(x, y, 300, 30);
        return label;
    }

    public static JTextField newTextField(String text, int x, int y, int width, Consumer<JTextField> onChangeListener) {
        var field = new JTextField(text);
        field.setBounds(x, y, width, 30);
        field.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                onChangeListener.accept(field);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                onChangeListener.accept(field);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {}
        });
        return field;
    }

    public static JCheckBox newCheckBox(String text, int x, int y) {
        var box = new JCheckBox(text);
        box.setBounds(x, y, 500, 30);
        box.setEnabled(false);
        return box;
    }

    public static JButton newButton(String text, int x, int y, int width) {
        var butt = new JButton(text);
        butt.setBounds(x, y, width, 30);
        return butt;
    }
}