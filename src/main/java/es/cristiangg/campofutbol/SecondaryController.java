package es.cristiangg.campofutbol;

import es.cristiangg.campofutbol.entities.Division;
import es.cristiangg.campofutbol.entities.Estadio;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.Query;
import javax.persistence.RollbackException;

public class SecondaryController implements Initializable {
   
    private Estadio estadio;
    private boolean nuevoEstadio;
   
    private static final char EUROPA = 'E';
    private static final char MEDIA = 'M';
    private static final char DESCENSO = 'D';
   
    private static final String FOTO_ESTADIO = "Fotos";
   
    @FXML
    private TextField textFieldNombre;
    @FXML
    private TextField textFieldLocalizacion;
    @FXML
    private TextField textFieldTelefono;
    @FXML
    private TextField textFieldEmail;
    @FXML
    private TextField textFieldProvincia;
    @FXML
    private ComboBox<Division> comboBoxDivision;
    @FXML
    private DatePicker datePickerfecha_fundacion;
    @FXML
    private RadioButton radioButtonEuropa;
    @FXML
    private RadioButton radioButtonMedia;
    @FXML
    private RadioButton radioButtonDescenso;
    @FXML
    private TextField textFieldPrecio;
    @FXML
    private TextField textFieldMedida_Campo;
    @FXML  
    private CheckBox checkBoxEntradasDis;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private ToggleGroup categoria_clubs;
    @FXML
    private BorderPane rootSecondary;
   
    @Override
    public void initialize(URL url, ResourceBundle rb){
    }
   
    public void setEstadio(Estadio estadio, boolean nuevoEstadio){
        App.em.getTransaction().begin();
        if(!nuevoEstadio){
           this.estadio = App.em.find(Estadio.class, estadio.getId());
        } else {
            this.estadio = estadio;
        }
        this.nuevoEstadio = nuevoEstadio;
        mostrarDatos();
    }
   
    private void mostrarDatos() {
        textFieldNombre.setText(estadio.getNombre());
        textFieldLocalizacion.setText(estadio.getLocalizacion());
       
        if (estadio.getTelefono() != null){
            textFieldTelefono.setText(String.valueOf(estadio.getTelefono()));
        }
        if (estadio.getEmail() != null){
            textFieldEmail.setText(String.valueOf(estadio.getEmail()));
        }
        if (estadio.getProvincia() != null){
            textFieldProvincia.setText(String.valueOf(estadio.getProvincia()));
        }
        if (estadio.getPrecio() != null){
            textFieldPrecio.setText(String.valueOf(estadio.getPrecio()));
        }
        if (estadio.getMedidaCampo() != null){
            textFieldMedida_Campo.setText(String.valueOf(estadio.getMedidaCampo()));
        }
        if (estadio.getEntradasDisponibles() != null){
            checkBoxEntradasDis.setSelected(estadio.getEntradasDisponibles());
        }
        if (estadio.getCategoriaClubs() != null){
            switch (estadio.getCategoriaClubs()){
                case EUROPA:
                    radioButtonEuropa.setSelected(true);
                    break;
                    case MEDIA:
                    radioButtonMedia.setSelected(true);
                    break;
                    case DESCENSO:
                    radioButtonDescenso.setSelected(true);
                    break;
            }
        }
        if (estadio.getFechaFundacion() != null){
            Date date = estadio.getFechaFundacion();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            datePickerfecha_fundacion.setValue(localDate);
        }
       
        Query queryEstadioFindAll = App.em.createNamedQuery("Division.findAll");
        List<Division> listDivision = queryEstadioFindAll.getResultList();
        System.out.println(listDivision.size());
        comboBoxDivision.setItems(FXCollections.observableList(listDivision));
        if (estadio.getDivision() != null){
            comboBoxDivision.setValue(estadio.getDivision());
        }
        comboBoxDivision.setCellFactory((ListView<Division> l) -> new ListCell<Division>() {
            @Override
            protected void updateItem(Division division, boolean empty) {
                super.updateItem(division, empty);
                if (division == null || empty){
                    setText("");
                } else {
                    setText(division.getNombre() + "-" + division.getCodigo());
                }
            }  
           
        });
   
        //
        comboBoxDivision.setConverter(new StringConverter<Division>() {
            @Override
            public String toString(Division division) {
                if (division == null){
                    return null;
                } else {
                    return division.getNombre() + "-" + division.getCodigo();
                }
            }
       
            @Override
            public Division fromString(String userId){
                return null;
            }
        });
       
        if (estadio.getFotoEstadio() != null) {
            String imageFileName = estadio.getFotoEstadio();
            File file = new File(FOTO_ESTADIO + "/" + imageFileName);
            if (file.exists()){
                Image image = new Image(file.toURI().toString());
                imageViewFoto.setImage(image);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "No se encuentra la imagen");
                alert.showAndWait();
            }
        }
    }
               
     
        @FXML
        private void onActionButtonGuardar(ActionEvent event) {
            int numFilaSeleccionada;
            boolean errorFormato=false;
           
            estadio.setNombre(textFieldNombre.getText());
            estadio.setLocalizacion(textFieldLocalizacion.getText());
           
            estadio.setPrecio(BigDecimal.valueOf(Double.valueOf(textFieldPrecio.getText()).doubleValue()));
           
            estadio.setEntradasDisponibles(checkBoxEntradasDis.isSelected());
           
            if (radioButtonEuropa.isSelected()) {
                estadio.setCategoriaClubs(EUROPA);
            } else if (radioButtonMedia.isSelected()) {
                estadio.setCategoriaClubs(MEDIA);
            } else if (radioButtonDescenso.isSelected()) {
                estadio.setCategoriaClubs(DESCENSO);
            }
           
           
        if (datePickerfecha_fundacion.getValue() != null){
            LocalDate localDate = datePickerfecha_fundacion.getValue();
            ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
            Instant instant = zonedDateTime.toInstant();
            Date date = Date.from(instant);
            estadio.setFechaFundacion(date);
        } else {
            estadio.setFechaFundacion(null);
        }
       
        estadio.setDivision(comboBoxDivision.getValue());
       
        if (!errorFormato) {
            try {
                if (estadio.getId() == null){
                    System.out.println("Guardando nuevo estadio en BD");
                    App.em.persist(estadio);
                } else {
                    System.out.println("Actualizando estadio en BD");
                    App.em.merge(estadio);
                }
                App.em.getTransaction().commit();
               
                App.setRoot("primary");
            } catch (RollbackException ex){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("No se han podido guardar los cambios."
                        + "Compruebe que los datos cumplen los requisitos");
                alert.setContentText(ex.getLocalizedMessage());
                alert.showAndWait();
            } catch (IOException ex) {
                Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
       
    @FXML
    private void onActionButtonCancelar(ActionEvent event){
        App.em.getTransaction().rollback();
        
        try {
            App.setRoot("primary");
        } catch (IOException ex){
            Logger.getLogger(SecondaryController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML   
    private void onActionButtonExaminar(ActionEvent event){
            File carpetaFotos = new File(FOTO_ESTADIO);
            if (!carpetaFotos.exists()) {
                    carpetaFotos.mkdir();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar Imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagenes (jpg, png)", "*.jpg", "*.png"),
                    new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
            );
            File file = fileChooser.showOpenDialog(rootSecondary.getScene().getWindow());
            if (file != null) {
                    try {
                            Files.copy(file.toPath(), new File(FOTO_ESTADIO + "/" + file.getName()).toPath());
                            estadio.setFotoEstadio(file.getName());
                            Image image = new Image(file.toURI().toString());
                            imageViewFoto.setImage(image);
                    } catch (FileAlreadyExistsException ex) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre de archivo duplicado");
                            alert.showAndWait();
                    } catch (IOException ex) {
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Nombre se ha podido guardar la imagen");
                            alert.showAndWait();
                    }
            }
    }
    
        @FXML
    private void onActionSuprimirFoto(ActionEvent event) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar supresion de imagen");
            alert.setHeaderText("¿Desea SUPRIMIR el achivo asociado a la imagen, \n"
                    +	"quitar la foto pero MANTENER el archivo, \no CANCELAR la operacion?");
            alert.setContentText("Elija la opcion deseada:");

            ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
            ButtonType buttonTypeMantener = new ButtonType("Mantener");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeMantener, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeEliminar) {
                    String imageFileName = estadio.getFotoEstadio();
                    File file = new File(FOTO_ESTADIO + "/" + imageFileName);
                    if (file.exists()) {
                            file.delete();
                    }
                    estadio.setFotoEstadio(null);
                    imageViewFoto.setImage(null);
            } else if (result.get() == buttonTypeMantener) {
                    estadio.setFotoEstadio(null);
                    imageViewFoto.setImage(null);
            }
    }
   
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}
    