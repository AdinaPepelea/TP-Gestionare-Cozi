package org.example.businessLogic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    public void addTask(List<Server> servers, Task t) throws InterruptedException {
        int min=3600, poz=0;
        for(int i=0;i<servers.size();i++){
            int value=servers.get(i).getWaitingPeriod().get();
            if(value<min){
                poz=i;
                min=value;
            }
        }
        servers.get(poz).addTask(t);
    }
}
