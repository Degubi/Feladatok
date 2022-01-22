class Elethossz
{
    private int Tol { get; set; }
    private int Ig { get; set; }
    public int  ElethosszEvekben => Tol == -1 || Ig == -1 ? -1 : Ig - Tol;

    public bool IsmertAzElethossz => ElethosszEvekben != -1;

    public Elethossz(string SzuletesHalalozas)
    {
        string[] m = SzuletesHalalozas.Split('-');
        try
        {
            Tol = int.Parse(m[0]);
        }
        catch (Exception)
        {
            Tol = -1;
        }
        try
        {
            Ig = int.Parse(m[1]);
        }
        catch (Exception)
        {
            Ig = -1;
        }
    }
}