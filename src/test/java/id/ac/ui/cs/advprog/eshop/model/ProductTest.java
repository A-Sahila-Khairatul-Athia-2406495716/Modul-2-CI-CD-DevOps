package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product product;

    @BeforeEach
    void setUp() {
        this.product = new Product();
        this.product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        this.product.setProductName("Sampo Cap Bambang");
        this.product.setProductQuantity(100);
    }

    @Test
    void testGetProductId() {
        assertEquals("eb558e9f-1c39-460e-8860-71af6af63bd6", this.product.getProductId(), "Product ID should match the set value");
    }

    @Test
    void testGetProductName() {
        assertEquals("Sampo Cap Bambang", this.product.getProductName(), "Product name should match the set value");
    }

    @Test
    void testGetProductQuantity() {
        assertEquals(100, this.product.getProductQuantity(), "Product quantity should match the set value");
    }

    @Test
    void testGetProductIdWrong() {
        assertNotEquals("wrong-id", this.product.getProductId(), "Product ID should match the set value");
    }

    @Test
    void testGetProductNameWrong() {
        assertNotEquals("Sampo Cap Badak", this.product.getProductName(), "Product name should match the set value");
    }

    @Test
    void testGetProductQuantityWrong() {
        assertNotEquals(50, this.product.getProductQuantity(), "Product quantity should match the set value");
    }
}