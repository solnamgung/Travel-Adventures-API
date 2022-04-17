package com.example.mySpringProject.controller;


import com.example.mySpringProject.entity.Adventure;
import com.example.mySpringProject.repository.AdventureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/traveladventures")
public class TravelAdventuresController {

    private final AdventureRepository adventureRepository;

    public TravelAdventuresController(AdventureRepository adventureRepo) {
        this.adventureRepository = adventureRepo;
    }

    // Add controller methods below:
    @GetMapping
    public Iterable<Adventure> getAllAdventure() {
        Iterable<Adventure> adventures = this.adventureRepository.findAll();
        return adventures;
    }

    //   /traveladventures/bycountry/Greece
    @GetMapping("/bycountry/{country}")
    public List<Adventure> fineByCountry(@PathVariable("country") String country) {
        List<Adventure> findCountry = adventureRepository.findByCountry(country);
        return findCountry;
    }

    //  /traveladventures/bystate?state=Lisboa
    @GetMapping("/bystate")
    public List<Adventure> findByState(@RequestParam(name = "state") String state) {
        List<Adventure> findState = adventureRepository.findByState(state);
        return findState;
    }

    @PostMapping()
    public Adventure adventure(@RequestBody Adventure adventure) {
        Adventure newAdventure = adventureRepository.save(adventure);
        return newAdventure;
    }

    @PutMapping("/{id}")
    public Adventure adventure(@PathVariable("id") Integer id, @RequestBody Adventure adventure) {
        Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
        if (!adventureOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        Adventure adventureUpdate = adventureOptional.get();
        if (adventure.getBlogCompleted()) {
            adventureUpdate.setBlogCompleted(true);
        } else {
            adventureUpdate.setBlogCompleted(false);
        }

        Adventure updatedAdventure = this.adventureRepository.save(adventureUpdate);
        return updatedAdventure;
    }

    @DeleteMapping("/{id}")
    public void adventureRemove(@PathVariable("id") Integer id) {
        Optional<Adventure> adventureOptional = this.adventureRepository.findById(id);
        if(adventureOptional.isPresent()) {
            Adventure adventureRemove = adventureOptional.get();
            adventureRepository.delete(adventureRemove);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
