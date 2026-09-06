package it.skillfactory.eco.scheduler;

import it.skillfactory.eco.model.Booking;
import it.skillfactory.eco.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BookingCleanupScheduler {

    @Autowired
    private BookingRepository bookingRepository;

    // Esegue il controllo ogni giorno a mezzanotte (00:00:00)
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void cleanupOldBookings() {
        LocalDateTime twoYearsAgo = LocalDateTime.now().minusYears(2);
        List<Booking> expiredBookings = bookingRepository.findByCreatedAtBeforeAndAnonymizedFalse(twoYearsAgo);

        for (Booking booking : expiredBookings) {
            booking.setNome("[DATO ELIMINATO]");
            booking.setCognome("[DATO ELIMINATO]");
            booking.setEmail("[DATO ELIMINATO]");
            booking.setTelefono("[DATO ELIMINATO]");
            booking.setCodiceFiscale("[DATO ELIMINATO]");
            booking.setCitta("[DATO ELIMINATO]");
            booking.setOrganizzazione("[DATO ELIMINATO]");
            booking.setRuolo("[DATO ELIMINATO]");
            booking.setNote("[I dati personali sono stati eliminati per scadenza dei 2 anni secondo normativa Privacy/GDPR]");
            booking.setAnonymized(true);
        }

        if (!expiredBookings.isEmpty()) {
            bookingRepository.saveAll(expiredBookings);
            System.out.println("GDPR Cleanup: Anonimizzate " + expiredBookings.size() + " prenotazioni antecedenti a " + twoYearsAgo);
        }
    }
}