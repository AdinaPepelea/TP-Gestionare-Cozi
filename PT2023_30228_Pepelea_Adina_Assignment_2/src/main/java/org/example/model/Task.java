package org.example.model;

public class Task implements Comparable<Task>{
    private int arrivalTime;
    private int serviceTime;
    private int id;

    public Task(int arrivalTime, int serviceTime,int id) {
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.id = id++;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int compareTo(Task task){
        if(this.arrivalTime<=task.arrivalTime){
            return -1;
        }
        else if(this.arrivalTime>task.arrivalTime){
            return 1;
        }
        return 0;
    }
    @Override
    public String toString() {
        return "(" + id + "," + arrivalTime + "," + serviceTime + ')';
    }
}
