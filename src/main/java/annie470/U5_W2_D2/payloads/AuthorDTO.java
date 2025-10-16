package annie470.U5_W2_D2.payloads;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record AuthorDTO (
    @NotBlank
     String name,
    @NotBlank
    String surname,
     @Email
     String email,
     String dateOfBirth) {}
