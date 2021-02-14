from statistics import mean

class Versenyzo:
    def __init__(self, line):
        split = line.split(";")

        self.nev = split[0]
        self.rajtszam = int(split[1])
        self.kategoria = split[2]
        self.ido = split[3]
        self.befejezesSzazalek = int(split[4])

    def idoOraban(self):
        daraboltIdo = self.ido.split(":")
        ora = float(daraboltIdo[0])
        perc = float(daraboltIdo[1])
        mp = float(daraboltIdo[2])

        return ora + (perc / 60.0) + (mp / 3600.0)

with open("ub2017egyeni.txt", "r") as file:
    lines = file.readlines()
    versenyzok = [ Versenyzo(lines[i]) for i in range(1, len(lines)) ]

print(f"3. Feladat: Egyéni indulók: {len(versenyzok)}")

celbaertNoiVersenyzokSzama = sum(1 for k in versenyzok if k.befejezesSzazalek == 100 and k.kategoria == "Noi")
print(f"4. Feladat: Célbaért női versenyzők: {celbaertNoiVersenyzokSzama}")

bekertNev = input("5. Feladat: Írd be egy versenyző nevét! ")
bekertNevuVersenyzo = next((versenyzo for versenyzo in versenyzok if versenyzo.nev == bekertNev), None)

if bekertNevuVersenyzo is not None:
    print("    Indult egyéniben? Igen")
    teljesitette = "Igen" if bekertNevuVersenyzo.befejezesSzazalek == 100 else "Nem"
    print(f"    Teljesítette a távot? {teljesitette}")
else:
    print("    Indult egyéniben? Nem")

ferfiAtlagIdo = mean(k.idoOraban() for k in versenyzok if k.befejezesSzazalek == 100 and k.kategoria == "Ferfi")
print(f"7. Feladat: Átlagos idő: {ferfiAtlagIdo} óra")

noiGyoztes =   min((k for k in versenyzok if k.befejezesSzazalek == 100 and k.kategoria == "Noi"),   key = lambda k: k.idoOraban())
ferfiGyoztes = min((k for k in versenyzok if k.befejezesSzazalek == 100 and k.kategoria == "Ferfi"), key = lambda k: k.idoOraban())

print(f"Nők: {noiGyoztes.nev} ({noiGyoztes.rajtszam}) - {noiGyoztes.ido}")
print(f"Férfiak: {ferfiGyoztes.nev} ({ferfiGyoztes.rajtszam}) - {ferfiGyoztes.ido}")