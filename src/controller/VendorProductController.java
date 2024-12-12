package controller;

import driver.Connect;
import driver.Results;
import model.VendorProduct;
import model.join.JoinField;
import model.user.impl.VendorUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VendorProductController {

    public static List<VendorProduct> getAll() {
        List<VendorProduct> vendorProducts = new ArrayList<>();

        String query = "SELECT * FROM vendor_product";
        try (Results results = Connect.getInstance().executeQuery(query)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                VendorProduct vendorProduct = fromResultSet(set);
                vendorProducts.add(vendorProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vendorProducts;
    }

    public static List<VendorProduct> getMany(List<Long> ids) {
        List<VendorProduct> vendorProducts = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return vendorProducts;
        }

        String placeholders = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = "SELECT * FROM vendor_product WHERE id IN (" + placeholders + ")";
        try (Results results = Connect.getInstance().executeQuery(query, ids.toArray())) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                VendorProduct vendorProduct = fromResultSet(set);
                vendorProducts.add(vendorProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vendorProducts;
    }

    public static List<VendorProduct> getForUser(long userId) {
        List<VendorProduct> vendorProducts = new ArrayList<>();

        String query = "SELECT * FROM vendor_product WHERE vendor_id = ?";
        try (Results results = Connect.getInstance().executeQuery(query, userId)) {
            ResultSet set = results.getResultSet();
            while (set.next()) {
                VendorProduct vendorProduct = fromResultSet(set);
                vendorProducts.add(vendorProduct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return vendorProducts;
    }

    public static boolean save(VendorProduct vendorProduct) {
        String query = "INSERT INTO vendor_product (name, description, vendor_id) VALUES (?, ?, ?)";
        try {
            Connect.getInstance().executeUpdate(query, vendorProduct.getName(), vendorProduct.getDescription(), vendorProduct.getVendor().getId());
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save vendor product", e);
        }
    }

    public static boolean saveAll(List<VendorProduct> vendorProducts) {
        boolean success = true;

        for (VendorProduct vendorProduct : vendorProducts) {
            success &= save(vendorProduct);
        }

        return success;
    }

    private static VendorProduct fromResultSet(ResultSet set) throws SQLException {
        long id = set.getLong("id");
        String name = set.getString("name");
        String description = set.getString("description");
        long vendorId = set.getLong("vendor_id");

        return createVendorProduct(id, name, description, vendorId);
    }

    private static VendorProduct createVendorProduct(long id, String name, String description, long vendorId) {
        VendorProduct vendorProduct = new VendorProduct(id, name, description);

        JoinField<VendorUser> vendorUserJoinField = new JoinField<>(vendorId, () -> (VendorUser) UserController.getOne(vendorId));
        vendorProduct.setVendor(vendorUserJoinField);

        return vendorProduct;
    }
}
