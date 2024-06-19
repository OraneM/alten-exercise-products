package perso.alten.test.service;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import perso.alten.test.api.ProductDTO;
import perso.alten.test.data.entity.ProductEntity;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-06-19T11:26:58+0200",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Azul Systems, Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDTO entityToDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Long id = null;
        String code = null;
        String name = null;
        String description = null;
        Double price = null;
        Integer quantity = null;
        String inventoryStatus = null;
        String category = null;
        String image = null;
        Double rating = null;

        id = productEntity.getId();
        code = productEntity.getCode();
        name = productEntity.getName();
        description = productEntity.getDescription();
        price = productEntity.getPrice();
        quantity = productEntity.getQuantity();
        inventoryStatus = productEntity.getInventoryStatus();
        category = productEntity.getCategory();
        image = productEntity.getImage();
        rating = productEntity.getRating();

        ProductDTO productDTO = new ProductDTO( id, code, name, description, price, quantity, inventoryStatus, category, image, rating );

        return productDTO;
    }

    @Override
    public List<ProductDTO> entitiesToDtos(List<ProductEntity> productEntities) {
        if ( productEntities == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( productEntities.size() );
        for ( ProductEntity productEntity : productEntities ) {
            list.add( entityToDto( productEntity ) );
        }

        return list;
    }

    @Override
    public ProductEntity dtoToEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( productDTO.id() );
        productEntity.setCode( productDTO.code() );
        productEntity.setName( productDTO.name() );
        productEntity.setDescription( productDTO.description() );
        productEntity.setImage( productDTO.image() );
        productEntity.setPrice( productDTO.price() );
        productEntity.setQuantity( productDTO.quantity() );
        productEntity.setCategory( productDTO.category() );
        productEntity.setInventoryStatus( productDTO.inventoryStatus() );
        productEntity.setRating( productDTO.rating() );

        return productEntity;
    }

    @Override
    public void updateProductFromDto(ProductDTO productDTO, ProductEntity entity) {
        if ( productDTO == null ) {
            return;
        }

        if ( productDTO.id() != null ) {
            entity.setId( productDTO.id() );
        }
        if ( productDTO.code() != null ) {
            entity.setCode( productDTO.code() );
        }
        if ( productDTO.name() != null ) {
            entity.setName( productDTO.name() );
        }
        if ( productDTO.description() != null ) {
            entity.setDescription( productDTO.description() );
        }
        if ( productDTO.image() != null ) {
            entity.setImage( productDTO.image() );
        }
        if ( productDTO.price() != null ) {
            entity.setPrice( productDTO.price() );
        }
        if ( productDTO.quantity() != null ) {
            entity.setQuantity( productDTO.quantity() );
        }
        if ( productDTO.category() != null ) {
            entity.setCategory( productDTO.category() );
        }
        if ( productDTO.inventoryStatus() != null ) {
            entity.setInventoryStatus( productDTO.inventoryStatus() );
        }
        if ( productDTO.rating() != null ) {
            entity.setRating( productDTO.rating() );
        }
    }
}
