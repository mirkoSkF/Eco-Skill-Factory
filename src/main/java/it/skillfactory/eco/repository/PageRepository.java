package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    Optional<Page> findBySlug(String slug);
    boolean existsBySlug(String slug);
    boolean existsBySlugAndIdNot(String slug, Long id);
}