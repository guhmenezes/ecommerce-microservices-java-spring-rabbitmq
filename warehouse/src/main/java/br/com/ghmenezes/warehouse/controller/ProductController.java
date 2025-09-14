package br.com.ghmenezes.warehouse.controller;

import br.com.ghmenezes.warehouse.controller.request.ProductSaveRequest;
import br.com.ghmenezes.warehouse.controller.response.ProductDetailsResponse;
import br.com.ghmenezes.warehouse.controller.response.ProductSavedResponse;
import br.com.ghmenezes.warehouse.mapper.ProductMapper;
import br.com.ghmenezes.warehouse.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("products")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    ProductSavedResponse create(@RequestBody final ProductSaveRequest request){
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toSavedResponse(entity);
    }

    @PostMapping("{id}/purchase")
    @ResponseStatus(NO_CONTENT)
    void purchase(@PathVariable final UUID id){
        service.purchase(id);
    }

    @GetMapping("{id}")
    ProductDetailsResponse findById(@PathVariable final UUID id){
        var entity = service.findById(id);
        return mapper.toDetailResponse(entity);

    }
}
