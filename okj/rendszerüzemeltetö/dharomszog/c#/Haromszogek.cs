using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Windows.Forms;

public class Haromszogek {

    [STAThread]
    public static void Main(string[] args) {
        var form = new Form{ Size = new Size(800, 600) };

        var haromszogTarolo = new List<DHaromszog>();
        var errorList = NewListWithPanel("Hibák a kiválasztott tartományban", 20, 60, 740, 200, form);
        var selectionList = NewListWithPanel("Derékszögű háromszögek", 20, 280, 240, 220, form);
        var infoList = NewListWithPanel("Kiválasztott derékszögő háromszög adatai", 300, 280, 460, 220, form);
        var loadButton = new Button{ Location = new Point(20, 20), Size = new Size(150, 30), Text = "Adatok betöltése" };

        loadButton.Click += (e, o) => ImportFromFile(errorList, selectionList, infoList, haromszogTarolo);

        selectionList.SelectedValueChanged += (e, o) => SelectionChange(selectionList, infoList, haromszogTarolo);
        form.Controls.Add(loadButton);

        Application.Run(form);
    }

    private static void ImportFromFile(ListBox errorList, ListBox selectionList, ListBox infoList, List<DHaromszog> haromszogTarolo) {
        var fileChooser = new OpenFileDialog{ InitialDirectory = Directory.GetCurrentDirectory() };

        if(fileChooser.ShowDialog() == DialogResult.OK) {
            var fileAdat = File.ReadAllLines(fileChooser.FileName);

            infoList.Items.Clear();
            haromszogTarolo.Clear();
            errorList.Items.Clear();

            for (var i = 0; i < fileAdat.Length; i++) {
                var sorszam = (i + 1) + ". sor: ";
                var sor = fileAdat[i];

                try {
                    var haromszog = new DHaromszog(sor, i + 1);

                    selectionList.Items.Add(sorszam + "a=" + haromszog.aOldal + " b=" + haromszog.bOldal + " c=" + haromszog.cOldal);
                    haromszogTarolo.Add(haromszog);
                } catch (Exception e) {
                    errorList.Items.Add(sorszam + e.Message);
                }
            }
        }
    }

    private static void SelectionChange(ListBox selectionList, ListBox infoList, List<DHaromszog> haromszogTarolo) {
        var kivalasztott = haromszogTarolo[selectionList.SelectedIndex];

        infoList.Items.Clear();
        infoList.Items.Add("Kerület: " + kivalasztott.Kerulet());
        infoList.Items.Add("Terület: " + kivalasztott.Terulet());
    }

    private static ListBox NewListWithPanel(String topText, int x, int y, int width, int height, Form form) {
        var panel = new GroupBox{ Text = topText, Location = new Point(x, y), Size = new Size(width, height) };
        var list = new ListBox{Location = new Point(0, 20), Size = new Size(width, height)};

        panel.Controls.Add(list);
        form.Controls.Add(panel);

        return list;
    }
}