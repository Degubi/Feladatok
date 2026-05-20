from itertools import chain

class Utas:

    def __init__(self, line: str, sorszam: int):
        split = line.split(' ')

        self.sorszam = sorszam
        self.ules = int(split[0])
        self.felszallas_km = int(split[1])
        self.leszallas_km = int(split[2])

def get_ar(utas: Utas, ar_per_10km: int):
    tav = utas.leszallas_km - utas.felszallas_km
    utolsoSzamjegy = tav % 10
    tizesek = tav // 10 + (1 if(utolsoSzamjegy == 3 or utolsoSzamjegy == 4 or utolsoSzamjegy == 8 or utolsoSzamjegy == 9) else 0)

    return ar_per_10km * tizesek

def get_ules_status(ules: int, bekert_km: int, utasok: list[Utas]):
    ules_stat = 'üres'

    for utas in utasok:
        if utas.ules == ules and (utas.felszallas_km == bekert_km or utas.leszallas_km == bekert_km):
            ules_stat = f'{utas.sorszam}. utas'
            break

    return str(ules) + '. ülés: ' + ules_stat + '\n'

with open('eladott.txt') as input_file:
    lines = input_file.readlines()
    first_line_split = lines[0].split(' ')
    vonal_hossz = int(first_line_split[1])
    ar_per_10km = int(first_line_split[2])
    utasok = [ Utas(lines[i], i) for i in range(1, len(lines)) ]

utolso = utasok[-1]

print(f'2.Feladat: Utolsó utas ülése: {utolso.ules} utazott távolság: {utolso.leszallas_km - utolso.felszallas_km}')
print('3.Feladat:')

for utas in utasok:
    if (utas.leszallas_km - utas.felszallas_km) == vonal_hossz:
        print(utas.sorszam, end = ' ')

print(f'\n4. Feladat: Összes bevétel: {sum(get_ar(k, ar_per_10km) for k in utasok)}')

utolso_elotti_megallo_km = max(k.leszallas_km for k in utasok if k.leszallas_km != vonal_hossz)
utoljara_felszallok = sum(1 for k in utasok if k.felszallas_km == utolso_elotti_megallo_km)
utoljara_leszallok  = sum(1 for k in utasok if k.leszallas_km == utolso_elotti_megallo_km)

print(f'5.Feladat: Utolsó megállónál felszállók: {utoljara_felszallok}, leszállók: {utoljara_leszallok}')

osszes_megallo = chain((k.leszallas_km for k in utasok), (k.felszallas_km for k in utasok))
megallok_szama = len(set(osszes_megallo)) - 2

print(f'6.Feladat: Megállók száma: {megallok_szama}')

bekert_km = int(input('Írj be 1 km számot!'))
fileba = (get_ules_status(i, bekert_km, utasok) for i in range(1, 49))

with open('kihol.txt', 'w') as output:
    output.writelines(fileba)