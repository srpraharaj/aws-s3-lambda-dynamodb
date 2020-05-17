package com.ranjan.aws.handler;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Object;
import com.ranjan.aws.dynamodb.DynamoDBManager;
import com.ranjan.aws.s3.GetObject;
import com.ranjan.aws.util.Constants;

public class S3JSONToDynamoDBHandler implements RequestHandler<S3Event, String> {

	private Table table = DynamoDBManager.getClient().getTable(Constants.DYNAMODB_TABLE_NAME);

	@Override
	public String handleRequest(S3Event input, Context context) {

		try {
			LambdaLogger logger = context.getLogger();

			S3EventNotificationRecord record = input.getRecords().get(0);
			String bucketName = record.getS3().getBucket().getName();
			// String key = record.getS3().getObject().getKey();
			String key = record.getS3().getObject().getUrlDecodedKey();

			logger.log("--Uploaded file details -->" + " bucket: " + bucketName + " ,key: " + key);

			S3Object s3Object = GetObject.getS3Object(bucketName, key);
			InputStream inputStream = s3Object.getObjectContent();

			JSONObject jsonObject = (JSONObject) new JSONParser().parse(new InputStreamReader(inputStream, "UTF-8"));

			if (jsonObject != null) {

				Item item = new Item().withString("id", jsonObject.get("id").toString())
						.withString("empName", (String) jsonObject.get("empName").toString())
						.withString("age", (String) jsonObject.get("age").toString());

				table.putItem(item);

			}

			return key + " loaded to DynamoDB.";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
