setwd("/home/degubi/Prog/R")

eredmenyek = read.table("fifa.txt", header = TRUE, sep = ";")

paste("3. Feladat: Csapatok száma:", nrow(eredmenyek))
paste("4. Feladat: Átlagpontszám:", sprintf("%.2f", mean(eredmenyek$Pontszam)))

legtobbetJavito = eredmenyek[which.max(eredmenyek$Valtozas), ]

paste("5. Feladat: Legtöbbet javító csapat:", legtobbetJavito$Csapat, ", helyezés:", legtobbetJavito$Helyezes, ", pontszam:", legtobbetJavito$Pontszam)

if("Magyarország" %in% eredmenyek$Csapat) "6. Feladat: Csapatok között van Magyarország" else "6. Feladat: Csapatok között nincs Magyarország"

valtozasStat = table(eredmenyek$Valtozas)
valtozasStat[valtozasStat > 1]