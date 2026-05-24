import random

alkalmak_szama = int(input('Hány alkalommal legyen feldobás? '))
korok = []

while len(korok) != alkalmak_szama:
    elso = random.randint(1, 6)
    masodik = random.randint(1, 6)
    harmadik = random.randint(1, 6)

    dobasok = [ elso, masodik, harmadik, elso + masodik + harmadik ]

    if not dobasok in korok:
        korok.append(dobasok)

anni_nyert_korok = 0
panni_nyert_korok = 0

for kor in korok:
    anni_nyert = kor[3] < 10

    print(f'Dobás: {kor[0]} + {kor[1]} + {kor[2]} = {kor[3]}, nyert: {"Anni" if kor[3] < 10 else "Panni"}')

    if anni_nyert:
        anni_nyert_korok += 1
    else:
        panni_nyert_korok += 1

print(f'A játékok során {anni_nyert_korok} alkalommal nyert Anni, {panni_nyert_korok} alkalommal pedig Panni')
