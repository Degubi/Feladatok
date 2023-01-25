from collections import Counter
from datetime import date

class Eredmeny:

    def __init__(self, line: str):
        split = line.split(';')

        self.datum = date.fromisoformat(split[0])
        self.dijNev = split[1]
        self.helyezes = int(split[2])
        self.befejezettKorok = int(split[3])
        self.szerzettPontok = int(split[4])
        self.csapat = split[5]
        self.vegeredmenyStatusz = split[6].strip()

with open('schumacher.csv') as file:
    lines = file.readlines()
    eredmenyek = [ Eredmeny(lines[i]) for i in range(1, len(lines)) ]

print(f'3. Feladat: Adatsorok száma: {len(eredmenyek)}')
print('4. Feladat:')

for eredmeny in eredmenyek:
    if eredmeny.dijNev == 'Hungarian Grand Prix' and eredmeny.helyezes != 0:
        print(f'    {eredmeny.datum}: {eredmeny.helyezes}. hely')

print('5. Feladat:')

celbaeres_stat = Counter(k.vegeredmenyStatusz for k in eredmenyek if k.helyezes == 0)

for statusz, dbSzam in celbaeres_stat.items():
    if dbSzam > 2:
        print(f'    {statusz}: {dbSzam}')