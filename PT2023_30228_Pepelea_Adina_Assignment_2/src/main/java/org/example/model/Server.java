package org.example.model;

import org.example.model.Task;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable{
    private BlockingQueue<Task> tasks;
    private AtomicInteger waitingPeriod;
    public Server(){
        this.tasks=new LinkedBlockingQueue<Task>();
        this.waitingPeriod=new AtomicInteger();
    }
    public void addTask(Task newTask) throws InterruptedException {
        try{
            tasks.put(newTask);
        }catch(InterruptedException ex){
            throw new InterruptedException();
        }
        waitingPeriod.incrementAndGet();
    }

    public void run(){
        while(true){
            try {
                Task newTask= tasks.peek();
                if(newTask!=null){
                    Thread.sleep(1000*newTask.getServiceTime());
                    tasks.remove(newTask);
                    waitingPeriod.decrementAndGet();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public BlockingQueue<Task> getTasks()
    {
        return tasks;
    }
}
