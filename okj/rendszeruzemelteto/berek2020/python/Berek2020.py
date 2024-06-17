from statistics import mean
from collections import Counter

class Dolgozo:

    def __init__(self, line: str):
        split = line.split(';')

        self.nev = split[0]
        self.nem = split[1]
        self.munkaReszleg = split[2]
        self.munkabaLepesEv = int(split[3])
        self.munkaBer = int(split[4])

with open('berek2020.txt', encoding = 'UTF-8') as file:
    dolgozok = [ Dolgozo(k) for k in file.readlines()[1:] ]

print(f'3. Feladat: Dolgozók száma: {len(dolgozok)}')
print('4. Feladat: Átlagbér: %.2f' % (mean(k.munkaBer for k in dolgozok) / 1000))

bekert_reszleg = input('5. Feladat: Írjon be 1 részleg nevet!')
legtobb_munkaberes = max((k for k in dolgozok if k.munkaReszleg == bekert_reszleg), key = lambda k: k.munkaBer, default = None)

if legtobb_munkaberes == None:
    print('A megadott részleg nem létezik a cégnél!')
else:
    print(f'6. Feladat: {legtobb_munkaberes.nev} ({legtobb_munkaberes.munkabaLepesEv}) : {legtobb_munkaberes.munkaBer} FT')

print('7. Feladat:')

for reszleg, db in Counter(k.munkaReszleg for k in dolgozok).items():
    print(f'    {reszleg} - {db} fő')