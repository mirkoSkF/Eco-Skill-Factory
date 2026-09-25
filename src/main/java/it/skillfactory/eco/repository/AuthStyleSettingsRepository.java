package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.AuthStyleSettings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthStyleSettingsRepository
        extends JpaRepository<AuthStyleSettings, Long> {

}