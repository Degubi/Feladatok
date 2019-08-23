Érettségi, OKJ és informatikai ismeretek vizsga Programozás megoldások.
Ha valaki hibát talál/bővíteni szeretné nyugodtan lehet issue-kat vagy pull requesteket küldeni.
Java és C# nyelven általában 2 fajta megoldás van(erősen hiányosak lehetnek): Normál imperatív módon ciklusokkal, illetve linq/stream módszerrel.
UI: a C# és a Cpp megoldások hiányozhatnak/out of datek lehetnek

Új java verziókhoz:
* 'var': a 'var' azt jelenti nem érdekel milyen típusú a változó, kitalálja a fordító. (Jó változónévnek amúgy is el kell árulnia micsoda)
* Files.writeString(dataStr, utvonalPath) -> régi verziókban: Files.write(dataStr.getBytes(), utvonalPath)
* Path.of(utvonalStr) -> régi verziókban Paths.get(utvonalStr)

TODO:
* Java megoldásokhoz normál módszereket írni a streames megoldásokhoz
* C# megoldásokat befejezni a C++ megoldásokkal együtt
* Informatika ismeretek emeltet befejezni