package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.FooterItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FooterItemRepository extends JpaRepository<FooterItem, Long> {
    List<FooterItem> findByParentIsNullOrderByItemOrderAsc();
}