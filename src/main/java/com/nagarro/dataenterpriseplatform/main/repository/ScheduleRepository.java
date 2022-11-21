package com.nagarro.dataenterpriseplatform.main.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nagarro.dataenterpriseplatform.main.entity.JobSchedule;

@Repository
public class ScheduleRepository {
    
    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public void addSchedule(JobSchedule jobName) {
       
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":v1", new AttributeValue().withS(jobName.getStepname()));

        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("stepname = :v1 ")
                .withExpressionAttributeValues(eav);

        List<JobSchedule> all_jobs = dynamoDBMapper.parallelScan(JobSchedule.class, scanExpression, 3);

        if (all_jobs.size() > 0) {
            this.dynamoDBMapper.delete(all_jobs.get(0));
        }

        this.dynamoDBMapper.save(jobName);
    }

    public String listStepARN(String stepName){

        JobSchedule all_jobs = dynamoDBMapper.load(JobSchedule.class, stepName);
        
        if (all_jobs == null) {
            return null;
        }
        
        return all_jobs.getSteparn();

    }

    public List<JobSchedule> getAllClient() {
        return this.dynamoDBMapper.scan(JobSchedule.class, new DynamoDBScanExpression());
    }
    
}
