package com.example.presensiapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/presensi")
@CrossOrigin(origins = "*")
public class PresensiController {

    @Autowired
    private PresensiRepository repository;

    @Autowired
    private GeoService geoService;

    // GET dengan Pagination (untuk mobile scrolling)
    @GetMapping("/history/{nim}")
    public Page<Presensi> getHistory(
            @PathVariable String nim,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        return repository.findByNimMhs(nim, pageable);
    }

    // POST: Simpan presensi baru
    @PostMapping
    public Presensi savePresensi(@RequestBody Presensi presensi) {
        return repository.save(presensi);
    }

    // PUT: Update presensi
    @PutMapping("/{id}")
    public ResponseEntity<Presensi> updatePresensi(
            @PathVariable Long id,
            @RequestBody Presensi details
    ) {
        return repository.findById(id)
                .map(p -> {
                    p.setStatus(details.getStatus());
                    p.setJamPresensi(details.getJamPresensi());
                    return ResponseEntity.ok(repository.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Tambahkan mapping berikut
    @PostMapping("/locate")
    public String checkLocation(@RequestBody Map<String, Object> request) {
        double lat = Double.parseDouble(request.get("lat").toString());
        double lng = Double.parseDouble(request.get("lng").toString());

        boolean inside = geoService.isInside(lat, lng);
        return inside ? "IN AREA" : "OUT AREA";
    }
}