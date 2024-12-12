package controller.view.vendor.create;

import javafx.scene.control.TextField;
import model.user.impl.VendorUser;

public class CreateVendorProductController {

    public static void saveVendorProduct(VendorUser user, TextField nameInput, TextField descriptionInput) {
        String name = nameInput.getText();
        String description = descriptionInput.getText();

    }

}
