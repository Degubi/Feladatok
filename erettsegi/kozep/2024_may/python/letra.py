from dataclasses import dataclass

@dataclass
class LepesAllapot:
    dobas_i: int
    ertek: int
    visszalepes: bool

with open('dobasok.txt') as dobasok_input:
    dobasok = [ int(k) for k in dobasok_input.readline().strip().split(', ') ]

lepesek = []
for dobas in dobasok:
    elozo_lepes = LepesAllapot(0, 0, False) if len(lepesek) == 0 else lepesek[-1]
    osszeg = elozo_lepes.ertek + dobas
    visszalepes = osszeg % 10 == 0

    lepesek.append(LepesAllapot(elozo_lepes.dobas_i + 1, osszeg - 3 if visszalepes else osszeg, visszalepes))

print('2. Feladat: ')

for lepes in lepesek:
    print(f'{lepes.ertek} ', end = '')

print()

visszalepesek_szama = sum(1 if k.visszalepes else 0 for k in lepesek)

print(f'3. Feladat: A játék során {visszalepesek_szama} alkalommal lépett létrára')
print(f'4. Feladat: {"A játékot befejezte" if lepesek[-1].ertek > 45 else "A játékot abbahagyta"}')
