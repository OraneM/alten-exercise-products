package perso.alten.test.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import perso.alten.test.api.ProductDTO;
import perso.alten.test.data.entity.ProductEntity;
import perso.alten.test.data.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

    ProductMapper productMapper;

    ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<ProductDTO> getAll(){
        return productMapper.entitiesToDtos(productRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ProductDTO getById(Long idProduct) throws ProductNotFoundException {
        return productMapper.entityToDto(
                productRepository.findById(idProduct)
                .orElseThrow(ProductNotFoundException::new)
        );
    }

    @Transactional(rollbackFor = ProductNotFoundException.class)
    public void deleteById(Long idProduct) throws ProductNotFoundException {
        try{
            productRepository.deleteById(idProduct);
        } catch (EmptyResultDataAccessException e){
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public ProductDTO add(ProductDTO product) {
        ProductEntity productEntity = productMapper.dtoToEntity(product);
        return productMapper.entityToDto(productRepository.save(productEntity));
    }

    @Transactional(rollbackFor = ProductNotFoundException.class)
    public ProductDTO update(ProductDTO product, Long idProduct) throws ProductNotFoundException {
        ProductEntity productEntity = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
        productMapper.updateProductFromDto(product, productEntity);
        return productMapper.entityToDto(productRepository.save(productEntity));
    }

    @Transactional(rollbackFor = ProductNotFoundException.class)
    public ProductDTO updateInventoryStatus(String inventoryStatus, Long idProduct) throws ProductNotFoundException {
        ProductEntity productEntity = productRepository.findById(idProduct).orElseThrow(ProductNotFoundException::new);
        productEntity.setInventoryStatus(inventoryStatus);
        return productMapper.entityToDto(productRepository.save(productEntity));
    }
}
