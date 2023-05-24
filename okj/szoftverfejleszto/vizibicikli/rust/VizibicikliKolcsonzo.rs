use std::fs::File;
use std::io::{ BufReader, BufRead, stdin, Write };
use chrono::NaiveTime;

use itertools::Itertools;

struct Kolcsonzes {
    nev: String,
    jarmu_azonosito: String,
    elvitel_idopont: NaiveTime,
    visszahozatal_idopont: NaiveTime
}

fn create_kolcsonzes(line: &String) -> Kolcsonzes {
    let split = line.split(';').collect::<Vec<_>>();

    Kolcsonzes {
        nev: split[0].to_string(),
        jarmu_azonosito: split[1].to_string(),
        elvitel_idopont: NaiveTime::from_hms(split[2].parse::<u32>().unwrap(), split[3].parse::<u32>().unwrap(), 0),
        visszahozatal_idopont: NaiveTime::from_hms(split[4].parse::<u32>().unwrap(), split[5].parse::<u32>().unwrap(), 0)
    }
}

fn main() {
    let kolcsonzesek = BufReader::new(File::open("kolcsonzesek.txt").unwrap())
                                .lines()
                                .skip(1)
                                .map(|k| create_kolcsonzes(&k.unwrap()))
                                .collect::<Vec<_>>();

    println!("5. Feladat: Kölcsönzések száma: {}", kolcsonzesek.len());
    println!("Írj be 1 nevet!");

    let mut bekert_nev_str = String::new();
    stdin().read_line(&mut bekert_nev_str);

    let bekert_nev = bekert_nev_str.trim_end();
    let bekert_nevhez_tartozo_kolcsonzesek = kolcsonzesek.iter()
                                                         .filter(|k| k.nev == bekert_nev)
                                                         .collect::<Vec<_>>();

    let bekerthez_kiirando = if bekert_nevhez_tartozo_kolcsonzesek.len() == 0 { "Nem volt ilyen nevű kölcsönző".to_string() }
                             else { bekert_nevhez_tartozo_kolcsonzesek.iter()
                                                                      .map(|k| format!("{}-{}", k.elvitel_idopont, k.visszahozatal_idopont))
                                                                      .join("\n")
                                  };

    println!("{}", bekerthez_kiirando);
    println!("Adj meg 1 időpontot! (óra:perc)");

    let mut bekert_idopont_str = String::new();
    stdin().read_line(&mut bekert_idopont_str);

    let bekert_idopont_parts = bekert_idopont_str.trim_end()
                                                 .split(':')
                                                 .collect::<Vec<_>>();

    let bekert_idopont = NaiveTime::from_hms(bekert_idopont_parts[0].parse::<u32>().unwrap(), bekert_idopont_parts[1].parse::<u32>().unwrap(), 0);

    println!("7. Feladat:");

    kolcsonzesek.iter()
                .filter(|k| bekert_idopont > k.elvitel_idopont && bekert_idopont < k.visszahozatal_idopont)
                .for_each(|k| println!("    {}-{}: {}", k.elvitel_idopont, k.visszahozatal_idopont, k.nev));

    let total_started_hours = kolcsonzesek.iter()
                                          .map(|k| (k.visszahozatal_idopont - k.elvitel_idopont).num_minutes() as f32)
                                          .map(|k| (k / 30.0).ceil() as i32)
                                          .sum::<i32>();

    println!("8. Feladat: A bevétel: {}Ft", total_started_hours * 2400);

    let fileba = kolcsonzesek.iter()
                             .filter(|k| k.jarmu_azonosito == "F")
                             .map(|k| format!("{}-{}: {}", k.elvitel_idopont, k.visszahozatal_idopont, k.nev))
                             .join("\n");

    write!(File::create("F.txt").unwrap(), "{}", fileba);
    println!("10. Feladat:");

    kolcsonzesek.iter()
                .sorted_by_key(|k| &k.jarmu_azonosito)
                .group_by(|k| &k.jarmu_azonosito)
                .into_iter()
                .map(|(azonosito, kolcsonzesek)| (azonosito, kolcsonzesek.count()))
                .for_each(|(azonosito, kolcsonzesek_szama)| println!("    {}-{}", azonosito, kolcsonzesek_szama));
}