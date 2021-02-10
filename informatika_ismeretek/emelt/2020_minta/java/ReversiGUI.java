import java.io.*;
import javax.swing.*;

public class ReversiGUI {

    public static void main(String[] args) throws IOException {
        var frame = new JFrame("ReversiGUI");
        var tabla = new Tabla("allas.txt", frame);
        
        tabla.megjelenit();
        
        frame.setContentPane(tabla);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 620);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}