package perso.alten.test.service;

import org.instancio.junit.InstancioExtension;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import perso.alten.test.api.ProductDTO;
import perso.alten.test.data.entity.ProductEntity;
import perso.alten.test.data.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class, InstancioExtension.class})
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Spy
    private ProductMapper productMapper = new ProductMapperImpl();

    @InjectMocks
    private ProductService productService;

    @Captor
    private ArgumentCaptor<ProductEntity> productEntityCaptor;

    @Test
    void getAllTest() {
        final int PRODUCT_NB = 10;
        final List<ProductEntity> productsInDb = Instancio.ofList(ProductEntity.class).size(PRODUCT_NB).create();
        when(productRepository.findAll()).thenReturn(productsInDb);

        List<ProductDTO> productsResult = productService.getAll();
        assertThat(productsResult).isNotNull().isNotEmpty().hasSize(PRODUCT_NB);

        for (int i = 0; i < PRODUCT_NB; i++) {
            assertThat(productsResult.get(i)).usingRecursiveComparison().isEqualTo(productsInDb.get(i));
        }
    }

    @Test
    void getByIdValidProductTest() throws ProductNotFoundException {
        final long id = 3;
        final ProductEntity product = Instancio.create(ProductEntity.class);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        ProductDTO productResult = productService.getById(id);
        assertThat(productResult).isNotNull().usingRecursiveComparison().isEqualTo(product);
    }

    @Test
    void getByIdNullTest() {
        final long id = 3;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(id)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteByIdValidProductTest() {
        final long id = 3;
        doNothing().when(productRepository).deleteById(id);

        assertThatNoException().isThrownBy(() -> productService.deleteById(id));
    }

    @Test
    void deleteByIdNullTest() {
        final long id = 3;
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(id);

        assertThatThrownBy(() -> productService.deleteById(id)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void addTest() {
        final ProductDTO product = new ProductDTO(
                1L,
                "code",
                "testProduct",
                "This is a test",
                99.99,
                2,
                "AVAILABLE",
                "Ballroom Extravaganza",
                "image.jpg",
                4.3
        );
        final ProductEntity productEntity = productMapper.dtoToEntity(product);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);

        ProductDTO productResult = productService.add(product);
        assertThat(productResult).isNotNull().usingRecursiveComparison().isEqualTo(product);
    }

    @Test
    void updateValidProductTest() throws ProductNotFoundException {
        final ProductDTO oldProduct = new ProductDTO(
                1L,
                "code",
                "testProduct",
                "This is a test",
                99.99,
                2,
                "AVAILABLE",
                "Ballroom Extravaganza",
                "image.jpg",
                4.3
        );

        final ProductDTO newProduct = new ProductDTO(
                1L,
                "code2",
                "testProduct2",
                "This is a re-test",
                0.99,
                3,
                "SUPER AVAILABLE",
                "Ballroom Extravaganzaaaaa",
                "image.png",
                4.5
        );
        final ProductEntity oldProductEntity = productMapper.dtoToEntity(oldProduct);
        final ProductEntity newProductEntity = productMapper.dtoToEntity(newProduct);

        when(productRepository.findById(1L)).thenReturn(Optional.of(oldProductEntity));
        when(productRepository.save(productEntityCaptor.capture())).thenReturn(newProductEntity);

        ProductDTO productResult = productService.update(newProduct, 1L);
        assertThat(productEntityCaptor.getValue()).usingRecursiveComparison().isEqualTo(newProductEntity);
        assertThat(productResult).isNotNull().usingRecursiveComparison().isEqualTo(newProduct);
    }

    @Test
    void updateNullTest() {
        final ProductDTO newProduct = new ProductDTO(
                1L,
                "code2",
                "testProduct2",
                "This is a re-test",
                0.99,
                3,
                "SUPER AVAILABLE",
                "Ballroom Extravaganzaaaaa",
                "image.png",
                4.5
        );

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(newProduct, 1L)).isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void updateInventoryStatusValidProductTest() throws ProductNotFoundException {
        final String inventoryStatus = "TOOMUCHSTOCK";
        final ProductDTO oldProduct = new ProductDTO(
                1L,
                "code",
                "testProduct",
                "This is a test",
                99.99,
                2,
                "LOWSTOCK",
                "Ballroom Extravaganza",
                "image.jpg",
                4.3
        );
        final ProductDTO newProduct = new ProductDTO(
                1L,
                "code",
                "testProduct",
                "This is a test",
                99.99,
                2,
                "TOOMUCHSTOCK",
                "Ballroom Extravaganza",
                "image.jpg",
                4.3
        );
        final ProductEntity oldProductEntity = productMapper.dtoToEntity(oldProduct);
        final ProductEntity newProductEntity = productMapper.dtoToEntity(newProduct);

        when(productRepository.findById(1L)).thenReturn(Optional.of(oldProductEntity));
        when(productRepository.save(productEntityCaptor.capture())).thenReturn(newProductEntity);

        ProductDTO productResult = productService.updateInventoryStatus(inventoryStatus, 1L);
        assertThat(productEntityCaptor.getValue()).usingRecursiveComparison().isEqualTo(newProductEntity);
        assertThat(productResult).isNotNull().usingRecursiveComparison().isEqualTo(newProduct);
    }

    @Test
    void updateInventoryStatusNullTest() {
        final String inventoryStatus = "TOOMUCHSTOCK";

        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.updateInventoryStatus(inventoryStatus, 1L)).isInstanceOf(ProductNotFoundException.class);
    }
}
