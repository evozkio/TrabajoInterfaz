package controller;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class AleJuego implements Initializable{
	@FXML AnchorPane pane;
	
	//variables
	int ancho = 800;
	int largo = 600;
	int alturaJugador = 100;
	int anchoJugador = 15;
	double bola = 20;
	double velocidadY = 1;
	double velocidadX = 1;
	double J1posicionY = largo / 2;
	double J2posicionY = largo / 2;
	double J1posicionX = 0;
	double J2posicionX = ancho - anchoJugador;
	double posicionBolaX = ancho / 2;
	double posicionBolaY = largo / 2;
	int puntuacionJ1 = 0;
	int puntuacionJ2 = 0;
	boolean empezar;
	
		
	// public void start(Stage stage) throws Exception {

	// }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
			// 	// Titulo
	// 	stage.setTitle("P I N G - P O N G");
		
		//Backgraund
		Canvas canvas = new Canvas(ancho, largo);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		
		// Animacion
		Timeline tl = new Timeline(new KeyFrame(Duration.millis(10), e -> run(gc)));
		tl.setCycleCount(Timeline.INDEFINITE);
		
		// Control de raton
		canvas.setOnMouseMoved(e ->  J1posicionY  = e.getY());
		canvas.setOnMouseClicked(e ->  empezar = true);
		pane.getChildren().add(canvas);
		tl.play();
		
	}

	private void run(GraphicsContext gc) {
		
		//Fondo y color de texto
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, ancho, largo);

		gc.setFill(Color.GREEN);
		gc.setFont(Font.font(38));
		
		if(empezar) {
			//Movimiento de la bola
			posicionBolaX+=velocidadX;
			posicionBolaY+=velocidadY;
			
			// La Superpoderosa IA
			if(posicionBolaX < ancho - ancho  / 15) {
				J2posicionY = posicionBolaY - alturaJugador / 2;
			}  else {
				J2posicionY =  posicionBolaY > J2posicionY + alturaJugador / 2 ?J2posicionY += 1: J2posicionY - 1;
			}
			// Sacar la bola
			gc.fillOval(posicionBolaX, posicionBolaY, bola, bola);
			
		} else {
			//Texto de inicio de saque
			
			gc.setStroke(Color.WHITE);
			gc.setTextAlign(TextAlignment.CENTER);
			
			// Texto de los puntos
			if(puntuacionJ1==0 && puntuacionJ2==0){
				gc.strokeText("DALE CLICK", ancho / 2, largo / 3);
				gc.strokeText("y la superpoderosa IA te patear�", ancho / 2, largo / 2);
			}else if(puntuacionJ1<puntuacionJ2) {
				gc.strokeText("AS� ME GUSTA, PERDIENDO MALOTE", ancho / 2, largo / 3);
			}else if(puntuacionJ1>puntuacionJ2){
				gc.strokeText("No te pases ni un pelo chaval", ancho / 2, largo / 3);
			}else {
				gc.strokeText("EMPATITO ��", ancho / 2, largo / 3);
			}
			
			// Reestablecer la bola
			posicionBolaX = ancho / 2;
			posicionBolaY = largo / 2;
			velocidadX = new Random().nextInt(2) == 0 ? 1: -1;
			velocidadY = new Random().nextInt(2) == 0 ? 1: -1;
			}
		
		// Asegurar la pelota en la lona
		if(posicionBolaY > largo || posicionBolaY < 0) velocidadY *=-1.3;
		
		// Sumar +1 al marcador si fallas
		if(posicionBolaX < J1posicionX - anchoJugador) {
			puntuacionJ2++;
			empezar = false;
		}
		
		// Sumar +1 al marcador si falla el pc
		if(posicionBolaX > J2posicionX + anchoJugador) {  
			puntuacionJ1++;
			empezar = false;
		}
	
		//Aumentar la velocidad despu�s de que la pelota golpea al jugador
		if( ((posicionBolaX + bola > J2posicionX) && posicionBolaY >= J2posicionY && posicionBolaY <= J2posicionY + alturaJugador) || 
			((posicionBolaX < J1posicionX + anchoJugador) && posicionBolaY >= J1posicionY && posicionBolaY <= J1posicionY + alturaJugador)) {
			velocidadY += 1 * Math.signum(velocidadY);
			velocidadX += 2 * Math.signum(velocidadX);
			velocidadX *= -1;
			velocidadY *= -1;
		}
		
		//Delimitacion del marcador
		gc.fillText(puntuacionJ1 + "\t\t\t\t\t\t\t\t" + puntuacionJ2, ancho / 2, 100);
		
		//Delimitacion de jugadores
		
		gc.fillRect(J2posicionX, J2posicionY, anchoJugador, alturaJugador);
		gc.fillRect(J1posicionX, J1posicionY, anchoJugador, alturaJugador);
	}


	

}