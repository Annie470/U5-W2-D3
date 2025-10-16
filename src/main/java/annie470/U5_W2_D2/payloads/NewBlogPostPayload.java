package annie470.U5_W2_D2.payloads;

import annie470.U5_W2_D2.entities.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class NewBlogPostPayload {
    private String category;
    private String title;
    private String content;
    private Integer timeLecture;
    private long authorId;
}
