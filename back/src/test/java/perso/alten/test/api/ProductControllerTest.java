package perso.alten.test.api;

import org.instancio.Instancio;
import org.instancio.junit.InstancioExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import perso.alten.test.service.ProductNotFoundException;
import perso.alten.test.service.ProductService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getProductsTest(){
        final List<ProductDTO> products = Instancio.ofList(ProductDTO.class).size(10).create();
        when(productService.getAll()).thenReturn(products);

        ResponseEntity<List<ProductDTO>> response = productController.getProducts();
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(products);
    }

    @Test
    void getProductByIdValidProductTest() throws ProductNotFoundException {
        final ProductDTO product = Instancio.create(ProductDTO.class);
        final long id = 3;
        when(productService.getById(id)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.getProductById(id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualTo(product);
    }

    @Test
    void getProductByIdNullTest() throws ProductNotFoundException {
        final long id = 3;
        when(productService.getById(id)).thenThrow(ProductNotFoundException.class);

        ResponseEntity<ProductDTO> response = productController.getProductById(id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteProductByIdValidProductTest() throws ProductNotFoundException {
        final long id = 3;
        doNothing().when(productService).deleteById(id);

        ResponseEntity<Void> response = productController.deleteProductById(id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
    }

    @Test
    void deleteProductByIdNullTest() throws ProductNotFoundException {
        final long id = 3;
        doThrow(ProductNotFoundException.class).when(productService).deleteById(id);

        ResponseEntity<Void> response = productController.deleteProductById(id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void addProductTest(){
        final ProductDTO product = Instancio.create(ProductDTO.class);
        when(productService.add(product)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.addProduct(product);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualTo(product);
    }

    @Test
    void updateProductValidProductTest() throws ProductNotFoundException {
        final ProductDTO product = Instancio.create(ProductDTO.class);
        final long id = 3;
        when(productService.update(product, id)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.updateProduct(product, id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualTo(product);
    }

    @Test
    void updateProductNullTest() throws ProductNotFoundException {
        final ProductDTO product = Instancio.create(ProductDTO.class);
        final long id = 3;
        when(productService.update(product, id)).thenThrow(ProductNotFoundException.class);

        ResponseEntity<ProductDTO> response = productController.updateProduct(product, id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void updateInventoryStatusValidProductTest() throws ProductNotFoundException {
        final ProductDTO product = Instancio.create(ProductDTO.class);
        final String inventoryStatus = "TOOMUCHSTOCK";
        final long id = 3;
        when(productService.updateInventoryStatus(inventoryStatus, id)).thenReturn(product);

        ResponseEntity<ProductDTO> response = productController.updateProductInventoryStatus(inventoryStatus, id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .isNotNull()
                .isEqualTo(product);
    }

    @Test
    void updateInventoryStatusNullTest() throws ProductNotFoundException {
        final String inventoryStatus = "TOOMUCHSTOCK";
        final long id = 3;
        when(productService.updateInventoryStatus(inventoryStatus, id)).thenThrow(ProductNotFoundException.class);

        ResponseEntity<ProductDTO> response = productController.updateProductInventoryStatus(inventoryStatus, id);
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isNotNull().isEqualTo(HttpStatus.NOT_FOUND);
    }
}
