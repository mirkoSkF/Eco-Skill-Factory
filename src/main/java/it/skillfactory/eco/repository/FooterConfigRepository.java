package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.FooterConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FooterConfigRepository extends JpaRepository<FooterConfig, Long> {
}