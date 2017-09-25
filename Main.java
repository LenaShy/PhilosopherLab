package com.company;

public class Main {

    public static void main(String[] args) {
        Philosopher.initFork(10);
        Philosopher.caseVar = 4;
        for(int i = 1; i<10; i++){
            Philosopher philosopher = new Philosopher(i);
            Thread t = new Thread(philosopher);
            t.start();
        }
    }
}
