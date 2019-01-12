import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData {

    //０：道　１：壁　２：時計　３：ピッケル　４：カギ　５：ドア
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_WATCH  = 2;
    public static final int TYPE_PIK    = 3;
    public static final int TYPE_KEY    = 4;
    public static final int TYPE_DOOR   = 5;

    private static final String mapImageFiles[] = {
        "png/SPACE.png",
        "png/WALL.png",
        "png/SPACE.png", //"png/mapwatch.png"
        "png/SPACE.png", //"png/mappik.png"
        "png/mapkey.png",
        "png/mapdoor.png"
    };

    //mapImageFilesに入っている画像の相対パスから画像化したものを代入
    private Image[] mapImages;
    //mapsに入っているマップの０か１から画像化し，迷路を作成したもの
    private ImageView[][] mapImageViews;
    //width * height の二次配列に０か１を代入
    private int[][] maps;
    private int width;
    private int height;

    //MapData コンストラクタ
    MapData(int x, int y){
        mapImages     = new Image[mapImageFiles.length];
        mapImageViews = new ImageView[y][x];
        for (int i=0; i<mapImageFiles.length; i++) {
            mapImages[i] = new Image(mapImageFiles[i]);
        }

        width  = x;
        height = y;
        maps = new int[y][x];

        fillMap(MapData.TYPE_WALL);
        digMap(19, 13);  //ゴールから掘り始めることで，ゴールを袋小路にする．
        setMap(19,13,5);
        setImageViews();
    }

    //ゲッター
    public int getHeight(){ return height; }
    public int getWidth(){ return width; }
    public ImageView getImageView(int x, int y) { return mapImageViews[y][x]; }
    public int getMap(int x, int y) {
        if (x < 0 || width <= x || y < 0 || height <= y) {
            return -1;
        }
        return maps[y][x];
    }
    
    //セッター
    //setMap : 一箇所のタイプを変更する時に使う(外周の壁を除く)
    public void setMap(int x, int y, int type){
        if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
            return;
        }
        maps[y][x] = type;
        if(mapImageViews[y][x] != null) mapImageViews[y][x].setImage(mapImages[type]);
    }
    //setImageViews : mapsから各点のタイプを画像化し，代入する
    public void setImageViews() {
        for (int y=0; y<height; y++) {
            for (int x=0; x<width; x++) {
                mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
            }
        }
    }
    //fillMap : 引数で指定したタイプでマップを埋め尽くす
    public void fillMap(int type){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                maps[y][x] = type;
            }
        }
    }


    //穴掘り法による迷路作成のアルゴリズム
    public void digMap(int x, int y){
        setMap(x, y, MapData.TYPE_NONE);
        int[][] dl = {{0,1},
                      {0,-1},
                      {-1,0},
                      {1,0}};
        int[] tmp;

        //dlの配列をランダムで入れ替えた
        for (int i=0; i<dl.length; i++) {
            int r = (int)(Math.random()*dl.length);
            tmp = dl[i];
            dl[i] = dl[r];
            dl[r] = tmp;
        }

        //再帰法を用いて４方向ランダム２マス先が壁が見て，壁なら道を開けていく
        for (int i=0; i<dl.length; i++){
            int dx = dl[i][0];
            int dy = dl[i][1];
            if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
                setMap(x+dx, y+dy, MapData.TYPE_NONE);
                digMap(x+dx*2, y+dy*2);

            }
        }
    }

    public void printMap(){
        for (int y=0; y<height; y++){
            for (int x=0; x<width; x++){
                if (getMap(x,y) == MapData.TYPE_WALL){
                    System.out.print("++");
                }else{
                    System.out.print("  ");
                }
            }
            System.out.print("\n");
        }
    }
}
