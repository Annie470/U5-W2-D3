package annie470.U5_W2_D2.controllers;

import annie470.U5_W2_D2.entities.BlogPost;
import annie470.U5_W2_D2.exceptions.ValidationException;
import annie470.U5_W2_D2.payloads.BlogPostUpdateDTO;
import annie470.U5_W2_D2.payloads.NewBlogPostDTO;
import annie470.U5_W2_D2.services.BlogPostsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class BlogPostController {
    @Autowired
    private BlogPostsService blogPostsService;

    //GET ALL
    @GetMapping
    public List<BlogPost> getPosts() {
       return this.blogPostsService.findAll();
    }

    //GET SINGLE
    @GetMapping("/{id}")
    public BlogPost getPostById(@PathVariable long id){
        return this.blogPostsService.findPostById(id);
    }

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogPost createBlogPost(@RequestBody NewBlogPostDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) { throw  new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.blogPostsService.saveBlogPost(body);
    }

    //PUT
    @PutMapping("/{id}")
    public  BlogPost getAndUpdate(@PathVariable long id,  @RequestBody BlogPostUpdateDTO body) {
        return this.blogPostsService.findAndUpdate(id, body);
    }


    // DELETE SINGLE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getByIdAndDelete(@PathVariable long id) {
        this.blogPostsService.findAndDelete(id);
    }
}
