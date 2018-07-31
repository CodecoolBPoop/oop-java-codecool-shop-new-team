package com.codecool.shop.controller;

import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.SupplierDaoMem;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = {"/"})
public class ProductController extends HttpServlet {
    private boolean supplierButton;
    private boolean cathegoryButton;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProductDao productDataStore = ProductDaoMem.getInstance();
        ProductCategoryDao productCategoryDataStore = ProductCategoryDaoMem.getInstance();
        SupplierDaoMem supplierDaoMem = SupplierDaoMem.getInstance();

        String supplierID = req.getParameter("dropdown2");
        String categoryID = req.getParameter("dropdown");

        if (categoryID == null){
            categoryID = "1";
            cathegoryButton = false;
        }else{
            cathegoryButton = true;
        }
        if (supplierID==null){
            supplierID = "1";
            supplierButton = false;
        }else {
            supplierButton = true;
        }
        Integer parsedId = Integer.parseInt(categoryID);
        Integer parsedSupplierId = Integer.parseInt(supplierID);
 //       Map params = new HashMap<>();
   //     params.put("category", productCategoryDataStore.find(1));
     //   params.put("products", productDataStore.getBy(productCategoryDataStore.find(1)));

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
//        context.setVariables(params);
        context.setVariable("recipient", "World");
        context.setVariable("category", productCategoryDataStore.find(parsedId));
        context.setVariable("categories", productCategoryDataStore.getAll());
        context.setVariable("supplier", supplierDaoMem.getAll());
        context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(1)));
        if (cathegoryButton==true) {
            context.setVariable("products", productDataStore.getBy(productCategoryDataStore.find(parsedId)));
        }
        if(supplierButton==true){
            context.setVariable("products", productDataStore.getBy(supplierDaoMem.find(parsedSupplierId)));
        }
        engine.process("product/index.html", context, resp.getWriter());
    }

}
