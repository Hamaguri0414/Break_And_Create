import java.net.URL;
import java.util.ResourceBundle;

import com.sun.glass.events.MouseEvent;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.File;
import java.net.MalformedURLException;
import javafx.scene.media.AudioClip;
import java.nio.file.Paths;

public class MapGameController implements Initializable {
    public MapData mapData;
    public MoveChara chara;
    public GridPane mapGrid;
    public Pane pikCount;
    public Pane wallCount;
    public Pane timeCount;
    public Pane plus;
    public Pane coolTime;
    public Pane retryGame;
    public ImageView back;
    public ImageView m3r;
    public ImageView m3l;
    public ImageView m3m;
    public ImageView m2r;
    public ImageView m2l;
    public ImageView m2m;
    public ImageView m1r;
    public ImageView m1l;
    public ImageView m1m;
    public ImageView m0r;
    public ImageView m0l;
    public ImageView watch;
    public ImageView pik;
    public ImageView key;
    public ImageView bigkey;
    public ImageView door;
    public ImageView door2;
    public ImageView door3;
    public ImageView solve;
    public ImageView give;
    public ImageView[] mapImageViews;
    public int direction = 4;
    public int SW = 0;
    public int timeWatch = 0;
    public int coolWatch = 0;
    public int timeDetail = 10000000;
    public int haveKey = 0;
    public int coolCheck = 0;//0なら置ける
    public int idaten = 1;
    public int intTime = 0;
    public int time2 = 0;
    public int init = 0;
    public int timeCheck = 0;
    public int checkMusic = 0;
    public int channel = 1;
    public int prechan = 1 ;
    public int checkFinal = 0;
    AudioClip[] BGM;
    AudioClip PikS;
    AudioClip watS;
    AudioClip KeyS;
    AudioClip digS;
    AudioClip putS;
    AudioClip ippo;
    AudioClip gams;
    AudioClip gamf;
    public URL url;
    public ResourceBundle rb;

    //バランス調整────────────────────────────────
    public static int player = 0;      //0なら丸  1ならネコが主人公になる────
    public int havePik   =     5;      //最初に持ってるピッケルの数を設定────
    public int haveWall  =     8;      //最初に持ってる壁の数を設定───────
    public int time      =    45;      //制限時間の秒数を設定──────────
    public int times     =     3;      //タイマーを使用した時の増加時間を設定──
    public int roadTime  =     3;      //試合が終了してから再開するまでの時間設定
    public int putWatch  =     4;      //マップ上に置かれているタイマーの数を設定
    public int putPik    =     2;      //マップ上に置かれているピッケルの数を設定
    public int coolTimes =     6;      //壁を置いた時のクールタイムが何秒かを設定
                                       //coolTimesは10で1秒→1.2秒に設定の場合 12
    //バランス調整────────────────────────────────



    //初期化用保存──────────いじらないで────────────────
    public int playerR    = player    ;
    public int havePikR   = havePik   ;
    public int haveWallR  = haveWall  ;
    public int timeR      = time      ;
    public int timeDR     = timeDetail;
    public int timesR     = times     ;
    public int putWatchR  = putWatch  ;
    public int putPikR    = putPik    ;
    public int coolTimesR = coolTimes ;
    //初期化用保存──────────いじらないで────────────────

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.url = url;
        this.rb = rb;
        //変数の値を元に戻す
        player    = playerR   ;
        havePik   = havePikR  ;  
        haveWall  = haveWallR ; 
        time      = timeR     ;
        timeDetail= timeDR    ;
        times     = timesR    ;  
        putWatch  = putWatchR ;  
        putPik    = putPikR   ; 
        coolTimes = coolTimesR;
        SW        = 0         ;
        coolCheck = 0         ;
        init      = 0         ;
        direction = 0         ;
        idaten    = 1         ;
        haveKey   = 0         ;
        checkFinal = 0        ;
        mapData = new MapData(21,15);
        chara = new MoveChara(1,1,mapData);
        bigkey   .setVisible(false);
        give     .setVisible(false);
        solve    .setVisible(false);
        retryGame.setVisible(false);

        if(BGM == null){
            BGM = new AudioClip[23];
            for(int i = 1;i < 23;i++){
                BGM[i] = new AudioClip(Paths.get("BGM/BGM" + i +".wav").toUri().toString());
            }
            PikS = new AudioClip(Paths.get("sound/pikS.wav").toUri().toString());
            watS = new AudioClip(Paths.get("sound/watS.wav").toUri().toString());
            KeyS = new AudioClip(Paths.get("sound/KeyS.wav").toUri().toString());
            digS = new AudioClip(Paths.get("sound/digS.wav").toUri().toString());
            putS = new AudioClip(Paths.get("sound/putS.wav").toUri().toString());
            ippo = new AudioClip(Paths.get("sound/ippo.wav").toUri().toString());
            gams = new AudioClip(Paths.get("sound/gameStart.wav").toUri().toString());
            gamf = new AudioClip(Paths.get("sound/gameFinal.wav").toUri().toString());

        }
        gams.play();
        try{
            Thread.sleep(500);
            }catch(InterruptedException e){}
        channel = (int)(Math.random()*22 + 1);
        BGM[channel].setVolume(1.0);
        BGM[prechan].stop();
        prechan = channel;
        BGM[channel].play();
        mapImageViews = new ImageView[mapData.getHeight()*mapData.getWidth()];
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                mapImageViews[index] = mapData.getImageView(x,y);
            }
        }
        
        setItem(MapData.TYPE_WATCH,putWatch);
        setItem(MapData.TYPE_PIK,putPik);
        setItem(MapData.TYPE_KEY,1);
        chara.setCharaDir(direction % 4);
        update3D();
        mapPrint(chara, mapData);
        if(timeCheck == 0){
            time();
            timeCheck = 1;
        }
        javafx.application.Platform.runLater(() -> {
            Scene s = mapGrid.getScene();
            s.setOnKeyReleased(key_event -> {
                keyAction(key_event);
            });
            clickAction();
            update3D();
        });
        
    }

    public void time(){
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(100), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                if(time >= 0 && SW == 0){
                    Text countt = new Text(String.valueOf(time));
                    countt.setFont( Font.font(100) );
                    if(time >= 100){
                        countt.setX(78);
                    }else if(time >= 10){
                        countt.setX(105);
                    }else{
                        countt.setX(136);
                    }
                    timeCount.getChildren().clear();
                    timeCount.getChildren().add(countt);
                }
                if(time < 0 || SW == 1){
                    if(checkFinal == 0){
                        gamf.play();
                        checkFinal = 1;
                    }
                    Text countt2 = new Text(String.valueOf(intTime));
                    idaten %= 10;
                    if(idaten == 0){
                        intTime--;
                    }
                    countt2.setFont( Font.font(130) );
                    countt2.setX(60);
                    countt2.setY(10);
                    retryGame.getChildren().clear();
                    retryGame.getChildren().add(countt2);
                    if(intTime < 0){
                        retryGame.getChildren().clear();
                        initialize(url,rb);
                    }
                }
                if(timeWatch > timeDetail + 8){
                    plus.getChildren().clear();
                }
                if(coolWatch > timeDetail + coolTimes){
                    coolTime.getChildren().clear();
                    if(coolCheck == 1){
                        coolCheck = 0;
                    }
                }
                idaten %= 10;
                if(idaten == 0){
                    time -= 1;
                }
                idaten++;
                timeDetail -= 1;

                if(SW == 0){
                    give.setVisible(time < 0);
                    initializeTime();
                }
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    public void mapPrint(MoveChara c, MapData m){
        int cx = c.getPosX();
        int cy = c.getPosY();
        mapGrid.getChildren().clear();
        for(int y=0; y<mapData.getHeight(); y++){
            for(int x=0; x<mapData.getWidth(); x++){
                int index = y*mapData.getWidth() + x;
                if (x==cx && y==cy) {
                    mapGrid.add(c.getCharaImageView(), x, y);
                } else {
                    mapGrid.add(mapImageViews[index], x, y);
                }
            }
        }
    }

    public void setItem(int TYPE,int numbers){
        //Mapは21x15
        for (int i = 0;i < numbers;i++){
            int temp = 0;;
            while(temp == 0){
                int ranX = (int)(Math.random()*19 + 1);
                int ranY = (int)(Math.random()*13 + 1);
                if(mapData.getMap(ranX,ranY) == 0){
                    if(ranX != 1 || ranY != 1){
                        if(ranX != 19 || ranY != 13){
                            if(TYPE != MapData.TYPE_KEY){
                                mapData.setMap(ranX,ranY,TYPE);
                                temp++;
                            }else if(TYPE == MapData.TYPE_KEY){
                                if((7 <= ranX && ranX <= 13) && (5 <= ranY && ranY <= 9)){
                                    mapData.setMap(ranX,ranY,TYPE);
                                    temp++;
                                }
                            }
                        }
                    }
                }            
            }
        }
    }

    public void initializeTime(){
        if(init == 0){
            retryGame.setVisible(true);
            idaten = 1;
            intTime = roadTime;
            init = 1;
        }
    }

    public void clickAction(){
        Scene s = mapGrid.getScene();
        s.setOnMouseClicked((e) -> {
            int xs = (int)((e.getX() - 682) / 32);
            int ys = (int)((e.getY() - 87) / 32);
            if((0 < xs && xs < 20) && (0 < ys && ys < 14)){
                if(mapData.getMap(xs,ys) != MapData.TYPE_KEY && mapData.getMap(xs,ys) != MapData.TYPE_WALL 
                    && mapData.getMap(xs,ys) != MapData.TYPE_DOOR && haveWall > 0 && time >= 0 && SW == 0 && coolCheck == 0){
                        mapData.setMap(xs,ys,MapData.TYPE_WALL);
                        item(MapData.TYPE_WALL,1);
                    }
                update3D();
                mapPrint(chara, mapData);
            }
        });
    }

    public void keyAction(KeyEvent event){
        if(time >= 0 && SW == 0){
            KeyCode key = event.getCode();
            if (key == KeyCode.DOWN){
                downButtonAction();
            }else if (key == KeyCode.RIGHT){
                rightButtonAction();
            }else if (key == KeyCode.LEFT){
                leftButtonAction();
            }else if (key == KeyCode.UP){
                upButtonAction();
            }else if (key == KeyCode.SPACE){
                action();
            }else if (key == KeyCode.ESCAPE){
                time = 0;
            }else if (key == KeyCode.T){
                time = 999;
                havePik = 99;
                haveWall = 99;
                update3D();
            }
        }
    }

    public void item(int TYPE,int haveOrUse){
        if(haveOrUse == 0){ //have
            if(TYPE == MapData.TYPE_WATCH){
                time += times;
                timeWatch = timeDetail;
                watS.play();
                Text plusTime = new Text("+ " + times + " sec !");
                plusTime.setFont( Font.font(30) );
                plus.getChildren().clear();
                plus.getChildren().add(plusTime);
            }else if(TYPE == MapData.TYPE_PIK){
                havePik++;
                PikS.play();
            }else if(TYPE == MapData.TYPE_KEY){
                haveKey++;
                bigkey.setVisible(true);
                KeyS.play();
            }else if(TYPE == MapData.TYPE_WALL){
                haveWall++;
            }
        }else{ //use
            if(TYPE == MapData.TYPE_PIK){
                havePik--;
                digS.play();
            }else if(TYPE == MapData.TYPE_WALL){
                haveWall--;
                coolWatch = timeDetail;
                coolCheck = 1;
                putS.play();
                Text coolTimer = new Text("かべ準備中...");
                coolTimer.setFont( Font.font(20) );
                coolTimer.setX(50);
                coolTime.getChildren().clear();
                coolTime.getChildren().add(coolTimer);
            }
        }
    }

    public void action(){
        if(chara.getCharaDir() == 0 && chara.getPosY() != 13){
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() + 1) == MapData.TYPE_WALL ){
                if(havePik > 0){
                    mapData.setMap(chara.getPosX()    ,chara.getPosY() + 1,MapData.TYPE_NONE);
                    item(MapData.TYPE_PIK,1);
                }
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() + 1) == MapData.TYPE_WATCH){
                mapData.setMap(chara.getPosX()    ,chara.getPosY() + 1,MapData.TYPE_NONE);
                item(MapData.TYPE_WATCH,0);
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() + 1) == MapData.TYPE_PIK  ){
                mapData.setMap(chara.getPosX()    ,chara.getPosY() + 1,MapData.TYPE_NONE);
                item(MapData.TYPE_PIK,0);
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() + 1) == MapData.TYPE_KEY  ){
                mapData.setMap(chara.getPosX()    ,chara.getPosY() + 1,MapData.TYPE_NONE);
                item(MapData.TYPE_KEY,0);
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() + 1) == MapData.TYPE_DOOR ){
                if(haveKey != 0){
                    SW = 1;
                    initializeTime();
                }
            }
        }else if(chara.getCharaDir() == 1){
            if (mapData.getMap(chara.getPosX() - 1,chara.getPosY()    ) == MapData.TYPE_WALL ){
                if(havePik > 0){
                    if(chara.getPosX() - 1 != 0){
                        mapData.setMap(chara.getPosX() - 1,chara.getPosY()    ,MapData.TYPE_NONE);
                        item(MapData.TYPE_PIK,1);
                    }
                }
            }
            if (mapData.getMap(chara.getPosX() - 1,chara.getPosY()    ) == MapData.TYPE_WATCH){
                mapData.setMap(chara.getPosX() - 1,chara.getPosY()    ,MapData.TYPE_NONE);
                item(MapData.TYPE_WATCH,0);
            }
            if (mapData.getMap(chara.getPosX() - 1,chara.getPosY()    ) == MapData.TYPE_PIK  ){
                mapData.setMap(chara.getPosX() - 1,chara.getPosY()    ,MapData.TYPE_NONE);
                item(MapData.TYPE_PIK,0);
            }
            if (mapData.getMap(chara.getPosX() - 1,chara.getPosY()    ) == MapData.TYPE_KEY  ){
                mapData.setMap(chara.getPosX() - 1,chara.getPosY()    ,MapData.TYPE_NONE);
                item(MapData.TYPE_KEY,0);
            }
            if (mapData.getMap(chara.getPosX() - 1,chara.getPosY()    ) == MapData.TYPE_DOOR ){
                if(haveKey != 0){
                    SW = 1;
                    initializeTime();
                }
            }
        }else if(chara.getCharaDir() == 2){
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() - 1) == MapData.TYPE_WALL ){
                if(havePik > 0){
                    if(chara.getPosY() -1 != 0){
                        mapData.setMap(chara.getPosX()    ,chara.getPosY() - 1,MapData.TYPE_NONE);
                        item(MapData.TYPE_PIK,1);
                    }
                }
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() - 1) == MapData.TYPE_WATCH){
                mapData.setMap(chara.getPosX()    ,chara.getPosY() - 1,MapData.TYPE_NONE);
                item(MapData.TYPE_WATCH,0);
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() - 1) == MapData.TYPE_PIK  ){
                mapData.setMap(chara.getPosX()    ,chara.getPosY() - 1,MapData.TYPE_NONE);
                item(MapData.TYPE_PIK,0);
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() - 1) == MapData.TYPE_KEY  ){
                mapData.setMap(chara.getPosX()    ,chara.getPosY() - 1,MapData.TYPE_NONE);
                item(MapData.TYPE_KEY,0);
            }
            if (mapData.getMap(chara.getPosX()    ,chara.getPosY() - 1) == MapData.TYPE_DOOR ){
                if(haveKey != 0){
                    SW = 1;
                    initializeTime();
                }
            }
        }else if(chara.getCharaDir() == 3 && chara.getPosX() != 19){
            if (mapData.getMap(chara.getPosX() + 1,chara.getPosY()    ) == MapData.TYPE_WALL ){
                if(havePik > 0){
                    mapData.setMap(chara.getPosX() + 1,chara.getPosY()    ,MapData.TYPE_NONE);
                    item(MapData.TYPE_PIK,1);
                }
            }
            if (mapData.getMap(chara.getPosX() + 1,chara.getPosY()    ) == MapData.TYPE_WATCH){
                mapData.setMap(chara.getPosX() + 1,chara.getPosY()    ,MapData.TYPE_NONE);
                item(MapData.TYPE_WATCH,0);
            }
            if (mapData.getMap(chara.getPosX() + 1,chara.getPosY()    ) == MapData.TYPE_PIK  ){
                mapData.setMap(chara.getPosX() + 1,chara.getPosY()    ,MapData.TYPE_NONE);
                item(MapData.TYPE_PIK,0);
            }
            if (mapData.getMap(chara.getPosX() + 1,chara.getPosY()    ) == MapData.TYPE_KEY  ){
                mapData.setMap(chara.getPosX() + 1,chara.getPosY()    ,MapData.TYPE_NONE);
                item(MapData.TYPE_KEY,0);
            }
            if (mapData.getMap(chara.getPosX() + 1,chara.getPosY()    ) == MapData.TYPE_DOOR ){
                if(haveKey != 0){
                    SW = 1;
                    initializeTime();
                }
            }
        }
        update3D();
    }

    void update3D(){
        MoveChara c = this.chara;
        int direct = this.direction % 4;
        int[] checkMapAround = new int[11];

        if(direct == 0){ //down
            checkMapAround[0]  = mapData.getMap(c.getPosX()+1, c.getPosY()  );
            checkMapAround[1]  = mapData.getMap(c.getPosX()-1, c.getPosY()  );
            checkMapAround[2]  = mapData.getMap(c.getPosX()+1, c.getPosY()+1);
            checkMapAround[3]  = mapData.getMap(c.getPosX()  , c.getPosY()+1);
            checkMapAround[4]  = mapData.getMap(c.getPosX()-1, c.getPosY()+1);
            checkMapAround[5]  = mapData.getMap(c.getPosX()+1, c.getPosY()+2);
            checkMapAround[6]  = mapData.getMap(c.getPosX()  , c.getPosY()+2);
            checkMapAround[7]  = mapData.getMap(c.getPosX()-1, c.getPosY()+2);
            checkMapAround[8]  = mapData.getMap(c.getPosX()+1, c.getPosY()+3);
            checkMapAround[9]  = mapData.getMap(c.getPosX()  , c.getPosY()+3);
            checkMapAround[10] = mapData.getMap(c.getPosX()-1, c.getPosY()+3);
        }else if(direct == 1){ //left                                      
            checkMapAround[0]  = mapData.getMap(c.getPosX()  , c.getPosY()+1);
            checkMapAround[1]  = mapData.getMap(c.getPosX()  , c.getPosY()-1);
            checkMapAround[2]  = mapData.getMap(c.getPosX()-1, c.getPosY()+1);
            checkMapAround[3]  = mapData.getMap(c.getPosX()-1, c.getPosY()  );
            checkMapAround[4]  = mapData.getMap(c.getPosX()-1, c.getPosY()-1);
            checkMapAround[5]  = mapData.getMap(c.getPosX()-2, c.getPosY()+1);
            checkMapAround[6]  = mapData.getMap(c.getPosX()-2, c.getPosY()  );
            checkMapAround[7]  = mapData.getMap(c.getPosX()-2, c.getPosY()-1);
            checkMapAround[8]  = mapData.getMap(c.getPosX()-3, c.getPosY()+1);
            checkMapAround[9]  = mapData.getMap(c.getPosX()-3, c.getPosY()  );
            checkMapAround[10] = mapData.getMap(c.getPosX()-3, c.getPosY()-1);
        }else if(direct == 2){ //up                                    
            checkMapAround[0]  = mapData.getMap(c.getPosX()-1, c.getPosY()  );
            checkMapAround[1]  = mapData.getMap(c.getPosX()+1, c.getPosY()  );
            checkMapAround[2]  = mapData.getMap(c.getPosX()-1, c.getPosY()-1);
            checkMapAround[3]  = mapData.getMap(c.getPosX()  , c.getPosY()-1);
            checkMapAround[4]  = mapData.getMap(c.getPosX()+1, c.getPosY()-1);
            checkMapAround[5]  = mapData.getMap(c.getPosX()-1, c.getPosY()-2);
            checkMapAround[6]  = mapData.getMap(c.getPosX()  , c.getPosY()-2);
            checkMapAround[7]  = mapData.getMap(c.getPosX()+1, c.getPosY()-2);
            checkMapAround[8]  = mapData.getMap(c.getPosX()-1, c.getPosY()-3);
            checkMapAround[9]  = mapData.getMap(c.getPosX()  , c.getPosY()-3);
            checkMapAround[10] = mapData.getMap(c.getPosX()+1, c.getPosY()-3);
        }else if(direct == 3){ //rright                                        
            checkMapAround[0]  = mapData.getMap(c.getPosX()  , c.getPosY()-1);
            checkMapAround[1]  = mapData.getMap(c.getPosX()  , c.getPosY()+1);
            checkMapAround[2]  = mapData.getMap(c.getPosX()+1, c.getPosY()-1);
            checkMapAround[3]  = mapData.getMap(c.getPosX()+1, c.getPosY()  );
            checkMapAround[4]  = mapData.getMap(c.getPosX()+1, c.getPosY()+1);
            checkMapAround[5]  = mapData.getMap(c.getPosX()+2, c.getPosY()-1);
            checkMapAround[6]  = mapData.getMap(c.getPosX()+2, c.getPosY()  );
            checkMapAround[7]  = mapData.getMap(c.getPosX()+2, c.getPosY()+1);
            checkMapAround[8]  = mapData.getMap(c.getPosX()+3, c.getPosY()-1);
            checkMapAround[9]  = mapData.getMap(c.getPosX()+3, c.getPosY()  );
            checkMapAround[10] = mapData.getMap(c.getPosX()+3, c.getPosY()+1);
        }
        m0l.setVisible(checkMapAround[0]  == 1 || checkMapAround[0]  == 5);
        m0r.setVisible(checkMapAround[1]  == 1 || checkMapAround[1]  == 5);
        m1l.setVisible(checkMapAround[2]  == 1 || checkMapAround[2]  == 5);
        m1m.setVisible(checkMapAround[3]  == 1 || checkMapAround[3]  == 5);
        m1r.setVisible(checkMapAround[4]  == 1 || checkMapAround[4]  == 5);
        m2l.setVisible(checkMapAround[5]  == 1 || checkMapAround[5]  == 5);
        m2m.setVisible(checkMapAround[6]  == 1 || checkMapAround[6]  == 5);
        m2r.setVisible(checkMapAround[7]  == 1 || checkMapAround[7]  == 5);
        m3l.setVisible(checkMapAround[8]  == 1 || checkMapAround[8]  == 5);
        m3m.setVisible(checkMapAround[9]  == 1 || checkMapAround[9]  == 5);
        m3r.setVisible(checkMapAround[10] == 1 || checkMapAround[10] == 5);

        watch.setVisible(checkMapAround[3] == 2);
        pik  .setVisible(checkMapAround[3] == 3);
        key  .setVisible(checkMapAround[3] == 4);

        door .setVisible(checkMapAround[3]  == 5);
        door2.setVisible(checkMapAround[6]  == 5);
        door3.setVisible(checkMapAround[9]  == 5);

        solve.setVisible(SW == 1);

        pikCount.getChildren().clear();
        wallCount.getChildren().clear();

        Text count1 = new Text(String.valueOf(havePik));
        count1.setFont( Font.font(130) );
        pikCount.getChildren().add(count1);

        Text count2 = new Text(String.valueOf(haveWall));
        count2.setFont( Font.font(130) );
        wallCount.getChildren().add(count2);
    }

    public void downButtonAction(){
        if(chara.getCharaDir() == 0){
            chara.move(0, -1);
        }else if(chara.getCharaDir() == 1){
            chara.move(1 , 0);
        }else if(chara.getCharaDir() == 2){
            chara.move(0 , 1);
        }else if(chara.getCharaDir() == 3){
            chara.move(-1 , 0);
        }
        update3D();
        mapPrint(chara, mapData);
    }
    public void downButtonAction(ActionEvent event) {
        downButtonAction();
    }

    public void rightButtonAction(){
        direction++;
        direction %= 4;
        direction += 4;
        chara.setCharaDir(direction % 4);
        update3D();
        mapPrint(chara, mapData);
    }
    public void rightButtonAction(ActionEvent event) {
        rightButtonAction();
    }

    public void leftButtonAction(){
        direction--;
        direction %= 4;
        direction += 4;
        chara.setCharaDir(direction % 4);
        update3D();
        mapPrint(chara, mapData);
    }
    public void leftButtonAction(ActionEvent event) {
        leftButtonAction();
    }

    public void upButtonAction(){
        if(chara.getCharaDir() == 0){
            chara.move(0, 1);
        }else if(chara.getCharaDir() == 1){
            chara.move(-1 , 0);
        }else if(chara.getCharaDir() == 2){
            chara.move(0 , -1);
        }else if(chara.getCharaDir() == 3){
            chara.move(1 , 0);
        }
        update3D();
        mapPrint(chara, mapData);
    }
    public void upButtonAction(ActionEvent event) {
        upButtonAction();
    }
}