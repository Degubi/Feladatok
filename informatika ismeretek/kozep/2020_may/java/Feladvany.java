public class Feladvany {

    public final String feladvanyTeljes;
    public final int meret;

    public Feladvany(String sor) {
        this.feladvanyTeljes = sor;
        this.meret = (int) (Math.sqrt(sor.length()));
    }

    public void kirajzol() {
        for(var i = 0; i < feladvanyTeljes.length(); i++) {
            if(feladvanyTeljes.charAt(i) == '0') {
                System.out.print(".");
            }else{
                System.out.print(feladvanyTeljes.charAt(i));
            }
            if(i % meret == meret - 1) {
                System.out.println();
            }
        }
    }
}