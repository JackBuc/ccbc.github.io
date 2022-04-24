package com.hspedu.tankgame05;

public class Bomb {//炸弹
    int x, y;//坐标
    int life = 9;//炸弹生命周期
    boolean isLive = true;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //减少生命值
    public void lifeDown(){
        if (life > 0){
            life--;
        }else{
            isLive = false;
        }
    }
}
