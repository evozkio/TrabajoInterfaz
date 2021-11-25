package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball {
    private ImageView imageView = new ImageView( new Image("/imagen/ball.png"));
    private Double posicionX;
    private Double posicionY;
    private Double ocupa;
    private Boolean direccion;
    private Image imagenBall = new Image("/imagen/ball.png");
    private Double dx = 3.0; 
    private Double dy = 1.0; 
    private ImageView imagenDeColision;

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

    public Boolean hitBoxLeft(){
        Double imagenTopXRight = imagenDeColision.getX()+imagenDeColision.getFitWidth();
        Double imagenTopYRight = imagenDeColision.getY();
        
        Double [] cordenada = {imagenTopXRight,imagenTopYRight};
        
        if (calculoY(puntoLeft(), puntoCenter(), cordenada))return true;
        return false;
    }
    public Boolean hitBoxRight(){
        Double imagenTopXLeft = imagenDeColision.getX();
        Double imagenTopYLeft = imagenDeColision.getY();
        
        Double [] cordenada = {imagenTopXLeft,imagenTopYLeft};


        if (calculoY(puntoLeft(), puntoCenter(), cordenada))return true;
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

    //Devuelve true cuando es arriba o false si es abajo esto se 
    //calcula de la cordenada a una linea que se forma con los dos puntos
    public Boolean calculoY(Double[] punto1,Double[] punto2, Double[] cordenada){
        Double calculo = ((cordenada[0]-punto1[0])/(punto2[0]-punto1[0])*(punto2[1]-punto1[1]))+punto1[1];

        if(calculo>cordenada[1]) return true;
        return false;
    }

    public Boolean calcularColision(ImageView imagen){

        this.imagenDeColision = imagen;

        if (RespectoAbajo() && RespectoLateralDerecho() && RespectoLateralIzquierdo()) {
          return true;
                   
        }
        return false;
    }

    public Boolean RespectoAbajo(){
        if(imagenDeColision.getY() < this.getImageView().getY()+ (this.getImageView().getFitHeight()))return true;
        return false;
    }

    public Boolean RespectoLateralDerecho(){
        if(imagenDeColision.getX() < (this.getImageView().getX()+ this.getImageView().getFitWidth()))return true;
        return false;
    }

    public Boolean RespectoLateralIzquierdo(){
        if(imagenDeColision.getX() + (imagenDeColision.getFitWidth()) > this.getImageView().getX())return true;
        return false;
    }


}




// if((this.imageView.getY()+(this.imageView.getFitHeight()*3/4))<imagen.getY()){
//     if (puntoCenter()[0]<imagen.getX()) {
//         System.out.println("right");
//         return hitBoxRight();
//     }
//     else{
//         System.out.println("left");

//         return hitBoxLeft();
//     }
// }
// else{
//     System.out.println("fuera");
//     return true;  
// } 
