from collections import Counter

parancsok = input('Kérem a robot parancsait: ')
betu_statok = Counter(parancsok)

for betu, db in betu_statok.items():
    print(f'{betu} betűk száma: {db}')

vegso_x = 0
vegso_y = 0
for parancs in parancsok:
    vegso_x = vegso_x - 1 if parancs == 'N' else vegso_x + 1 if parancs == 'K' else vegso_x
    vegso_y = vegso_y + 1 if parancs == 'E' else vegso_y - 1 if parancs == 'D' else vegso_y

x_parancs_betu = 'K' if vegso_x > 0 else 'N'
egyszerusitett_x_parancsok = x_parancs_betu * abs(vegso_x)
y_parancs_betu = 'E' if vegso_y > 0 else 'D'
egyszerusitett_y_parancsok = y_parancs_betu * abs(vegso_y)

print(f'Egy legrövidebb út parancsszava: {egyszerusitett_x_parancsok}{egyszerusitett_y_parancsok}')
