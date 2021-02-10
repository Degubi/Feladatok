import java.io.*;
import java.nio.file.*;
import java.util.stream.*;
import javax.swing.*;

public class IskolaGUI {

    public static void main(String[] args) {
        var frame = new JFrame("Iskola GUI");
        var lineList = new JList<>(readGUIFile());
        var scrollBar = new JScrollPane(lineList);
        var deleteButton = new JButton("Törlés");
        var saveButton = new JButton("Mentés");
        
        deleteButton.addActionListener(e -> handleDeleteButtonPress(lineList));
        saveButton.addActionListener(e -> handleSaveButtonPress(lineList));
        
        scrollBar.setBounds(20, 20, 340, 600);
        deleteButton.setBounds(20, 660, 120, 30);
        saveButton.setBounds(240, 660, 120, 30);
        
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(scrollBar);
        frame.add(deleteButton);
        frame.add(saveButton);
        frame.setVisible(true);
    }
    
    public static void handleSaveButtonPress(JList<String> lineList) {
        //Ez a tragédia azért kell, mert a Java Swing 2 millió éves...
        
        var listModel = (DefaultListModel<String>) lineList.getModel();
        var allElements = IntStream.range(0, listModel.size())
                                   .mapToObj(listModel::get)
                                   .collect(Collectors.toList());
        try {
            Files.write(Path.of("nevekNEW.txt"), allElements);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hiba történt: " + e.getMessage(), "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void handleDeleteButtonPress(JList<String> lineList) {
        var selection = lineList.getSelectedValue();
        
        if(selection != null) {
            var listModel = (DefaultListModel<String>) lineList.getModel();
            
            listModel.removeElement(selection);
        }else{
            JOptionPane.showMessageDialog(null, "Nincs kiválaszott tanuló!", "Hiba", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static DefaultListModel<String> readGUIFile(){
        var model = new DefaultListModel<String>();
        
        try {
            model.addAll(Files.readAllLines(Path.of("nevekGUI.txt")));
        } catch (IOException e) {}
        
        return model;
    }
}