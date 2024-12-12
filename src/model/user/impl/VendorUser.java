package model.user.impl;

import model.VendorProduct;
import model.join.JoinFields;
import model.user.User;
import view.SFView;
import view.vendor.VendorHomeView;

public class VendorUser extends User {

    private JoinFields<VendorProduct> vendorProducts;

    public VendorUser(long id) {
        super(id);
    }

    public VendorUser(long id, String email, String username) {
        super(id, email, username);
    }

    @Override
    public Class<? extends SFView> getHomeView() {
        return VendorHomeView.class;
    }

    public JoinFields<VendorProduct> getVendorProducts() {
        return vendorProducts;
    }

    public void setVendorProducts(JoinFields<VendorProduct> vendorProducts) {
        this.vendorProducts = vendorProducts;
    }
}
