package com.nagarro.dataenterpriseplatform.main.AWS.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudwatchLogsConfiguration {

    @Value("${aws.region}")
    private String awsRegion;

    @Value("${aws.accessKey}")
    private String dynamodbAccessKey;

    @Value("${aws.secretKey}")
    private String dynamodbSecretKey;

    @Value("${aws.sessionToken}")
    private String dynamodbSessionToken;

    @Bean
    public AWSLogs cloudWatchLogconfig() {
        BasicSessionCredentials awsCreds = new BasicSessionCredentials(dynamodbAccessKey, dynamodbSecretKey, dynamodbSessionToken);
//        BasicAWSCredentials awsCreds = new BasicAWSCredentials(dynamodbAccessKey, dynamodbSecretKey);
        return AWSLogsClientBuilder
                .standard()
                .withRegion(awsRegion)
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
    }
}
