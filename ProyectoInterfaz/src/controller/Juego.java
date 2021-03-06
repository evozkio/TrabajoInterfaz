package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.Ball;

public class Juego implements Initializable {
        private Boolean juego = false;
        private int contador = 3;
        private Boolean pressA = false;
        private Boolean pressD = false;
        private Boolean derechaI = true;
        private Boolean izquierdaI = true;
        private Image imagenDisparo = new Image("/imagen/disparos.png");
        private Image imagenpersonaje = new Image("/imagen/Personaje.png");
        private LocalTime temporizador;
        private LocalTime inicioTemporizador;
        

        @FXML private Label tiempo;
        @FXML private ImageView fondoJuego;
        @FXML private AnchorPane fondo;
        @FXML private Button boton;
        @FXML private AnchorPane panel;
        @FXML private Label mensaje;

        @FXML private ImageView corazon1;
        @FXML private ImageView corazon2;
        @FXML private ImageView corazon3;

        ImageView personaje = new ImageView(imagenpersonaje);
        LinkedList<ImageView> listadisparo = new LinkedList<ImageView>();
        LinkedList<Ball> listaball = new LinkedList<Ball>();

        private void disparar() {
                if (listadisparo.size() < 3) {
                        listadisparo.add(new ImageView(imagenDisparo));
                        listadisparo.getLast().setX(personaje.getX() + 10);
                        listadisparo.getLast().setY(380);
                        listadisparo.getLast().setFitWidth(imagenDisparo.getWidth() * 0.5);
                        listadisparo.getLast().setViewOrder(2);

                        panel.getChildren().add(listadisparo.getLast());

                }

        }

        private void inicioBall() {
                listaball.add(new Ball(310.0, 50.0, 0.5, true));
                panel.getChildren().add(listaball.getLast().getImageView());
             
        }

        private void divisionBall(Ball ball) {
                Ball bola;
                Double reduccion = 0.50;

                if (ball.getOcupa() < 0.07) {
                        panel.getChildren().remove(ball.getImageView());
                        listaball.remove(ball);
                } else {
                        bola = new Ball(ball);
                        bola.setOcupa(bola.getOcupa() * reduccion);
                        bola.setPosicionY(bola.getPosicionY() + bola.getImageView().getFitHeight() / 2);
                        listaball.add(bola);
                        panel.getChildren().add(bola.getImageView());
                        bola = new Ball(bola);
                        bola.setPosicionX(bola.getPosicionX() + bola.getImageView().getFitWidth());
                        bola.setDireccion(!bola.getDireccion());
                        listaball.add(bola);
                        panel.getChildren().add(bola.getImageView());
                        listaball.remove(ball);
                        panel.getChildren().remove(ball.getImageView());

                }

        }

        public void golpePersonaje(){
                for (Ball ball : listaball) {
                        if (personaje.getY() < ball.getImageView().getY()+ (ball.getImageView().getFitHeight())) {
                                if ((personaje.getX() + (personaje.getFitWidth()) > ball.getImageView().getX())) {
                                        if (personaje.getX() < (ball.getImageView().getX()+ ball.getImageView().getFitWidth())) {
                                                personaje.setX(0);
                                                contador -= 1;
                                                vidas();
                                        }
                                }

                        }
                }
        }

        private void eliminarball() {
                for (Ball ball : listaball) {
                        panel.getChildren().remove(ball.getImageView());
                }
                listaball.clear();
        }

        public void vidas(){
                if (contador == 3){
                        corazon1.setVisible(true);
                        corazon2.setVisible(true);
                        corazon3.setVisible(true);
                }
                else{
                        if (contador == 2){
                                corazon1.setVisible(false);
                                corazon2.setVisible(true);
                                corazon3.setVisible(true);
                        }
                        else{
                                if (contador == 1){
                                        corazon1.setVisible(false);
                                        corazon2.setVisible(false);
                                        corazon3.setVisible(true);
                                }
                                else{
                                        corazon1.setVisible(false);
                                        corazon2.setVisible(false);
                                        corazon3.setVisible(false);
                                        movimiento.stop();
                                        mensaje.setText("Has Perdido");
                                        eliminarball();
                                        boton.setText("Iniciar");
                                        juego =false;
                                }
                        }

                }
        }

    



       

        @FXML
        private void actionBoton(ActionEvent event) throws IOException {
               if(boton.getText().equals("Iniciar")){
                       boton.setText("disparar");
                       juego= true;
                       inicioBall();
                       mensaje.setText("");
                       personaje.setX(350);
                       movimiento.start();
                       contador = 3;
                       vidas();
                       inicioTemporizador = LocalTime.now();
                       temporizador = inicioTemporizador.plusSeconds(60);
                       tiempo.setText("60");

               }else{
                       disparar();
               }
        }

        @FXML
        public void mouseClick(MouseEvent mouse) {
                if(juego){
                        if (mouse.getButton().equals(MouseButton.PRIMARY)) {
                                disparar();
                        }
                }
        }

        @FXML
        public void pressKey(KeyEvent key) {
                if(juego){

                        if (!(pressA && pressD)) {
                                if (key.getCode().equals(KeyCode.A)) {
                                        pressA = true;
                                        movimiento.start();
                                }
                                if (key.getCode().equals(KeyCode.D)) {
                                        pressD = true;
                                        movimiento.start();
                                }
                        }
                }
                
                
        }

        @FXML
        public void releaseKey(KeyEvent key) {
                if (key.getCode().equals(KeyCode.A)) {
                        pressA = false;
                        personaje.setImage(imagenpersonaje);
                }
                if (key.getCode().equals(KeyCode.D)) {
                        pressD = false;
                        personaje.setImage(imagenpersonaje);
                }
        }

        AnimationTimer movimiento = new AnimationTimer() {
                @Override
                public void handle(long currentTime) {
                        if (!(pressA && pressD)) {
                                if ((personaje.getX() < 670) && pressD) {
                                        if (derechaI) {
                                                personaje.setImage(new Image("/imagen/PersonajeDerecha.png"));
                                                derechaI = false;
                                        } else {
                                                personaje.setImage(new Image("/imagen/PersonajeDerecha2.png"));
                                                derechaI = true;
                                        }
                                        personaje.setX(personaje.getX() + 5);
                                }
                                if ((personaje.getX() > 0) && pressA) {
                                        if (izquierdaI) {
                                                personaje.setImage(new Image("/imagen/PersonajeIzquierda.png"));
                                                izquierdaI = false;
                                        } else {
                                                personaje.setImage(new Image("/imagen/PersonajeIzquierda2.png"));
                                                izquierdaI = true;
                                        }
                                        personaje.setX(personaje.getX() - 5);
                                }
                        } else {
                                personaje.setImage(imagenpersonaje);
                        }
                        for (Ball ball : listaball) {
                                ball.movimiento();
                        }
                        if (listaball.size() == 0) {
                                mensaje.setText("Has ganado");
                                boton.setText("Iniciar");
                                juego= false;
                        }
                        inicioTemporizador= LocalTime.now();
                        if(temporizador.isAfter(inicioTemporizador)){
                                tiempo.setText(String.valueOf(inicioTemporizador.until(temporizador, ChronoUnit.SECONDS)%60));
                        }else{
                                movimiento.stop();
                                mensaje.setText("Has Perdido");
                                eliminarball();
                                boton.setText("Iniciar");
                                juego =false;
                        }
                        golpePersonaje();
                }
        };

        AnimationTimer movimientodisparo = new AnimationTimer() {
                @Override
                public void handle(long currentTime) throws ConcurrentModificationException {
                        for (ImageView imageView : listadisparo) {
                                if (imageView.getY() == 0) {
                                        panel.getChildren().remove(imageView);
                                        listadisparo.remove(imageView);
                                } else
                                        imageView.setY(imageView.getY() - 5);
                        }
                }
        };

        

        AnimationTimer efectosball = new AnimationTimer() {
                @Override
                public void handle(long currentTime) throws ConcurrentModificationException{
                        for (Ball ball : listaball) {
                                for (ImageView disparo : listadisparo) {
                                        if (disparo.getY() < ball.getImageView().getY() + (ball.getImageView().getFitHeight())) {
                                                if ((disparo.getX() + (disparo.getFitWidth()) > ball.getImageView().getX())) {
                                                        if (disparo.getX() < (ball.getImageView().getX()+ ball.getImageView().getFitWidth())) {
                                                                divisionBall(ball);
                                                                listadisparo.remove(disparo);
                                                                panel.getChildren().remove(disparo);
                                                                panel.getChildren().remove(ball.getImageView());
                                                        }
                                                }

                                        }
                                }
                        }
                }
        };

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                double ocupa = 0.3;
                personaje.setX(180);
                personaje.setY(330);
                personaje.setFitHeight(imagenpersonaje.getHeight() * ocupa);
                personaje.setFitWidth(imagenpersonaje.getWidth() * ocupa);

                panel.getChildren().add(personaje);

                personaje.setViewOrder(0);
                fondo.setViewOrder(1);
                fondoJuego.setViewOrder(3);
                movimientodisparo.start();
                efectosball.start();
                mensaje.setText("");

        }

}
