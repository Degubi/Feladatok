import java.awt.event.*;

import javax.swing.*;

public class MachKalkulatorGUI {

    public static void main(String[] args) {
        var torlonyomasField = newTextField(250, 20, 200);
        var statikusnyomasField = newTextField(250, 60, 200);
        var eredmenyekListData = new DefaultListModel<String>();

        var frame = new JFrame("Mach-szám kalkulátor");
        frame.setLayout(null);
        frame.setSize(700, 500);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(newLabel("Torlónyomás:", 20, 20));
        frame.add(torlonyomasField);
        frame.add(newLabel("Statikus nyomás:", 20, 60));
        frame.add(statikusnyomasField);
        frame.add(newButton("Számol", 500, 20, 120, 70, e -> tryCalculateMachResult(torlonyomasField, statikusnyomasField, eredmenyekListData)));
        frame.add(newLabel("Eredmények:", 20, 150));
        frame.add(newList(20, 190, 660, 250, eredmenyekListData));
        frame.setVisible(true);
    }

    private static JLabel newLabel(String text, int x, int y) {
        var label = new JLabel(text);
        label.setBounds(x, y, text.length() * 7, 30);
        return label;
    }

    private static JTextField newTextField(int x, int y, int width) {
        var field = new JTextField();
        field.setBounds(x, y, width, 30);
        return field;
    }

    private static JButton newButton(String label, int x, int y, int width, int height, ActionListener onClick) {
        var button = new JButton(label);
        button.setBounds(x, y, width, height);
        button.addActionListener(onClick);
        return button;
    }

    private static JList<String> newList(int x, int y, int width, int height, DefaultListModel<String> dataModel) {
        var list = new JList<String>(dataModel);
        list.setBounds(x, y, width, height);
        return list;
    }

    private static void tryCalculateMachResult(JTextField torlonyomasField, JTextField statikusnyomasField, DefaultListModel<String> eredmenyekListData) {
        var qc = tryParseDouble(torlonyomasField.getText().replace(',', '.'));
        var p0 = tryParseDouble(statikusnyomasField.getText().replace(',', '.'));

        if(Float.isNaN(qc)) {
            JOptionPane.showMessageDialog(null, "Nem megfelelő bemeneti érték a 'Torlónyomás' mezőben!");
            return;
        }

        if(Float.isNaN(p0)) {
            JOptionPane.showMessageDialog(null, "Nem megfelelő bemeneti érték a 'Statikus Nyomás' mezőben!");
            return;
        }

        var machValue = Math.sqrt(5 * (Math.pow((qc / p0) + 1, 2F / 7F) - 1));

        eredmenyekListData.addElement("qc=" + qc + " p0=" + p0 + " Ma=" + machValue);
        torlonyomasField.setText("");
        statikusnyomasField.setText("");
    }

    private static float tryParseDouble(String text) {
        try {
            return Float.parseFloat(text);
        }catch(NumberFormatException e) {
            return Float.NaN;
        }
    }
}