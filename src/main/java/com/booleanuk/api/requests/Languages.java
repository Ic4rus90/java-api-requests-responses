package com.booleanuk.api.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/languages")
public class Languages {
    private final List<Language> languageList;

    public Languages(){
        this.languageList = new ArrayList<>();
        this.languageList.add(new Language("Java"));
        this.languageList.add(new Language("C#"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Language add(@RequestBody Language language){
        this.languageList.add(language);
        return language;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List <Language> getAll(){
        return this.languageList;
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Language get(@PathVariable String name){
        return getLanguage(name);
    }

    // Helper function to adhere to DRY and increase readability
    private Language getLanguage(String name){
        return this.languageList.stream()
                .filter(l -> l.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Language not found"));
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Language update(@PathVariable String name, @RequestBody Language language){
        Language existingLanguage = getLanguage(name);
        existingLanguage.setName(language.getName());
        return existingLanguage;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Language delete(@PathVariable String name){
        Language language = getLanguage(name);
        this.languageList.remove(language);
        return language;
    }
}
