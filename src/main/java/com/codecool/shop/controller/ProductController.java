package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartItems;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import com.codecool.shop.model.OrderItem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private boolean supplierButton;
    private boolean categoryButton;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpServletRequest httpReq = req;
        HttpServletResponse httpResp = resp;
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        CartItems cartItems = CartItems.getInstance();
        SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();

        String supplierID = req.getParameter("dropdown2");
        String categoryID = req.getParameter("dropdown");

        if (categoryID == null){
            categoryID = "1";
            categoryButton = false;
        }else{
            categoryButton = true;
        }
        if (supplierID==null){
            supplierID = "1";
            supplierButton = false;
        }else {
            supplierButton = true;
        }
        Integer parsedId = Integer.parseInt(categoryID);

        String cartProductId = req.getParameter("cart_item_id");
        if (cartProductId != null) {
            Integer cartProductIdInt = Integer.parseInt(cartProductId);
            cartItems.add(cartProductIdInt);
        }
        //Map params = new HashMap<>();
        //params.put("category", productCategoryDataStore.find(1));
        //params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        Integer parsedSupplierId = Integer.parseInt(supplierID);

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//      context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.find(parsedId));
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDaoMem.getAll());
        context.setVariable("products", productDataStore.getAll());
        if (categoryButton ==true) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(parsedId)));
        }
        if (supplierButton==true){
            context.setVariable("products", productDataStore.getBy(supplierDaoMem.find(parsedSupplierId)));
        }

        context.setVariable("cartSize", OrderItem.totalItems);
        engine.process("product/index.html", context, resp.getWriter());
    }

}
