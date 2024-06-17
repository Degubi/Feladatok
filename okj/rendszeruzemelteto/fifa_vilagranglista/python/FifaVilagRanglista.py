from statistics import mean
from collections import Counter

class Eredmeny:
    def __init__(self, line: str):
        split = line.split(';')

        self.csapat = split[0]
        self.helyezes = int(split[1])
        self.valtozas = int(split[2])
        self.pontszam = int(split[3])


with open('fifa.txt') as file:
    eredmenyek = [ Eredmeny(k) for k in file.readlines()[1:] ]

print(f'3. Feladat: Csapatok száma: {len(eredmenyek)}')

atlagPontszam = mean(k.pontszam for k in eredmenyek)
print('4. Feladat: Átlagpontszám: %.2f' % atlagPontszam)

legtobbetJavitoCsapat = max(eredmenyek, key = lambda k: k.valtozas)
print(f'5. Feladat: Legtöbbet javító csapat: {legtobbetJavitoCsapat.csapat}, helyezés: {legtobbetJavitoCsapat.helyezes}, pontszam: {legtobbetJavitoCsapat.pontszam}')

voltEMo = any(True for k in eredmenyek if k.csapat == 'Magyarország')
print('6. Feladat: Csapatok között van Magyarország' if voltEMo else '6. Feladat: Csapatok között nincs Magyarország')
print('7. Feladat:')

for valtozas, db in Counter(k.valtozas for k in eredmenyek).items():
    if db > 1:
        print(f'    {valtozas} helyet változott: {db} csapat')