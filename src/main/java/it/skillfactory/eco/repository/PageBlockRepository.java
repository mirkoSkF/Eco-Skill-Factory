package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.PageBlock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageBlockRepository extends JpaRepository<PageBlock, Long> {

    // Recupera tutte le righe ordinate in sequenza per il rendering dell'Index
    List<PageBlock> findAllByOrderByPositionAsc();
}