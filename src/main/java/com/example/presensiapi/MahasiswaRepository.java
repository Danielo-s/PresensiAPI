package com.example.presensiapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MahasiswaRepository extends JpaRepository<Mahasiswa, String> {
    // Kosong saja, Spring Boot otomatis membuat method CRUD
}