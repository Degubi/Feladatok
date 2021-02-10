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

versenyzok = []
with open("ub2017egyeni.txt", "r") as file:
    lines = file.readlines()

    for i in range(1, len(lines)):
        versenyzok.append(Versenyzo(lines[i]))

print(f"3. Feladat: Egyéni indulók: {len(versenyzok)}")

celbaertNoiVersenyzok = 0
for versenyzo in versenyzok:
    if versenyzo.befejezesSzazalek == 100 and versenyzo.kategoria == "Noi":
        celbaertNoiVersenyzok += 1

print(f"4. Feladat: Célbaért női versenyzők: {celbaertNoiVersenyzok}")
bekertNev = input("5. Feladat: Írd be egy versenyző nevét!")
indultEBekert = False
belertSzazaleka = -1

for versenyzo in versenyzok:
    if versenyzo.nev == bekertNev:
        indultEBekert = True
        belertSzazaleka = versenyzo.befejezesSzazalek
        break

if indultEBekert:
    print("Indult egyéniben? Igen")
    teljesitette = "Igen" if belertSzazaleka == 100 else "Nem"
    print(f"Teljesítette a távot? {teljesitette}")
else:
    print("Indult egyéniben? Nem")

ferfiAtlagIdo = 0.0
ferfiakSzama = 0

for versenyzo in versenyzok:
    if versenyzo.befejezesSzazalek == 100 and versenyzo.kategoria == "Ferfi":
        ferfiakSzama += 1
        ferfiAtlagIdo += versenyzo.idoOraban()

print(f"7. Feladat: Átlagos idő: {ferfiAtlagIdo / ferfiakSzama}")

ferfiGyoztes = versenyzok[0]
noiGyoztes = versenyzok[0]

for versenyzo in versenyzok:
    if versenyzo.befejezesSzazalek == 100:
        if versenyzo.kategoria == "Noi":
            if versenyzo.idoOraban() < noiGyoztes.idoOraban():
                noiGyoztes = versenyzo
        else:
            if versenyzo.idoOraban() < ferfiGyoztes.idoOraban():
                ferfiGyoztes = versenyzo

print(f"Nők: {noiGyoztes.nev} ({noiGyoztes.rajtszam}) - {noiGyoztes.ido}")
print(f"Férfiak: {ferfiGyoztes.nev} ({ferfiGyoztes.rajtszam}) - {ferfiGyoztes.ido}")