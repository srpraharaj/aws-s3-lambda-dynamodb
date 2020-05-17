package com.ranjan.aws.handler;

import java.io.InputStream;
import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Object;
import com.ranjan.aws.manager.DynamoDBManager;
import com.ranjan.aws.s3.GetObject;
import com.ranjan.aws.util.Constants;
import com.ranjan.aws.util.ServiceUtil;

// Read CSV from S3 and upload to DynamoDB
public class S3CSVToDynamoDBHandler implements RequestHandler<S3Event, String> {

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

			List<String> objectList = ServiceUtil.readAsString(inputStream);

			if (objectList != null) {

				for (String s : objectList) {
					String[] str = s.split(";");
					persistDataMethod1(str);
				}
			}

			return key + " loaded to DynamoDB.";

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	private void persistDataMethod1(String[] str) {

		Item item = new Item().withPrimaryKey("id", str[0]).withString("name", str[1]).withString("location", str[2]);
		table.putItem(item);

	}

	@SuppressWarnings("unused")
	private void persistDataMethod2(String[] str) {

		table.putItem(new PutItemSpec().withItem(
				new Item().withPrimaryKey("id", str[0]).withString("name", str[1]).withString("location", str[2])));

	}

}
