/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizny;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Login Controller.
 */
public class PrincipalControler extends AnchorPane {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ImageView balao;
    @FXML
    AnchorPane pane;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ImageView n1, n2, n3, n4, n5, n6, n7, n8, n9;

    private Principal application;
    
    private PathTransition transition;
    private PathTransition transition2;
    private Timeline timeline;
    
    
    public void setApp(Principal application){
        this.application = application;
    }
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        animacaoaviao();
        efeitonuvens();
    }
    
    public void play(){
        transition.play();
        transition2.play();
        timeline.play();
    }
    
    
    
    public void animacaoaviao(){
        
        final ImageView imageView = new ImageView("/Imagens/av.png");
        imageView.setFitWidth(92);
        imageView.setFitHeight(32);
        
        
        
        Rectangle rect = new Rectangle(0, 0, 40, 40);
        rect.setArcHeight(10);
        rect.setArcWidth(10);
        rect.setFill(Color.ORANGE);
        
        
        pane.getChildren().add(rect);
        
        Path path = new Path(new MoveTo(0, 500),
                             new CubicCurveTo(220, 700, 1050, 600, 1920, 500));
        
        Path path2 = new Path(new MoveTo(0, 300),
                             new CubicCurveTo(220, 50, 1050, 500, 1400, 5));
        path2.setStroke(Color.BLUE);
        path2.getStrokeDashArray().setAll(5d, 5d);
        
        path.setStroke(Color.BLUE);
        path.getStrokeDashArray().setAll(5d, 5d);
        
        pane.getChildren().addAll(path,path2);
        
        transition = new PathTransition(Duration.seconds(10), path);
        transition.setNode(imageView);
        
        transition2 = new PathTransition(Duration.seconds(40), path2);
        transition2.setNode(balao);
        transition2.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        transition2.setCycleCount(Timeline.INDEFINITE);
        
        pane.getChildren().addAll(imageView);
        transition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        transition.setCycleCount(Timeline.INDEFINITE);
     
       // transition.setAutoReverse(true);
        
        
    }
    
    public void efeitonuvens(){
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
       
       
 
        // add the following keyframes to the timeline
        timeline.getKeyFrames().addAll(new KeyFrame(Duration.ZERO,
                new KeyValue(n1.translateXProperty(), 0)),
                new KeyFrame(new Duration(40000),
                new KeyValue(n1.translateXProperty(), -1200)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n2.translateXProperty(), 0)),
                new KeyFrame(new Duration(40000),
                new KeyValue(n2.translateXProperty(), -1200)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n3.translateXProperty(), 0)),
                new KeyFrame(new Duration(45000),
                new KeyValue(n3.translateXProperty(), -1200)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n4.translateXProperty(), 0)),
                new KeyFrame(new Duration(40000),
                new KeyValue(n4.translateXProperty(), -1000)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n5.translateXProperty(), 0)),
                new KeyFrame(new Duration(34000),
                new KeyValue(n5.translateXProperty(), -1000)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n6.translateXProperty(), 0)),
                new KeyFrame(new Duration(34000),
                new KeyValue(n6.translateXProperty(), -400)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n7.translateXProperty(), 0)),
                new KeyFrame(new Duration(34000),
                new KeyValue(n7.translateXProperty(), -450)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n8.translateXProperty(), 0)),
                new KeyFrame(new Duration(34000),
                new KeyValue(n8.translateXProperty(), -400)),
                new KeyFrame(Duration.ZERO,
                new KeyValue(n9.translateXProperty(), 0)),
                new KeyFrame(new Duration(40000),
                new KeyValue(n9.translateXProperty(), -400)));
    }
    
    public void abrirPergunta(){
        System.out.println("sdsdsdsdsd");
        application.gotoPergunta();
    }

    
}

