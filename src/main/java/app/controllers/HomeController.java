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

    @GetMapping("/")
    public List<AnimalEntity> index() {
        return animalRepository.findAll();
    }

    @PostMapping("/create")
    public String add(Animal animal)
    {
        AnimalEntity animalEntity =  animalMapper.AnimalToAnimalEntity(animal);
        animalRepository.save(animalEntity);
        return "Додано";
    }

    @PostMapping("/upload")
    public String upload(@RequestBody UploadImageDto dto) {
        String image = storageService.save(dto.getBase64());
        return image;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws Exception {

        Resource file = storageService.loadAsResource(filename);
        String urlFileName =  URLEncoder.encode("сало.jpg", StandardCharsets.UTF_8.toString());
        return ResponseEntity.ok()
                //.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION,"filename=\""+urlFileName+"\"")
                .body(file);
    }


}