package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.ProductDao;
import com.codecool.shop.model.Product;
import com.codecool.shop.model.ProductCategory;
import com.codecool.shop.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJDBC implements ProductDao {

    private static ProductDaoJDBC instance = null;
    JDBCConnection conn = new JDBCConnection();
    PreparedStatement preparedStatement = null;
    Connection con = conn.getConnection();

    ProductCategoryDaoJDBC categoriesDB = ProductCategoryDaoJDBC.getInstance();
    SupplierDaoJDBC suppliersDB = SupplierDaoJDBC.getInstance();

    private ProductDaoJDBC() throws SQLException {
    }

    public static ProductDaoJDBC getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProductDaoJDBC();
        }
        return instance;
    }

    @Override
    public void add(Product product) throws SQLException {
        String addProductQuery = "INSERT INTO product (name, price, currency, description, category_id, supplier_id)" +
                "VALUES (?,?,?,?,?,?);";

            preparedStatement = con.prepareStatement(addProductQuery);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setFloat(2, product.getDefaultPrice());
            preparedStatement.setString(3, product.getDefaultCurrency().toString());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getProductCategory().getId());
            preparedStatement.setInt(6, product.getSupplier().getId());
            preparedStatement.executeUpdate();
    }

    @Override
    public Product find(int id) throws SQLException {
        String findProductQuery = "SELECT * FROM product WHERE id =?;";

            preparedStatement = con.prepareStatement(findProductQuery);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getProduct(id, resultSet);
        } return null;
    }

    @Override
    public Product findByName(String name) throws SQLException {
        String findProductQuery = "SELECT * FROM product WHERE name = ?;";

            preparedStatement = con.prepareStatement(findProductQuery);
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getProduct(resultSet);
        } return null;
    }

    @Override
    public void remove(int id) throws SQLException {
        String removeProductQuery = "DELETE FROM product WHERE id =?;";

            preparedStatement = con.prepareStatement(removeProductQuery);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

    }

    @Override
    public List<Product> getAll() throws SQLException {
        String getAllProductsQuery = "SELECT * FROM product ORDER BY id;";

            preparedStatement = con.prepareStatement(getAllProductsQuery);
            ResultSet allProducts = preparedStatement.executeQuery();
            List <Product> productList = new ArrayList<Product>();
            while (allProducts.next()) {
                productList.add(getProduct(allProducts));
            }
            return productList;
    }

    @Override
    public List<Product> getBy(Supplier supplier) throws SQLException {
        String getProductsBySupplierQuery = "SELECT * FROM product WHERE supplier_id = ?;";

            preparedStatement = con.prepareStatement(getProductsBySupplierQuery);
            preparedStatement.setInt(1, supplier.getId());
            ResultSet allProducts = preparedStatement.executeQuery();
            List <Product> productList = new ArrayList<Product>();
            while (allProducts.next()) {
                productList.add(getProduct(allProducts.getInt("id"), allProducts));
            }
            return productList;
    }

    @Override
    public List<Product> getBy(ProductCategory productCategory) throws SQLException {
        String getProductsByCategoryQuery = "SELECT * FROM product WHERE category_id = ?;";

            preparedStatement = con.prepareStatement(getProductsByCategoryQuery);
            preparedStatement.setInt(1, productCategory.getId());
            ResultSet allProducts = preparedStatement.executeQuery();
            List <Product> productList = new ArrayList<Product>();
            while (allProducts.next()) {
                productList.add(getProduct(allProducts.getInt("id"), allProducts));
            }
            return productList;
    }

    public int getLastId() throws SQLException {
        String findProductIdQuery = "SELECT id FROM product WHERE id = (SELECT max(id) FROM product);";

        preparedStatement = con.prepareStatement(findProductIdQuery);
        ResultSet resultSet = preparedStatement.executeQuery();
        int lastId = 0;
        if (resultSet.next()) {
            lastId = resultSet.getInt("id");
            return lastId;
        } return lastId;
    }

    private Product getProduct(int productId, ResultSet resultSet) throws SQLException {
        Product resultProduct = new Product(resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("currency"),
                resultSet.getString("description"),
                categoriesDB.find(resultSet.getInt("category_id")),
                suppliersDB.find(resultSet.getInt("supplier_id")));
        resultProduct.setId(productId);

        return resultProduct;
    }

    private Product getProduct(ResultSet resultSet) throws SQLException {
        Product resultProduct = new Product(resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("currency"),
                resultSet.getString("description"),
                categoriesDB.find(resultSet.getInt("category_id")),
                suppliersDB.find(resultSet.getInt("supplier_id")));
        resultProduct.setId(resultSet.getInt("id"));

        return resultProduct;
    }

    public void deleteDataFromTable(){
        String removeProductQuery = "DELETE FROM product;";

        try {
            preparedStatement = con.prepareStatement(removeProductQuery);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void resetId() {
        String resetIdQuery = "ALTER SEQUENCE product_id_seq RESTART WITH 1;";
        try {
            preparedStatement = con.prepareStatement(resetIdQuery);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public void startId() {
        String resetIdQuery =
                "UPDATE product SET id=nextval('product_id_seq');";
        try {
            preparedStatement = con.prepareStatement(resetIdQuery);
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
