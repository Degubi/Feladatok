from collections import Counter

class Dij:
    def __init__(self, line: str):
        split = line.split(';')

        self.ev = int(split[0])
        self.tipus = split[1]
        self.keresztNev = split[2]
        self.vezetekNev = split[3] if len(split) == 4 else ''


with open('nobel.csv', encoding = 'utf-8', mode = 'r') as file:
    dijak = [ Dij(k.strip()) for k in file.readlines()[1:] ]

for dij in dijak:
    if dij.keresztNev == 'Arthur B.' and dij.vezetekNev == 'McDonald':
        print(f'3. Feladat: Arthur {dij.tipus} díjat kapott')
        break

print('4. Feladat:')
for dij in dijak:
    if dij.ev == 2017 and dij.tipus == 'irodalmi':
        print(f'Irodalmi díjat kapott: {dij.keresztNev} {dij.vezetekNev}')

print('5. Feladat:')
for dij in dijak:
    if dij.ev >= 1990 and dij.vezetekNev == '':
        print(f'{dij.ev}: {dij.keresztNev}')

print('6. Feladat:')
for dij in dijak:
    if 'Curie' in dij.vezetekNev:
        print(f'{dij.ev}: {dij.keresztNev} {dij.vezetekNev}: {dij.tipus}')

print('7.Feladat:')

for tipus, db in Counter(k.tipus for k in dijak).items():
    print(f'{tipus}: {db} db')

dijak.sort(key = lambda k: k.ev)

with open('orvosi.txt', mode = 'w', encoding = 'utf-8') as file:
    for dij in dijak:
        if dij.tipus == 'orvosi':
            file.write(f'{dij.ev}: {dij.keresztNev} {dij.vezetekNev}\n')