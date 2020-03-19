class Keres:
    def __init__(self, line):
        split = line.split("*")
        utolsoSzokozIndex = split[3].find(" ")
        
        self.cim = split[0]
        self.datumIdo = split[1]
        self.keres = split[2]
        self.httpKod = split[3][:utolsoSzokozIndex]
        self.meret = split[3][utolsoSzokozIndex + 1:]
    
    def byteMeret(self):
        return 0 if self.meret == "-\n" else int(self.meret)
    
    def domain(self):
        return str.isalpha(self.cim[len(self.cim) - 1])