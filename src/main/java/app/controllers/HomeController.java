package app.controllers;

import app.dto.Animal;
import app.dto.Person;
import app.entities.AnimalEntity;
import app.mapper.AnimalMapper;
import app.repositories.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

@RestController
public class HomeController {
    private final AnimalRepository animalRepository;
    private final AnimalMapper animalMapper;

    @Autowired
    public HomeController(AnimalRepository animalRepository,
                          AnimalMapper animalMapper) {
        this.animalRepository = animalRepository;
        this.animalMapper = animalMapper;
    }

    @GetMapping("/")
    public List<AnimalEntity> index() {
        return animalRepository.findAll();
    }

    @PostMapping("/create")
    public String add(Person dto) throws IOException {
        String [] charArray = dto.getPhoto().split(",");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = new byte[0];
        bytes = decoder.decode(charArray[1]);
        String directory= "uploaded/"+"app.jpg";
        new FileOutputStream(directory).write(bytes);
        return "Added";
    }
}
