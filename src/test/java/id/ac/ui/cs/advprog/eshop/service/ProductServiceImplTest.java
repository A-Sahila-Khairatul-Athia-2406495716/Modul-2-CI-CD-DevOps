package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    private static final String PRODUCT_ID = "eb558e9f-1c39-460e-8860-71af6af63bd6";

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(PRODUCT_ID);
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);
        Product created = productService.create(product);
        assertAll("Verify product creation",
                () -> assertEquals(product.getProductId(), created.getProductId(), "Created product ID should match"),
                () -> verify(productRepository, times(1)).create(product)
        );
    }

    @Test
    void testFindAll() {
        Iterator<Product> iterator = Arrays.asList(product).iterator();
        when(productRepository.findAll()).thenReturn(iterator);
        List<Product> allProducts = productService.findAll();
        assertAll("Verify all products list",
                () -> assertFalse(allProducts.isEmpty(), "Product list should not be empty"),
                () -> assertEquals(1, allProducts.size(), "Product list should have exactly 1 product")
        );
    }

    @Test
    void testFindById() {
        when(productRepository.findById(PRODUCT_ID)).thenReturn(product);
        Product found = productService.findById(PRODUCT_ID);
        assertEquals(product.getProductId(), found.getProductId(), "Found product ID should match");
    }

    @Test
    void testEdit() {
        when(productRepository.edit(product)).thenReturn(product);
        Product edited = productService.edit(product);
        assertEquals(product.getProductName(), edited.getProductName(), "Edited product name should match");
    }

    @Test
    void testDelete() {
        when(productRepository.delete(PRODUCT_ID)).thenReturn(product);
        Product deleted = productService.delete(PRODUCT_ID);
        assertEquals(product.getProductId(), deleted.getProductId(), "Deleted product ID should match");
    }
}