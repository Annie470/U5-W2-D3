package annie470.U5_W2_D2.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BlogPost {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private long id;
    private String category;
    private String title;
    private String cover;
    private String content;
    private int timeLecture;

    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;

    public BlogPost(String category, String title, String content, int timeLecture, Author author) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.timeLecture = timeLecture;
        this.author = author;
    }
}
