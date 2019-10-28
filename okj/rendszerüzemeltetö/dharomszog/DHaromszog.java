public class DHaromszog{
    private double aOldal;
    private double bOldal;
    private double cOldal;
    private int sorszam;
    
    public DHaromszog(String sor, int sorszam) throws OldalNullaException, AdatNemJoSorrendException, NemSzerkeszthetoException, NemDerekszoguException{
        var oldalak = sor.replace(',', '.').split(" ");
        
        var beAOldal = Double.parseDouble(oldalak[0]);
        var beBOldal = Double.parseDouble(oldalak[1]);
        var beCOldal = Double.parseDouble(oldalak[2]);
        
        setaOldal(beAOldal);
        setbOldal(beBOldal);
        setcOldal(beCOldal);
        
        if(!isNovekvoSorrend()) {
            throw new AdatNemJoSorrendException();
        }
        
        if(!isSzerkesztheto()) {
            throw new NemSzerkeszthetoException();
        }
        
        if(!isDerekszogu()) {
            throw new NemDerekszoguException();
        }
        
        this.sorszam = sorszam;
    }
    
    private boolean isNovekvoSorrend() {
        return aOldal <= bOldal && bOldal <= cOldal;
    }
    
    private boolean isSzerkesztheto() {
        return (aOldal + bOldal) > cOldal;
    }
    
    private boolean isDerekszogu() {
        return (cOldal * cOldal) == (aOldal * aOldal + bOldal * bOldal);
    }
    
    public double kerulet() {
        return aOldal + bOldal + cOldal;
    }
    
    public double terulet() {
        return aOldal * bOldal / cOldal;
    }

    public double getaOldal() {
        return aOldal;
    }

    public double getbOldal() {
        return bOldal;
    }

    public double getcOldal() {
        return cOldal;
    }
    
    public int getSorszam() {
        return sorszam;
    }

    public void setSorszam(int sorszam) {
        this.sorszam = sorszam;
    }
    
    public void setaOldal(double aOldal) throws OldalNullaException{
        if(aOldal > 0D) {
            this.aOldal = aOldal;
        }else{
            throw new OldalNullaException("Az \"a\" oldal nem lehet nulla vagy negatív!");
        }
    }
    
    public void setbOldal(double bOldal) throws OldalNullaException{
        if(aOldal > 0D) {
            this.bOldal = bOldal;
        }else {
            throw new OldalNullaException("A \"b\" oldal nem lehet nulla vagy negatív!");
        }
    }
    
    public void setcOldal(double cOldal) throws OldalNullaException{
        if(aOldal > 0D) {
            this.cOldal = cOldal;
        }else {
            throw new OldalNullaException("A \"c\" oldal nem lehet nulla vagy negatív!");
        }
    }
}