package com.nishanth.schedule;

import java.util.Date;

public class ScheduledTask implements Runnable{     
 
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" Start. Time = "+new Date());
        System.out.println(Thread.currentThread().getName()+" End. Time = "+new Date());
    }
 
}