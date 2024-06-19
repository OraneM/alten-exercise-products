package perso.alten.test.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import perso.alten.test.service.ProductNotFoundException;
import perso.alten.test.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController implements ProductAPI {

    ProductService productService;

    @Override
    @GetMapping("/products")
    public ResponseEntity<List<ProductDTO>> getProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    @Override
    @GetMapping("/products/{idProduct}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long idProduct){
        try{
            return ResponseEntity.ok(productService.getById(idProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    @DeleteMapping("/products/{idProduct}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long idProduct){
        try{
            productService.deleteById(idProduct);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    @PostMapping("/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody @Valid ProductDTO product){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.add(product));
    }

    @Override
    @PutMapping("/products/{idProduct}")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody @Valid ProductDTO product, @PathVariable Long idProduct){
        try{
            return ResponseEntity.ok(productService.update(product, idProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @Override
    @PatchMapping("/products/{idProduct}")
    public ResponseEntity<ProductDTO> updateProductInventoryStatus(@RequestBody String inventoryStatus, @PathVariable Long idProduct){
        try{
            return ResponseEntity.ok(productService.updateInventoryStatus(inventoryStatus, idProduct));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
