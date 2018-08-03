package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.FinalData;
import com.codecool.shop.model.OrderItem;
import com.codecool.shop.dao.implementation.CheckoutDao;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;



@WebServlet(urlPatterns = {"/checkout"})
public class Checkout extends HttpServlet {

    CheckoutDao checkoutDaoMap = CheckoutDao.getInstance();
    FinalData finalData = FinalData.getInstance();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(request.getServletContext());
        WebContext context = new WebContext(request, response, request.getServletContext());

        finalData.setFinalOrderPrice(OrderItem.getRoundedTotalPrice());

        context.setVariable("cartSize", OrderItem.totalItems);
        engine.process("product/checkout.html", context, response.getWriter());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        checkoutDaoMap.add(request.getParameterMap());
        System.out.println(checkoutDaoMap.getAll());
        response.sendRedirect("/payment");
    }
}
