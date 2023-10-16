package game.modelo;

import javax.swing.*;
import java.awt.*;

public class Tiro {
    private Image imagem;
    private int x,y;
    private int largura,altura;
    private boolean isVisivel;

    private static final int LARGURA = 938;
    private static int VELOCIDADE =2;

    public Tiro(int x,int y){
        this.x=x;
        this.y=y;
        isVisivel=true;
    }
    public void load(){
        ImageIcon referencia = new ImageIcon("pygame/Icons/sword.png");
        imagem = referencia.getImage();

        this.largura = imagem.getWidth(null);
        this.altura = imagem.getHeight(null);
    }
    public  void update(){
        this.x+=VELOCIDADE;
        if (this.x > LARGURA) {
            isVisivel=false;
        }
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,largura,altura);
    }
    public void setVisivel(boolean visivel) {
        isVisivel = visivel;
    }

    public Image getImagem() {
        return imagem;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisivel() {
        return isVisivel;
    }

    public static int getVELOCIDADE() {
        return VELOCIDADE;
    }

    public static void setVELOCIDADE(int VELOCIDADE) {
        Tiro.VELOCIDADE = VELOCIDADE;
    }
}

