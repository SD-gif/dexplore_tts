package com.example.echo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import com.example.echo.Data.Artwork;

public interface ArtworkRepository extends JpaRepository<Artwork, Long> {
    Optional<Artwork> findByName(String name);
}
