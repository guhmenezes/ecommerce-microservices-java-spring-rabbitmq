package br.com.ghmenezes.storefront.controller;

import br.com.ghmenezes.storefront.controller.request.ProductSaveRequest;
import br.com.ghmenezes.storefront.controller.response.ProductAvailableResponse;
import br.com.ghmenezes.storefront.controller.response.ProductDetailsResponse;
import br.com.ghmenezes.storefront.controller.response.ProductSavedResponse;
import br.com.ghmenezes.storefront.mapper.ProductMapper;
import br.com.ghmenezes.storefront.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("products")
@AllArgsConstructor
@Log4j2
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    ProductSavedResponse create(@RequestBody final ProductSaveRequest request){
        var entity = mapper.toEntity(request);
        entity = service.save(entity);
        return mapper.toResponse(entity);
    }

    @PostMapping("{id}/purchase")
    @ResponseStatus(NO_CONTENT)
    void purchase(@PathVariable final UUID id){
        service.purchase(id);
    }

    @GetMapping
    List<ProductAvailableResponse> listAvailable(){
        var entitites = service.findAllActive();
        return mapper.toResponse(entitites);
    }

    @GetMapping("{id}")
    ProductDetailsResponse findById(@PathVariable final UUID id){
        var dto = service.findInfo(id);
        return mapper.toResponse(dto);
    }
}
