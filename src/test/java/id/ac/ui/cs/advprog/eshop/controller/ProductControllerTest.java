package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private Model model;

    @InjectMocks
    private ProductController productController;

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
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);

        assertAll("Verify create product page",
                () -> assertEquals("CreateProduct", viewName, "View name should be CreateProduct"),
                () -> verify(model, times(1)).addAttribute(eq("product"), any(Product.class))
        );
    }

    @Test
    void testCreateProductPostWithNullId() {
        product.setProductId(null);
        String viewName = productController.createProductPost(product, model);

        assertAll("Verify create product post with null ID",
                () -> assertEquals("redirect:list", viewName, "Should redirect to list after creation"),
                () -> assertNotNull(product.getProductId(), "Product ID should be generated if null")
        );
    }

    @Test
    void testCreateProductPostWithExistingId() {
        String viewName = productController.createProductPost(product, model);

        assertAll("Verify create product post with existing ID",
                () -> assertEquals("redirect:list", viewName, "Should redirect to list"),
                () -> assertEquals(PRODUCT_ID, product.getProductId(), "Product ID should remain unchanged")
        );
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        when(productService.findAll()).thenReturn(products);
        String viewName = productController.productListPage(model);

        assertAll("Verify product list page",
                () -> assertEquals("ProductList", viewName, "View name should be ProductList"),
                () -> verify(model, times(1)).addAttribute("products", products)
        );
    }

    @Test
    void testEditProductPage() {
        when(productService.findById(PRODUCT_ID)).thenReturn(product);
        String viewName = productController.editProductPage(PRODUCT_ID, model);

        assertAll("Verify edit product page",
                () -> assertEquals("EditProduct", viewName, "View name should be EditProduct"),
                () -> verify(model, times(1)).addAttribute("product", product)
        );
    }

    @Test
    void testEditProductPost() {
        String viewName = productController.editProductPost(product);

        assertAll("Verify edit product post",
                () -> assertEquals("redirect:list", viewName, "Should redirect to list after edit"),
                () -> verify(productService, times(1)).edit(product)
        );
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct(PRODUCT_ID);

        assertAll("Verify delete product",
                () -> assertEquals("redirect:../list", viewName, "Should redirect to parent list"),
                () -> verify(productService, times(1)).delete(PRODUCT_ID)
        );
    }
}