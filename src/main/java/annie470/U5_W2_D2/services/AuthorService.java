package annie470.U5_W2_D2.services;

import annie470.U5_W2_D2.entities.Author;
import annie470.U5_W2_D2.exceptions.BadRequestException;
import annie470.U5_W2_D2.exceptions.NotFoundException;
import annie470.U5_W2_D2.payloads.AuthorDTO;
import annie470.U5_W2_D2.repositories.AuthorRepository;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private Cloudinary imageUploader;

    private static final long MAX_SIZE = 5 * 1024 * 1024;
    private static final Set<String> ALLOWED_TYPES = Set.of( //GRAZIE INTERNET
            "image/jpg",
            "image/png"
    );


    //GET ALL
    public Page<Author> findAll(int pageN, int pageSize) {
        Pageable pageable = PageRequest.of(pageN, pageSize);
        return this.authorRepository.findAll(pageable);
    }

//    //POST
    public Author saveAuthor(AuthorDTO payload) {
        //verifico che lo user che sto salvando non abbia la stessa email di uno gia presente in db
        this.authorRepository.findByEmail(payload.email()).ifPresent(author -> {throw new BadRequestException("Email gia in utilizzo!");});
        Author newAuthor = new Author(payload.name(), payload.surname(), payload.email(), payload.dateOfBirth());
        newAuthor.setAvatar("https://ui-avatars.com/api/?name=" + payload.name() + "+" + payload.surname());
        this.authorRepository.save(newAuthor);
        return newAuthor;
    }
//
//    //GET SINGLE
    public Author getById(long id) {
        return this.authorRepository.findById(id).orElseThrow(()-> new NotFoundException("Author non trovato o id incorretto!"));
    }

    //PUT UPDATE
    public Author getAndUpdate( long id, AuthorDTO payload) {
        Author found = this.getById(id);
        this.authorRepository.findByEmail(payload.email())
                .ifPresent(author -> {
                    if(author.getId() != id) {  //E SE NON VOLESSI CAMBIARE EMAIL?
                        throw new BadRequestException("Email già in utilizzo!");
                    }
                });
        found.setName(payload.name());
        found.setSurname(payload.surname());
        found.setEmail(payload.email());
        found.setDateOfBirth(payload.dateOfBirth());
        this.authorRepository.save(found);
        return found;
    }

    //DELETE SINGLE
    public void findAndDelete(long id) {
        Author found = this.getById(id);
        this.authorRepository.delete(found);
    }

        //PATCH AVATAR
        public Author uploadAvatar(MultipartFile file, long id) {
            Author found = this.getById(id);
            if (file.isEmpty()) throw new BadRequestException("File vuoto!");
            if (file.getSize() > MAX_SIZE) throw new BadRequestException("File troppo grande!");
            if (!ALLOWED_TYPES.contains(file.getContentType())) throw new BadRequestException("I formati permessi sono png e jpeg!");

            try {
                Map result = imageUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl= (String) result.get("url");
                found.setAvatar(imageUrl);
            } catch (IOException ex) {
                throw  new RuntimeException(ex);
            }
            this.authorRepository.save(found);
           return found;
        }
}
