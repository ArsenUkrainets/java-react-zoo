package app.controllers;

import app.dto.Animal;
import app.dto.UploadImageDto;
import app.entities.AnimalEntity;
import app.mapper.AnimalMapper;
import app.repositories.AnimalRepository;
import app.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HomeController {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;
    private final StorageService storageService;

    @Autowired
    public HomeController(AnimalRepository animalRepository,
                          AnimalMapper animalMapper, StorageService storageService) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
        this.storageService=storageService;
    }

    @GetMapping("/") //incoming request method type. @GetMapping is used to handle GET type of request method
    public List<AnimalEntity> index() {
        return animalRepository.findAll();
    }

    @PostMapping("/create") // @PostMapping is used to handle POST type of request method // create post
    public String add(Animal animal)
    {
        AnimalEntity animalEntity =  animalMapper.AnimalToAnimalEntity(animal);
        animalRepository.save(animalEntity); //save new animal to Repository
        return "Додано";
    }

    @PostMapping("/upload") // upload post
    public String upload(@RequestBody UploadImageDto dto) {
        String image = storageService.save(dto.getBase64()); //saving image from dto.Base64
        return image;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("сало.jpg", StandardCharsets.UTF_8.toString()); // coding name by url to UTF8
        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                .contentType(MediaType.IMAGE_JPEG) //type of file jpeg
                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }


}