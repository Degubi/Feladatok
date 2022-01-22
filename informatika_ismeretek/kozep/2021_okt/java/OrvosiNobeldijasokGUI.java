import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;

public class OrvosiNobeldijasokGUI {

    public static void main(String[] args) {
        var frame = new JFrame("Orvosi Nobel-díjasok");
        var panel = new JPanel(null);

        var evInput = newTextField(20, 75);
        var nevInput = newTextField(60, 250);
        var elethosszInput = newTextField(100, 150);
        var orszagInput = newTextField(140, 75);

        panel.add(newLabel("Év:", 20));
        panel.add(evInput);
        panel.add(newLabel("Név:", 60));
        panel.add(nevInput);
        panel.add(newLabel("Sz/H:", 100));
        panel.add(elethosszInput);
        panel.add(newLabel("Ország:", 140));
        panel.add(orszagInput);
        panel.add(newButton("Adatok mentése", 125, 300, e -> handleSaveButtonClick(evInput, nevInput, elethosszInput, orszagInput)));

        frame.setSize(400, 400);
        frame.setContentPane(panel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public static void handleSaveButtonClick(JTextField evInput, JTextField nevInput, JTextField elethosszInput, JTextField orszagInput) {
        var bekertEv = evInput.getText();
        var bekertNev = nevInput.getText();
        var bekertElethossz = elethosszInput.getText();
        var bekertOrszag = orszagInput.getText();

        if(bekertEv.isBlank() || bekertNev.isBlank() || bekertElethossz.isBlank() || bekertOrszag.isBlank()) {
            JOptionPane.showMessageDialog(null, "Töltsön ki minden mezőt!");
        }else{
            if(Integer.parseInt(bekertEv) <= 1989) {
                JOptionPane.showMessageDialog(null, "Hiba! Az évszám nem megfelelő!");
            }else{
                try {
                    Files.write(Path.of("uj_dijazott.txt"), List.of(
                            "Év;Név;SzületésHalálozás;Országkód",
                            bekertEv + ';' + bekertNev + ';' + bekertElethossz + ';' + bekertOrszag
                    ));

                    evInput.setText(null);
                    nevInput.setText(null);
                    elethosszInput.setText(null);
                    orszagInput.setText(null);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Hiba az állomány írásánál!");
                }
            }
        }
    }


    public static JLabel newLabel(String text,  int y) {
        var label = new JLabel(text);
        label.setBounds(20, y, text.length() * 7, 30);
        return label;
    }

    public static JTextField newTextField(int y, int width) {
        var field = new JTextField();
        field.setBounds(100, y, width, 30);
        return field;
    }

    public static JButton newButton(String text, int x, int y, ActionListener listener) {
        var butt = new JButton(text);
        butt.setBounds(x, y, 150, 30);
        butt.addActionListener(listener);
        return butt;
    }
}