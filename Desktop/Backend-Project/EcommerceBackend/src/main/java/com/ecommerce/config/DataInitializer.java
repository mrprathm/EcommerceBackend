package com.ecommerce.config;

import com.ecommerce.entity.Category;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.CategoryRepository;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdmin();
        seedCategories();
        seedProducts();
    }

    private void seedAdmin() {
        if (userRepository.existsByEmail("prathamrathod200@gmail.com")) return;
        userRepository.save(User.builder()
                .firstName("Pratham").lastName("Rathod")
                .email("prathamrathod200@gmail.com")
                .password(passwordEncoder.encode("Golu"))
                .phone("+91-9890394356")
                .role(User.Role.ADMIN).enabled(true).build());
    }

    private void seedCategories() {
        if (categoryRepository.count() > 0) return;
        categoryRepository.save(Category.builder().name("Electronics").description("Mobiles, Laptops, Gadgets & Accessories").imageUrl("https://images.unsplash.com/photo-1498049794561-7780e7231661?w=400").build());
        categoryRepository.save(Category.builder().name("Fashion").description("Clothing, Footwear & Accessories").imageUrl("https://images.unsplash.com/photo-1445205170230-053b83016050?w=400").build());
        categoryRepository.save(Category.builder().name("Home & Kitchen").description("Furniture, Appliances & Home Decor").imageUrl("https://images.unsplash.com/photo-1556909114-f6e7ad7d3136?w=400").build());
        categoryRepository.save(Category.builder().name("Books").description("Academic, Fiction & Non-Fiction").imageUrl("https://images.unsplash.com/photo-1512820790803-83ca734da794?w=400").build());
        categoryRepository.save(Category.builder().name("Sports").description("Fitness, Outdoor & Sports Equipment").imageUrl("https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=400").build());
    }

    private void seedProducts() {
        if (productRepository.count() > 0) return;

        Category electronics = categoryRepository.findByName("Electronics").orElse(null);
        Category fashion     = categoryRepository.findByName("Fashion").orElse(null);
        Category home        = categoryRepository.findByName("Home & Kitchen").orElse(null);
        Category books       = categoryRepository.findByName("Books").orElse(null);
        Category sports      = categoryRepository.findByName("Sports").orElse(null);

        // Electronics
        productRepository.save(Product.builder().name("Samsung Galaxy M34 5G")
                .description("6.5 inch FHD+ Super AMOLED display, 6000mAh battery, 128GB storage, 50MP triple camera, 5G enabled")
                .price(new BigDecimal("18999")).stockQuantity(50).active(true).category(electronics)
                .imageUrl("https://images.unsplash.com/photo-1610945415295-d9bbf067e59c?w=400").build());

        productRepository.save(Product.builder().name("boAt Rockerz 450 Headphones")
                .description("Wireless Bluetooth headphones, 15hr playtime, 40mm dynamic drivers, foldable design, built-in mic")
                .price(new BigDecimal("1299")).stockQuantity(100).active(true).category(electronics)
                .imageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=400").build());

        productRepository.save(Product.builder().name("Lenovo IdeaPad Slim 3 Laptop")
                .description("Intel Core i5 12th Gen, 8GB RAM, 512GB SSD, 15.6 inch FHD display, Windows 11, Backlit keyboard")
                .price(new BigDecimal("45999")).stockQuantity(20).active(true).category(electronics)
                .imageUrl("https://images.unsplash.com/photo-1496181133206-80ce9b88a853?w=400").build());

        productRepository.save(Product.builder().name("Apple AirPods Pro")
                .description("Active Noise Cancellation, Transparency mode, Spatial Audio, IPX4 water resistance, MagSafe charging")
                .price(new BigDecimal("19900")).stockQuantity(35).active(true).category(electronics)
                .imageUrl("https://images.unsplash.com/photo-1588423771073-b8903febb85b?w=400").build());

        productRepository.save(Product.builder().name("Canon EOS 1500D DSLR Camera")
                .description("24.1MP APS-C sensor, Full HD video, Built-in WiFi & NFC, 3 inch LCD, 18-55mm kit lens included")
                .price(new BigDecimal("34990")).stockQuantity(15).active(true).category(electronics)
                .imageUrl("https://images.unsplash.com/photo-1516035069371-29a1b244cc32?w=400").build());

        // Fashion
        productRepository.save(Product.builder().name("Men's Premium Cotton T-Shirt")
                .description("100% organic cotton, round neck, pre-shrunk fabric, available in 8 colors, sizes S to XXL")
                .price(new BigDecimal("599")).stockQuantity(200).active(true).category(fashion)
                .imageUrl("https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400").build());

        productRepository.save(Product.builder().name("Nike Air Max Running Shoes")
                .description("Lightweight mesh upper, Air Max cushioning, rubber outsole, reflective details, unisex design, sizes 6-11")
                .price(new BigDecimal("7999")).stockQuantity(75).active(true).category(fashion)
                .imageUrl("https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400").build());

        productRepository.save(Product.builder().name("Women's Floral Kurti")
                .description("Rayon fabric, A-line cut, floral print, 3/4 sleeves, machine washable, ethnic wear for all occasions")
                .price(new BigDecimal("899")).stockQuantity(120).active(true).category(fashion)
                .imageUrl("https://images.unsplash.com/photo-1594938298603-c8148c4b4c5f?w=400").build());

        // Home & Kitchen
        productRepository.save(Product.builder().name("Prestige Pressure Cooker 5L")
                .description("Aluminium body, ISI certified, double safety valve, compatible with all stoves, 5 year warranty")
                .price(new BigDecimal("1199")).stockQuantity(60).active(true).category(home)
                .imageUrl("https://images.unsplash.com/photo-1585515320310-259814833e62?w=400").build());

        productRepository.save(Product.builder().name("Philips Air Fryer HD9200")
                .description("1400W, 4.1L capacity, Rapid Air technology, digital touch display, auto shutoff, dishwasher safe parts")
                .price(new BigDecimal("6999")).stockQuantity(30).active(true).category(home)
                .imageUrl("https://images.unsplash.com/photo-1648720682791-d2ce9f57e3c1?w=400").build());

        productRepository.save(Product.builder().name("Wooden Study Table")
                .description("Solid wood construction, 2 drawers, cable management hole, ergonomic design, easy assembly, 120cm x 60cm")
                .price(new BigDecimal("8499")).stockQuantity(25).active(true).category(home)
                .imageUrl("https://images.unsplash.com/photo-1518455027359-f3f8164ba6bd?w=400").build());

        // Books
        productRepository.save(Product.builder().name("Clean Code by Robert C. Martin")
                .description("A handbook of agile software craftsmanship. Essential reading for every professional Java developer")
                .price(new BigDecimal("599")).stockQuantity(150).active(true).category(books)
                .imageUrl("https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?w=400").build());

        productRepository.save(Product.builder().name("Spring Boot in Action")
                .description("Complete guide to building Spring Boot applications. Covers REST APIs, security, testing, and deployment")
                .price(new BigDecimal("799")).stockQuantity(80).active(true).category(books)
                .imageUrl("https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=400").build());

        // Sports
        productRepository.save(Product.builder().name("Cosco Dribble Football Size 5")
                .description("FIFA approved, machine stitched, all-weather design, high air retention bladder, vibrant color design")
                .price(new BigDecimal("649")).stockQuantity(120).active(true).category(sports)
                .imageUrl("https://images.unsplash.com/photo-1614632537239-e2258203ac2f?w=400").build());

        productRepository.save(Product.builder().name("Yoga Mat Premium 6mm")
                .description("Non-slip surface, extra thick 6mm cushioning, eco-friendly TPE material, carrying strap included, 183x61cm")
                .price(new BigDecimal("1299")).stockQuantity(90).active(true).category(sports)
                .imageUrl("https://images.unsplash.com/photo-1544367567-0f2fcb009e0b?w=400").build());
    }
}
