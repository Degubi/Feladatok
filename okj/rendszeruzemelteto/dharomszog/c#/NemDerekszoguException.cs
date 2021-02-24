using System;

public class NemDerekszoguException : Exception {
    public NemDerekszoguException() : base("A háromszög nem derékszögű!") {}
}