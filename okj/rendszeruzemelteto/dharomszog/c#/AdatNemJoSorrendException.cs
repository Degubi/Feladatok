using System;

public class AdatNemJoSorrendException : Exception {
    public AdatNemJoSorrendException() : base("Az adatok nincsenek jó sorrendben!") {}
}