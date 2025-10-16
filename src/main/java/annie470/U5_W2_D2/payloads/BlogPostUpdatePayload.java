package annie470.U5_W2_D2.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class BlogPostUpdatePayload {
    private String category;
    private String title;
    private String content;
    private Integer timeLecture;
}