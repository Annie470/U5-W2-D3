package annie470.U5_W2_D2.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public record BlogPostUpdateDTO (
     String category,
     String title,
     String content,
     Integer timeLecture){
}