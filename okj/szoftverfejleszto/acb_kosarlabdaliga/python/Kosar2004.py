from datetime import date
from collections import Counter

class Eredmeny:

    def __init__(self, line: str):
        split = line.split(';')

        self.hazaiCsapat = split[0]
        self.idegenCsapat = split[1]
        self.hazaiPont = int(split[2])
        self.idegenPont = int(split[3])
        self.helyszin = split[4]
        self.idopont = date.fromisoformat(split[5].strip())

with open('eredmenyek.csv') as file:
    lines = file.readlines()
    eredmenyek = [ Eredmeny(lines[i]) for i in range(1, len(lines)) ]

hazaiMadridDb  = sum(1 for k in eredmenyek if k.hazaiCsapat  == 'Real Madrid')
idegenMadridDb = sum(1 for k in eredmenyek if k.idegenCsapat == 'Real Madrid')

print(f'3. Feladat: Hazai: {hazaiMadridDb}, idegen: {idegenMadridDb}')

voltEDontetlen = any(k for k in eredmenyek if k.hazaiPont == k.idegenPont)
print(f'4. Feladat: Volt e döntetlen: {"igen" if voltEDontetlen else "nem"}')

barcelona = next(k for k in eredmenyek if 'Barcelona' in k.hazaiCsapat or 'Barcelona' in k.idegenCsapat)
print(f'5. Feladat: Barcelona csapat neve: {barcelona.hazaiCsapat if "Barcelona" in barcelona.hazaiCsapat else barcelona.idegenCsapat}')
print('6. Feladat:')

hatosFeladatIdopont = date(2004, 11, 21)
for eredmeny in eredmenyek:
    if eredmeny.idopont == hatosFeladatIdopont:
        print(f'    {eredmeny.hazaiCsapat} - {eredmeny.idegenCsapat} ({eredmeny.hazaiPont}:{eredmeny.idegenPont})')

print('7. Feladat:')

stat = Counter(k.helyszin for k in eredmenyek)
for helyszin, db in stat.items():
    if db > 20:
        print(f'    {helyszin}: {db}')