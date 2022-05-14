import java.awt.*;
import java.awt.event.*;
import java.util.stream.*;
import javax.swing.*;
import javax.swing.event.*;

public class KarakterDekodoloGUI {

    public static void main(String[] args) {
        var content = new JPanel(null);
        content.add(newLabel("Karakter", 20, 20));
        content.add(newTextField(null, 80, 20));

        IntStream.range(0, 4).forEach(n -> {
            var x = 60 + n * 36;

            IntStream.range(0, 7).forEach(y -> content.add(newDynamicInputTextField(x, 60 + y * 36)));
        });

        var frame = new JFrame("Dekodolo GUI");
        frame.setContentPane(content);
        frame.setSize(300, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JLabel newLabel(String text, int x, int y) {
        var label = new JLabel(text);
        label.setBounds(x, y, text.length() * 7, 30);
        return label;
    }

    private static JTextField newTextField(String text, int x, int y) {
        var field = new JTextField(text);
        field.setBounds(x, y, 32, 32);
        return field;
    }

    private static JTextField newDynamicInputTextField(int x, int y) {
        var field = newTextField("0", x, y);
        field.getDocument().addDocumentListener(new DocumentListener() {

            private void handleTextChange() {
                field.setBackground(field.getText().equals("1") ? Color.LIGHT_GRAY : Color.WHITE);
            }

            @Override
            public void removeUpdate(DocumentEvent e) { handleTextChange(); }

            @Override
            public void insertUpdate(DocumentEvent e) { handleTextChange(); }

            @Override
            public void changedUpdate(DocumentEvent e) { handleTextChange(); }

        });

        field.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent event) {
                if(event.getClickCount() == 2) {
                    field.setText(field.getText().equals("0") ? "1" : "0");
                }
            }
        });

        return field;
    }
}