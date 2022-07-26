from collections import Counter

class Keres:
    def __init__(self, line: str):
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

with open("NASAlog.txt", "r") as file:
    keresek = [ Keres(k) for k in file.readlines() ]

osszmeret = sum(k.byteMeret() for k in keresek)
domainesek = sum(1 for k in keresek if k.domain())

print(f"5. Feladat: Kérések száma: {len(keresek)}")
print(f"6. Feladat: Összméret: {osszmeret} byte")
print("8. Feladat: Domaines kérések: %.2f%%" % (domainesek / len(keresek) * 100))

stat = Counter(k.httpKod for k in keresek)
for key, value in stat.items():
    print(f"    {key}: {value} db")