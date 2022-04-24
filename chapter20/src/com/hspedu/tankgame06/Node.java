package com.hspedu.tankgame06;

public class Node {//一个Node对象表示一个敌人的信息
    private int x;
    private int y;
    private int director;

    public Node(int x, int y, int director) {
        this.x = x;
        this.y = y;
        this.director = director;
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

    public int getDirector() {
        return director;
    }

    public void setDirector(int director) {
        this.director = director;
    }
}
