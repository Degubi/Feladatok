using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Windows.Forms;

namespace Mivaaa {
    public class EgyszamjatekGUI {
        
        public static void Main() {
            var jatekosok = Egyszamjatek.Beolvas("egyszamjatek2.txt");
            var form = new Form { Text = "Egyszámjáték GUI", ClientSize = new Size(800, 300) };
            var nevInputBox = NewTextBox("Játékos neve:", 40, 250, form);
            var tippInputBox = NewTextBox("Játékos tippjei:", 80, 450, form);
            var hozzadasButton = NewButton("Játékos hozzáadása", 230, 230, 350, form);
            var tippSzamlaloLabel = NewLabel("0 db", 600, 80, 50, form);

            tippInputBox.TextChanged += (s, e) => tippSzamlaloLabel.Text = TippekSzama(tippInputBox.Text.Trim()) + " db";
            hozzadasButton.Click += (s, e) => {
                var hozzaadandoNev = nevInputBox.Text;

                if(jatekosok.ContainsKey(hozzaadandoNev)) {
                    MessageBox.Show("Van már ilyen nevű játékos!");
                } else {
                    var tippBemenet = tippInputBox.Text.Trim();
                    var elvartSzokozokSzama = jatekosok.Values.First().Length;

                    if(TippekSzama(tippBemenet) != elvartSzokozokSzama) {
                        MessageBox.Show("A tippek száma nem megfelelő!");
                    } else {
                        File.AppendAllText("egyszamjatek2.txt", hozzaadandoNev + ' ' + tippBemenet + '\n');
                        MessageBox.Show("Az állomány bővítése sikeres volt!");
                        tippInputBox.Text = null;
                        nevInputBox.Text = null;
                    }
                }
            };

            Application.Run(form);
        }

        public static int TippekSzama(string tippSzoveg) {
            return tippSzoveg.Length == 0 ? 0 : tippSzoveg.Split(' ').Length;
        }

        public static TextBox NewTextBox(string labelText, int y, int width, Form form) {
            var box = new TextBox { Location = new Point(130, y), Size = new Size(width, 13) };
            NewLabel(labelText, 20, y, 100, form);
            form.Controls.Add(box);
            return box;
        }

        public static Label NewLabel(string text, int x, int y, int width, Form form) {
            var label = new Label { Text = text, Location = new Point(x, y), Size = new Size(width, 13) };
            form.Controls.Add(label);
            return label;
        }

        public static Button NewButton(string text, int x, int y, int width, Form form) {
            var button = new Button { Text = text, Location = new Point(x, y), Size = new Size(width, 26), BackColor = Color.LightGray };
            form.Controls.Add(button);
            return button;
        }
    }
}