package annie470.U5_W2_D2.services;

import annie470.U5_W2_D2.entities.Author;
import annie470.U5_W2_D2.entities.BlogPost;
import annie470.U5_W2_D2.exceptions.NotFoundException;
import annie470.U5_W2_D2.payloads.BlogPostUpdatePayload;
import annie470.U5_W2_D2.payloads.NewBlogPostPayload;
import annie470.U5_W2_D2.repositories.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class BlogPostsService {
    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private AuthorService authorService;


    //GET ALL
    public List<BlogPost> findAll() {
        return this.blogPostRepository.findAll();
    }

    //POST
    public BlogPost saveBlogPost(NewBlogPostPayload payload) {
        Author author = this.authorService.getById(payload.getAuthorId());
        BlogPost newPost = new BlogPost(payload.getCategory(), payload.getTitle(), payload.getContent(), payload.getTimeLecture(), author);
        newPost.setCover("http://picsum.photos/200/300");
        this.blogPostRepository.save(newPost);
        return  newPost;
    }

    //GET SINGLE
    public BlogPost findPostById(long id) {
       return this.blogPostRepository.findById(id).orElseThrow(()-> new NotFoundException("Id post incorretto!"));
    }

    //PUT UPDATE
    public BlogPost findAndUpdate(long id, BlogPostUpdatePayload payload){
        BlogPost found = this.findPostById(id);
        found.setCategory(payload.getCategory());
        found.setTitle(payload.getTitle());
        found.setContent(payload.getContent());
        found.setTimeLecture(payload.getTimeLecture());
        this.blogPostRepository.save(found);
        return found;
    }

    //DELETE
    public void findAndDelete(long id) {
        BlogPost found = this.findPostById(id);
        this.blogPostRepository.delete(found);
    }
}
