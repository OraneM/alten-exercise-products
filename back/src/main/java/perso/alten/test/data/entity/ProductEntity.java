package perso.alten.test.data.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String code;

    String name;

    String description;

    String image;

    Double price;

    Integer quantity;

    String category;

    String inventoryStatus;

    Double rating;

}
