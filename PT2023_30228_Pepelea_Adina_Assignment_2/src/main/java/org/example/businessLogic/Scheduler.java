package org.example.businessLogic;

import org.example.model.Server;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    private List<Server> servers;
    private int maxNoServers;
    private int maxTasksPerServer;
    private Strategy strategy;
    public Scheduler(int maxNoServers, int maxTasksPerServer){
        this.maxNoServers=maxNoServers;
        this.maxTasksPerServer=maxTasksPerServer;
        this.servers=new ArrayList<Server>();
        for(int i=0;i<maxNoServers;i++){
            Server server=new Server();
            servers.add(server);
            Thread thread=new Thread(server);
            thread.start();
        }
        strategy=new ConcreteStrategyTime();
    }

    public void dispatchTask(Task t) throws InterruptedException {
        strategy.addTask(servers, t);
    }
    public List<Server> getServers(){
        return servers;
    }
}
