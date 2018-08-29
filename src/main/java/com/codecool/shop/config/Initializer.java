package com.codecool.shop.config;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.dao.implementation.*;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.SQLException;

@WebListener
public class Initializer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = null;
        SupplierDaoJDBC supplierDataStore = null;
        try {
            supplierDataStore = SupplierDaoJDBC.getInstance();
            productCategoryDataStore = ProductCategoryDaoJDBC.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //setting up a new supplier
        Supplier amazon = new Supplier("Amazon", "Digital content and services");
        supplierDataStore.add(amazon);
        Supplier lenovo = new Supplier("Lenovo", "Computers");
        supplierDataStore.add(lenovo);
        Supplier samsung = new Supplier("Samsung", "Electronics");
        supplierDataStore.add(samsung);
        Supplier sony = new Supplier("Sony", "Electronics");
        supplierDataStore.add(sony);
        Supplier apple = new Supplier("Apple", "Electronics");
        supplierDataStore.add(apple);
        Supplier lg = new Supplier("LG", "Electronics");
        supplierDataStore.add(lg);

        //setting up a new product category
        ProductCategory tablet = new ProductCategory("Tablet", "Hardware", "A tablet computer, commonly shortened to tablet, is a thin, flat mobile computer with a touchscreen display.");
        ProductCategory tv = new ProductCategory("TV", "Hardware", "A box which gives you the impression of actually seeing inside things.");
        ProductCategory phone = new ProductCategory("Phone", "Hardware", "A calling device.");
        ProductCategory computer = new ProductCategory("Computer", "Hardware", "Machine that will take over the world.");
        productCategoryDataStore.add(tablet);
        productCategoryDataStore.add(tv);
        productCategoryDataStore.add(phone);
        productCategoryDataStore.add(computer);

        //setting up products and printing it
        productDataStore.add(new Product("Amazon Fire", 49.9f, "USD", "Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.", tablet, amazon));
        productDataStore.add(new Product("Lenovo IdeaPad Miix 700", 479, "USD", "Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.", tablet, lenovo));
        productDataStore.add(new Product("Amazon Fire HD 8", 89, "USD", "Amazon's latest Fire HD 8 tablet is a great value for media consumption.", tablet, amazon));
        productDataStore.add(new Product("Amazon tv", 2000, "USD", "Dolby Atmos is the same audio technology developed for state-of-the-art cinemas, .", tv, amazon));

        productDataStore.add(new Product("Ipad", 889, "USD", "Latest Ipad ever created... Buy it now!!! BlaBlaBla...", tablet, apple));

        productDataStore.add(new Product("Samsung UE65MU6120 65 inch SMART Ultra HD TV - Black", 800, "USD", "Enjoy a beautifully vibrant Certified Ultra HD experience with our MU6100 UHD TV.", tv, samsung));
        productDataStore.add(new Product("Samsung UE55MU6400 55\" 4K Ultra HD Smart LED TV with Freesat HD", 789, "USD", "Active Crystal Colour HDR UHD 4K TV Certified Ultra HD One Remote Control SMART.", tv, samsung));
        productDataStore.add(new Product("Samsung T32E390SX 32 Smart LED FHD TV", 315, "USD", "ull HD 1080p 400 Hz processing rate Access content on Netflix Tuner: Freeview HD Connectivity: HDMI x 2.", tv, samsung));
        productDataStore.add(new Product("SONY BRAVIA 32WD752SU Smart 32\" LED TV", 336, "USD", "Full HD 1080p. Tuner: Freeview HD. Connectivity: HDMI x 2", tv, sony));
        productDataStore.add(new Product("LG Electronics 65UJ6300 65-Inch 4K Ultra HD Smart LED TV (2017 Model)", 796, "USD", "TruMotion 120 (Refresh Rate 60Hz) allows fast moving action scenes to be seen with minimal motion blur.Resolution : 3840 x 2160", tv, lg));
        productDataStore.add(new Product("LG 65SK8000PUA 65-Inch 4K Ultra HD Smart LED TV (2018 Model)", 1199, "USD", "Local dimming can brighten and dim independently, enhancing contrast and achieving deeper black levels for a rich, lifelike image.", tv, lg));
        productDataStore.add(new Product("LG OLED65E8PUA 65-Inch 4K Ultra HD Smart OLED TV (2018 Model)", 2699, "USD", "LG OLED Display uses the latest panels, with brighter, self-illuminating pixels that deliver perfect black and intense color. ", tv, lg));
        productDataStore.add(new Product("Samsung QN65Q9F FLAT 65” QLED 4K UHD 9 Series Smart TV 2018", 3767, "USD", "Q Contrast EliteMax: The pinnacle of QLED innovation with our best Direct Full Array LED ", tv, samsung));
        productDataStore.add(new Product("Sony XBR65A1E 65\" 4K Ultra HD Smart Bravia OLED TV 2017", 2200, "USD", "Combined with 4K ULTRA HD RESOLUTION, HDR video content delivers EXCEPTIONAL DETAIL, color and contrast", tv, sony));
        productDataStore.add(new Product("LG 65 Inches 4K Smart OLED TV OLED65B8PUA (2018)", 349, "USD", "LG OLED TV with AI (Artificial Intelligence) ThinQ has the Google Assistant built in", tv, lg));

        productDataStore.add(new Product("Apple iPhone 6S, Fully Unlocked, 64GB - Rose Gold", 264, "USD", "4.70-inch touchscreen display with a resolution of 750 pixels by 1334 pixels at a PPI of 326 pixels per inch.", phone, apple));
        productDataStore.add(new Product("Apple iPhone 8 4.7\", 64 GB, Fully Unlocked, Gold", 749, "USD", "We’ve always intended iPhone to be a truly wireless device", phone, apple));
        productDataStore.add(new Product("Apple iPhone X, Fully Unlocked 5.8\", 64 GB - Silver", 1100, "USD", "The front and back are all glass — the most durable we’ve ever made, with a strengthening layer that’s 50% deeper. ", phone, apple));
        productDataStore.add(new Product("Samsung Galaxy J7 Prime (32GB) G610F/DS - 5.5\" Dual SIM Unlocked Phone with Finger Print Sensor (Gold)", 198, "USD", "5.5-inch Full HD 1080 x 1920 pixels (~401 ppi pixel density) IPS LCD", phone, samsung));
        productDataStore.add(new Product("Samsung Galaxy S8 Unlocked 64GB - US Version (Midnight Black)", 499, "USD", "Infinity Display: a bezel-less, full-frontal, edge-to-edge screen. Default resolution is Full HD+ and can be changed to Quad HD+ (WQHD+) in Settings", phone, samsung));
        productDataStore.add(new Product("Samsung Galaxy S7 Edge unlocked smartphone, 32 GB Silver ", 419, "USD", "Swipe and You're In. Only Samsung gives you an edge. One swipe and you’re talking to your friend or liking a post.", phone, samsung));
        productDataStore.add(new Product("LG D850 32GB WHITE G3 D850 32GB Unlocked GSM 4G LTE Quad-HD 13MP Camera Smartphone - Silk White", 349, "USD", "5.5-inch True HD-IPS + LCD Capacitive Multi-Touchscreen w/ Protective Corning Gorilla Glass 3", phone, lg));
        productDataStore.add(new Product("LG G Stylo 4G LTE Mobile Stylus Smartphone GSM Unlocked", 229, "USD", "The Good The LG G Stylo has a competitively low prepaid price, a long-lasting battery and a built-in stylus for added functionality.", phone, lg));
        productDataStore.add(new Product("Apple 13\" MacBook Pro, Retina, Touch Bar, 3.1GHz Intel Core i5 Dual Core, 8GB RAM, 256GB SSD, Space Gray", 1599, "USD", "This Certified Refurbished product has been tested and certified to work and look like new, with minimal to no signs of wear", computer, apple));

    }
}
