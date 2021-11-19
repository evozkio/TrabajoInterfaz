package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class Juego implements Initializable {
        private Boolean pressA = false;
        private Boolean pressD = false;
        private Boolean derechaI = true;
        private Boolean izquierdaI = true;
        @FXML ImageView fondo;
        @FXML private Button boton;
        @FXML private AnchorPane panel;
        String rutaImagen = "/imagen/Personaje.png";
        ImageView personaje = new ImageView(new Image(rutaImagen));

        @FXML
	private void actionBoton(ActionEvent event) {
                movientodisparo.start();
	}

        @FXML
        public void personajeMove(KeyEvent key) {
                if(pressA && pressD){
                        personaje.setImage(new Image(rutaImagen));
                        movimiento.stop();
                        movimiento.stop();
                }else{
                        if (key.getCode().equals(KeyCode.A)){
                                pressA=true;
                                movimiento.start();
                        } 
                        if (key.getCode().equals(KeyCode.D)) {
                                pressD=true;
                                movimiento.start();
                        }
                }
        }

        @FXML
        public void personajeStop(KeyEvent key) {
                if (key.getCode().equals(KeyCode.A)){
                        pressA=false;
                        movimiento.stop();
                        personaje.setImage(new Image(rutaImagen));
                } 
                if (key.getCode().equals(KeyCode.D)){
                        pressD=false;    
                        movimiento.stop();
                        personaje.setImage(new Image(rutaImagen));
                } 
        }

       
        AnimationTimer movimiento = new AnimationTimer() { 
                @Override
                public void handle(long currentTime) {
                        if(!(pressA && pressD)){
                                if ((personaje.getLayoutX() < 430) && pressD) {
                                        if (derechaI) {
                                                personaje.setImage(new Image("/imagen/PersonajeDerecha.png"));
                                                derechaI = false;
                                        }else{
                                                personaje.setImage(new Image("/imagen/PersonajeDerecha2.png"));
                                                derechaI= true;
                                        }
                                        personaje.setLayoutX(personaje.getLayoutX()+5);
                                }
                                if ((personaje.getLayoutX() > -235) && pressA) {
                                
                                        if (izquierdaI) {
                                                personaje.setImage(new Image("/imagen/PersonajeIzquierda.png"));
                                                izquierdaI = false;
                                        }else{
                                                personaje.setImage(new Image("/imagen/PersonajeIzquierda2.png"));
                                                izquierdaI= true;
                                        }
                                        personaje.setLayoutX(personaje.getLayoutX()-5);
                                }
                        }
                }
        };

        AnimationTimer movientodisparo = new AnimationTimer() { 
                ImageView disparo = new ImageView(new Image("/imagen/disparos.png"));
                @Override
                public void handle(long currentTime) {
                      
                }
                @Override
                public void start(){
                        
                        // double ocupa = 0.7;
                        disparo.setX(0);
                        disparo.setY(0);
                        disparo.setScaleX(0.5);
                        
                        panel.getChildren().add(disparo);
                        disparo.setViewOrder(0);
                }
        };

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                double ocupa = 0.3;
                personaje.setX(180);
                personaje.setY(270);
                personaje.setScaleX(ocupa);
                personaje.setScaleY(ocupa);
                
                panel.getChildren().add(personaje);
                
        }

      


}


    

