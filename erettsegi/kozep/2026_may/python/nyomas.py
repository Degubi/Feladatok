with open('nyomas.txt') as nyomas_input:
    meresek = [ int(k) for k in nyomas_input.read().strip().split(', ') ]

legkisebb_index = min((i for i in range(0, len(meresek))), key = lambda i: meresek[i])

print(f'A legkisebb mért érték: {meresek[legkisebb_index]}, sorszáma: {legkisebb_index + 1}')

bekert_ertek = int(input('Minél kisebb értékeket keres? (egész szám) '))
kisebb_ertekek_szama = sum(1 for k in meresek if k < bekert_ertek)

print(f'{bekert_ertek} alatti mérések száma: {kisebb_ertekek_szama}')

legnagyobb_csokkenes = max(k - l for k, l in zip(meresek[:-1], meresek[1:]))

print(f'A két mérés közötti legnagyobb csökkenés: {legnagyobb_csokkenes}')
