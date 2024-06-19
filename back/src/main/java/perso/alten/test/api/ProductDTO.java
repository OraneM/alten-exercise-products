package perso.alten.test.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record ProductDTO(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,
        @NotNull
        String code,
        @NotNull
        String name,
        @NotNull
        String description,
        @NotNull
        Double price,
        @NotNull
        Integer quantity,
        @NotNull
        String inventoryStatus,
        @NotNull
        String category,
        String image,
        Double rating
) {
}
