setwd("/home/degubi/Prog/R")

dolgozok = read.table("berek2020.txt", header = TRUE, sep = ";")

paste("3. Feladat: Dolgozók száma:", nrow(dolgozok))
paste("4. Feladat: Átlagbér:", sprintf("%.2f", mean(dolgozok$Bér) / 1000))

bekertReszleg = readline("5. Feladat: Írjon be 1 részleg nevet! ")
bekertReszlegDolgozok = dolgozok[dolgozok$Részleg == bekertReszleg, ]

if(nrow(bekertReszlegDolgozok) == 0) {
  paste("6. Feladat: A megadott részleg nem létezik a cégnél!")
}else{
  maxBerDolgozo = bekertReszlegDolgozok[which.max(bekertReszlegDolgozok$Bér), ]

  paste("6. Feladat:", maxBerDolgozo$Név, "(", maxBerDolgozo$Belépés, ") :", maxBerDolgozo$Bér, "FT")
}

paste("7. Feladat:")
table(dolgozok$Részleg)