class Dij:
    def __init__(self, line):
        split = line.split(";")

        self.ev = int(split[0])
        self.tipus = split[1]
        self.keresztNev = split[2]
        self.vezetekNev = split[3] if len(split) == 4 else ""

dijak = []
with open("nobel.csv", encoding = "utf-8", mode = "r") as file:
    lines = file.readlines()
    
    for i in range(1, len(lines)):
        dijak.append(Dij(lines[i].strip()))

for dij in dijak:
    if dij.keresztNev == "Arthur B." and dij.vezetekNev == "McDonald":
        print(f"3. Feladat: Arthur {dij.tipus} díjat kapott")
        break

print("4. Feladat:")
for dij in dijak:
    if dij.ev == 2017 and dij.tipus == "irodalmi":
        print(f"Irodalmi díjat kapott: {dij.keresztNev} {dij.vezetekNev}")

print("5. Feladat:")
for dij in dijak:
    if dij.ev >= 1990 and dij.vezetekNev == "":
        print(str(dij.ev) + ": " + dij.keresztNev)

print("6. Feladat:")
for dij in dijak:
    if "Curie" in dij.vezetekNev:
        print(str(dij.ev) + ": " + dij.keresztNev + " " + dij.vezetekNev + ": " + dij.tipus)

print("7.Feladat:")
tipusSzamlalok = {}
for dij in dijak:
    tipusSzamlalok[dij.tipus] = tipusSzamlalok.get(dij.tipus, 0) + 1

for tipus, db in tipusSzamlalok.items():
    print(tipus + ": " + str(db) + " db")

dijak.sort(key = lambda k : k.ev)

with open("orvosi.txt", mode = "w", encoding = "utf-8") as file:
    for dij in dijak:
        if dij.tipus == "orvosi":
            file.write(str(dij.ev) + ":" + dij.keresztNev + " " + dij.vezetekNev + "\n")