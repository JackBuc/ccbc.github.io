package com.hspedu.tankgame05;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class HspTankGame05 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        new HspTankGame05();
    }
    public HspTankGame05(){
        System.out.println("请选择重新开始输入0，还是继续上一局输入1");
        Scanner scanner = new Scanner(System.in);
        String key = scanner.next();
        while (true){
        if (key.equals("0") || key.equals("1") ){
            break;
        }else{
            System.out.println("请选择重新开始输入0，还是继续上一局输入1");
            key = scanner.next();
        }
        }
        mp = new MyPanel(key);
        //将冒泡放到Thread，并启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);
        this.setSize(1300,750);
        this.addKeyListener(mp);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        //在JFrame中增加相应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("监听到关闭窗口");
                Recorder.keepRecord();
                System.exit(0);//调System的一个方法，退出
            }
        });
    }
}
