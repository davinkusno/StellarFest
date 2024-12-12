package view.vendor.view;

import controller.view.vendor.view.VendorProductsViewController;
import datastore.UserDatastore;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import model.VendorProduct;
import model.user.User;
import model.user.impl.VendorUser;
import view.Refreshable;
import view.SFView;
import view.StageManager;
import view.component.TopBar;
import view.vendor.VendorHomeView;
import view.vendor.create.CreateVendorProduct;

public class VendorProductsView extends SFView implements Refreshable {

    private final ObservableList<VendorProduct> vendorProducts;

    public VendorProductsView(StageManager stageManager) {
        super(stageManager);
        BorderPane root = new BorderPane();
        this.vendorProducts = FXCollections.observableArrayList();

        this.prepareView(root);

        this.windowTitle = "View Products";
        this.scene = stageManager.getSceneFactory().createScene(root);
    }

    @Override
    protected void prepareView(Pane root) {
        BorderPane borderPane = (BorderPane) root;

        TableView<VendorProduct> productsTable = this.createProductsTable(vendorProducts);
        borderPane.setCenter(productsTable);

        HBox buttonContainer = new HBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20));

        Button addButton = this.createAddButton();
        buttonContainer.getChildren().add(addButton);

        borderPane.setBottom(buttonContainer);

        Pane topBar = TopBar.getTopBar(VendorHomeView.class);
        borderPane.setTop(topBar);
    }

    @Override
    public void destroyView() {
        vendorProducts.clear();
    }

    @Override
    public void refreshData() {
        User user = UserDatastore.getInstance().getCurrentUser();
        VendorProductsViewController.loadProducts(vendorProducts, user);
    }

    private TableView<VendorProduct> createProductsTable(ObservableList<VendorProduct> events) {
        TableView<VendorProduct> tableView = new TableView<>();

        TableColumn<VendorProduct, Long> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));

        TableColumn<VendorProduct, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getName()));

        TableColumn<VendorProduct, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDescription()));

        idColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.1));
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        descriptionColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.7));

        tableView.getColumns().addAll(idColumn, nameColumn, descriptionColumn);
        tableView.setItems(events);

        return tableView;
    }

    private Button createAddButton() {
        Button addButton = new Button("Add New Product");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        addButton.setPrefWidth(200);

        addButton.setOnMouseClicked(e -> {
            User user = UserDatastore.getInstance().getCurrentUser();
            new CreateVendorProduct((VendorUser) user, this::refreshData).show();
        });

        return addButton;
    }

}
