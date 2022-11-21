package com.nagarro.dataenterpriseplatform.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.RequestBody;
import com.amazonaws.services.stepfunctions.AWSStepFunctions;
import com.amazonaws.services.stepfunctions.model.StartExecutionRequest;
// import com.amazonaws.services.stepfunctions.model.StartExecutionResult;
import com.nagarro.dataenterpriseplatform.main.entity.JobSchedule;

import net.minidev.json.JSONObject;

@Service
public class JobScheduling implements Runnable {
    @Value("${profile_env}")
    private String profile_env;

    @Value("${aws.bucketName}")
    private String awsBucketName;

    @Autowired
    private AWSStepFunctions sfnClient;
    
    private JobSchedule taskDefinition;

    @Override
    public void run() {
        
        System.out.println("Coming here in Scheduled Task");
        String stepFunctionARN = taskDefinition.getSteparn();

        JSONObject input = new JSONObject();
        input.put("rule_engine", taskDefinition.getRule_engine());
        input.put("batch_name", taskDefinition.getBatch_name());
        input.put("client_id", taskDefinition.getClient_id());
        input.put("batch_id", taskDefinition.getBatch_id());
        input.put("execution_id", taskDefinition.getExecution_id());
        input.put("client_name", taskDefinition.getClient_name());
        input.put("table_name", taskDefinition.getTable_name());
        input.put("profile_env_bucket", taskDefinition.getProfile_env_bucket());
        input.put("profile_env", taskDefinition.getProfile_env());
        

        
        System.out.println(stepFunctionARN);
        StartExecutionRequest executionRequest = new StartExecutionRequest()
                .withStateMachineArn(stepFunctionARN)
                .withInput(input.toString());

        sfnClient.startExecution(executionRequest);
    }

    public JobSchedule getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(JobSchedule taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
    
}



