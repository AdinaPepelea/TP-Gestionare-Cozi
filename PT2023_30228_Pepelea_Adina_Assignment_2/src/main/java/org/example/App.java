package org.example;

import org.example.businessLogic.SimulationManager;

public class App
{
    public static void main( String[] args ) {
        SimulationManager gen=new SimulationManager(60, 4, 2, 30, 2, 2, 4);
        Thread t=new Thread(gen);
        t.start();
    }
}
