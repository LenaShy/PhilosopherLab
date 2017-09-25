package com.company;

import java.util.ArrayList;

/**
 * Created by lena on 17.09.17.
 */
public class Philosopher implements Runnable {

    private Integer philosopherID;
    private boolean ownForkIsInHand;
    private boolean anotherForkIsInHand;

    private static ArrayList<Fork> forkArrayList;

    public static int caseVar;

    public static ArrayList<Fork> getForkList(){
        if(forkArrayList == null)
            forkArrayList = new ArrayList<Fork>();
        return forkArrayList;
    }

    public static void initFork(int n){
        for(int i=0; i < n; i++){
            getForkList().add(new Fork(i));
        }
    }

    public Philosopher(Integer id) {
        this.philosopherID = id;
        this.ownForkIsInHand = false;
        this.anotherForkIsInHand = false;
    }

    public void think(int time) {
        System.out.println("Philosopher " + philosopherID + " is thinking about " + time + " seconds");
        try {
            Thread.currentThread().sleep(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eat(int time) {
        System.out.println("Philosopher " + philosopherID + " trying to eat...");
        this.takeOwnFork();
        this.takeAnotherFork();
        if(this.ownForkIsInHand && this.anotherForkIsInHand){
            System.out.println("State: is eating about " + time + " seconds");
            try {
                Thread.currentThread().sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            putForks();
        }
        else System.out.println("State: needs more fork to eat");
    }
    public void takeOwnFork() {//берем свою вилку: ID вилки соответствует ID философа
        if(getForkList().get(philosopherID).isOnTable){//проверяем можно ее ли вообще взять
            getForkList().get(philosopherID).isOnTable = false;//помечаем вилку как занятую
            this.ownForkIsInHand = true;//говорим что вилка философа в его руке
            System.out.println("Philosopher " + philosopherID +" takes own fork");
        }
        else {
            System.out.println("Philosopher " + philosopherID +" can't take own fork. Waiting until it free.");
        }
    }

    public void takeAnotherFork() {//берем не свою вилку: так как философы сисдят по кругу, а реализуется это списком, то у нас может быть два случая

        if (philosopherID == 0) {
            if(getForkList().get(getForkList().size() - 1).isOnTable) {
                getForkList().get(getForkList().size() - 1).isOnTable = false;//помечаем вилку как занятую
                this.anotherForkIsInHand = true;
                System.out.println("Philosopher " + philosopherID +" takes another's fork");
            }
            else System.out.println("Philosopher " + philosopherID +" can't take another's fork. Waiting until it free.");
        }
        else {
            if(getForkList().get(philosopherID - 1).isOnTable){
                getForkList().get(philosopherID - 1).isOnTable = false;
                this.anotherForkIsInHand = true;
                System.out.println("Philosopher " + philosopherID +" takes another's fork");
            }
            else System.out.println("Philosopher " + philosopherID +" can't take another's fork. Waiting until it free.");
        }
    }

    public void putForks() {
        this.ownForkIsInHand = false;
        this.anotherForkIsInHand = false;
        getForkList().get(philosopherID).isOnTable = true;
        if(philosopherID == 0) getForkList().get(getForkList().size() - 1).isOnTable = true;
        else getForkList().get(philosopherID - 1).isOnTable = true;
        System.out.println("Philosopher " + philosopherID + " put forks");
    }

    @Override
    public void run() {
       synchronized (getForkList()){
        switch (caseVar){
            case 1:
                think(0);
                eat(0);
                break;
            case 2:
                think(100);
                eat(0);
                break;
            case 3:
                think(0);
                eat(100);
                break;
            case 4:
                think(100);
                eat(100);
                break;

        }
        System.out.print("\n");
       }
    }
}
