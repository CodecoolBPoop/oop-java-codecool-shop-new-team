
package com.codecool.shop.controller;

import com.codecool.shop.config.TemplateEngineUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
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

        if (parameterMap.get("card-holder-name")[0].length()>0 && parameterMap.get("card-number")[0].length()==10
                && parameterMap.get("cvv")[0].length()==3 && parameterMap.get("card-holder-name")[0].equals("xyz")
                &&  parameterMap.get("card-number")[0].equals("0123456789") && parameterMap.get("cvv")[0].equals("123"))
            {
            final JFrame parent = new JFrame();
            JButton button = new JButton();
            button.setText("Succesfull paying");
            parent.add(button);
            parent.pack();
            parent.setVisible(true);
            resp.sendRedirect("/");

        }
        System.out.println(parameterMap);
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