use std::fs::File;
use std::io::{ BufReader, BufRead, stdin, Write };

use itertools::Itertools;

struct Utas {
    sorszam: usize,
    ules: i32,
    felszallas_km: i32,
    leszallas_km: i32
}

fn create_utas(line: &str, sorszam: usize) -> Utas {
    let split = line.split(' ').collect::<Vec<_>>();

    Utas {
        sorszam: sorszam,
        ules: split[0].parse::<i32>().unwrap(),
        felszallas_km: split[1].parse::<i32>().unwrap(),
        leszallas_km: split[2].parse::<i32>().unwrap()
    }
}

fn get_ar(utas: &Utas, ar_per_10km: i32) -> i32 {
    let tav = utas.leszallas_km - utas.felszallas_km;
    let utolso_szamjegy = tav % 10;
    let tizesek = tav / 10 + (if utolso_szamjegy == 3 || utolso_szamjegy == 4 || utolso_szamjegy == 8 || utolso_szamjegy == 9 { 1 } else { 0 });

    ar_per_10km * tizesek
}

fn get_ules_status(ules: i32, bekert_km: i32, utasok: &Vec<Utas>) -> String {
    let ules_status = utasok.iter()
                            .filter(|k| k.ules == ules)
                            .find(|k| k.felszallas_km == bekert_km || k.leszallas_km == bekert_km)
                            .map(|k| format!("{}. utas", k.sorszam))
                            .unwrap_or("üres".to_string());

    return format!("{}. ülés: {}", ules, ules_status);
}

fn main() {
    let lines = BufReader::new(File::open("eladott.txt").unwrap())
                         .lines()
                         .map(|k| k.unwrap())
                         .collect::<Vec<_>>();

    let first_line_split = lines[0].split(' ').collect::<Vec<_>>();
    let vonal_hossz = first_line_split[1].parse::<i32>().unwrap();
    let ar_per_10km = first_line_split[2].parse::<i32>().unwrap();
    let utasok = (1 .. lines.len()).map(|i| create_utas(&lines[i], i))
                                   .collect::<Vec<_>>();

    let utolso = &utasok[utasok.len() - 1];

    println!("2.Feladat: Utolsó utas ülése: {} utazott távolság: {}", utolso.ules, utolso.leszallas_km - utolso.felszallas_km);
    println!("3.Feladat:");

    utasok.iter()
          .filter(|k| (k.leszallas_km - k.felszallas_km) == vonal_hossz)
          .for_each(|k| print!("{} ", k.sorszam));

    println!("\n4.Feladat: Összes bevétel: {}", utasok.iter().map(|k| get_ar(k, ar_per_10km)).sum::<i32>());

    let utolso_elotti_megallo_km = utasok.iter()
                                         .map(|k| k.leszallas_km)
                                         .filter(|k| *k != vonal_hossz)
                                         .max()
                                         .unwrap();

    let utoljara_felszallok = utasok.iter().filter(|k| k.felszallas_km == utolso_elotti_megallo_km).count();
    let utoljara_leszallok  = utasok.iter().filter(|k| k.leszallas_km == utolso_elotti_megallo_km).count();

    println!("5.Feladat: Utolsó megállónál felszállók: {}, leszállók: {}", utoljara_felszallok, utoljara_leszallok);

    let megallok_szama = utasok.iter().map(|k| k.leszallas_km)
                               .chain(utasok.iter().map(|k| k.felszallas_km))
                               .unique()
                               .count() - 2;

    println!("6.Feladat: Megállók száma: {}", megallok_szama);
    println!("Írj be 1 km számot!");

    let mut bekert_km_str = String::new();
    stdin().read_line(&mut bekert_km_str);

    let bekert_km = bekert_km_str.trim_end().parse::<i32>().unwrap();
    let fileba = (1 .. 49).map(|i| get_ules_status(i, bekert_km, &utasok)).join("\n");

    write!(File::create("kihol.txt").unwrap(), "{}", fileba);
}