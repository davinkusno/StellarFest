package controller.view.vendor.view;

import controller.VendorProductController;
import javafx.collections.ObservableList;
import model.VendorProduct;
import model.user.User;

import java.util.List;

public class VendorProductsViewController {

    public static void loadProducts(ObservableList<VendorProduct> vendorProducts, User user) {
        vendorProducts.clear();

        // Alternative implementation
        List<VendorProduct> products = VendorProductController.getForUser(user.getId());
        vendorProducts.addAll(products);
    }

}
