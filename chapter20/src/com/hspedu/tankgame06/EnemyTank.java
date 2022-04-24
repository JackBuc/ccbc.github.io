package com.hspedu.tankgame06;

import java.util.Vector;

public class EnemyTank extends Tank implements Runnable{//敌人的坦克
    Vector<Shot> shots = new Vector<>();
    //增加成员，EnemyTank可以得到敌人坦克的Vector
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    //这里提供一个方法，可以将MyPanel的成员Vector<EnemyTank> enemyTanks = new Vector<>();
    //设置到EnemyTank的成员enemyTanks
    public void setEnemyTanks(Vector<EnemyTank> enemyTanks){
        this.enemyTanks = enemyTanks;
    }
    //编写方法，判断当前的这个敌人坦克，是否和enemyTanks中的其他坦克发生重叠或碰撞
    public boolean isTouchEnemyTank(){
        //判断当前坦克的方向,
        switch (this.getDirect()){
            case 0://上
            case 2://下
                //让当前坦克和其他的所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this){
                        //根据敌人坦克方向讨论
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            //上下方向
                            if(this.getX()>=enemyTank.getX()-40
                                    &&this.getX()<= enemyTank.getX()+40
                                    &&this.getY()>= enemyTank.getY()-60
                                    &&this.getY()<= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //右左方向
                            if (this.getX()>= enemyTank.getX()-40
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()>= enemyTank.getY()-60
                                    &&this.getY()<= enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
            case 3://左
                //让当前坦克和其他的所有坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    if (enemyTank != this){
                        //根据敌人坦克方向讨论
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2){
                            //上下方向
                            if(this.getX()>=enemyTank.getX()-60
                                    &&this.getX()<= enemyTank.getX()+40
                                    &&this.getY()>= enemyTank.getY()-40
                                    &&this.getY()<= enemyTank.getY()+60){
                                return true;
                            }
                        }
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3){
                            //右左方向
                            if (this.getX()>= enemyTank.getX()-60
                                    &&this.getX()<=enemyTank.getX()+60
                                    &&this.getY()>= enemyTank.getY()-40
                                    &&this.getY()<= enemyTank.getY()+40){
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }
    @Override
    public void run() {
        while(true){
            //判断子弹消亡后创建子弹前提是坦克还在
            if (isLive && shots.size() < 1){

                Shot s = null;
                //判断坦克方向对应子弹
                switch (getDirect()) {//得到hero对象的方向
                    case 0:
                        s = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(s);
                new Thread(s).start();
            }

            //根据坦克方向继续运动
            switch (getDirect()){
                case 0://上
                    if (getY() > 0 && !isTouchEnemyTank()) {
                        for (int i = 0; i < 15; i++) {
                            moveUp();
                        } //休眠500ms
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 1://右
                    if (getX() < 1000 - 60&& !isTouchEnemyTank()) {
                        for (int i = 0; i < 30; i++) {
                            moveRight();
                        } //休眠500ms
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 2://下
                    if (getY() < 750 - 60&& !isTouchEnemyTank()) {
                        for (int i = 0; i < 15; i++) {
                            moveDown();
                        } //休眠500ms
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 3://左
                    if (getX() > 0&& !isTouchEnemyTank()) {
                        for (int i = 0; i < 15; i++) {
                            moveLeft();
                        } //休眠500ms
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            //得到随机的一个方向0-3
            setDirect((int)(Math.random()*4));
            if (!isLive){
                break;//退出线程
            }
        }
    }
}
