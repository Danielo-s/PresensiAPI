package com.example.presensiapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/mahasiswa")
@CrossOrigin(origins = "*")
public class MahasiswaController {
    @Autowired
    private MahasiswaRepository repository;

    // GET: Ambil data mahasiswa
    @GetMapping("/{nim}")
    public ResponseEntity<Mahasiswa> getMahasiswa(@PathVariable String nim) {
        return repository.findById(nim)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/upload")
    public ResponseEntity<?> uploadProfile(
        @RequestParam("nim") String nim,
        @RequestParam("nama") String nama,
        @RequestParam("foto") MultipartFile file) throws IOException {
             Mahasiswa mhs = repository.findById(nim).orElse(new Mahasiswa());
             mhs.setNimMhs(nim);
             mhs.setNamaMhs(nama);
             mhs.setFotoMhs(file.getBytes());

             repository.save(mhs);
             return ResponseEntity.ok("Data berhasil diperbarui");
        }
    
}
