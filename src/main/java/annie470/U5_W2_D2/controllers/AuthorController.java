package annie470.U5_W2_D2.controllers;

import annie470.U5_W2_D2.entities.Author;
import annie470.U5_W2_D2.exceptions.ValidationException;
import annie470.U5_W2_D2.payloads.AuthorDTO;
import annie470.U5_W2_D2.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    //POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Author saveAuthor(@RequestBody @Validated AuthorDTO body, BindingResult validationResult) {
        if(validationResult.hasErrors()) { throw  new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.authorService.saveAuthor(body);
    }

    //GET ALL
    @GetMapping
    public Page<Author> getAll(@RequestParam(defaultValue = "0") int pageN, @RequestParam(defaultValue = "10") int pageSize) {
        return this.authorService.findAll(pageN, pageSize);
    }

   //GET ONE
    @GetMapping("/{id}")
    public Author getById(@PathVariable long id) {
      return this.authorService.getById(id);
    }


    //DELETE ONE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public  void deleteAuthor(@PathVariable long id) {
        this.authorService.findAndDelete(id);
    }

    //PUT
   @PutMapping("/{id}")    public Author modifyAuthor(@PathVariable long id, @RequestBody @Validated AuthorDTO body, BindingResult validationResult) {
       if(validationResult.hasErrors()) { throw  new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
       }
       return this.authorService.getAndUpdate(id, body);
   }
}
