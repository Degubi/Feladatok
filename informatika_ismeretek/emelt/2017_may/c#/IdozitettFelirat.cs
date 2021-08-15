using System.Linq;

public class IdozitettFelirat {

    public readonly string idozites;
    public readonly string felirat;

    public IdozitettFelirat(string idoz, string felir){
        idozites = idoz;
        felirat = felir;
    }

    public int SzavakSzama() => felirat.Where(k => k == ' ').Count() + 1;

    private string Str_idoforma(string ido) {
        var perc_mp = ido.Split(':');
        var pars = int.Parse(perc_mp[0]);

        return string.Format("{0:00}:{1:00}", pars / 60, pars % 60) + ":" + perc_mp[1];
    }

    public string StrIdozites() => Str_idoforma(idozites.Split('-')[0]) + "--> " + Str_idoforma(idozites.Split('-')[1]);
}