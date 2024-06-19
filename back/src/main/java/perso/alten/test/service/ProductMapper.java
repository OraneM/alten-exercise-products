package perso.alten.test.service;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import perso.alten.test.api.ProductDTO;
import perso.alten.test.data.entity.ProductEntity;

import java.util.List;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    ProductDTO entityToDto(ProductEntity productEntity);

    List<ProductDTO> entitiesToDtos(List<ProductEntity> productEntities);

    ProductEntity dtoToEntity(ProductDTO productDTO);

    void updateProductFromDto(ProductDTO productDTO, @MappingTarget ProductEntity entity);
}
