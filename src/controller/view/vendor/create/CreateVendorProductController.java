package controller.view.vendor.create;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.user.impl.VendorUser;
import util.AlertUtil;
import util.Callable;

public class CreateVendorProductController {

    public static void saveVendorProduct(VendorUser user, TextField nameInput, TextArea descriptionInput, Callable refreshCallable) {
        String name = nameInput.getText();
        String description = descriptionInput.getText();

        if (name.isEmpty()) {
            AlertUtil.showError("Name is required", "Please enter the event name");
            return;
        }

        if (description.isEmpty()) {
            AlertUtil.showError("Description is required", "Please enter the event description");
            return;
        }

        if (description.length() > 200) {
            AlertUtil.showError("Description is too long", "Please enter a description with at most 200 characters");
            return;
        }

        boolean saved = saveProduct(user, name, description);
        if (!saved) {
            AlertUtil.showError("Product creation failed", "An error occurred while creating the product");
            return;
        }

        AlertUtil.showInfo("Product created", "The product has been created successfully", refreshCallable);
    }

    private static boolean saveProduct(VendorUser user, String name, String description) {
        // Save the product
        return true;
    }

}
