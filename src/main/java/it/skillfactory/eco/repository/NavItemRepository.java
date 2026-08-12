package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.NavItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NavItemRepository extends JpaRepository<NavItem, Long> {
    // Recupera solo gli elementi principali (root) con ordinamento
    List<NavItem> findByParentIsNullOrderByItemOrderAsc();
}