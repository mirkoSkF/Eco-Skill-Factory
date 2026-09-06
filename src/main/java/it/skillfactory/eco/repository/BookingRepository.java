package it.skillfactory.eco.repository;

import it.skillfactory.eco.model.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE " +
           "(:search IS NULL OR LOWER(b.nome) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.cognome) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.email) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.courseName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(b.organizzazione) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Booking> searchBookings(@Param("search") String search, Pageable pageable);

    List<Booking> findByCreatedAtBeforeAndAnonymizedFalse(LocalDateTime cutoffDate);
}