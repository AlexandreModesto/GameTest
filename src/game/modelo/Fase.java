package game.modelo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Fase extends JPanel implements ActionListener {
    private Image fundo;
    private Player player;
    private Timer timer;

    private List<Enemy1> enemy1;

    private boolean emJogo;

    public Fase(){
        setFocusable(true);
        setDoubleBuffered(true);
        ImageIcon refencia = new ImageIcon("pygame/bg/background.png");
        fundo = refencia.getImage();

        player = new Player();
        player.load();

        addKeyListener(new TecladoAdapter());

        timer = new Timer(5,this);
        timer.start();

        inicializaInimigos();
        emJogo=true;
    }
    public void inicializaInimigos(){
        int coordenadas[]=new int[40];
        enemy1 = new ArrayList<Enemy1>();

        for(int i=0;i<coordenadas.length;i++){
            int x = (int)(Math.random()*8000+1024);
            int y = (int)(Math.random()*650+30);
            enemy1.add(new Enemy1(x,y));
        }
    }
    public void paint(Graphics g){
        Graphics2D graficos = (Graphics2D) g;
        if (emJogo == true) {
            graficos.drawImage(fundo, 0, 0, null);
            graficos.drawImage(player.getImagem(), player.getX(), player.getY(), this);

            List<Tiro> tiros = player.getTiros();
            for (int i = 0; i < tiros.size(); i++) {
                Tiro m = tiros.get(i);
                m.load();
                graficos.drawImage(m.getImagem(), m.getX(), m.getY(), this);
            }
            for (int j = 0; j < enemy1.size(); j++) {
                Enemy1 in = enemy1.get(j);
                in.load();
                graficos.drawImage(in.getImagem(), in.getX(), in.getY(), this);
            }
        }else {
            ImageIcon fimJogo = new ImageIcon();
            graficos.drawImage(fimJogo.getImage(),0,0,null);
        }
        g.dispose();
    }


    @Override
    public void actionPerformed(ActionEvent e){
        player.update();
        List<Tiro> tiros = player.getTiros();

        for(int i=0;i<tiros.size();i++){
            Tiro m = tiros.get(i);
            if (m.isVisivel()) {
                m.update();
            }else {
                tiros.remove(i);
            }
        }
        for(int j=0;j<enemy1.size();j++){
            Enemy1 in = enemy1.get(j);
            if (in.isVisivel()) {
                in.update();
            }else {
                enemy1.remove(j);
            }
        }
        checarColisoes();
        repaint();
    }
    public  void checarColisoes(){
        Rectangle formanave=player.getBounds();
        Rectangle formaEnemy;
        Rectangle formaTiro;

        for(int i=0;i<enemy1.size();i++){
            Enemy1 tempEnemy1 = enemy1.get(i);
            formaEnemy = tempEnemy1.getBounds();
            if (formanave.intersects(formaEnemy)) {
                player.setVisible(false);
                tempEnemy1.setVisivel(false);
                emJogo=false;
            }
        }
        List<Tiro> tiros = player.getTiros();
        for(int j=0;j<tiros.size();j++){
            Tiro tempTiro = tiros.get(j);

            formaTiro = tempTiro.getBounds();
            for (int o=0;o<enemy1.size();o++){
                Enemy1 tempEnemy1 = enemy1.get(o);
                formaEnemy = tempEnemy1.getBounds();
                if(formaTiro.intersects(formaEnemy)){
                    tempEnemy1.setVisivel(false);
                    tempTiro.setVisivel(false);
                }
            }
        }
    }
    private class TecladoAdapter extends KeyAdapter {

        @Override
        public synchronized void keyPressed(KeyEvent e){
            player.keyPressed(e);
        }

        @Override
        public synchronized void keyReleased(KeyEvent e){
            player.keyReleased(e);
        }
    }
}
