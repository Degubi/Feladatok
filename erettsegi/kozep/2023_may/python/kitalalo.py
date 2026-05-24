import random

with open('szavak.txt') as szavak_input:
    szavak = szavak_input.readline().strip().replace('"', '').split(', ')

kivalasztott_szo = szavak[random.randint(0, len(szavak) - 1)]
probak_szama = 1

while True:
    tipp = input('Kérem a tippet: ')

    if tipp == 'stop':
        break
    else:
        print('Az eredmény: ', end = '')

        eltalalt_karakterek = 0
        for i in range(0, 6):
            eredeti_karakter = kivalasztott_szo[i]
            eltalalta_e = eredeti_karakter == tipp[i]

            print(eredeti_karakter if eltalalta_e else '.', end = '')

            if eltalalta_e:
                eltalalt_karakterek += 1

        print()

        if eltalalt_karakterek == 6:
            print(f'\n {probak_szama} tippeléssel sikerült kitalálni')
            break;
        else:
            probak_szama += 1
