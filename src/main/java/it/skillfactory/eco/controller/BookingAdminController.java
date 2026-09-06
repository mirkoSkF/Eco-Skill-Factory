package it.skillfactory.eco.controller;

import it.skillfactory.eco.model.Booking;
import it.skillfactory.eco.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/bookings")
public class BookingAdminController {

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping
    public String listBookings(
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            Model model) {

        int currentPage = Math.max(1, page);
        String searchParam = (search != null && !search.trim().isEmpty()) ? search.trim() : null;

        PageRequest pageRequest = PageRequest.of(currentPage - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Booking> bookingPage = bookingRepository.searchBookings(searchParam, pageRequest);

        model.addAttribute("bookings", bookingPage.getContent());
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", Math.max(1, bookingPage.getTotalPages()));
        model.addAttribute("totalItems", bookingPage.getTotalElements());
        model.addAttribute("pageSize", size);
        model.addAttribute("search", searchParam != null ? searchParam : "");
        model.addAttribute("activeMenu", "bookings");

        return "admin/tab-prenotazioni";
    }

    @GetMapping("/delete/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingRepository.deleteById(id);
        return "redirect:/admin/bookings";
    }
}