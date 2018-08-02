package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.ProductCategoryDao;
import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.dao.implementation.CartItems;
import com.codecool.shop.dao.implementation.ProductCategoryDaoMem;
import com.codecool.shop.dao.implementation.ProductDaoMem;
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

@WebServlet(urlPatterns = {"/cart"})
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CartDao cartData = CartItems.getInstance();

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        context.setVariable("cartItems", cartData.getAll());
        context.setVariable("cartSize", OrderItem.totalItems);
        context.setVariable("totalPrice", OrderItem.getTotalPrice());

        CartItems cartItems = CartItems.getInstance();
        String cartItemToRemove = req.getParameter("minus");
        if (cartItemToRemove != null) {
            Integer cartProductIdInt = Integer.parseInt(cartItemToRemove);
            cartItems.remove(cartProductIdInt);
            context.setVariable("totalPrice", OrderItem.getTotalPrice());
        }

        String cartItemToAdd = req.getParameter("plus");
        if (cartItemToAdd != null) {
            Integer cartProductIdInt = Integer.parseInt(cartItemToAdd);
            cartItems.add(cartProductIdInt);
            context.setVariable("totalPrice", OrderItem.getTotalPrice());
        }

        engine.process("cart.html", context, resp.getWriter());
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);

    }

}
