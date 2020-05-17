package com.ranjan.aws.manager;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.ranjan.aws.util.Constants;

public class DynamoDBManager {
	
	private static volatile DynamoDBManager instance;

    private DynamoDBMapper mapper;
    private DynamoDB dynamoDb;
    
    private DynamoDBManager() {

        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().withRegion(Constants.clientRegion).build();
        mapper = new DynamoDBMapper(client);
        dynamoDb = new DynamoDB(client);
    }
    
    public static DynamoDBManager instance() {

        if (instance == null) {
            synchronized(DynamoDBManager.class) {
                if (instance == null)
                    instance = new DynamoDBManager();
            }
        }

        return instance;
    }

    public static DynamoDBMapper getMapper() {

        DynamoDBManager manager = instance();
        return manager.mapper;
    }
    

    public static DynamoDB getClient() {
        DynamoDBManager manager = instance();
        return manager.dynamoDb;
    }

}
