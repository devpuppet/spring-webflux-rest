package com.kkukielka.springwebfluxrest.controllers;

import com.kkukielka.springwebfluxrest.domain.Category;
import com.kkukielka.springwebfluxrest.repositories.CategoryRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(CategoryController.BASE_URL)
@RestController
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories";

    private final CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping
    public Flux<Category> listCategories() {
        return categoryRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Category> getCategoryById(@PathVariable String id) {
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> createCategory(@RequestBody Publisher<Category> categoryStream) {
        return categoryRepository.saveAll(categoryStream).then();
    }

}
