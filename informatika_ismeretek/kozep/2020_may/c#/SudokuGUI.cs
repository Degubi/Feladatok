using System;
using System.Drawing;
using System.Windows.Forms;

var form = new Form{ Text = "Sudoku-ellenőrző", ClientSize = new Size(540, 210) };
var dimensionBox = new TextBox{ Location = new Point(220, 15), Size = new Size(40, 30), Text = "4", ReadOnly = true };
var minusDimensionButton = new Button{ Location = new Point(150, 15), Size = new Size(50, 30), Text = "-" };
var plusDimensionButton = new Button{ Location = new Point(280, 15), Size = new Size(50, 30), Text = "+" };

minusDimensionButton.Click += (s, e) => HandleMinusDimensionButtonPress(dimensionBox);
plusDimensionButton.Click += (s, e) => HandlePlusDimensionButtonPress(dimensionBox);

form.Controls.Add(new Label{ Location = new Point(15, 15), Size = new Size(150, 30), Text = "Új feladvány mérete:" });
form.Controls.Add(minusDimensionButton);
form.Controls.Add(dimensionBox);
form.Controls.Add(plusDimensionButton);

var userTextInput = new TextBox{ Location = new Point(15, 90), Size = new Size(480, 30) };
var lengthLabel = new Label{ Location = new Point(15, 120), Size = new Size(150, 30), Text = "Hossz: 0" };
var checkButton = new Button{ Location = new Point(395, 125), Size = new Size(100, 30), Text = "Ellenőrzés" };

form.Controls.Add(new Label{ Location = new Point(15, 60), Size = new Size(100, 30), Text = "Kezdőállapot" });
form.Controls.Add(userTextInput);
form.Controls.Add(lengthLabel);
form.Controls.Add(checkButton);

userTextInput.TextChanged += (s, e) => lengthLabel.Text = "Hossz: " + userTextInput.Text.Length;
checkButton.Click += (s, e) => HandleCheckButtonPress(dimensionBox, userTextInput);

Application.Run(form);


void HandleMinusDimensionButtonPress(TextBox dimensionField) {
    var oldValue = int.Parse(dimensionField.Text);
    var newValue = Math.Max(oldValue - 1, 4);

    dimensionField.Text = newValue.ToString();
}

void HandlePlusDimensionButtonPress(TextBox dimensionField) {
    var oldValue = int.Parse(dimensionField.Text);
    var newValue = Math.Min(oldValue + 1, 9);

    dimensionField.Text = newValue.ToString();
}

void HandleCheckButtonPress(TextBox dimensionBox, TextBox userTextInput) {
    var expectedDimension = int.Parse(dimensionBox.Text);
    var userInputTextLength = userTextInput.Text.Length;
    var expectedLength = expectedDimension * expectedDimension;

    if(userInputTextLength == expectedLength) {
        MessageBox.Show("A feladvány megfelelő hosszúságú!");
    }else{
        var smaller = Math.Min(expectedLength, userInputTextLength);
        var larger = Math.Max(expectedLength, userInputTextLength);
        var argumentPart = larger == expectedLength ? "rövid: kell még" : "hosszú: törlendő";

        MessageBox.Show($"A feladvány {argumentPart} {larger - smaller} számjegy!");
    }
}