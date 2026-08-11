package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.BlockItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockItemRepository extends JpaRepository<BlockItem, Long> {
}