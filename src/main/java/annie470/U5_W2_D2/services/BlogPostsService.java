package annie470.U5_W2_D2.services;

import annie470.U5_W2_D2.entities.Author;
import annie470.U5_W2_D2.entities.BlogPost;
import annie470.U5_W2_D2.exceptions.NotFoundException;
import annie470.U5_W2_D2.payloads.BlogPostUpdateDTO;
import annie470.U5_W2_D2.payloads.NewBlogPostDTO;
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
    public BlogPost saveBlogPost(NewBlogPostDTO payload) {
        Author author = this.authorService.getById(payload.authorId());
        BlogPost newPost = new BlogPost(payload.category(), payload.title(), payload.content(), payload.timeLecture(), author);
        newPost.setCover("http://picsum.photos/200/300");
        this.blogPostRepository.save(newPost);
        return  newPost;
    }

    //GET SINGLE
    public BlogPost findPostById(long id) {
       return this.blogPostRepository.findById(id).orElseThrow(()-> new NotFoundException("Id post incorretto!"));
    }

    //PUT UPDATE
    public BlogPost findAndUpdate(long id, BlogPostUpdateDTO payload){
        BlogPost found = this.findPostById(id);
        found.setCategory(payload.category());
        found.setTitle(payload.title());
        found.setContent(payload.content());
        found.setTimeLecture(payload.timeLecture());
        this.blogPostRepository.save(found);
        return found;
    }

    //DELETE
    public void findAndDelete(long id) {
        BlogPost found = this.findPostById(id);
        this.blogPostRepository.delete(found);
    }
}
