package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.CartDao;
import com.codecool.shop.dao.implementation.CartItems;
import com.codecool.shop.dao.implementation.CartItemsJDBC;
import com.codecool.shop.dao.implementation.CheckoutDao;
import com.codecool.shop.dao.implementation.FinalData;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;



@WebServlet(urlPatterns = {"/confirmation"})
public class Confirmation extends HttpServlet {

    CheckoutDao checkoutDaoMap = CheckoutDao.getInstance();
    CartDao cartData = CartItemsJDBC.getInstance();
    FinalData finalData = FinalData.getInstance();

    public Confirmation() throws SQLException {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        Map <String, String[]> persDetails = checkoutDaoMap.getAll();
        try {
            context.setVariable("cartItems", cartData.getAll());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        context.setVariable("firstname", persDetails.get("firstname")[0]);
        context.setVariable("lastname", persDetails.get("lastname")[0]);
        context.setVariable("email", persDetails.get("email")[0]);
        context.setVariable("phone_number", persDetails.get("phone_number")[0]);
        context.setVariable("shipping_country", persDetails.get("shipping_country")[0]);
        context.setVariable("shipping_city", persDetails.get("shipping_city")[0]);
        context.setVariable("shipping_postalcode", persDetails.get("shipping_postal_code")[0]);
        context.setVariable("shipping_street", persDetails.get("shipping_street")[0]);
        context.setVariable("shipping_housenumber", persDetails.get("shipping_house_number")[0]);
        context.setVariable("final_price", finalData.getFinalOrderPrice());

        engine.process("product/Confirmation.html", context, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
