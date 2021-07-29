/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quizny;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javax.imageio.ImageIO;
//import org.opencv.core.Core;
//import org.opencv.core.CvType;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfByte;
//import org.opencv.highgui.Highgui;
//import org.opencv.highgui.VideoCapture;
//import org.opencv.imgproc.Imgproc;
import javafx.scene.effect.BlendMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Stop;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.*;
import javafx.stage.WindowEvent;

/**
 *
 * @author RC
 */
public class CameraControle extends AnchorPane{

    @FXML
    ImageView img;
    @FXML
    AnchorPane pane;
    @FXML
    Label texto;
    
    private Principal application;
    
    public static String pontos;
    
    AudioClip clip;
    
    //static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    
    @FXML
    public void initialize() {
        //Calcla o tamanho da tela para centralizar a imagem
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        texto.setText("Congratulations you got to Win!!!");
        
        colors = new Paint[181];
            colors[0] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, 
                        new Stop(0, Color.WHITE),
                        new Stop(0.2, Color.hsb(59, 0.38, 1)),
                        new Stop(0.6, Color.hsb(59, 0.38, 1,0.1)),
                        new Stop(1, Color.hsb(59, 0.38, 1,0))
                        );
            for (int h=0;h<360;h+=2) {
                colors[1+(h/2)] = new RadialGradient(0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE, 
                        new Stop(0, Color.WHITE),
                        new Stop(0.2, Color.hsb(h, 1, 1)),
                        new Stop(0.6, Color.hsb(h, 1, 1,0.1)),
                        new Stop(1, Color.hsb(h, 1, 1,0))
                        );
            }
        
            canvas = new Canvas(1910, 1000);
 
            canvas.setBlendMode(BlendMode.ADD);
            canvas.setEffect(new Reflection(0,0.4,0.15,0));
            pane.getChildren().addAll(canvas);
            // create animation timer that will be called every frame
            // final AnimationTimer timer = new AnimationTimer() {
            timer = new AnimationTimer() {
 
                @Override public void handle(long now) {
                    GraphicsContext gc = canvas.getGraphicsContext2D();
                    // clear area with transparent black
                    gc.setFill(Color.rgb(0, 0, 0, 0.2));
                    gc.fillRect(0, 0, 1920, 1080);
                    // draw fireworks
                    drawFireworks(gc);
                    // countdown to launching the next firework
                    if (countDownTillNextFirework == 0) {
                        countDownTillNextFirework = 10 + (int)(Math.random()*30);
                        fireParticle();
                    }
                    countDownTillNextFirework --;
                }
            };
            
        start();
        timer.start();
       
    }
    
    
    
    private Boolean begin = false;
    private String video = null;
    private CaptureThread thread = null;
    
private void start()
  {

    
    if(begin == false)
    {
      //video = new VideoCapture(0);

      /*if(video.isOpened())
      {
        thread = new CaptureThread();
        thread.start();
        begin = true;
      }*/
    }
  }

    public void Sair(){
        thread.stop();
    }

    /*private MatOfByte matOfByte = new MatOfByte();
    private BufferedImage bufImage = null;
    private InputStream in;
    private Mat frameaux = new Mat();
    private Mat frame = new Mat(768, 1366, CvType.CV_8UC3);*/
    
class CaptureThread extends Thread
  {
    @Override
    public void run()
    {
     /* if(video.isOpened())
      {
        while(begin == true)
        {
          //video.read(frameaux);
          video.retrieve(frameaux);
          Imgproc.resize(frameaux, frame, frame.size());
          Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2RGBA);
          
          Highgui.imencode(".jpg", frame, matOfByte);
          byte[] byteArray = matOfByte.toArray();
       
          try
          {
            in = new ByteArrayInputStream(byteArray);
            //Imagem corrente do video
            bufImage = ImageIO.read(in);
         
          }
          catch(Exception ex)
          {
            ex.printStackTrace();
          }
         
          //image.updateImage(new ImageIcon("figs/lena.png").getImage());
          Image image = SwingFXUtils.toFXImage(bufImage, null);
          img.setImage(image);
          
          try{ Thread.sleep(5); } catch(Exception ex){}
        }
      }*/
    }
  }

        private AnimationTimer timer;
        private Canvas canvas;
        private final List<Particle> particles = new ArrayList<Particle>();
        private Paint[] colors;
        private int countDownTillNextFirework = 40;
        
        private void drawFireworks(GraphicsContext gc) {
            Iterator<Particle> iter = particles.iterator();
            List<Particle> newParticles = new ArrayList<Particle>();
            while(iter.hasNext()) {
                Particle firework = iter.next();
                // if the update returns true then particle has expired
                if(firework.update()) {
                    // remove particle from those drawn
                    iter.remove();
                    // check if it should be exploded
                    if(firework.shouldExplodeChildren) {
                        if(firework.size == 9) {
                            explodeCircle(firework, newParticles);
                        } else if(firework.size == 8) {
                            explodeSmallCircle(firework, newParticles);
                        }
                    }
                }
                firework.draw(gc);
            }
            particles.addAll(newParticles);
        }
         
        private void fireParticle() {
            particles.add(new Particle(
                canvas.getWidth()*0.5, canvas.getHeight()+10,
                Math.random() * 5 - 2.5, 0, 
                0, 150 + Math.random() * 100,
                colors[0], 9,
                false, true, true));
        }
 
 
        private void explodeCircle(Particle firework, List<Particle> newParticles) {
            final int count = 20 + (int)(60*Math.random());
            final boolean shouldExplodeChildren = Math.random() > 0.5;
            final double angle = (Math.PI * 2) / count;
            final int color = (int)(Math.random()*colors.length);
            for(int i=count; i>0; i--) {
                double randomVelocity = 4 + Math.random() * 4;
                double particleAngle = i * angle;
                newParticles.add(
                    new Particle(
                        firework.posX, firework.posY, 
                        Math.cos(particleAngle) * randomVelocity, Math.sin(particleAngle) * randomVelocity,
                        0, 0, 
                        colors[color], 
                        8,
                        true, shouldExplodeChildren, true));
            }
        }
 
        private void explodeSmallCircle(Particle firework, List<Particle> newParticles) {
            final double angle = (Math.PI * 2) / 12;
            for(int count=12; count>0; count--) {
                double randomVelocity = 2 + Math.random() * 2;
                double particleAngle = count * angle;
                newParticles.add(
                    new Particle(
                        firework.posX, firework.posY, 
                        Math.cos(particleAngle) * randomVelocity, Math.sin(particleAngle) * randomVelocity,
                        0, 0, 
                        firework.color, 
                        4,
                        true, false, false));
            }
        }
        public static class Particle {
        private static final double GRAVITY = 0.06;
        // properties for animation
        // and colouring
        double alpha;
        final double easing;
        double fade;
        double posX;
        double posY;
        double velX;
        double velY;
        final double targetX;
        final double targetY;
        final Paint color;
        final int size;
        final boolean usePhysics;
        final boolean shouldExplodeChildren;
        final boolean hasTail;
        double lastPosX;
        double lastPosY;
         
        public Particle(double posX, double posY, double velX, double velY, double targetX, double targetY, 
                Paint color,int size, boolean usePhysics, boolean shouldExplodeChildren, boolean hasTail) {
            this.posX = posX;
            this.posY = posY;
            this.velX = velX;
            this.velY = velY;
            this.targetX = targetX;
            this.targetY = targetY;
            this.color = color;
            this.size = size;
            this.usePhysics = usePhysics;
            this.shouldExplodeChildren = shouldExplodeChildren;
            this.hasTail = hasTail;
            this.alpha    = 1;
            this.easing   = Math.random() * 0.02;
            this.fade     = Math.random() * 0.1;
        }
 
        public boolean update() {
            lastPosX = posX;
            lastPosY = posY;
            if(this.usePhysics) { // on way down
                velY += GRAVITY;
                posY += velY;
                this.alpha -= this.fade; // fade out particle
            } else { // on way up
                double distance = (targetY - posY);
                // ease the position
                posY += distance * (0.03 + easing);
                // cap to 1
                alpha = Math.min(distance * distance * 0.00005, 1);
            }
            posX += velX;
            return alpha < 0.005;
        }
 
        public void draw(GraphicsContext context) {
            final double x = Math.round(posX);
            final double y = Math.round(posY);
            final double xVel = (x - lastPosX) * -5;
            final double yVel = (y - lastPosY) * -5;
            // set the opacity for all drawing of this particle
            context.setGlobalAlpha(Math.random() * this.alpha);
            // draw particle
            context.setFill(color);
            context.fillOval(x-size, y-size, size+size, size+size);
            // draw the arrow triangle from where we were to where we are now
            if (hasTail) {
                context.setFill(Color.rgb(255,255,255,0.3));
                context.fillPolygon(new double[]{posX + 1.5,posX + xVel,posX - 1.5}, 
                        new double[]{posY,posY + yVel,posY}, 3);
            }
        }
    }
    
    public void setApp(Principal application){
        this.application = application;
    }
    
    public void Inicio(){
        
        System.out.println("clicou");
        Sair();
        timer.stop();
        clip.stop();
        this.application.gotoLogin();
    }
    
    public void setAudio(AudioClip clip){
        this.clip = clip;
    }
    
}
