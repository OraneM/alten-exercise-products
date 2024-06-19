package perso.alten.test.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "Product API",
                version = "1.0",
                description = "API permettant de gérer des produits",
                contact = @Contact(email = "mark.hervagault@gmail.com")
        ),
        tags = @Tag(name = "Product")
)
public interface ProductAPI {

    @Operation(description = "Récupère tous les produits",
            tags = "Product",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Tous les produits"),
                    @ApiResponse(responseCode = "500", description = "Problème interne au serveur", content = @Content())
            }
    )
    ResponseEntity<List<ProductDTO>> getProducts();

    @Operation(description = "Récupère un produit par son ID",
            tags = "Product",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Produit correspondant à l'ID donné"),
                    @ApiResponse(responseCode = "404", description = "Le produit n'existe pas", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Problème interne au serveur", content = @Content())
            }
    )
    ResponseEntity<ProductDTO> getProductById(Long idProduct);

    @Operation(description = "Supprime le produit correspondant à l'ID donné",
            tags = "Product",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Le produit a été supprimé"),
                    @ApiResponse(responseCode = "404", description = "Le produit n'existe pas", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Problème interne au serveur", content = @Content())
            }
    )
    ResponseEntity<Void> deleteProductById(Long idProduct);

    @Operation(description = "Ajoute le produit",
            tags = "Product",
            responses = {
                    @ApiResponse(responseCode = "201",
                            description = "Produit ajouté"),
                    @ApiResponse(responseCode = "400", description = "Problème avec la requête, il manque peut-être des informations obligatoires", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Problème interne au serveur", content = @Content())
            }
    )
    ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product);

    @Operation(description = "Modifie le produit",
            tags = "Product",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Produit modifié"),
                    @ApiResponse(responseCode = "400", description = "Problème avec la requête, il y a peut-être des typos dans la liste des updates", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Le produit n'existe pas", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Problème interne au serveur", content = @Content())
            }
    )
    ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO product, @PathVariable Long idProduct);

    @Operation(description = "Modifie l'état des stocks du produit",
            tags = "Product",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "État des stocks du produit modifié"),
                    @ApiResponse(responseCode = "400", description = "Problème avec la requête", content = @Content()),
                    @ApiResponse(responseCode = "404", description = "Le produit n'existe pas", content = @Content()),
                    @ApiResponse(responseCode = "500", description = "Problème interne au serveur", content = @Content())
            }
    )
    ResponseEntity<ProductDTO> updateProductInventoryStatus(@RequestBody String inventoryStatus, @PathVariable Long idProduct);
}