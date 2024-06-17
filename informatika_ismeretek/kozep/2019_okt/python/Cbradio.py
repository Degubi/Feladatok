class Bejegyzes:

    def __init__(self, line: str):
        split = line.split(';')

        self.ora = int(split[0])
        self.perc = int(split[1])
        self.adasok = int(split[2])
        self.nev = split[3].strip()

def atszamolPercre(ora: int, perc: int):
    return ora * 60 + perc


with open('cb.txt', 'r') as file:
    bejegyzesek = [ Bejegyzes(k) for k in file.readlines()[1:] ]

print(f'3. Feladat: Bejegyzések száma: {len(bejegyzesek)}')

voltE4Adasos = any(True for k in bejegyzesek if k.adasok == 4)
print(f'4. Feladat: {"Volt" if voltE4Adasos else "Nem volt"} 4 adást indító sofőr')

bekertNev = input('5. Feladat: Írj be egy nevet! ')
bekertHasznalatok = sum(k.adasok for k in bejegyzesek if k.nev == bekertNev)

if bekertHasznalatok > 0:
    print(f'{bekertNev} {bekertHasznalatok}x használta a rádiót')
else:
    print('Nincs ilyen nevű sofőr!')

with open('cb2.txt', 'w') as file:
    file.write('Kezdes;Nev;AdasDb\n')

    for bejegyzes in bejegyzesek:
        file.write(f'{atszamolPercre(bejegyzes.ora, bejegyzes.perc)};{bejegyzes.nev};{bejegyzes.adasok}\n')

soforokAdasszamokkal = {}
for bejegyzes in bejegyzesek:
    soforNeve = bejegyzes.nev

    soforokAdasszamokkal[soforNeve] = soforokAdasszamokkal.get(soforNeve, 0) + bejegyzes.adasok

legtobbAdasBejegyzes = max((k for k in soforokAdasszamokkal.items()), key = lambda k: k[1])

print(f'9. Feladat: Legtöbb adást indító sofőr: {legtobbAdasBejegyzes[0]}, adások: {legtobbAdasBejegyzes[1]}')