package es.cristiangg.campofutbol;

import es.cristiangg.campofutbol.entities.Estadio;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.Query;

public class PrimaryController implements Initializable{

    private Estadio estadioSeleccionada;
    @FXML
    private TableView<Estadio> tableViewEstadio;
    @FXML
    private TableColumn<Estadio, String> columnNombre;
    @FXML
    private TableColumn<Estadio, String> columnLocalizacion;
    @FXML
    private TableColumn<Estadio, String> columnDivision;
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldLocalizacion;
    @FXML
    private TextField textFieldBuscar;
    @FXML  
    private CheckBox checkCoincide;
   
    @Override
    public void initialize(URL url, ResourceBundle rb){
//        System.out.print("entrando initialize");
//        System.out.println("adfhj");
        columnNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnLocalizacion.setCellValueFactory(new PropertyValueFactory<>("localizacion"));
        columnDivision.setCellValueFactory(
                cellData -> {
                    SimpleStringProperty property = new SimpleStringProperty();
                    if (cellData.getValue().getDivision()!= null){
                        String nombreEst = cellData.getValue().getDivision().getNombre();
                        property.setValue(nombreEst);
                    }
                    return property;
                });
        tableViewEstadio.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    estadioSeleccionada = newValue;
                    if (estadioSeleccionada != null){
                        textFieldNombre.setText(estadioSeleccionada.getNombre());
                        textFieldLocalizacion.setText(estadioSeleccionada.getLocalizacion());
                    }else {
                        textFieldNombre.setText("");
                        textFieldLocalizacion.setText("");
                    }
                });
            cargarTodosEstadio();

        }      
        private void cargarTodosEstadio(){
            Query queryEstadioFindAll = App.em.createNamedQuery("Estadio.findAll");
            List<Estadio> listEstadio = queryEstadioFindAll.getResultList();
//            System.out.print("a" + listEstadio.size());
            tableViewEstadio.setItems(FXCollections.observableArrayList(listEstadio));
        }
       
        @FXML
        private void onActionButtonGuardar(ActionEvent event){
            if (estadioSeleccionada != null){
                estadioSeleccionada.setNombre(textFieldNombre.getText());
                estadioSeleccionada.setLocalizacion(textFieldLocalizacion.getText());
                App.em.getTransaction().begin();
                App.em.merge(estadioSeleccionada);
                App.em.getTransaction().commit();
                
                int numFilaSeleccionada = tableViewEstadio.getSelectionModel().getSelectedIndex();
                tableViewEstadio.getItems().set(numFilaSeleccionada, estadioSeleccionada);
                TablePosition pos = new TablePosition(tableViewEstadio, numFilaSeleccionada, null);
                tableViewEstadio.getFocusModel().focus(pos);
                tableViewEstadio.requestFocus();
            }
           
        }
        
        @FXML
        private void onActionButtonSuprimir(ActionEvent event){
            if(estadioSeleccionada != null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar");
                alert.setHeaderText("¿Desea suprimir el siguiente registro?");
                alert.setContentText(estadioSeleccionada.getNombre() + " "
                    + estadioSeleccionada.getLocalizacion());
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    App.em.getTransaction().begin();
                    App.em.remove(estadioSeleccionada);
                    App.em.getTransaction().commit();
                    tableViewEstadio.getItems().remove(estadioSeleccionada);
                    tableViewEstadio.getFocusModel().focus(null);
                    tableViewEstadio.requestFocus();
                } else {
                    int numFilaSeleccionada = tableViewEstadio.getSelectionModel().getSelectedIndex();
                    tableViewEstadio.getItems().set(numFilaSeleccionada, estadioSeleccionada);
                    TablePosition pos = new TablePosition(tableViewEstadio, numFilaSeleccionada, null);
                    tableViewEstadio.getFocusModel().focus(pos);
                    tableViewEstadio.requestFocus();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atencion");
                alert.setHeaderText("Debe seleccionar un registro");
                alert.showAndWait();
            }            
        }
        
        @FXML
        private void onActionButtonNuevo(ActionEvent event){
            try{
                App.setRoot("secondary");
                SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
                estadioSeleccionada = new Estadio();
                secondaryController.setEstadio(estadioSeleccionada, true);
            } catch (IOException ex){
                Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
        
        @FXML
        private void onActionButtonEditar(ActionEvent event){
            if(estadioSeleccionada != null){
                try{
                    App.setRoot("secondary");
                    SecondaryController secondaryController = (SecondaryController)App.fxmlLoader.getController();
                    secondaryController.setEstadio(estadioSeleccionada, false);
                }catch (IOException ex){
                    Logger.getLogger(PrimaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atencion");
                alert.setHeaderText("Debe seleccionar un registro");
                alert.showAndWait();
            }
        }
        
    @FXML
    private void onActionButtonBuscar(ActionEvent event) {
        if(!textFieldBuscar.getText().isEmpty()) {
            if(checkCoincide.isSelected()) {
                Query queryEstadioFindAll = App.em.createNamedQuery("Estadio.findByNombre");
                queryEstadioFindAll.setParameter("nombre", textFieldBuscar.getText());
                List<Estadio> listEstadio = queryEstadioFindAll.getResultList();
                tableViewEstadio.setItems(FXCollections.observableArrayList(listEstadio));
            } else {
                String strQuery = "SELECT * FROM Estadio WHERE LOWER(nombre) LIKE ";
                strQuery += "\'%" + textFieldBuscar.getText().toLowerCase() + "%\'";
                Query queryEstadioFindAll = App.em.createNativeQuery(strQuery, Estadio.class);
                
                List<Estadio> listEstadio = queryEstadioFindAll.getResultList();
                tableViewEstadio.setItems(FXCollections.observableArrayList(listEstadio));
                
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, strQuery);
            }
        } else {
            cargarTodosEstadio();
        }
    }
           
        
   
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

}