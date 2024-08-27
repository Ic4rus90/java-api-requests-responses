package com.booleanuk.api.requests;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/languages")
public class Languages {
    private List<Language> languages = new ArrayList<>(){{
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
        Language language;
        for (Language l : languages){
            if (l.getName().equals(name)){
                return l;
            }
        }
        return null;
    }

    @PutMapping("/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public Language update(@PathVariable String name, @RequestBody String newName){
        for (Language l : languages){
            if (l.getName().equals(name)){
                l.setName(newName);
            }
        }
        return null;
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public Language delete(@PathVariable String name){
        Language language;
        for (Language l : languages){
            if (l.getName().equals(name)){
                language = l;
                languages.remove(l);
                return language;
            }
        }
        return null;
    }

}
