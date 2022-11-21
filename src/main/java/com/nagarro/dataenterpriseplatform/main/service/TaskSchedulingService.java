package com.nagarro.dataenterpriseplatform.main.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Service
public class TaskSchedulingService {

    @Autowired
    private TaskScheduler taskScheduler;

    Map<String, ScheduledFuture<?>> jobsMap = new HashMap<>();

    public void scheduleTask(String stepFunctionARN,Runnable task,String cronJob){
        
        System.out.println("Scheduling step function with ARN:"+stepFunctionARN+" and cron expression"+cronJob);
        
        ScheduledFuture<?> scheduledTask = taskScheduler.schedule(task, new CronTrigger(cronJob, TimeZone.getTimeZone(TimeZone.getDefault().getID())));

        jobsMap.put(stepFunctionARN, scheduledTask);

    }

    // public void removeScheduledTask(String jobId) {
    //     ScheduledFuture<?> scheduledTask = jobsMap.get(jobId);
    //     if(scheduledTask != null) {
    //         scheduledTask.cancel(true);
    //         jobsMap.put(jobId, null);
    //     }
    // }
}
