package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        private LocalTime calculoteporizador;
        private Boolean Golpeado =false;
        private LocalTime tiempoGolpe;

        

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

        private void isVictoria(){
                if (listaball.size() == 0) {
                        movimiento.stop();
                        mensaje.setText("Has ganado");
                        eliminarDisparo();
                        boton.setText("Iniciar");
                        juego= false;
                }
        }

        
        public void derrota(){
                movimiento.stop();
                mensaje.setText("Has Perdido");
                eliminarBall();
                eliminarDisparo();
                boton.setText("Iniciar");
                juego =false;
        }

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

        public void movimientodisparo() {
                ArrayList<ImageView> listaremove = new ArrayList<ImageView>();
                for (ImageView imageView : listadisparo) {
                        if (imageView.getY() == 0) {
                                listaremove.add(imageView);
                                panel.getChildren().remove(imageView);
                               
                        } else imageView.setY(imageView.getY() - 5);
                        
                }

                listadisparo.removeAll(listaremove);
        }
       

        

        public void colisionBallDisparo(){
                ArrayList<ImageView> listaremove = new ArrayList<ImageView>();
                for (ImageView disparo : listadisparo) {
                        for (Ball ball : listaball) {
                                if (disparo.getY() < ball.getImageView().getY() + (ball.getImageView().getFitHeight())) {
                                        if ((disparo.getX() + (disparo.getFitWidth()) > ball.getImageView().getX())) {
                                                if (disparo.getX() < (ball.getImageView().getX()+ ball.getImageView().getFitWidth())) {
                                                        divisionBall(ball);
                                                        listaremove.add(disparo);
                                                        panel.getChildren().remove(disparo);
                                                        panel.getChildren().remove(ball.getImageView());
                                                        break;
                                                }
                                        }

                                }
                        }
                }
                listadisparo.removeAll(listaremove);
        }
       
    

        private void inicioBall() {
                listaball.add(new Ball(310.0, 50.0, 0.5, true));
                panel.getChildren().add(listaball.getLast().getImageView());
             
        }


        private void movimientoBalls() {
                for (Ball ball : listaball) {
                        ball.movimiento();
                }
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

       

        private void eliminarBall() {
                for (Ball ball : listaball) {
                        panel.getChildren().remove(ball.getImageView());
                }
                listaball.clear();
        }

        private void eliminarDisparo(){
                for (ImageView disparo : listadisparo) {
                        panel.getChildren().remove(disparo);
                }
                listadisparo.clear();
        }

        public void movimientoPersonaje(){
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
        }

        public void golpePersonaje(){
                

                if(Golpeado){
                        animationGolpePersonaje();
                        calculoteporizador= LocalTime.now();
                        if(tiempoGolpe.isBefore(calculoteporizador)){
                                Golpeado = false;
                                personaje.setOpacity(1.0);
                        }
                }
                else{
                        for (Ball ball : listaball) {
                                if (personaje.getY() < ball.getImageView().getY()+ (ball.getImageView().getFitHeight())) {
                                        if ((personaje.getX() + (personaje.getFitWidth()) > ball.getImageView().getX())) {
                                                if (personaje.getX() < (ball.getImageView().getX()+ ball.getImageView().getFitWidth())) {
                                                        if(!Golpeado){
                                                                contador -= 1;
                                                                vidas();
                                                                Golpeado = true;
                                                                calculoteporizador= LocalTime.now();
                                                                tiempoGolpe = calculoteporizador.plusSeconds(3);
                                                                break;
                                                        }
                                                }
                                        }
        
                                }
                        }
                }
        }

        public void animationGolpePersonaje(){
                if(personaje.getOpacity() == 0){
                        personaje.setOpacity(1.0);
                }
                else{
                        personaje.setOpacity(0);     
                }
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
                                        derrota();
                                }
                        }

                }
        }

        private void temporizador() {
                calculoteporizador= LocalTime.now();
                if(temporizador.isAfter(calculoteporizador)){
                        tiempo.setText(String.valueOf(calculoteporizador.until(temporizador, ChronoUnit.SECONDS)%60));
                }else{
                        derrota();
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
                       calculoteporizador = LocalTime.now();
                       temporizador = calculoteporizador.plusSeconds(60);
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
                public void handle(long currentTime){
                        isVictoria();
                        movimientoPersonaje();
                        movimientoBalls();
                        temporizador();
                        golpePersonaje();
                        colisionBallDisparo();
                        movimientodisparo();
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
                mensaje.setText("");

        }

}
