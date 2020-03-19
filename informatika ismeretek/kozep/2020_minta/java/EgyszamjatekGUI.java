import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;

public class EgyszamjatekGUI {
    
    public static void main(String[] args) throws IOException {
        var jatekosok = Egyszamjatek.beolvas("egyszamjatek2.txt");
        var frame = new JFrame("Egyszámjáték GUI");
        var nevInputField = newInputField("Játékos neve:", 40, 250, frame);
        var tippInputField = newInputField("Játékos tippjei:", 80, 450, frame);
        var hozzadasButton = newButton("Játékos hozzáadása", 250, 200, frame);
        var tippSzamlaloLabel = newLabel("0 db", 600, 80, 50, frame);
        
        tippInputField.addCaretListener(e -> tippSzamlaloLabel.setText(tippekSzama(tippInputField.getText().strip()) + " db"));
        hozzadasButton.addActionListener(e -> {
            var hozzaadandoNev = nevInputField.getText();
            
            if(jatekosok.containsKey(hozzaadandoNev)) {
                JOptionPane.showMessageDialog(frame, "Van már ilyen nevű játékos!");
            }else{
                var tippBemenet = tippInputField.getText().strip();
                var elvartSzokozokSzama = jatekosok.values().iterator().next().length;
                
                if(tippekSzama(tippBemenet) != elvartSzokozokSzama) {
                    JOptionPane.showMessageDialog(frame, "A tippek száma nem megfelelő!");
                }else{
                    try {
                        Files.writeString(Path.of("egyszamjatek2.txt"), hozzaadandoNev + ' ' + tippBemenet + '\n', StandardOpenOption.APPEND);
                    } catch (IOException e1) {}
                    
                    JOptionPane.showMessageDialog(frame, "Az állomány bővítése sikeres volt!");
                    tippInputField.setText(null);
                    nevInputField.setText(null);
                }
            }
        });
        
        frame.setLayout(null);
        frame.setBounds(0, 0, 800, 300);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
    public static int tippekSzama(String tippSzoveg) {
        return tippSzoveg.length() == 0 ? 0 : tippSzoveg.split(" ").length;
    }
    
    
    public static JTextField newInputField(String labelText, int y, int fieldWidth, JFrame frame) {
        var textField = new JTextField();
        textField.setBounds(130, y, fieldWidth, 30);
        
        newLabel(labelText, 20, y, 100, frame);
        frame.add(textField);
        return textField;
    }
    
    public static JLabel newLabel(String text, int x, int y, int width, JFrame frame) {
        var label = new JLabel(text);
        label.setBounds(x, y, width, 30);
        frame.add(label);
        return label;
    }
    
    public static JButton newButton(String text, int x, int y, JFrame frame) {
        var butt = new JButton(text);
        butt.setBackground(Color.LIGHT_GRAY);
        butt.setBounds(x, y, 250, 30);
        
        frame.add(butt);
        return butt;
    }
}