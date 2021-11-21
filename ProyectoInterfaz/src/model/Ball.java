package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    ImageView imageView = new ImageView( new Image("/imagen/ball.png"));
    Double posicionX;
    Double posicionY;
    Double ocupa;
    Boolean direccion;
    Image imagenBall = new Image("/imagen/ball.png");
    double dx = 3; 
    double dy = 1; 
    double [] punto1;
    double [] punto2;
    double [] punto3;

    public Ball(){

    }

    public Ball(Double posicionX,Double posicionY,Double ocupa,Boolean direccion){
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.direccion = direccion;
        this.setImageView();
        this.setOcupa(ocupa);
        if(direccion){
            dx = -dx;
        }
    }

    public Ball(Ball ball) {
        this.posicionX = ball.getPosicionX();
        this.posicionY = ball.getPosicionY();
        this.direccion = ball.getDireccion();
        this.setImageView();
        this.setOcupa(ball.getOcupa());
    }

    public ImageView getImageView() {
        return this.imageView;
        
    }

    public void setImageView() {
        this.imageView.setX(posicionX);
        this.imageView.setY(posicionY);
        this.imageView.setViewOrder(0);
    }

    public Double getOcupa() {
        return this.ocupa;
    }

    public void setOcupa(Double ocupa) {
        this.imageView.setFitHeight(this.imagenBall.getHeight()*ocupa);
        this.imageView.setFitWidth(this.imagenBall.getWidth()*ocupa);
        this.ocupa = ocupa;
    }

    public Double getPosicionX() {
        return this.posicionX;
    }

    public void setPosicionX(Double posicionX) {
        this.imageView.setX(posicionX);
        this.posicionX = posicionX;
    }

    public Double getPosicionY() {
        return this.posicionY;
    }

    public void setPosicionY(Double posicionY) {
        this.imageView.setY(posicionY);
        this.posicionY = posicionY;
    }

    public Boolean getDireccion() {
        return this.direccion;
    }

    public void setDireccion(Boolean direccion) {
        if (direccion != this.direccion){
            dx = -dx;
        }
        this.direccion = direccion;
    }



    public void movimiento(){
 
        this.posicionX += dx;
        this.posicionY +=dy;

        this.imageView.setX(posicionX);
        this.imageView.setY(posicionY);

        if (this.posicionX<0 || this.posicionX+this.imageView.getFitWidth()>720.0) {
            dx = -dx;
        }
        if (this.posicionY<0 || this.posicionY+this.imageView.getFitHeight()>390.0) {
            dy = -dy;
        }
    }

    public Boolean hitBoxLeft(double [] cordenada){
        Double[] punto1 = puntoLeft();
        Double[] punto2 = puntoCenter();
        
        Double calculo = (((punto2[1]-punto1[1])/(punto2[0]-punto1[0]))*(cordenada[0]-punto1[0]))-punto1[1];

        if(calculo>cordenada[1]) return true;
        return false;
    }
    public Boolean hitBoxRight(double [] cordenada){
        Double[] punto1 = puntoRight();
        Double[] punto2 = puntoCenter();

        Double calculo = (((punto2[1]-punto1[1])/(punto2[0]-punto1[0]))*(cordenada[0]-punto1[0]))-punto1[1];

        if(calculo>cordenada[1]) return true;
        return false;
    }

    public Double[] puntoLeft(){
        Double x = this.imageView.getX();
        Double y = this.imageView.getY()+(this.getImageView().getFitHeight()*0.75);
        Double [] punto = {x,y};
        return punto;
    }

    public Double[] puntoRight(){  
        Double x = this.imageView.getX()+this.imageView.getFitWidth();
        Double y = this.imageView.getY()+(this.getImageView().getFitHeight()*0.75);
        Double [] punto = {x,y};
        return punto; 
    }
    public Double[] puntoCenter(){  
        Double x = this.imageView.getX()+(this.imageView.getFitWidth()*0.5);
        Double y = this.imageView.getY()+(this.getImageView().getFitHeight());
        Double [] punto = {x,y};
        return punto;
    }


}
