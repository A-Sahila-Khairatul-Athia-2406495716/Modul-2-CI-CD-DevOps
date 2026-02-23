package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    private static final String PRODUCT_ID1 = "eb558e9f-1c39-460e-8860-71af6af63bd6";
    private static final String PRODUCT_ID2 = "a0f9de46-90b1-437d-a0bf-d0821dde9096";
    private static final String PRODUCT_NAME1 = "Sampo Cap Bambang";
    private static final String PRODUCT_NAME2 = "Sampo Cap Usep";

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID1);
        product.setProductName(PRODUCT_NAME1);
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext(), "Repository should not be empty after creating a product");
        Product savedProduct = productIterator.next();
        assertAll("Verify product properties",

                () -> assertEquals(product.getProductId(), savedProduct.getProductId(), "Product ID should match"),
                () -> assertEquals(product.getProductName(), savedProduct.getProductName(), "Product name should match"),
                () -> assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity(), "Product quantity should match")
        );
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext(), "Repository should be empty initially");
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(PRODUCT_ID1);
        product1.setProductName(PRODUCT_NAME1);
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(PRODUCT_ID2);
        product2.setProductName(PRODUCT_NAME2);
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();

        assertAll("Verify all products in repository",
                () -> assertTrue(productIterator.hasNext(), "Should contain at least one product"),
                () -> assertEquals(product1.getProductId(), productIterator.next().getProductId(), "First product ID should match"),
                () -> assertEquals(product2.getProductId(), productIterator.next().getProductId(), "Second product ID should match"),
                () -> assertFalse(productIterator.hasNext(), "Should not contain more than two products")
        );
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID1);
        product.setProductName(PRODUCT_NAME1);
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findById(product.getProductId());
        assertAll("Verify found product",
                () -> assertNotNull(foundProduct, "Product should be found"),
                () -> assertEquals(product.getProductId(), foundProduct.getProductId(), "Found product ID should match")
        );
    }

    @Test
    void testFindByIdNotFound() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct, "Should return null for a non-existent product ID");
    }

    @Test
    void testEditSuccess() {
        // setup
        Product product = new Product();
        product.setProductId(PRODUCT_ID1);
        product.setProductName(PRODUCT_NAME1);
        product.setProductQuantity(100);
        productRepository.create(product);

        // action
        Product editedProduct = new Product();
        editedProduct.setProductId(PRODUCT_ID1);
        editedProduct.setProductName(PRODUCT_NAME2);
        editedProduct.setProductQuantity(50);
        productRepository.edit(editedProduct);

        // verify
        Product savedProduct = productRepository.findById(product.getProductId());
        assertAll("Verify product edit details",
                () -> assertNotNull(savedProduct, "Product should still exist after edit"),
                () -> assertEquals(PRODUCT_NAME2, savedProduct.getProductName(), "Product name should be updated"),
                () -> assertEquals(50, savedProduct.getProductQuantity(), "Product quantity should be updated")
        );
    }

    @Test
    void testEditNotFound() {
        Product editedProduct = new Product();
        editedProduct.setProductId("non-existent-id");
        editedProduct.setProductName("Barang Palsu");
        editedProduct.setProductQuantity(10);

        Product result = productRepository.edit(editedProduct);
        assertNull(result, "Updating a non-existent product should return null");
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId(PRODUCT_ID1);
        product.setProductName(PRODUCT_NAME1);
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete(PRODUCT_ID1);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext(), "Repository should be empty after deleting the only product");
    }

    @Test
    void testDeleteNotFound() {
        Product result = productRepository.delete("non-existent-id");

        assertNull(result, "Deleting a non-existent product should return null");
    }

    @Test
    void testFindByIdWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId(PRODUCT_ID1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(PRODUCT_ID2);
        productRepository.create(product2);

        Product foundProduct = productRepository.findById(PRODUCT_ID2);
        assertEquals(PRODUCT_ID2, foundProduct.getProductId(), "Should find the correct product by its ID");
    }

    @Test
    void testEditWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId(PRODUCT_ID1);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(PRODUCT_ID2);
        productRepository.create(product2);

        Product editedProduct = new Product();
        editedProduct.setProductId(PRODUCT_ID2);
        editedProduct.setProductName(PRODUCT_NAME2);

        productRepository.edit(editedProduct);

        Product savedProduct = productRepository.findById(PRODUCT_ID2);
        assertEquals(PRODUCT_NAME2, savedProduct.getProductName(), "Product name should be updated correctly");
    }

    @Test
    void testCreateWithNullId() {
        Product product = new Product();
        product.setProductName(PRODUCT_NAME1);
        product.setProductQuantity(100);

        productRepository.create(product);

        assertNotNull(product.getProductId(), "Product ID should be generated");
    }

    @Test
    void testCreateWithEmptyId() {
        Product product = new Product();
        product.setProductId("");
        product.setProductName(PRODUCT_NAME2);
        product.setProductQuantity(50);

        productRepository.create(product);

        assertFalse(product.getProductId().isEmpty(), "Product ID should not be empty");
    }
}