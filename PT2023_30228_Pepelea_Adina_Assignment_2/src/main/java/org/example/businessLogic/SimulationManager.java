package org.example.businessLogic;

import org.example.businessLogic.Scheduler;
import org.example.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SimulationManager implements Runnable{
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int maxArrivalTime;
    public int minArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    private Scheduler scheduler;
    private List<Task> generatedTasks;
    private float averageWaitingTime;
    private int peekHour;
    private float averageServiceTime;

    public SimulationManager(int timeLimit, int maxProcessingTime, int minProcessingTime,
                             int maxArrivalTime, int minArrivalTime, int numberOfServers, int numberOfClients){
        this.timeLimit=timeLimit;
        this.maxProcessingTime=maxProcessingTime;
        this.minProcessingTime=minProcessingTime;
        this.maxArrivalTime=maxArrivalTime;
        this.minArrivalTime=minArrivalTime;
        this.numberOfClients=numberOfClients;
        this.numberOfServers=numberOfServers;
        this.peekHour=0;
        this.averageWaitingTime=0;
        this.averageServiceTime=0;
        this.scheduler=new Scheduler(numberOfServers, numberOfClients);
        this.generateNRandomTasks();
    }
    private void generateNRandomTasks(){
        Random r=new Random();
        this.generatedTasks = new ArrayList<Task>();
        for (int i = 0; i < numberOfClients; i++) {
            generatedTasks.add(new Task(r.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime, r.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime,i));
            averageWaitingTime += generatedTasks.get(i).getServiceTime();
        }
        Collections.sort(generatedTasks);
        averageServiceTime = averageWaitingTime / numberOfClients;
        averageWaitingTime = averageWaitingTime / (numberOfClients * numberOfServers);
    }
    public void run() {
            int currentTime = 0;
            int max1 = -3200;
            try {
                FileWriter myWriter = new FileWriter("project.txt");
                PrintWriter printWriter = new PrintWriter(myWriter);
                printWriter.print(generatedTasks.toString() + "\n");
                while (currentTime < timeLimit) {
                    for (int i = 0; i < generatedTasks.size(); i++) {
                        if (generatedTasks.get(i).getArrivalTime() == currentTime) {
                            scheduler.dispatchTask(generatedTasks.get(i));
                            generatedTasks.remove(i--);
                        }
                    }
                    printWriter.print("Timp simulare " + currentTime + "\n");
                    System.out.println(currentTime + "\n");
                    for (int i = 0; i < scheduler.getServers().size(); i++) {
                        String print = new String();
                        printWriter.print("Coada " + (i + 1) + ": ");
                        print = scheduler.getServers().get(i).getTasks().toString();
                        printWriter.print(print + "\n");
                    }
                    int sum = 0;
                    for (int i = 0; i < scheduler.getServers().size(); i++) {
                        sum += scheduler.getServers().get(i).getTasks().size();
                    }
                    if (sum > max1) {
                        max1 = sum;
                        peekHour = currentTime;
                    }
                    Thread.sleep(1000);
                    currentTime++;

                }
                printWriter.print("Peek hour is " + peekHour + ".\n");
                printWriter.print("Average waiting time is " + averageWaitingTime + ".\n");
                printWriter.print("Average service time is " + averageServiceTime + ".\n");
                printWriter.close();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }
}
