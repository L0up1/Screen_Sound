package com.example.desafioScreenSound.Repository;

import com.example.desafioScreenSound.Model.Artista;
import com.example.desafioScreenSound.Model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeArtistaContainingIgnoreCase(String nomeArtista);
@Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nomeArtista ILIKE %:nome%")
    List<Musica> buscaMusicasPorArtista(String nome);
}
