package com.dbclientapp.mainscreen;

import com.dbclientapp.Application;
import com.dbclientapp.appointment.Appointment;
import com.dbclientapp.appointment.AppointmentDAO;
import com.dbclientapp.customer.Customer;
import com.dbclientapp.customer.CustomerDAO;
import com.dbclientapp.login.LoginController;
import com.dbclientapp.user.User;
import com.dbclientapp.util.DatabaseConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**
 * Controller that handles main menu screen.
 */
public class MainScreenController implements Initializable {

    public static Customer selectedCustomer = new Customer();
    public static Appointment selectedAppointment = new Appointment();
    private static final ObservableList<Customer> custList = FXCollections.observableArrayList();
    private static final ObservableList<Appointment> apptList = FXCollections.observableArrayList();
    private static final ObservableList<String> report1List = FXCollections.observableArrayList();
    private static final ObservableList<String> report3List = FXCollections.observableArrayList();
    private static final ObservableList<String> reportList = FXCollections.observableArrayList(
            "Report #1",
            "Report #2",
            "Report #3");
    private final ObservableList<String> contactList = FXCollections.observableArrayList(
            "Anika Costa",
            "Daniel Garcia",
            "Li Lee");

    @FXML
    private Button apptAddButton;
    @FXML
    private TableColumn<Appointment, String> apptContactCol;
    @FXML
    private TableColumn<Appointment, Number> apptCustIdCol;
    @FXML
    private Button apptDeleteButton;
    @FXML
    private TableColumn<?, ?> apptDescrCol;
    @FXML
    private Button apptEditButton;
    @FXML
    private TableColumn<?, ?> apptEndCol;
    @FXML
    private TableColumn<Appointment, ?> apptIdCol;
    @FXML
    private TableColumn<?, ?> apptLocationCol;
    @FXML
    private TableColumn<?, ?> apptStartCol;
    @FXML
    private Tab apptTab;
    @FXML
    private TableView<Appointment> apptTable;
    @FXML
    private TableColumn<?, ?> apptTitleCol;
    @FXML
    private TableColumn<?, ?> apptTypeCol;
    @FXML
    private TableColumn<Appointment, Number> apptUserIdCol;
    @FXML
    private Button custAddButton;
    @FXML
    private TableColumn<?, ?> custAddressCol;
    @FXML
    private TableColumn<Customer, String> custCountryCol;
    @FXML
    private Button custDeleteButton;
    @FXML
    private TableColumn<Customer, String> custDivisionCol;
    @FXML
    private Button custEditButton;
    @FXML
    private TableColumn<Customer, ?> custIdCol;
    @FXML
    private TableColumn<?, ?> custNameCol;
    @FXML
    private TableColumn<?, ?> custPhoneCol;
    @FXML
    private TableColumn<?, ?> custPostalCol;
    @FXML
    private Tab custTab;
    @FXML
    private TableView<Customer> custTable;
    @FXML
    private RadioButton monthRadio;
    @FXML
    private RadioButton noneRadio;
    @FXML
    private ListView<String> report1ListView;
    @FXML
    private TableColumn<Appointment, ?> report2ApptIdCol;
    @FXML
    private ComboBox<String> report2Box;
    @FXML
    private TableColumn<Appointment, Number> report2CustIdCol;
    @FXML
    private TableColumn<?, ?> report2DescrCol;
    @FXML
    private TableColumn<?, ?> report2EndCol;
    @FXML
    private TableColumn<?, ?> report2LocationCol;
    @FXML
    private TableColumn<?, ?> report2StartCol;
    @FXML
    private TableView<Appointment> report2Table;
    @FXML
    private TableColumn<?, ?> report2TitleCol;
    @FXML
    private ToolBar report2ToolBar;
    @FXML
    private TableColumn<?, ?> report2TypeCol;
    @FXML
    private ListView<String> report3ListView;
    @FXML
    private ComboBox<String> reportBox;
    @FXML
    private Button reportRunButton;
    @FXML
    private Tab reportTab;
    @FXML
    private TabPane tabPane;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private RadioButton weekRadio;

    /**
     * Handles action for when add appointment button is pressed.
     * @param event
     */
    @FXML
    void apptAddButtonAction(ActionEvent event) {
        try {
            setSelectedCustomer(custTable.getSelectionModel().getSelectedItem());
            goToScreen(event, "../Appointment/AppointmentAdd.fxml");
        } catch (Exception e) {
            Application.showError("Please select a customer to create an appointment.");
        }
    }

    /**
     * Handles action for when edit appointment button is pressed.
     * @param event
     */
    @FXML
    void apptEditButtonAction(ActionEvent event) {
        try {
            setSelectedAppointment(apptTable.getSelectionModel().getSelectedItem());
            goToScreen(event, "../Appointment/AppointmentEdit.fxml");
        } catch (Exception e) {
            Application.showError("Please select an appointment to edit.");
        }
    }

    /**
     * Handles action for when delete appointment button is pressed.
     * @param event
     */
    @FXML
    void apptDeleteButtonAction(ActionEvent event) {
        try {
            setSelectedAppointment(apptTable.getSelectionModel().getSelectedItem());
            deleteAppointment(selectedAppointment);
        } catch (Exception e) {
            Application.showError("Please select an appointment to delete.");
        }
        populateTableView();
    }

    /**
     * Handles action for when add customer button is pressed.
     * @param event
     */
    @FXML
    void custAddButtonAction(ActionEvent event) throws IOException {
        goToScreen(event, "../Customer/CustomerAdd.fxml");
    }

    /**
     * Handles action for when edit customer button is pressed.
     * @param event
     */
    @FXML
    void custEditButtonAction(ActionEvent event) {
        try {
            setSelectedCustomer(custTable.getSelectionModel().getSelectedItem());
            goToScreen(event, "../Customer/CustomerEdit.fxml");
        } catch (Exception e) {
            Application.showError("Please select a customer to edit.");
        }
    }

    /**
     * Handles action for when delete customer button is pressed.
     * @param event
     */
    @FXML
    void custDeleteButtonAction(ActionEvent event) {
        try {
            setSelectedCustomer(custTable.getSelectionModel().getSelectedItem());
            deleteCustomer(selectedCustomer);
        } catch (Exception e) {
            Application.showError("Please select a customer to delete.");
        }
        populateTableView();
    }

    /**
     * Handles action for when "filter by none" radio button is pressed.
     * @param event
     */
    @FXML
    void noneRadioAction(ActionEvent event) {
        if(toggleGroup.getSelectedToggle().equals(noneRadio))
            apptFilterNone();
    }

    /**
     * Handles action for when "filter by week" radio button is pressed.
     * @param event
     */
    @FXML
    void weekRadioAction(ActionEvent event) {
        if(toggleGroup.getSelectedToggle().equals(weekRadio))
            apptFilterWeek();
    }

    /**
     * Handles action for when "filter by month" radio button is pressed.
     * @param event
     */
    @FXML
    void monthRadioAction(ActionEvent event) {
        if(toggleGroup.getSelectedToggle().equals(monthRadio))
            apptFilterMonth();
    }

    /**
     * Handles action for when run report button is pressed.
     * @param event
     */
    @FXML
    void reportRunButtonAction(ActionEvent event) {
        if(reportBox.getSelectionModel().isSelected(0)) {
            report1ListView.setVisible(true);
            report2ToolBar.setVisible(false);
            report2Table.setVisible(false);
            report3ListView.setVisible(false);
            populateReport1ListView();
        }
        else if(reportBox.getSelectionModel().isSelected(1)) {
            report1ListView.setVisible(false);
            report2ToolBar.setVisible(true);
            report2Table.setVisible(true);
            report3ListView.setVisible(false);
        }
        else if(reportBox.getSelectionModel().isSelected(2)) {
            report1ListView.setVisible(false);
            report2ToolBar.setVisible(false);
            report2Table.setVisible(false);
            report3ListView.setVisible(true);
            populateReport3ListView();
        }
    }

    /**
     * Handles action for when an item in the 2nd report ComboBox is selected.
     * @param event
     */
    @FXML
    void report2BoxAction(ActionEvent event) {
        String selectedContact = report2Box.getSelectionModel().getSelectedItem();
        populateReport2TableView(selectedContact);
    }

    /**
     * Handles stage for the next screen.
     * @param event ActionEvent to determine if a button was pressed
     * @param location string listing .fxml location
     * @throws IOException
     */
    private void goToScreen(ActionEvent event, String location) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(location));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Selects customer or appointment tab to display by default.
     * @param tabIndex int index for tab selection
     */
    public void selectTabPane(int tabIndex) {
        // Change tabs depending on selection
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(tabIndex);
    }

    /**
     * Populates ComboBoxes on form using static lists.
     */
    private void populateComboBox() {
        // Populate report ComboBoxes
        reportBox.setItems(reportList);
        report2Box.setItems(contactList);
    }

    /**
     * Populates the ListView for report #1.
     * Gets all appointments and loops through each record while counting by month and type
     * Uses lambda expressions in switch statements to make the code more readable
     */
    private void populateReport1ListView() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.setAll(appointmentDAO.findAll());
        DatabaseConnectionManager.closeConnection();
        int janCount = 0, febCount = 0, marCount = 0, aprCount = 0, mayCount = 0, junCount = 0;
        int julCount = 0, augCount = 0, sepCount = 0, octCount = 0, novCount = 0, decCount = 0;
        int debriefCount = 0, planningCount = 0, teambuildCount = 0, feedbackCount = 0;
        for(Appointment i : appointments) {
            switch (i.getStart().getMonthValue()) {
                case 1 -> janCount++;
                case 2 -> febCount++;
                case 3 -> marCount++;
                case 4 -> aprCount++;
                case 5 -> mayCount++;
                case 6 -> junCount++;
                case 7 -> julCount++;
                case 8 -> augCount++;
                case 9 -> sepCount++;
                case 10 -> octCount++;
                case 11 -> novCount++;
                case 12 -> decCount++;
            }

            switch (i.getType()) {
                case "De-Briefing" -> debriefCount++;
                case "Planning Session" -> planningCount++;
                case "Team-Building" -> teambuildCount++;
                case "Feedback" -> feedbackCount++;
            }
        }

        report1List.add("JAN: " + janCount);
        report1List.add("FEB: " + febCount);
        report1List.add("MAR: " + marCount);
        report1List.add("APR: " + aprCount);
        report1List.add("MAY: " + mayCount);
        report1List.add("JUN: " + junCount);
        report1List.add("JUL: " + julCount);
        report1List.add("AUG: " + augCount);
        report1List.add("SEP: " + sepCount);
        report1List.add("OCT: " + octCount);
        report1List.add("NOV: " + novCount);
        report1List.add("DEC: " + decCount);
        report1List.add("De-Briefing: " + debriefCount);
        report1List.add("Planning Session: " + planningCount);
        report1List.add("Team-Building: " + teambuildCount);
        report1List.add("Feedback: " + feedbackCount);
        report1ListView.setItems(report1List);
        report1ListView.refresh();
    }

    /**
     * Populates the TableView for report #2.
     * @param selectedContact string to search list by contact
     */
    private void populateReport2TableView(String selectedContact) {
        // Assign columns for report
        report2ApptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        report2TitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        report2DescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        report2LocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        report2TypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        report2StartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        report2EndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        report2CustIdCol.setCellValueFactory(cdf -> cdf.getValue().getCustomer().idProperty());

        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        ObservableList<Appointment> apptListUnfiltered = FXCollections.observableArrayList();
        ObservableList<Appointment> apptListFiltered = FXCollections.observableArrayList();
        apptListUnfiltered.setAll(appointmentDAO.findAll());
        DatabaseConnectionManager.closeConnection();

        // Filter appointments by selected contact
        for(Appointment i : apptList) {
            if(i.getContact().getContactName().equals(selectedContact))
                apptListFiltered.add(i);
        }

        // Populate report TableView with selected contact's appointments
        report2Table.setItems(apptListFiltered);
        report2Table.getSortOrder().add(report2ApptIdCol);
        report2Table.refresh();
    }

    /**
     * Populates the ListView for report #3.
     * Gets currently logged-in user and all appointments, then counts each appointment assigned to user
     */
    private void populateReport3ListView() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.setAll(appointmentDAO.findAll());
        DatabaseConnectionManager.closeConnection();

        User user = LoginController.getLoggedInUser();
        int apptCount = 0;
        for(Appointment i : appointments) {
            if(user.getId() == i.getUser().getId())
                apptCount++;
        }

        report3List.add("Currently logged in as: " + user.getUsername());
        report3List.add("Number of appointments: " + apptCount);
        report3ListView.setItems(report3List);
        report3ListView.refresh();
    }

    /**
     * Populates TableViews for both customers and appointments.
     * Uses lambda expressions to assign column labels where an object needs to be accessed within another object
     */
    private void populateTableView() {
        // Assign columns for customer data
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        custDivisionCol.setCellValueFactory(cdf -> cdf.getValue().getDivision().
                divisionNameProperty());
        custCountryCol.setCellValueFactory(cdf -> cdf.getValue().getDivision().
                getCountry().countryNameProperty());

        // Populate customer TableView using SQL database
        CustomerDAO customerDAO = new CustomerDAO(DatabaseConnectionManager.openConnection());
        custList.setAll(customerDAO.findAll());
        DatabaseConnectionManager.closeConnection();
        custTable.setItems(custList);
        custTable.getSortOrder().add(custIdCol);
        custTable.refresh();

        // Assign columns for appointment data
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        apptTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescrCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptContactCol.setCellValueFactory(cdf -> cdf.getValue().getContact().contactNameProperty());
        apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        apptEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        apptCustIdCol.setCellValueFactory(cdf -> cdf.getValue().getCustomer().idProperty());
        apptUserIdCol.setCellValueFactory(cdf -> cdf.getValue().getUser().idProperty());

        // Populate appointment TableView using SQL database
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        apptList.setAll(appointmentDAO.findAll());
        DatabaseConnectionManager.closeConnection();
        apptTable.setItems(apptList);
        apptTable.getSortOrder().add(apptIdCol);
        apptTable.refresh();
    }

    public static Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(Customer customer) {
        MainScreenController.selectedCustomer = customer;
    }

    /**
     * Deletes selected customer record and displays alert with customer ID.
     * @param customer object to delete
     */
    private void deleteCustomer(Customer customer) {
        CustomerDAO customerDAO = new CustomerDAO(DatabaseConnectionManager.openConnection());
        int deletedId = customer.getId();
        if(customerDAO.delete(customer.getId())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted customer with an ID of " + deletedId);
            alert.show();
        }
        DatabaseConnectionManager.closeConnection();
    }

    public static Appointment getSelectedAppointment() {
        return selectedAppointment;
    }

    public void setSelectedAppointment(Appointment appointment) {
        MainScreenController.selectedAppointment = appointment;
    }

    /**
     * Deletes selected appointment record and displays alert with appointment type and ID.
     * @param appointment
     */
    private void deleteAppointment(Appointment appointment) {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        int deletedId = appointment.getId();
        String deletedType = appointment.getType();
        if(appointmentDAO.delete(appointment.getId())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted " + deletedType + " appointment with an ID of: " + deletedId);
            alert.show();
        }
        DatabaseConnectionManager.closeConnection();
    }

    /**
     * Removes filters from TableView of appointments and displays all records.
     */
    private void apptFilterNone() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        apptList.setAll(appointmentDAO.findAll());
        DatabaseConnectionManager.closeConnection();
        apptTable.setItems(apptList);
        apptTable.getSortOrder().add(apptIdCol);
        apptTable.refresh();
    }

    /**
     * Filters TableView of appointments by week.
     */
    private void apptFilterWeek() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        apptList.setAll(appointmentDAO.findAllByWeek());
        DatabaseConnectionManager.closeConnection();
        apptTable.setItems(apptList);
        apptTable.getSortOrder().add(apptIdCol);
        apptTable.refresh();
    }

    /**
     * Filters TableView of appointments by month.
     */
    private void apptFilterMonth() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        apptList.setAll(appointmentDAO.findAllByMonth());
        DatabaseConnectionManager.closeConnection();
        apptTable.setItems(apptList);
        apptTable.getSortOrder().add(apptIdCol);
        apptTable.refresh();
    }

    /**
     * Displays alert if there is an appointment starting within 15 minutes of user logging in.
     */
    public static void timeAlert() {
        AppointmentDAO appointmentDAO = new AppointmentDAO(DatabaseConnectionManager.openConnection());
        ObservableList<Appointment> checkAppt = FXCollections.observableArrayList();
        checkAppt.setAll(appointmentDAO.findAllByMonth());
        DatabaseConnectionManager.closeConnection();

        LocalDateTime currentTime = LocalDateTime.now();
        for(Appointment i : checkAppt) {
            long timeDiff = ChronoUnit.MINUTES.between(currentTime, i.getStart());
            if(timeDiff > 0 && timeDiff <= 15) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("You have an appointment in approx " + timeDiff + " minutes.\n" +
                        "Appointment ID: " + i.getId() + " at " + i.getStart());
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("There are no upcoming appointments.");
                alert.show();
                break;
            }
        }
    }

    /**
     * First method that is called when screen is loaded.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populateTableView();
        populateComboBox();
    }
}
