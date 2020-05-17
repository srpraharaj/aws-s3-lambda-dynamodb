package com.ranjan.aws.model;

import java.io.Serializable;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Employee")
public class Employee implements Serializable{

	private String empid;
	private String firstName;
	private String lastName;
}
