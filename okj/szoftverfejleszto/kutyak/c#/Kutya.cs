using System;

public class Kutya {
    public readonly int id;
    public readonly int fajtaId;
    public readonly int nevId;
    public readonly int eletkor;
    public readonly DateTime ellenorzes;

    public Kutya(string[] data) {
        id = int.Parse(data[0]);
        fajtaId = int.Parse(data[1]);
        nevId = int.Parse(data[2]);
        eletkor = int.Parse(data[3]);
        ellenorzes = DateTime.ParseExact(data[4], "yyyy.MM.dd", System.Globalization.CultureInfo.InvariantCulture);
    }
}