package com.hspedu.tankgame06;

public class Shot implements Runnable {
    private int x;
    private int y;//子弹坐标
    int direct = 0;//子弹方向
    int speed = 2;//子弹速度
    boolean isLive = true;//子弹是否存活

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);//休眠，看到子弹的轨迹
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch (direct) {//子弹方向
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }
            System.out.println("子弹坐标x="+ x + "y=" + y);
            //子弹出界会销毁
            //子弹碰到敌人坦克销毁
            if (!(x >= 0&& x <= 1000 && y>=0&&y<=750 && isLive )){
                isLive = false;
                System.out.println("子弹线程退出");
                break;
            }
        }
    }
}
