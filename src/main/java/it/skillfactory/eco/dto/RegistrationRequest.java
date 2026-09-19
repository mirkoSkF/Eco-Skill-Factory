package it.skillfactory.eco.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Lo username è obbligatorio")
    @Size(
        min = 3,
        max = 50,
        message = "Lo username deve contenere tra 3 e 50 caratteri"
    )
    private String username;

    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Inserisci un indirizzo email valido")
    @Size(
        max = 150,
        message = "L'email non può superare 150 caratteri"
    )
    private String email;

    @NotBlank(message = "Il nome è obbligatorio")
    @Size(
        max = 100,
        message = "Il nome non può superare 100 caratteri"
    )
    private String firstName;

    @NotBlank(message = "Il cognome è obbligatorio")
    @Size(
        max = 100,
        message = "Il cognome non può superare 100 caratteri"
    )
    private String lastName;

    @NotBlank(message = "La password è obbligatoria")
    @Size(
        min = 8,
        max = 100,
        message = "La password deve contenere tra 8 e 100 caratteri"
    )
    private String password;

    @NotBlank(message = "La conferma della password è obbligatoria")
    private String confirmPassword;
}
