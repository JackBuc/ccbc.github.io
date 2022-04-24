package com.hspedu.tankgame06;

import java.io.*;
import java.util.Vector;

public class Recorder {//记录相关的信息和文件进行交互
    private static int allEnemyTankNum = 0;//记录击毁坦克数量
    private static String heroIsLive = "";//记录自己的坦克是否被击毁
    private static FileWriter fw = null;//定义Io对象
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\myRecord.txt";

    private static Vector<EnemyTank> enemyTanks= null;

    private static Hero hero = null;

    //定义一个Node的Vector，用于保存敌人的信息
    private static Vector<Node> nodes = new Vector<>();
    //方法来获得heroIsLive
    public static String getHeroIsLive() {
        return heroIsLive;
    }
    //判断记录文件是否存在,返回记录文件的路径
    public static String getRecordFile() {
        return recordFile;
    }

    //增加一个方法，用于读取recordFile，恢复相关的信息
    public static Vector<Node> getNodesAndEnemyTankRec(){
        try {
            br = new BufferedReader(new FileReader("src\\myRecord.txt"));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            heroIsLive = br.readLine();
            //循环读取生成nodes集合
            String line = "";
            while ((line = br.readLine()) != null){
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),Integer.parseInt(xyd[1]),Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }
    public static void setHeroTank(Hero hero){
        Recorder.hero = hero;
    }


    //增加一个方向记录坦克坐标和方向
    //增加一个方法，当游戏退出时，将allEnemyTankNum保存到recordFile
    public static void keepRecord(){
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            if (hero.isLive){
                bw.write("isLive\r\n");
            }else{
                bw.write("dead\r\n");
            }
            //便利敌人坦克的Vector,遍历
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive){
                    //保存信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record+"\r\n");
                }
            }
            //记录自己坦克的位置，方向
            if (hero.isLive){
                //保存信息
                String record = hero.getX() + " " + hero.getY() + " " + hero.getDirect();
                bw.write(record+"\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }
    //当击毁一个敌方坦克，就自加
    public static void addAllEnemyTankNum(){
        allEnemyTankNum++;
    }
}
