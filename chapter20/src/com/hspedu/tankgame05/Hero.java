package com.hspedu.tankgame05;

import java.util.Vector;

public class Hero extends Tank {
    Boolean isLive = true;
    //定义一个Shot对象
    Shot shot = null;
    Vector<Shot> HeroShots = new Vector<>();//发射多颗子弹
    public Hero(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemyTank() {
        //最多发射5颗子弹
        if (HeroShots.size() == 5){
            return;
        }
        switch (getDirect()) {//得到hero对象的方向
            case 0:
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2:
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        HeroShots.add(shot);
        //启动Shot线程
        new Thread(shot).start();
    }
}
