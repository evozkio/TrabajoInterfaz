package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application{
	

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception{
		
		FXMLLoader loader = new FXMLLoader(App.class.getResource("/vista/Juego.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
				
		Scene scene = new Scene(pane);
		stage.setScene(scene);
        stage.requestFocus();
		// stage.setResizable(false);
		stage.setTitle("Super Pang");
		stage.show();
	}

}


