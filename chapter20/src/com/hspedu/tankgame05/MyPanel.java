package com.hspedu.tankgame05;

import sun.net.www.content.text.Generic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class MyPanel extends JPanel implements KeyListener, Runnable {
        int x = 500;
        int y = 500;
        Hero hero = null;//自己的坦克
        //定义敌人的Vector中，因为敌人坦克不止一个还要保证多线程安全
        Vector<EnemyTank> enemyTanks = new Vector<>();

        //定义一个Vector用于存放炸弹
        Vector<Bomb> bombs = new Vector<>();//炸弹本质是图片

        //定义Node对象的Vector用于恢复敌人坦克
        Vector<Node> nodes = new Vector<>();
        Image image1 = null;
        Image image2 = null;
        Image image3 = null;
        int enemyTankSize = 3;
        public MyPanel(String key){
            switch (key){
                case "0":
                    Recorder.setEnemyTanks(enemyTanks);
                    hero = new Hero(x,y);
                    Recorder.setHeroTank(hero);
                    hero.setSpeed(2);//设计速度
                    //初始化敌人的坦克
                    for (int i = 0;i < enemyTankSize;i++){
                        EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                        //将enemyTanks设置给enemyTank
                        enemyTank.setEnemyTanks(enemyTanks);
                        enemyTank.setDirect(2);

                        enemyTank.setSpeed(1);

                        //启动enemyTank线程，让坦克动起来
                        new Thread(enemyTank).start();

                        //给enemyTank加入一颗子弹
                        Shot shot = new Shot(enemyTank.getX() +20,enemyTank.getY()+60,enemyTank.getDirect());
                        shot.setSpeed(2);
                        enemyTank.shots.add(shot);
                        //启动shot
                        new Thread(shot).start();
                        enemyTanks.add(enemyTank);
                    }

                    //初始化图片
                    image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
                    image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
                    image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
                    break;
                case "1":
                    nodes = Recorder.getNodesAndEnemyTankRec();
                    Recorder.setEnemyTanks(enemyTanks);
                    if (Recorder.getHeroIsLive().equals("isLive")) {
                        Node endNode = nodes.get(nodes.size() - 1);
                        hero = new Hero(endNode.getX(), endNode.getY());
                        Recorder.setHeroTank(hero);
                        hero.setDirect(endNode.getDirector());
                        hero.setSpeed(2);//设计速度
                    }else{
                        hero = new Hero(x,y);
                        Recorder.setHeroTank(hero);
                        hero.setSpeed(2);//设计速度
                    }
                    //初始化敌人的坦克
                    if (Recorder.getHeroIsLive().equals("isLive")){
                        enemyTankSize = nodes.size() - 1;
                    }else {
                        enemyTankSize = nodes.size();
                    }
                    for (int i = 0;i < enemyTankSize;i++){
                        Node node = nodes.get(i);
                        EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                        //将enemyTanks设置给enemyTank
                        enemyTank.setEnemyTanks(enemyTanks);
                        enemyTank.setDirect(node.getDirector());

                        enemyTank.setSpeed(1);

                        //启动enemyTank线程，让坦克动起来
                        new Thread(enemyTank).start();

                        //给enemyTank加入一颗子弹
                        Shot shot = new Shot(enemyTank.getX() +20,enemyTank.getY()+60,enemyTank.getDirect());
                        shot.setSpeed(2);
                        enemyTank.shots.add(shot);
                        //启动shot
                        new Thread(shot).start();
                        enemyTanks.add(enemyTank);
                    }

                    //初始化图片
                    image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
                    image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
                    image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
                    break;
                default:
                    System.out.println("你的输入有误");
            }

        }
        //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g){
            //画出玩家总成绩
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累积击毁的坦克",1020,30);
        drawTank(1020,60,g,0,0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "",1080,100);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0,0,1000,750);//填充矩形默认黑色

        showInfo(g);

        //画出自己的坦克
        if (hero != null &&  hero.isLive) {
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }
        //        drawTank(hero.getX() + 60,hero.getY(),g,0,1);
        if (hero.HeroShots.size() > 0 && hero.HeroShots != null) {//画出hero子弹
            for (int i = 0; i < hero.HeroShots.size(); i++) {


                System.out.println("子弹被绘制···");
                Shot shot = hero.HeroShots.get(i);
                if (shot.isLive){
                    g.draw3DRect(shot.getX(),shot.getY(),1,1,false);
                }else {
                    hero.HeroShots.remove(shot);
                }

//            g.fill3DRect(hero.shot.getX(),hero.shot.getY(),1,1,false);
//                g.draw3DRect(hero.shot.getX(), hero.shot.getY(), 1, 1, false);
            }
            }

        //如果有bombs集合有对象就画出
        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据bomb对象的life值去画出相应的图片
            if (bomb.life > 6){
                g.drawImage(image1,bomb.x,bomb.y,60,60,this);
            }else if (bomb.life > 3){
                g.drawImage(image2,bomb.x,bomb.y,60,60,this);
            }else{
                g.drawImage(image3,bomb.x,bomb.y,60,60,this);
            }
            //让炸弹生命值减少
            bomb.lifeDown();
            if (bomb.life == 0){
                bombs.remove(bomb);
            }
        }

        //画出敌人的坦克
        for (int i = 0;i < enemyTanks.size();i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            if (enemyTank.isLive) {
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(j);
                    //绘制
                    if (shot.isLive) {
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    } else {
                        //移除子弹
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }


    }
    public void drawTank(int x,int y,Graphics g,int direct,int type){
            switch (type){
                case 0:
                    g.setColor(Color.cyan);
                    break;
                case 1:
                    g.setColor(Color.yellow);
                    break;
            }
            //根据坦克方向来绘制坦克
            switch (direct){
                case 0://方向向上
                    g.fill3DRect(x,y,10,60,false);
                    g.fill3DRect(x + 30,y,10,60,false);
                    g.fill3DRect(x + 10,y + 10,20,40,false);
                    g.drawOval(x + 10,y + 20,20,20);
                    g.drawLine(x + 20, y + 30,x + 20,y);
                    break;
                case 1://向右
                    g.fill3DRect(x,y,60,10,false);
                    g.fill3DRect(x,y + 30,60,10,false);
                    g.fill3DRect(x + 10,y + 10,40,20,false);
                    g.drawOval(x + 20,y + 10,20,20);
                    g.drawLine(x + 30, y + 20,x + 60,y + 20);
                    break;
                case 2://向下
                    g.fill3DRect(x,y,10,60,false);
                    g.fill3DRect(x + 30,y,10,60,false);
                    g.fill3DRect(x + 10,y + 10,20,40,false);
                    g.drawOval(x + 10,y + 20,20,20);
                    g.drawLine(x + 20, y + 30,x + 20,y + 60);
                    break;
                case 3://向左
                    g.fill3DRect(x,y,60,10,false);
                    g.fill3DRect(x,y + 30,60,10,false);
                    g.fill3DRect(x + 10,y + 10,40,20,false);
                    g.drawOval(x + 20,y + 10,20,20);
                    g.drawLine(x + 30, y + 20,x ,y + 20);
                    break;
            }
    }
    //编写方法，判断敌人坦克能否击中我的坦克
    public void hitHero(){
        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                switch (hero.getDirect()){
                    case 0:
                    case 2:
                        if (shot.getX() > hero.getX()&&shot.getX() < hero.getX()+40
                        &&shot.getY() > hero.getY()&&shot.getY() < hero.getY()+60){
                            shot.isLive = false;
                            hero.isLive = false;
                            enemyTank.shots.remove(shot);
                            Bomb bomb = new Bomb(hero.getX(), hero.getY());
                            bombs.add(bomb);
                            break;
                        }
                    case 1:
                    case 3:
                        if (shot.getX() > hero.getX()&&shot.getX() < hero.getX()+60
                                &&shot.getY() > hero.getY()&&shot.getY() < hero.getY()+40) {
                            shot.isLive = false;
                            hero.isLive = false;
                            enemyTank.shots.remove(shot);
                            Bomb bomb = new Bomb(hero.getX(), hero.getY());
                            bombs.add(bomb);
                            break;
                        }

                        }
            }
        }
    }


    //编写方法，判断我方坦克是否击中敌方坦克
    public void hitTank(Shot s,EnemyTank enemyTank){
        switch (enemyTank.getDirect()){
            case 0://上
                if (s.getX() > enemyTank.getX()&&s.getX()<enemyTank.getX()+40
                        &&s.getY()>enemyTank.getY()&&s.getY()< enemyTank.getY()+60){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //击中后创建一个Bomb对象加入到bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                    Recorder.addAllEnemyTankNum();
                }
                break;
            case 1://右
                if (s.getX() > enemyTank.getX()&&s.getX()<enemyTank.getX()+60
                        &&s.getY()>enemyTank.getY()&&s.getY()< enemyTank.getY()+40){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //击中后创建一个Bomb对象加入到bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                    Recorder.addAllEnemyTankNum();
                }
                break;
            case 2://坦克向下
                if (s.getX() > enemyTank.getX()&&s.getX()<enemyTank.getX()+40
                &&s.getY()>enemyTank.getY()&&s.getY()< enemyTank.getY()+60){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //击中后创建一个Bomb对象加入到bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                    Recorder.addAllEnemyTankNum();
                }
                break;
            case 3://左
                if (s.getX() > enemyTank.getX()&&s.getX()<enemyTank.getX()+60
                        &&s.getY()>enemyTank.getY()&&s.getY()< enemyTank.getY()+40){
                    s.isLive = false;
                    enemyTank.isLive = false;
                    //击中后创建一个Bomb对象加入到bombs
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                    enemyTanks.remove(enemyTank);
                    Recorder.addAllEnemyTankNum();
                }
                break;

        }
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println((char) e.getKeyChar());
        if (e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirect(2);//改变方向
//            hero.setY(++y);//改变坐标
            if (hero.getY() < 750 - 60) {//控制范围
                hero.moveDown();//在Tank类中集成，调用方法来实现坐标的改变
            }
        }else if (e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
//            hero.setY(--y);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
//            hero.setX(--x);
            }
        }else if (e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirect(1);
            if (hero.getX() < 1000-60)
            hero.moveRight();
//            hero.setX(++x);
        }

        //按键J发射子弹
        if (e.getKeyCode() == KeyEvent.VK_J){//只允许一颗子弹，(hero.shot == null || !hero.shot.isLive)
            System.out.println("用户按下了J，开始射击");
            hero.shotEnemyTank();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //判断是否击中敌人的坦克
            if (hero.HeroShots.size() > 0){
                for (int i = 0; i < hero.HeroShots.size(); i++) {
                    Shot shot = hero.HeroShots.get(i);
                    for (int j = 0; j < enemyTanks.size(); j++) {
                        EnemyTank enemyTank = enemyTanks.get(j);
                        hitTank(shot,enemyTank);
                    }
                }
            }
            //判断是否被敌方坦克击中
            if (hero.isLive) {
                hitHero();
            }
            this.repaint();
        }
    }
}
