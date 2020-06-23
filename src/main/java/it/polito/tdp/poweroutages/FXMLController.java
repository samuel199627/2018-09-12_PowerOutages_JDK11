package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText(model.creaGrafo());
    	
    	cmbBoxNerc.getItems().clear();
    	cmbBoxNerc.getItems().addAll(model.vertici());
    }

    @FXML
    void doSimula(ActionEvent event) {
    	txtResult.clear();
    	int mesi=0;
    	try {
    		mesi=Integer.parseInt(txtK.getText());
    	}
    	catch(NumberFormatException e){
    		txtResult.appendText("PARAMETRO INSERITO NON VALIDO");
    		return;
    	}

    	if(mesi<0) {
    		txtResult.appendText("PARAMETRO INSERITO NON VALIDO");
    		return;
    	}
    	
    	txtResult.appendText(model.simula(mesi));
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {
    	txtResult.clear();
    	Nerc selezionato=cmbBoxNerc.getValue();
    	if(selezionato==null) {
    		txtResult.appendText("DEVI SELEZIONARE UN NERC!!");
    		return;
    	}
    	
    	txtResult.appendText(model.vicini(selezionato));
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
