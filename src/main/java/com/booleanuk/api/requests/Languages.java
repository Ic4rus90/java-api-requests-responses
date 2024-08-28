package com.booleanuk.api.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/languages")
public class Languages {
    private final List<Language> languages = new ArrayList<>(){{
        add(new Language("Java"));
        add(new Language("C#"));
    }};

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Language add(@RequestBody Language language){
        this.languages.add(language);
        return language;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List <Language> getAll(){
        return languages;
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Language get(@PathVariable String name){
        try {
            return getLanguage(name);
        } catch (ResponseStatusException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    // Helper function to adhere to DRY and increase readability
    public Language getLanguage(String name){
        Optional<Language> optionalLanguage = languages.stream().filter(l -> l.getName().equals(name)).findFirst();
        if (optionalLanguage.isPresent()){
            return optionalLanguage.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Language not found");
        }
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Language update(@PathVariable String name, @RequestBody Language language){
        Optional<Language> optionalLanguage = languages.stream().filter(l -> l.getName().equals(name)).findFirst();
            if (optionalLanguage.isPresent()) {
                optionalLanguage.get().setName(language.getName());
                return optionalLanguage.get();
            }
            else {
                return null;
            }
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Language delete(@PathVariable String name){
        try {
            Language language = getLanguage(name);
            languages.remove(language);
            return language;

        } catch (ResponseStatusException e){
            System.out.println(e.getMessage());
            return null;
        }
    }
}
