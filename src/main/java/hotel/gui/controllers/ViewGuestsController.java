package hotel.gui.controllers;

import hotel.gui.GuiData;
import hotel.services.GuestManager;
import hotel.users.Guest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static hotel.gui.GuiData.guestManager;

public class ViewGuestsController {
    @FXML
    private TableView<Guest> GuestTable;
    @FXML
    private TableColumn<Guest, String> usernameCol;
    @FXML
    private TableColumn<Guest, String> passwordCol;
    @FXML
    private TableColumn<Guest, String> dateOfBirthCol;
    @FXML
    private TableColumn<Guest,Double>BalanceCol;
    @FXML
    private TableColumn<Guest,String> AddressCol;
    @FXML
    private TableColumn<Guest, String> GenderCol;
    @FXML
    private TextField searchField;
    @FXML
    public void Back(ActionEvent e) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/hotel/gui/scenes/receptionist-dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 1920, 1080);

        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void initialize(){
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        BalanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        AddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        GenderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ObservableList List= FXCollections.observableArrayList();
        for(Guest g: guestManager.getGuests()){
            List.add(g);
        }
        GuestTable.setItems(List);

    }
    public void searchbyname(){
        String name=searchField.getText();
        if(name.isEmpty()){
            System.out.println("please enter name");
            return;
        }
        ObservableList namee =FXCollections.observableArrayList();
        for(Guest g: GuiData.guestManager.getGuests()){
            if(g.getUsername().equals(name)){
                namee.add(g);
            }
        }
        GuestTable.setItems(namee);
        if(namee.isEmpty()){
            System.out.println("The name was not found");
            return;
        }
    }
    public void viewAll(){
        ObservableList All=FXCollections.observableArrayList();
        for(Guest g:GuiData.guestManager.getGuests()){
            All.add(g);
        }
        GuestTable.setItems(All);
    }
}