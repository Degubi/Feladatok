from statistics import mean
from collections import Counter

class Versenyzo:

    def __init__(self, line: str):
        split = line.split(';')

        self.helyezes = int(split[0])
        self.nev = split[1]
        self.orszag = split[2]
        self.nyeremeny = int(split[3])

with open('snooker.txt') as file:
    lines = file.readlines()
    versenyzok = [ Versenyzo(lines[i]) for i in range(1, len(lines)) ]

print(f'3. Feladat: Versenyzők száma: {len(versenyzok)}')

atlagKereset = mean(k.nyeremeny for k in versenyzok)
print('4. Feladat: Átlag kereset: %.2f' % atlagKereset)

legjobbKinai = max((k for k in versenyzok if k.orszag == 'Kína'), key = lambda k: k.nyeremeny)
print(f'5. Feladat: Legjobban kereső kínai versenyző:\n  Helyezés: {legjobbKinai.helyezes}\n  Név: {legjobbKinai.nev}\n  Nyeremény: {legjobbKinai.nyeremeny * 380} FT')

voltNorveg = any(True for k in versenyzok if k.orszag == 'Norvégia')
print(f'6. Feladat: {"Van" if voltNorveg else "Nincs"} norvég játékos')
print('7. Feladat:')

stat = Counter(k.orszag for k in versenyzok)
for orszag, db in stat.items():
    if db > 4:
        print(f'{orszag}: {db} fő')