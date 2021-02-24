using System;

public class NemSzerkeszthetoException : Exception {
    public NemSzerkeszthetoException() : base("A háromszöget nem lehet megszerkeszteni!") {}
}