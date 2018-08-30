package com.codecool.shop.dao.implementation;

import com.codecool.shop.dao.SupplierDao;
import com.codecool.shop.model.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDaoMem implements SupplierDao {

    private List<Supplier> data = new ArrayList<>();
    private static SupplierDaoMem instance = null;

    /* A private Constructor prevents any other class from instantiating.
     */
    private SupplierDaoMem() {
    }

    public static SupplierDaoMem getInstance() {
        if (instance == null) {
            instance = new SupplierDaoMem();
        }
        return instance;
    }

    @Override
    public void add(Supplier supplier) {
        if (findExistingSupplier(supplier)) {
            supplier.setId(data.size() + 1);
            data.add(supplier);
        }
    }

    public boolean findExistingSupplier(Supplier givenSupplier) {
        for (Supplier supplier : data){
            if (supplier.getName().equals(givenSupplier.getName())) {
                return false;
            }
        } return true;
    }

    @Override
    public Supplier find(int id) {
        return data.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Supplier findByName(String name) throws SQLException {
        return null;
    }

    @Override
    public void remove(int id) {
        if (data.size() > 0) {
            data.remove(find(id));
        }
    }

    @Override
    public List<Supplier> getAll() {
        return data;
    }
}
