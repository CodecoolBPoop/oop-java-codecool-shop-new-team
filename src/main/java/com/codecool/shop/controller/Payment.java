
package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import com.codecool.shop.dao.implementation.CartItems;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import com.codecool.shop.model.OrderItem;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/payment"})
public class Payment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());

        context.setVariable("cash", "9000000");
        engine.process("product/paypal.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TemplateEngine engine = TemplateEngineUtil.getTemplateEngine(req.getServletContext());
        WebContext context = new WebContext(req, resp, req.getServletContext());
        Map<String,String[]> parameterMap = req.getParameterMap();

        if (parameterMap.get("card-holder-name")[0].length()>0 && parameterMap.get("card-number")[0].length()== 4
                && parameterMap.get("cvv")[0].length()==3 && parameterMap.get("card-holder-name")[0].equals("xyz")
                &&  parameterMap.get("card-number")[0].equals("0123") && parameterMap.get("cvv")[0].equals("123"))
            {
            /*
            final JFrame parent = new JFrame();

            JButton b = new JButton();
            b.setLayout(new BorderLayout());
            //JLabel label1 = new JLabel("Succesfull paying");
            //JLabel label2 = new JLabel("You have payed "  + OrderItem.totalPrice + " $");
            b.add(BorderLayout.NORTH,label1);
            b.add(BorderLayout.SOUTH,label2);
            parent.add(b);
            parent.pack();
            parent.setVisible(true);
            CartItems.productList.clear();
            OrderItem.totalItems = 0;
            OrderItem.totalPrice = 0;
            */
            resp.sendRedirect("/confirmation");
            CartItems.productList.clear();
            OrderItem.totalItems = 0;
            OrderItem.totalPrice = 0;

        }
        System.out.println(parameterMap);

        context.setVariable("cartSize", OrderItem.totalItems);

        context.setVariable("cash", "9000000");
        engine.process("product/paypal.html", context, resp.getWriter());
        final JFrame parent = new JFrame();
        JButton button = new JButton();
        button.setText("Wrong data");
        parent.add(button);
        parent.pack();
        parent.setVisible(true);
    }
}