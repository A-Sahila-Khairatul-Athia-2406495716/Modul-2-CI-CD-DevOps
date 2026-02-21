package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product foundProduct = productRepository.findById(product.getProductId());
        assertNotNull(foundProduct);
        assertEquals(product.getProductId(), foundProduct.getProductId());
        assertEquals(product.getProductName(), foundProduct.getProductName());
    }

    @Test
    void testFindByIdNotFound() {
        Product foundProduct = productRepository.findById("non-existent-id");
        assertNull(foundProduct);
    }

    @Test
    void testEditSuccess() {
        // setup
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // action
        Product editedProduct = new Product();
        editedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        editedProduct.setProductName("Sampo Cap Bambang Baru");
        editedProduct.setProductQuantity(50);
        productRepository.edit(editedProduct);

        // verify
        Product savedProduct = productRepository.findById(product.getProductId());
        assertNotNull(savedProduct);
        assertEquals("Sampo Cap Bambang Baru", savedProduct.getProductName());
        assertEquals(50, savedProduct.getProductQuantity());
    }

    @Test
    void testEditNotFound() {
        Product editedProduct = new Product();
        editedProduct.setProductId("invalid-id");
        editedProduct.setProductName("Barang Palsu");
        editedProduct.setProductQuantity(10);

        Product result = productRepository.edit(editedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteSuccess() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteNotFound() {
        Product result = productRepository.delete("non-existent-id");

        assertNull(result);
    }

    @Test
    void testFindByIdWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        productRepository.create(product2);

        Product foundProduct = productRepository.findById("id-2");
        assertEquals("id-2", foundProduct.getProductId());
    }

    @Test
    void testEditWithMultipleProducts() {
        Product product1 = new Product();
        product1.setProductId("id-1");
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("id-2");
        productRepository.create(product2);

        Product editedProduct = new Product();
        editedProduct.setProductId("id-2");
        editedProduct.setProductName("Nama Baru");

        productRepository.edit(editedProduct);

        Product savedProduct = productRepository.findById("id-2");
        assertEquals("Nama Baru", savedProduct.getProductName());
    }
}