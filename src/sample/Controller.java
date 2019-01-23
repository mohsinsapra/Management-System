package sample;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    Connection connections;
    Statement stm;
    ResultSet resultSet;

    public void connection() {

        try {
            Class.forName("org.sqlite.JDBC");
            connections = DriverManager.getConnection("jdbc:sqlite:database.db");
            stm = connections.createStatement();

//            System.out.println("Connection Done");

        } catch (Exception e) {
            System.out.print("There is a Error" + e.getMessage());
        }


    }

    @FXML
    public TableView<Admin> table;
    @FXML
    public TableColumn<?, ?> id;
    @FXML
    public TableColumn<?, ?> name;
    @FXML
    public TableColumn<?, ?> email;
    @FXML
    public TableColumn<?, ?> pass;
    @FXML
    TextField a_name;
    @FXML
    TextField a_email;
    @FXML
    TextField a_pass;

    @FXML
    TextField u_name;
    @FXML
    TextField u_email;
    @FXML
    TextField u_pass;

    @FXML
    Circle circle;
    @FXML
    ImageView ViewAdmin;

    boolean canChange = false;
    String u_id;

    @FXML
    ImageView imageView;
    @FXML
    TextField search_name;

    @FXML
    Circle circle_Update;

    Image image;
    File profile_Image;

    File product_Image;

    //////////////////////////////// Employee//////////////////
    @FXML
    TableView<Employee> table_employee;

    @FXML
    TableColumn<?, ?> table_employee_id;

    @FXML
    TableColumn<?, ?> table_employee_name;
    //////////////////////////Product//////////////////////////
    @FXML
    TableView<Product> table_product;

    @FXML
    TableColumn<?, ?> product_id;

    @FXML
    TableColumn<?, ?> product_name;

    @FXML
    TableColumn<?, ?> product_description;

    @FXML
    TableColumn<?, ?> product_category;

    @FXML
    TableColumn<?, ?> product_rating;

    @FXML
    TableColumn<?, ?> product_price;

    @FXML
    TableColumn<?, ?> product_quantity;


    @FXML
    ChoiceBox choiceBox_category_product;

    @FXML
    ChoiceBox choiceBox_rating_product;

    @FXML
    TextField p_name;

    @FXML
    TextField p_price;

    @FXML
    TextArea p_description;

    @FXML
    Circle product_image;

    @FXML
    ChoiceBox choiceBox_category_product_update;

    @FXML
    ChoiceBox choiceBox_rating_product_update;

    @FXML
    TextField p_name_update;

    @FXML
    TextField p_price_update;

    @FXML
    TextField p_description_update;

    @FXML
    Circle product_image_update;
    @FXML
    ImageView ViewProduct;

    @FXML
    Button addnew_admin;
    @FXML
    ImageView imageViewadd;

    @FXML
    Button update_Admin;


    @FXML
    Button delete_Admin;

    @FXML
    Button Update_Product_btn;
    @FXML
    Button Delete_Product_btn;
    @FXML
    Button Add_Product_btn;
    @FXML
    ImageView imageViewProduct_select;
    String p_id;
    @FXML
    ImageView product_selector;

    Admin_Class admins;

    Product_Class product_class;

    @FXML
    ImageView imageViewAdmin_update;

    @FXML
    ChoiceBox search_type;
    @FXML
    ChoiceBox search_type_product;
    @FXML
    TextField search_name_product;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        connection();
        admins = new Admin_Class();
        product_class = new Product_Class();
        Create_Report();
/////////////////////////////////////////

        startup_Config();
        Retreiving();
        Setup();


    }

    void Setup() {

        table.setOnMouseClicked(e -> admins.Selection_Admin());
        table.setOnKeyReleased(e -> admins.Selection_Admin());
        addnew_admin.setOnAction(e -> admins.Add_Admin());
        delete_Admin.setOnMouseClicked(e -> admins.Delete_Admin());
        update_Admin.setOnAction(e -> admins.Update_Admin());

        imageViewadd.setOnMouseClicked(e -> admins.fileOpen());
        imageViewAdmin_update.setOnMouseClicked(e -> admins.fileOpenAdmin());

        imageViewProduct_select.setOnMouseClicked(e -> product_class.fileOpenProduct_update());
        product_selector.setOnMouseClicked(e -> product_class.fileOpenProduct());

        table_product.setOnMouseClicked(e -> product_class.Selection_Product());
        table_product.setOnKeyReleased(e -> product_class.Selection_Product());
        Update_Product_btn.setOnAction(e -> product_class.Update_Product());
        Delete_Product_btn.setOnAction(e -> product_class.Delete_Product());
        Add_Product_btn.setOnAction(e -> product_class.Add_Product());


        search_name.setOnKeyReleased(e->admins.SearchAdmin());

        search_name_product.setOnKeyReleased(e->product_class.SearchProduct());


    }


    ////////////////////////////////////////Start up/////////////////////////////////////
    void startup_Config() {//////////////////////////// Employee  ////////////////////////////////////////
        table_employee_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        table_employee_name.setCellValueFactory(new PropertyValueFactory<>("name"));
///////////////////////////////////////////////// Product//////////////////////////////
        product_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        product_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        product_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        product_category.setCellValueFactory(new PropertyValueFactory<>("category"));
        product_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        product_rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        product_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        choiceBox_category_product.getItems().add("<none>");
        choiceBox_category_product.getItems().add("Cakes");
        choiceBox_category_product.getItems().add("Candy");
        choiceBox_category_product.getItems().add("Cookies");
        choiceBox_category_product.getItems().add("Frozen");
        choiceBox_category_product.getItems().add("Low-Fat/Reduced-Fat");
        choiceBox_category_product.getItems().add("Breads");

        choiceBox_rating_product.getItems().add("<none>");
        choiceBox_rating_product.getItems().add("1");
        choiceBox_rating_product.getItems().add("2");
        choiceBox_rating_product.getItems().add("3");
        choiceBox_rating_product.getItems().add("4");
        choiceBox_rating_product.getItems().add("5");
        ////////////////////////////////////////Search Type///////////////////////
        search_type.getItems().add("<none>");
        search_type.getItems().add("ID");
        search_type.getItems().add("Name");
        search_type.getItems().add("Email");
        search_type.getSelectionModel().selectFirst();

        search_type_product.getItems().add("<none>");
        search_type_product.getItems().add("ID");
        search_type_product.getItems().add("Name");
        search_type_product.getItems().add("Description");
        search_type_product.getItems().add("Category");
        search_type_product.getItems().add("ProductRating");
        search_type_product.getItems().add("Price");
        search_type_product.getItems().add("QuantityOnHand");



        search_type_product.getSelectionModel().selectFirst();


        //search_type.set

/////////////////////////////////////////////////////Update Product////////////////////////////////////////
        choiceBox_category_product_update.getItems().add("<none>");
        choiceBox_category_product_update.getItems().add("Cakes");
        choiceBox_category_product_update.getItems().add("Candy");
        choiceBox_category_product_update.getItems().add("Cookies");
        choiceBox_category_product_update.getItems().add("Frozen");
        choiceBox_category_product_update.getItems().add("Low-Fat/Reduced-Fat");
        choiceBox_category_product_update.getItems().add("Breads");

        choiceBox_rating_product_update.getItems().add("<none>");
        choiceBox_rating_product_update.getItems().add("1");
        choiceBox_rating_product_update.getItems().add("2");
        choiceBox_rating_product_update.getItems().add("3");
        choiceBox_rating_product_update.getItems().add("4");
        choiceBox_rating_product_update.getItems().add("5");
        choiceBox_rating_product_update.getSelectionModel().selectFirst();
        choiceBox_category_product_update.getSelectionModel().selectFirst();

        ////////////////////////////////// Admin/////////////////////////////////
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        pass.setCellValueFactory(new PropertyValueFactory<>("password"));
        // image = imageView.getImage();

    }


    ////////////////////////////////////////////////////ADMIN///////////////////////
    class Admin_Class {
        public void Selection_Admin() {
            if (!table.getSelectionModel().isEmpty()) {
                TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Admin item = table.getItems().get(row);
                u_id = item.ID;
                u_email.setText(item.getEmail());
                u_name.setText(item.getName());
                u_pass.setText(item.getPassword());
                try {
                    File filf = new File("Profiles\\" + u_email.getText() + ".jpg");

                    Image img = new Image(filf.toURI().toString());
                    profile_Image = filf;
                    ViewAdmin.setImage(img);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        public void SearchAdmin() {
            int rowcount=0;
            connection();
            try {
                if (!search_name.getText().isEmpty() && !search_type.getSelectionModel().isSelected(0)) {
                    resultSet = stm.executeQuery("select * from admins where " + search_type.getSelectionModel().getSelectedItem().toString() + " like '" + search_name.getText() + "%'");

                    ObservableList<Admin> row = FXCollections.observableArrayList();

                    while (resultSet.next()) {

                        row.add(new Admin(resultSet.getString("id"),
                                resultSet.getString("name"),
                                resultSet.getString("email"),
                                resultSet.getString("password"))
                        );
                        rowcount++;

                        table.setItems(row);

                    }
                    if (rowcount<=0){
                        row.add(new Admin("","","",""));
                        table.setItems(row);
                    }
                } else {
                    Retreiving();
//                }
                }

            } catch (Exception e) {

            }
        }


        public void fileOpenAdmin() {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Profile Picture to Update");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            profile_Image = fileChooser.showOpenDialog(new Stage());

            if (profile_Image != null) {
                ViewAdmin.setImage(new Image(profile_Image.toURI().toString()));


            }

        }

        public void Add_Admin() {
            try {
                connection();
                Alert alert;

                if (!a_name.getText().isEmpty() && !a_email.getText().isEmpty() && !a_pass.getText().isEmpty() && profile_Image.toPath() != null) {//       stm.executeUpdate("insert into admins(name,email,password) values('saaa','ssssss','dfkjsd')");
                    stm.executeUpdate("insert into admins(name,email,password) values('" + a_name.getText() + "','" + a_email.getText() + "','" + a_pass.getText() + "')");
                    alert = new Alert(Alert.AlertType.INFORMATION);

                    alert.setTitle("Successfully!");
                    alert.setHeaderText("Successfully a New Admin Added");
                    alert.showAndWait();
                    File dest = new File("Profiles//" + a_email.getText() + ".jpg");
                    Files.copy(profile_Image.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    clear_Fields();
                    resultSet.close();
                    stm.close();
                    connections.close();


                    Retreiving();

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Something Missing");

                    alert.setHeaderText("Please check all Values to Be inserted something Missing");
                    alert.showAndWait();
//                System.out.println("Please check all Values to Be inserted");
                }


            } catch (Exception e) {
                System.out.print(e.getMessage());

            }
        }

        public void Update_Admin() {
            connection();
            try {
                Alert alert;
                if (!table.getSelectionModel().isEmpty()) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Permission");
                    alert.setHeaderText("Following data will be updated");
                    alert.setContentText("ID :" + u_id + "\n Name: " + u_name.getText() + "\n Email: " + u_email.getText() + "\n Password: " + u_pass.getText() + "\n\nAre you sure?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        File dest = new File("Profiles//" + u_email.getText() + ".jpg");
                        Files.copy(profile_Image.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        stm.executeUpdate("update admins set name='" + u_name.getText() + "', email='" + u_email.getText() + "',password='" + u_pass.getText() + "' where id='" + u_id + "'");
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Updated");
                        alert.setHeaderText("Successfully Updated");
                        alert.showAndWait();
                        clear_Fields();
                        stm.close();
                        connections.close();


                        Retreiving();
                    }

                } else {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nothing Seleted");
                    alert.setHeaderText("Please Select a row to Update");
                    alert.showAndWait();
                }


            } catch (Exception e) {
                System.out.println();
                System.out.println(e.getMessage() + "   Eror");
            }
        }

        public void Delete_Admin() {
            connection();
            Alert alert;

            try {

                if (!table.getSelectionModel().isEmpty()) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Permission");
                    alert.setHeaderText("Following data will be Deleted");
                    alert.setContentText("ID :" + u_id +
                            "\n Name: " + u_name.getText() +
                            "\n Email: " + u_email.getText() +
                            "\n Password: " + u_pass.getText() +
                            "\n\nAre you sure?");

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        stm.executeUpdate("Delete from admins  where id='" + u_id + "'");
                        File dest = new File("Profiles//" + u_email.getText() + ".jpg");
                        if (dest.exists())
                            Files.delete(dest.toPath());
                        else
                            System.out.print("file not found");
                        clear_Fields();
                        stm.close();
                        connections.close();
                        Retreiving();


                    } else {
                        System.out.print("error is in deleteing");
                    }

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nothing Seleted");
                    alert.setHeaderText("Please Select a row to Delete");
                    alert.showAndWait();
                }


            } catch (Exception e) {
                System.out.print("error is in deleteing");
                System.out.println(e.getMessage());
            }
        }

        public void fileOpen() {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Profile Picture");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            profile_Image = fileChooser.showOpenDialog(new Stage());

            if (profile_Image != null) {
                circle.setFill(new ImagePattern(new Image(profile_Image.toURI().toString())));


            }

        }

        public void Changed() {
//        System.out.println("Can Change");
            canChange = !canChange;
        }
    }


    ////////////////////////////////////////Clear Fields//////////////////////////////////////////////
    void clear_Fields() {
        u_name.clear();
        u_email.clear();
        u_pass.clear();

        a_name.clear();
        a_email.clear();
        a_pass.clear();
//        imageView.setImage(null);
        circle.setFill(null);
        product_image.setFill(null);
        profile_Image = null;
        ViewAdmin.setImage(null);
        p_description.clear();
        p_price.clear();
        p_name.clear();
        ViewProduct.setImage(null);
        p_name_update.clear();
        p_description_update.clear();
        p_price_update.clear();
        choiceBox_category_product_update.getSelectionModel().selectFirst();
        choiceBox_rating_product_update.getSelectionModel().selectFirst();
    }

    void Create_Report() {
        try {

            Document d = new Document();
            PdfWriter.getInstance(d, new FileOutputStream("Report.pdf"));
            PdfPTable pt = new PdfPTable(5);
            d.open();
            d.add(new Paragraph("Admins Report"));
            pt.addCell("Your ");
            pt.addCell("Name ");
            pt.addCell("Cast ");
            pt.addCell("Age ");
            pt.addCell("Age ");
            d.add(pt);

            d.close();

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    ///////////////////////////////////////////Products/////////////////////////////
    class Product_Class {
        public void Selection_Product() {
            if (!table_product.getSelectionModel().isEmpty()) {
                TablePosition pos = table_product.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                Product item = table_product.getItems().get(row);
                p_id = item.getId();
                p_name_update.setText(item.getName());
                p_price_update.setText(item.getPrice());
                p_description_update.setText(item.getDescription());
                choiceBox_category_product_update.setValue(item.getCategory());
                choiceBox_rating_product_update.setValue(item.getRating());

                try {
                    File filf = new File("Products\\" + item.name + ".jpg");
                    product_Image = filf;
                    Image img = new Image(filf.toURI().toString());
                    ViewProduct.setImage(img);
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        public void SearchProduct() {
            int rowcount=0;
            connection();
            try {
                if (!search_name_product.getText().isEmpty() && !search_type_product.getSelectionModel().isSelected(0)) {
                    resultSet = stm.executeQuery("select * from product where " + search_type_product.getSelectionModel().getSelectedItem().toString() + " like '" + search_name_product.getText() + "%'");

                    ObservableList<Product> p_row = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        p_row.add(new Product(resultSet.getString("id"),
                                        resultSet.getString("name"),
                                        resultSet.getString("description"),
                                        resultSet.getString("category"),
                                        resultSet.getString("productrating"),
                                        resultSet.getString("price"),
                                        resultSet.getString("quantityonhand")
                                )
                        );
                        table_product.setItems(p_row);


                    }

                    if (rowcount<=0){
                        p_row.add(new Product("","","","","","",""));
                        table_product.setItems(p_row);
                    }
                } else {
                    Retreiving();
//                }
                }

            } catch (Exception e) {

            }
        }


        public void Update_Product() {
            connection();
            try {
                Alert alert;
                if (!table_product.getSelectionModel().isEmpty()) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Permission");
                    alert.setHeaderText("Following data will be updated");
                    alert.setContentText("ID :" + u_id +
                            "\n Name: " + p_name_update.getText() +
                            "\n Price: " + p_price_update.getText() +
                            "\n Description: " + p_description_update.getText() +
                            "\n Category: " + choiceBox_category_product_update.getSelectionModel().getSelectedItem().toString() +
                            "\n Rating: " + choiceBox_rating_product_update.getSelectionModel().getSelectedItem().toString() +

                            "\n\nAre you sure?");
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {
                        File dest = new File("Products//" + p_name_update.getText() + ".jpg");

                        Files.copy(product_Image.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                        stm.executeUpdate("update product set name='" + p_name_update.getText() +
                                "', category='" + choiceBox_category_product_update.getSelectionModel().getSelectedItem().toString() +
                                "',productrating='" + choiceBox_rating_product_update.getSelectionModel().getSelectedItem().toString() +

                                "',price='" + p_price_update.getText() +

                                "',description='" + p_description_update.getText() +

                                "' where id='" + p_id + "'");
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Updated");
                        alert.setHeaderText("Successfully Updated");
                        alert.showAndWait();
                        clear_Fields();
                        stm.close();
                        connections.close();


                        Retreiving();

//                    canChange=true;
                        // ... user chose OK
                    } else {
//System.out.print("Data");
                        // ... user chose CANCEL or closed the dialog
                    }

                } else {

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nothing Seleted");
                    alert.setHeaderText("Please Select a row to Update");
                    alert.showAndWait();
                }


            } catch (Exception e) {
                System.out.println();
                System.out.println(e.getMessage());
            }
        }

        public void Delete_Product() {
            connection();
            Alert alert;

            try {

                if (!table_product.getSelectionModel().isEmpty()) {
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Permission");
                    alert.setHeaderText("Following data will be Deleted");
                    alert.setContentText("ID :" + p_id +
                            "\n Name: " + p_name_update.getText() +
                            "\n Price: " + p_price_update.getText() +
                            "\n Category: " + choiceBox_category_product.getSelectionModel().getSelectedItem() +
                            "\n Description: " + p_description_update.getText() +
                            "\n Rating: " + choiceBox_category_product.getSelectionModel().getSelectedItem() +

                            "\n\nAre you sure?");

                    //                ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
//                ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
//                alert.getButtonTypes().setAll(yes, no);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK) {

                        stm.executeUpdate("Delete from product  where id='" + p_id + "'");
                        File dest = new File("Products//" + p_name_update.getText() + ".jpg");
                        if (dest.exists())
                            Files.delete(dest.toPath());
                        else
                            System.out.print("file not found");
                        //                    System.out.println("Deleted");
                        clear_Fields();
                        stm.close();
                        connections.close();
                        Retreiving();


                    }

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Nothing Seleted");
                    alert.setHeaderText("Please Select a row to Delete");
                    alert.showAndWait();
                }


            } catch (Exception e) {
                System.out.print("error is in deleteing");
                System.out.println(e.getMessage());
            }
        }

        public void Add_Product() {

            try {
                connection();
                Alert alert;

                if (!p_name.getText().isEmpty() &&
                        !p_price.getText().isEmpty() &&
                        !choiceBox_category_product.getSelectionModel().isSelected(0) &&
                        product_Image.toPath() != null) {

                    if (!isHave_product(p_name.getText(), choiceBox_category_product.getSelectionModel().getSelectedItem().toString())) {
                        stm.executeUpdate("insert into product(name,category,price,productrating,description,quantityonhand) values('" +
                                p_name.getText() + "','"
                                + choiceBox_category_product.getSelectionModel().getSelectedItem().toString() +
                                "','" + p_price.getText() +
                                "','" + choiceBox_rating_product.getSelectionModel().getSelectedItem().toString() +
                                "','" + p_description.getText() +
                                "', '1')");

                        alert = new Alert(Alert.AlertType.INFORMATION);

                        alert.setTitle("Successfully!");
                        alert.setHeaderText("Successfully a New Product Added");
                        alert.showAndWait();
                        File dest = new File("Products//" + p_name.getText() + ".jpg");
                        Files.copy(product_Image.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

                        clear_Fields();
                        resultSet.close();
                        stm.close();
                        connections.close();


                        Retreiving();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);

                        alert.setTitle("Product Exist");
                        alert.setHeaderText("You already have the Product. Goto Update product");
                        alert.showAndWait();

                    }

                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Something Missing");

                    alert.setHeaderText("Please check all Values to Be inserted something Missing");
                    alert.showAndWait();
//                System.out.println("Please check all Values to Be inserted");
                }


            } catch (Exception e) {
                System.out.print(e.getMessage());

            }
        }


        public void fileOpenProduct() {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Product Picture ");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );
            product_Image = fileChooser.showOpenDialog(new Stage());

            if (product_Image != null) {
                product_image.setFill(new ImagePattern(new Image(product_Image.toURI().toString())));


            }
        }

        public void fileOpenProduct_update() {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Product Picture to Update");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Images", "*.*"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png")
            );

            product_Image = fileChooser.showOpenDialog(new Stage());

            if (product_Image != null) {
                ViewProduct.setImage(new Image(product_Image.toURI().toString()));


            }
        }

    }

    ///////////////////////////////////////// Have Product////////////////////////////////
    boolean isHave_product(String product, String type) {
        boolean flag = false;

        try {
//            System.out.print(product);
            connection();
            ResultSet rss = stm.executeQuery("select * from product where name='" + product + "' AND category='" + type + "'");
//            System.out.print(product + "  " + type);
            while (rss.next())
                if (rss.getRow() > 0) {
                    flag = true;

                }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
//        System.out.print("Not Exist");
    }

    ///////////////////////////////////////Search Data////////////////////////////

    /////////////////////////////////////////Fetch Data/////////////////////////////////////
    public void Retreiving() {
        try {
            connection();
/////////////////////////////// Admin//////////////////////////////
            resultSet = stm.executeQuery("select * from admins");
            ObservableList<Admin> row = FXCollections.observableArrayList();
            while (resultSet.next()) {
                row.add(new Admin(resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("password"))
                );
                table.setItems(row);

            }
            ///////////////////////////////////Employee /////////////////////////////////
            ObservableList<Employee> row_employee = FXCollections.observableArrayList();

            resultSet = stm.executeQuery("select * from employee");

            while (resultSet.next()) {
                row_employee.add(new Employee(resultSet.getString("e_id"),
                        resultSet.getString("e_name"))
                );

                table_employee.setItems(row_employee);

            }
///////////////////////////////////////////////////Product//////////////////////////////////
            resultSet = stm.executeQuery("select * from product");
            ObservableList<Product> p_row = FXCollections.observableArrayList();
            while (resultSet.next()) {
                p_row.add(new Product(resultSet.getString("id"),
                                resultSet.getString("name"),
                                resultSet.getString("description"),
                                resultSet.getString("category"),
                                resultSet.getString("productrating"),
                                resultSet.getString("price"),
                                resultSet.getString("quantityonhand")
                        )
                );
                table_product.setItems(p_row);


            }

            choiceBox_category_product.getSelectionModel().selectFirst();
            choiceBox_rating_product.getSelectionModel().selectFirst();


////////////////////////////////////////////////
            resultSet.close();
            stm.close();
            connections.close();
        } catch (Exception e) {

        }
    }

    ////////////////////////////////// Open Images///////////////////////////////////


}