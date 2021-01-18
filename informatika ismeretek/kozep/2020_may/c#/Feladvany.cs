using System;

public class Feladvany {

    public readonly string feladvanyTeljes;
    public readonly int meret;

    public Feladvany(string sor) {
        this.feladvanyTeljes = sor;
        this.meret = Convert.ToInt32(Math.Sqrt(sor.Length));
    }

    public void Kirajzol() {
        for(var i = 0; i < feladvanyTeljes.Length; i++) {
            if(feladvanyTeljes[i] == '0') {
                Console.Write(".");
            }else{
                Console.Write(feladvanyTeljes[i]);
            }

            if(i % meret == meret - 1){
                Console.WriteLine();
            }
        }
    }
}