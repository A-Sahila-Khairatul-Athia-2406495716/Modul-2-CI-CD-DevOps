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

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);
        Product created = productService.create(product);
        assertEquals(product.getProductId(), created.getProductId());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        Iterator<Product> iterator = Arrays.asList(product).iterator();
        when(productRepository.findAll()).thenReturn(iterator);
        List<Product> allProducts = productService.findAll();
        assertFalse(allProducts.isEmpty());
        assertEquals(1, allProducts.size());
    }

    @Test
    void testFindById() {
        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);
        Product found = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(product.getProductId(), found.getProductId());
    }

    @Test
    void testEdit() {
        when(productRepository.edit(product)).thenReturn(product);
        Product edited = productService.edit(product);
        assertEquals(product.getProductName(), edited.getProductName());
    }

    @Test
    void testDelete() {
        when(productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);
        Product deleted = productService.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertEquals(product.getProductId(), deleted.getProductId());
    }
}