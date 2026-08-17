package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.NavbarSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NavbarSettingsRepository extends JpaRepository<NavbarSettings, Long> {
}