public struct Pair {
    public readonly string domain;
    public readonly string ip;

    public Pair(string line) {
        var split = line.Split(';');
    
        domain = split[0];
        ip = split[1];
    }
}