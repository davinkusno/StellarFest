package controller.view.vendor.view;

import javafx.collections.ObservableList;
import model.VendorProduct;
import model.user.User;
import model.user.impl.VendorUser;

import java.util.List;

public class VendorProductsViewController {

    public static void loadProducts(ObservableList<VendorProduct> vendorProducts, User user) {
        vendorProducts.clear();

        // Alternative implementation
//        List<VendorProduct> products = VendorProductController.getForUser(user.getId());
//        vendorProducts.addAll(products);

        VendorUser vendorUser = (VendorUser) user;
        List<VendorProduct> products = vendorUser.getVendorProducts().getValue();
        vendorProducts.addAll(products);
    }

}
