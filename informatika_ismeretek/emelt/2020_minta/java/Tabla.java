import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.*;

public class Tabla extends JPanel {
    
    public final char[][] allas = new char[8][8];
    
    public Tabla(String fileName, JFrame frame) throws IOException {
        super(null);
        setBackground(Color.LIGHT_GRAY);
        setVisible(false);

        var lines = Files.readAllLines(Path.of(fileName));
        for(var x = 0; x < 8; ++x) {
            var jelenlegiSor = lines.get(x);
            
            for(var y = 0; y < 8; ++y) {
                var butt = new JButton();
                butt.setOpaque(false);
                butt.setContentAreaFilled(false);
                butt.setBorderPainted(false);
                butt.setBounds(y * 72 + 5, x * 72 + 5, 64, 64);
                
                var jelenlegiCella = jelenlegiSor.charAt(y);
                if(jelenlegiCella == '#') {
                    var sor = x;
                    var oszlop = y;
                    
                    butt.addActionListener(e -> handleEmptyCellClick(frame, sor, oszlop));
                }else {
                    butt.addActionListener(e -> frame.setTitle("ReversiGUI - " + (jelenlegiCella == 'K' ? "KÉK" : "FEHÉR")));
                }
                
                add(butt);
                allas[x][y] = jelenlegiCella;
            }
        }
    }

    private void handleEmptyCellClick(JFrame frame, int sor, int oszlop) {
        var title = frame.getTitle();
        
        if(!title.equals("ReversiGUI")) {
            var jelenlegiCella = title.contains("KÉK") ? 'K' : 'F';
            
            frame.setTitle(frame.getTitle() + (szabalyosLepes(jelenlegiCella, sor, oszlop) ? " > SZABÁLYOS" : " > SZABÁLYTALAN"));
        }
    }

    public void megjelenit() {
        setVisible(true);
    }
    
    public boolean vanForditas(char jatekos, int sor, int oszlop, int iranySor, int iranyOszlop) {
        var aktSor = sor + iranySor;
        var aktOszlop = oszlop + iranyOszlop;
        var ellenfel = jatekos == 'K' ? 'F' : 'K';
        var nincsEllenfel = true;
        
        while(aktSor > 0 && aktSor < 8 && aktOszlop > 0 && aktOszlop < 8 && allas[aktSor][aktOszlop] == ellenfel) {
            aktSor += iranySor;
            aktOszlop += iranyOszlop;
            nincsEllenfel = false;
            break;
        }
        
        return !(nincsEllenfel || aktSor < 0 || aktSor > 7 || aktOszlop < 0 || aktOszlop > 7 || allas[aktSor][aktOszlop] != jatekos);
    }
    
    public boolean szabalyosLepes(char jatekos, int sor, int oszlop) {
        return allas[sor][oszlop] == '#' &&
              (vanForditas(jatekos, sor, oszlop, -1, -1) || vanForditas(jatekos, sor, oszlop, -1, 0) || vanForditas(jatekos, sor, oszlop, -1, 1) ||
               vanForditas(jatekos, sor, oszlop,  0, -1) || vanForditas(jatekos, sor, oszlop,  0, 1) ||
               vanForditas(jatekos, sor, oszlop,  1, -1) || vanForditas(jatekos, sor, oszlop,  1, 0) || vanForditas(jatekos, sor, oszlop,  1, 1));
    }
    
    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        
        for(var x = 0; x < 8; ++x) {
            for(var y = 0; y < 8; ++y) {
                var currentCell = allas[x][y];
                var ovalColor = currentCell == 'K' ? Color.BLUE : currentCell == 'F' ? Color.WHITE : Color.GRAY;
                
                graphics.setColor(ovalColor);
                graphics.fillOval(y * 72 + 5, x * 72 + 5, 64, 64);
            }
        }
    }
}