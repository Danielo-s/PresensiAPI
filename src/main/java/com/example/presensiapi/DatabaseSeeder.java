package com.example.presensiapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private PresensiRepository repository;

    @Override
    public void run(String... args) throws Exception {

        // Cek apakah tabel presensi masih kosong
        if (repository.count() == 0) {
            System.out.println("Tabel Presensi kosong, sedang meng-generate 200 data dummy...");

            List<Presensi> dummyDataList = new ArrayList<>();
            Random random = new Random();

            // Daftar mata kuliah
            String[] namaMk = {"Mobile Programming", "Web Programming", "Database System", "Computer Network", "Software Engineering"};
            String[] kodeMk = {"TRPL205", "TRPL201", "TRPL203", "TRPL204", "TRPL202"};

            // Nama dosen
            String[] dosenList = {"Bpk. Budi", "Ibu Rina", "Bpk. Andi", "Ibu Siti", "Bpk. Joko"};

            // Status
            String[] statusOps = {"Present", "Present", "Present", "Present", "Present", "Absent", "Late"};

            // Tanggal mulai
            LocalDate startDate = LocalDate.of(2026, 2, 1);

            for (int i = 1; i <= 200; i++) {
                Presensi p = new Presensi();

                // Pilih MK random
                int mkIndex = random.nextInt(namaMk.length);
                p.setKodeMk(kodeMk[mkIndex]);
                p.setCourse(namaMk[mkIndex]);

                // Tanggal acak
                LocalDate date = startDate.plusDays(random.nextInt(90));
                p.setDate(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                // Jam acak
                LocalTime time = LocalTime.of(random.nextInt(6) + 8, random.nextInt(60));
                p.setJamPresensi(time.format(DateTimeFormatter.ofPattern("HH:mm:ss")));

                // Pertemuan
                p.setPertemuanKe((i % 14) + 1);

                // Status
                p.setStatus(statusOps[random.nextInt(statusOps.length)]);

                // NIM (fix)
                p.setNimMhs("0325260031");

                // Ruangan
                p.setRuangan("Lab Komputer " + (random.nextInt(3) + 1));

                // Dosen
                p.setDosenPengampu(dosenList[mkIndex]);

                dummyDataList.add(p);
            }
            repository.saveAll(dummyDataList);
            System.out.println("Berhasil menyimpan 200 data dummy ke database SQLite!");
        } else {
            System.out.println("Data presensi sudah ada, lewati proses seeding.");
        }
    }
}