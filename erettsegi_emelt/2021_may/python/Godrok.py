with open('melyseg.txt') as file:
    melysegek = [ int(k) for k in file.readlines() ]

print(f'1. Feladat: {len(melysegek)}')

bekert_tavolsag_index = int(input('2. Feladat: Írj be 1 távolságértéket! ')) - 1
melyseg_a_bekert_helyen = melysegek[bekert_tavolsag_index]

print(f'3. Feladat: A felszín {melyseg_a_bekert_helyen}m mélyen van')

erintetlen_felulet_szam = sum(1 for k in melysegek if k == 0)

print('3. Feladat: Érintetlen felszín: %.2f%%' % (erintetlen_felulet_szam / len(melysegek) * 100))

godor_index_e = lambda i: (melysegek[i] == 0 and melysegek[i + 1] != 0) or (melysegek[i] != 0 and melysegek[i + 1] == 0)
godor_kezdo_zaro_indexek = [ i + 1 for i in range(0, len(melysegek) - 1) if godor_index_e(i) ]
godrok = [ melysegek[godor_kezdo_zaro_indexek[i] : godor_kezdo_zaro_indexek[i + 1]] for i in range(0, len(godor_kezdo_zaro_indexek), 2) ]

with open('godrok.txt', 'w') as output:
    for godor in godrok:
        output.write(' '.join(str(k) for k in godor) + '\n')

if melyseg_a_bekert_helyen == 0:
    print('6. Feladat: Az adott helyen nincs gödör')
else:
    keresett_godor_index_e = lambda i: bekert_tavolsag_index >= godor_kezdo_zaro_indexek[i] and bekert_tavolsag_index <= godor_kezdo_zaro_indexek[i + 1]
    bekert_godor_index = next(i for i in range(0, len(godor_kezdo_zaro_indexek), 2) if keresett_godor_index_e(i)) // 2
    bekert_hely_kezdo_godor_tavolsag = godor_kezdo_zaro_indexek[bekert_godor_index] + 1
    bekert_hely_zaro_godor_tavolsag = godor_kezdo_zaro_indexek[bekert_godor_index + 1]

    print(f'    a) Gödör kezdete: {bekert_hely_kezdo_godor_tavolsag}m, vége: {bekert_hely_zaro_godor_tavolsag}m')

    a_godor = godrok[bekert_godor_index]
    legmelyebb_pont_index = max((i for i in range(0, len(a_godor))), key = lambda i: a_godor[i])
    bal_szeltol_legnagyobbig_no = all(a_godor[i] >= a_godor[i + 1] for i in range(0, legmelyebb_pont_index - 1))
    legnagyobbtol_jobb_szelig_csokken = all(a_godor[i] >= a_godor[i + 1] for i in range(legmelyebb_pont_index + 1, len(a_godor) - 1))

    print(f'    b) {"Folyamatosan mélyül" if bal_szeltol_legnagyobbig_no and legnagyobbtol_jobb_szelig_csokken else "Nem mélyül folyamatosan"}')
    print(f'    c) Legnagyobb mélység: {a_godor[legmelyebb_pont_index]}m')

    terfogat = sum(a_godor) * 10
    vizmennyiseg = terfogat - 10 * (bekert_hely_zaro_godor_tavolsag - bekert_hely_kezdo_godor_tavolsag + 1)

    print(f'    d) Térfogat: {terfogat}m^3')
    print(f'    e) Vízmennyiség: {vizmennyiseg}m^3')