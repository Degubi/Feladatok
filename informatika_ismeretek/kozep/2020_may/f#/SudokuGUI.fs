open System.Drawing;
open System.Windows.Forms

let handleMinusDimensionButtonPress(dimensionField: TextBox) =
    let oldValue = int dimensionField.Text
    let newValue = max (oldValue - 1) 4

    dimensionField.Text <- string newValue

let handlePlusDimensionButtonPress(dimensionField: TextBox) =
    let oldValue = int dimensionField.Text
    let newValue = min (oldValue + 1) 9

    dimensionField.Text <- string newValue

let handleCheckButtonPress(dimensionBox: TextBox, userTextInput: TextBox) =
    let expectedDimension = int dimensionBox.Text
    let userInputTextLength = userTextInput.Text.Length
    let expectedLength = expectedDimension * expectedDimension

    if userInputTextLength = expectedLength then
        MessageBox.Show "A feladvány megfelelő hosszúságú!" |> ignore
    else
        let smaller = min expectedLength userInputTextLength
        let larger = max expectedLength userInputTextLength
        let argumentPart = if larger = expectedLength then "rövid: kell még" else "hosszú: törlendő"

        MessageBox.Show $"A feladvány {argumentPart} {larger - smaller} számjegy!" |> ignore


let form = new Form( Text = "Sudoku-ellenőrző", ClientSize = Size(540, 210) )
let dimensionBox = new TextBox( Location = Point(220, 15), Size = Size(40, 30), Text = "4", ReadOnly = true )
let minusDimensionButton = new Button( Location = Point(150, 15), Size = Size(50, 30), Text = "-" )
let plusDimensionButton = new Button( Location = Point(280, 15), Size = Size(50, 30), Text = "+" )

minusDimensionButton.Click.Add(fun _ -> handleMinusDimensionButtonPress(dimensionBox))
plusDimensionButton.Click.Add(fun _ -> handlePlusDimensionButtonPress(dimensionBox))

form.Controls.Add(new Label( Location = Point(15, 15), Size = Size(150, 30), Text = "Új feladvány mérete:" ))
form.Controls.Add(minusDimensionButton)
form.Controls.Add(dimensionBox)
form.Controls.Add(plusDimensionButton)

let userTextInput = new TextBox( Location = Point(15, 90), Size = Size(480, 30) )
let lengthLabel = new Label( Location = Point(15, 120), Size = Size(150, 30), Text = "Hossz: 0" )
let checkButton = new Button( Location = Point(395, 125), Size = Size(100, 30), Text = "Ellenőrzés" )

form.Controls.Add(new Label( Location = Point(15, 60), Size = Size(100, 30), Text = "Kezdőállapot" ))
form.Controls.Add(userTextInput)
form.Controls.Add(lengthLabel)
form.Controls.Add(checkButton)

userTextInput.TextChanged.Add(fun _ -> lengthLabel.Text <- $"Hossz: {userTextInput.Text.Length}")
checkButton.Click.Add(fun _ -> handleCheckButtonPress(dimensionBox, userTextInput))

Application.Run(form)