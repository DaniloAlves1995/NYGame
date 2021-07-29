/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quizny;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Lighting;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 *
 * @author danil
 */
public class PerguntaControler extends AnchorPane {
    
    @FXML
    Label pergunta, msgcorreto, msgnota;
    @FXML
    Button yes, no;
    @FXML
    ImageView btn_proxima;
    
    
    private Principal application;
    
    int errou=0;
    
    int num_pergunta=0;
    
    Thread lerserial;
    Serial serial;
    
    String [] perguntas = {"O primeiro nome de nova york foi nova amisterdã."," Giovanni da Verrazano foi quem comprou nova york dos indígenas",
    "Brooklyn, Queens e Bronx são bairros criados desde a fundação da cidade.", "A extensão territorial de nova york era apenas manhattan.",
    "Na Broadway existem mais de 40 teatros","O Baskete foi criado por um professor de Ed. Física Canadense", 
    "Atualmente o NBA conta com 20 equipes","Os jogos do New York Knicks são disputados no Madison Square Garden",
    "Beyonce, Jay-Z e Cris Rock são torcedores famosos do New York Knicks","O ingresso mais barato para assistir o Brooklin Nets custa 20 dólares.",
    "O musical “O fantasma da Ópera” está em cartaz há mais de 40 anos.","A avenida da Broadway aparece no filme King Kong exibindo o teatro Broadway.",
    "A avenida Broadway está localizada no bairro do Brooklyn.","O hip-hop veio do Brooklyn e originou-se pelos escravos nas plantações de algodão.",
    "Lady Gaga nasceu e reside até hoje em Nova Iorque.", "O punk originou-se no Brooklyn.",
    "O café foi trazido pros EUA pelos europeus em 1600", "O chá era a bebida mais consumida até 1700 mas o rei George III colocava impostos muito altos então  a população se revoltou e começou a consumir mais o café",
    "Quem bebia chá por volta de 1700 era considerado antipatriota", "A estatua da liberdade foi construída na França",
    "A localização da estatua da liberdade é  em uma pequena ilha chamada unsuccessful", "A estatua da liberdade foi presente dos franceses ao cenário da sua independência",
    "As temporadas de jogos de Baseball iniciam em Abril e termina em Setembro", "Cada time de Baseball tem que jogar 100 partidas em cada temporada.",
    "Michel Jordan anunciou sua aposentadoria aos 35 anos","Michel Jordan é o quarto maior cestinha da história da liga.",
    "Michel Jordan foi três vezes medalha de outro nas olimpíadas.", "No total Michel Jordan venceu 8 vezes o título da NBA",
    "Michel Jordan nasceu no Brooklyn em New York.", "O número oficial de Michel Jordan é 23.",
    "O MoMa - Museu de Arte Moderna - foi retratado várias vezes na série Gossip Girls"," prédio Empire State Building é visto por todo o Brooklyn"};
    boolean [] resp = {true, false, false, true, true, true, false, true, false, true, false, true, false, false, true, false, true, true, true, true, false, true, true, false, true, true, false, false,
    true, true, false, false};
    int [] valores = {-1, -1, -1};
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        msgcorreto.opacityProperty().setValue(0);
        btn_proxima.opacityProperty().setValue(0);
        btn_proxima.setDisable(true);
        msgnota.setVisible(false);
        gerarPergunta();
        iniciarSerial();
    }
    
    public void gerarPergunta(){
        
        
        boolean verificar = true;
        int n=0;
        while(verificar){
            
            Random random = new Random();
            int cont=0;
            n = random.nextInt(perguntas.length);
            System.out.println("numero:"+n);
            for(int i=0;i<3;i++){
                if(valores[i]==n){
                    cont=1;
                    break;
                }
            }
            if(cont==0)
                verificar=false;
        }
        valores[num_pergunta] = n;
        //insere a pergunta
        pergunta.setText(perguntas[n]);
        
    }
    
    public void proxima(){
        if(errou!=1){
            if(num_pergunta<1){
                num_pergunta++;
                //Limpa os efeitos
                GaussianBlur ef1 = (GaussianBlur) msgnota.effectProperty().getValue();
                ef1.setRadius(63);
                msgnota.setRotate(-29.7);
                msgcorreto.opacityProperty().setValue(0);
                btn_proxima.opacityProperty().setValue(0);
                btn_proxima.setDisable(true);
                msgnota.setVisible(false);
                gerarPergunta();
                serial.p1=0;
                serial.p2=0;
            }else{
                //Som da mensagem para o erro
                AudioClip m1 = new AudioClip(PerguntaControler.class.getResource("/Sons/Vitoria.wav").toString());
                m1.play();
                //se conseguir chegara até aqui venceu
                this.application.gotoVitoria(m1);
                System.out.println("venceu!");
            }
        }else{
            serial.close();
            lerserial.stop();
            this.application.gotoLogin();
        }
    }
    
    public void setApp(Principal application){
        this.application = application;
    }
    
    public void pressionarY(){
        efeitobotaoClicado(yes);
    }
    
    public void soltarY(){
        efeitobotaoNClicado(yes);
        verificarResposta(true);
    }
    
    public void pressionarN(){
        efeitobotaoClicado(no);
    }
    
    public void soltarN(){
        efeitobotaoNClicado(no);
        verificarResposta(false);
    }
    
    public void efeitobotaoClicado(Button bt){
        Lighting l = new Lighting();
        l.setSpecularConstant(0.54);
        l.setSpecularExponent(25.85);
        l.setSurfaceScale(1.26);
        l.setDiffuseConstant(2);
        
        bt.setEffect(l);
    }
    public void efeitobotaoNClicado(Button bt){
        Lighting l = new Lighting();
        l.setSpecularConstant(0.54);
        l.setSpecularExponent(25.85);
        l.setSurfaceScale(1.26);
        l.setDiffuseConstant(1);
        
        bt.setEffect(l);
    }
    
    public void verificarResposta(boolean resposta){
        aparecer();
        if(resp[valores[num_pergunta]]==resposta){
            //Som da mensagem certa
            final AudioClip m1 = new AudioClip(PerguntaControler.class.getResource("/Sons/Acertar.wav").toString());
            m1.play();
            msgnota.setVisible(true);
            //----------------- Efeito de girar o label da nota ---------------------
            final Timeline timeli = new Timeline();
            final KeyValue v1 = new KeyValue(msgnota.rotateProperty(), 330);
            
            final KeyFrame k = new KeyFrame(Duration.millis(2000), v1);
 
            timeli.getKeyFrames().add(k);
            timeli.play();
            
            //---------------------- Fim do Efeito ---------------------
            System.out.println("certo");
            msgcorreto.setText("Right!");//
            msgcorreto.setTextFill(Paint.valueOf("#00ed18"));
            
            //---------------- Efeito de Aparecer as msg no quadro -----------
            
            GaussianBlur ef1 = (GaussianBlur) msgnota.effectProperty().getValue();
            final Timeline timeline = new Timeline();
            final KeyValue kv1 = new KeyValue(ef1.radiusProperty(), 0);
            final KeyFrame kf = new KeyFrame(Duration.millis(2000), kv1);
 
            timeline.getKeyFrames().add(kf);
            timeline.play();
            //----------------- Fim do Efeito ------------------------
        }else{
            //Som da mensagem para o erro
            final AudioClip m1 = new AudioClip(PerguntaControler.class.getResource("/Sons/Erro.wav").toString());
            m1.play();
            errou=1;
            System.out.println("errado");
            msgcorreto.setText("Wrong! >_<");
            msgcorreto.setTextFill(Paint.valueOf("#ff0000"));
        }
    }
    
    //Efeito pra fazer a msg de correto aparecer!
    private void aparecer(){
        btn_proxima.setDisable(false);
        final Timeline timeline = new Timeline();
        
        final KeyValue kv = new KeyValue(msgcorreto.opacityProperty(), 1);
        final KeyValue kv2 = new KeyValue(btn_proxima.opacityProperty(), 1);
        final KeyFrame kf = new KeyFrame(Duration.millis(3000), kv);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(3000), kv2);
 
        timeline.getKeyFrames().addAll(kf,kf2);
        timeline.play();
    }
    
    //leitura serial
    public void iniciarSerial(){
        serial = new Serial();
        serial.setPergunta(this);
        
        serial.initialize();
        lerserial = new Thread(){
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(1000);
                        
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PerguntaControler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
          
        };
        lerserial.start();
    }
}
