using System;

public class DHaromszog {
    private double _aOldal;
    private double _bOldal;
    private double _cOldal;

    public DHaromszog(string sor, int sorszam) {
        var oldalak = sor.Split(' ');

        Console.WriteLine(String.Join('\n', oldalak));

        aOldal = double.Parse(oldalak[0]);
        bOldal = double.Parse(oldalak[1]);
        cOldal = double.Parse(oldalak[2]);

        if(!NovekvoSorrendE()) {
            throw new AdatNemJoSorrendException();
        }

        if(!SzerkeszthetoE()) {
            throw new NemSzerkeszthetoException();
        }

        if(!DerekszoguE()) {
            throw new NemDerekszoguException();
        }

        this.Sorszam = sorszam;
    }

    public int Sorszam { get; set; }
    public double aOldal { get => this._aOldal;
                           set {
                               if(value > 0D) {
                                    this._aOldal = value;
                                }else{
                                    throw new OldalNullaException("Az \"a\" oldal nem lehet nulla vagy negatív!");
                                }
                           }}

    public double bOldal { get => this._bOldal;
                           set {
                               if(value > 0D) {
                                    this._bOldal = value;
                                }else {
                                    throw new OldalNullaException("A \"b\" oldal nem lehet nulla vagy negatív!");
                                }
                           }}

    public double cOldal { get => this._cOldal;
                           set {
                                if(value > 0D) {
                                    this._cOldal = value;
                                }else {
                                    throw new OldalNullaException("A \"c\" oldal nem lehet nulla vagy negatív!");
                                }
                           }}

    private bool NovekvoSorrendE() => aOldal <= bOldal && bOldal <= cOldal;
    private bool SzerkeszthetoE() => (aOldal + bOldal) > cOldal;
    private bool DerekszoguE() => (cOldal * cOldal) == (aOldal * aOldal + bOldal * bOldal);
    public double Kerulet() => aOldal + bOldal + cOldal;
    public double Terulet() => aOldal * bOldal / cOldal;
}