import random

folyo = [ 0 for i in range(20) ]
hajo_kezdet = random.randrange(0, 20)
folyo[hajo_kezdet]     = 1
folyo[hajo_kezdet + 1] = 1
folyo[hajo_kezdet + 2] = 1
folyo[hajo_kezdet + 3] = 1

while True:
    loves_helye = int(input('Kérem a lövés helyét (1-20): ')) - 1

    if folyo[loves_helye] == 0:
        print('Nem talált')
    else:
        print('Talált')
        folyo[loves_helye] = 2

    if folyo[hajo_kezdet] == 2 and folyo[hajo_kezdet + 1] == 2 and folyo[hajo_kezdet + 2] == 2 and folyo[hajo_kezdet + 3] == 2:
        print('Süllyedt')
        break
