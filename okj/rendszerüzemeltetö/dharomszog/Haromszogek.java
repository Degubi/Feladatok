import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class Haromszogek {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        var frame = new JFrame("Derékszögő háromszögek");
        var content = new JPanel(null);
        
        var haromszogTarolo = new ArrayList<DHaromszog>();
        var errorList = newListWithPanel("Hibák a kiválasztott tartományban", 20, 60, 740, 200, content);
        var selectionList = newListWithPanel("Derékszögű háromszögek", 20, 280, 240, 220, content);
        var infoList = newListWithPanel("Kiválasztott derékszögő háromszög adatai", 300, 280, 460, 220, content);
        
        var loadButton = newButton("Adatok betöltése", 20, 20, 150, 30, e -> importFromFile(errorList, selectionList, infoList, haromszogTarolo));
        
        selectionList.addListSelectionListener(e -> selectionChange(selectionList, infoList, haromszogTarolo));
        
        content.add(loadButton);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 800, 600);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setContentPane(content);
        frame.setVisible(true);
    }
    
    private static void importFromFile(JList<String> errorList, JList<String> selectionList, JList<String> infoList, ArrayList<DHaromszog> haromszogTarolo) {
        var fileChooser = new JFileChooser("./");
        
        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                var fileAdat = Files.readAllLines(fileChooser.getSelectedFile().toPath());
                var errorListModel = new DefaultListModel<String>();
                var selectionListModel = new DefaultListModel<String>();
                
                infoList.setListData(new String[0]);
                haromszogTarolo.clear();
                
                for (var i = 0; i < fileAdat.size(); i++) {
                    var sorszam = (i + 1) + ". sor: ";
                    var sor = fileAdat.get(i);
                    
                    try {
                        var haromszog = new DHaromszog(sor, i + 1);
                        
                        selectionListModel.addElement(sorszam + "a=" + haromszog.getaOldal() + " b=" + haromszog.getbOldal() + " c=" + haromszog.getcOldal());
                        haromszogTarolo.add(haromszog);
                    } catch (OldalNullaException | AdatNemJoSorrendException | NemSzerkeszthetoException | NemDerekszoguException e) {
                        errorListModel.addElement(sorszam + e.getMessage());
                    }
                }
                
                errorList.setModel(errorListModel);
                selectionList.setModel(selectionListModel);
            } catch (IOException e) {}
        }
    }
    
    private static void selectionChange(JList<String> selectionList, JList<String> infoList, ArrayList<DHaromszog> haromszogTarolo) {
        var model = new DefaultListModel<String>();
        var kivalasztott = haromszogTarolo.get(selectionList.getSelectedIndex());
        
        model.addElement("Kerület: " + kivalasztott.kerulet());
        model.addElement("Terület: " + kivalasztott.terulet());
        
        infoList.setModel(model);
    }
    
    
    private static JButton newButton(String text, int x, int y, int width, int height, ActionListener listener) {
        var butt = new JButton(text);
        butt.setBounds(x, y, width, height);
        butt.setFocusable(false);
        butt.addActionListener(listener);
        return butt;
    }
    
    private static JList<String> newListWithPanel(String topText, int x, int y, int width, int height, JPanel mainPanel){
        var panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        var list = new JList<String>();

        panel.setBounds(x, y, width, height);
        panel.setBorder(new TitledBorder(new LineBorder(Color.GRAY, 1), topText));
        panel.add(list);
        mainPanel.add(panel);
        
        return list;
    }
}