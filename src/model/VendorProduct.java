package model;

import model.join.JoinField;
import model.user.impl.VendorUser;

import java.util.Objects;

public class VendorProduct {

    private final long id;
    private String name;
    private String description;

    private JoinField<VendorUser> vendor;

    public VendorProduct(long id) {
        this.id = id;
    }

    public VendorProduct(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JoinField<VendorUser> getVendor() {
        return vendor;
    }

    public void setVendor(JoinField<VendorUser> vendor) {
        this.vendor = vendor;
    }

    @Override
    public String toString() {
        return "VendorProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", vendor=" + vendor.getId() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof VendorProduct)) return false;
        VendorProduct that = (VendorProduct) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
