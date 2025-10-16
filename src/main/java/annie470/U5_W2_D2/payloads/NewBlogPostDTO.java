package annie470.U5_W2_D2.payloads;

import annie470.U5_W2_D2.entities.Author;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public record NewBlogPostDTO(
        @NotBlank
     String category,
     @NotBlank
     String title,
     @Size(max=35)
     String content,
     Integer timeLecture,
     @NotNull
     long authorId) {
}
