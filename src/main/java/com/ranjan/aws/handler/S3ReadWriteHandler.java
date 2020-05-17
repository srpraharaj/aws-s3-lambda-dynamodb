package com.ranjan.aws.handler;

import java.io.InputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;
import com.amazonaws.services.s3.model.S3Object;
import com.ranjan.aws.s3.GetObject;
import com.ranjan.aws.s3.PutObject;

// Read uploaded file from one bucket and write to another bucket

public class S3ReadWriteHandler implements RequestHandler<S3Event, String> {

	private static final String TARGET_BUCKET = "ranjan-s3-target-bucket";

	@Override
	public String handleRequest(S3Event input, Context context) {

		LambdaLogger logger = context.getLogger(); 
		
		
		// Step 1: Read input event and extract uploaded file details
		S3EventNotificationRecord record = input.getRecords().get(0);
		String srcBucketName = record.getS3().getBucket().getName();
		
		//String srcKey = record.getS3().getObject().getKey(); 
		// Object key may have spaces or unicode non-ASCII characters.
		String srcKey = record.getS3().getObject().getUrlDecodedKey();

		logger.log("Uploaded file details : " + " bucket: " + srcBucketName + " key: " + srcKey);

		// STEP2: Read the object as a stream
		S3Object s3Object = GetObject.getS3Object(srcBucketName, srcKey);
		InputStream objectData = s3Object.getObjectContent();
		
		String contentType = s3Object.getObjectMetadata().getContentType();
		logger.log(" Content-Type : " + contentType);

		// STEP3: Uploading to S3 target bucket
		PutObject.uploadS3Object(TARGET_BUCKET, srcKey, contentType, objectData);

		return "Object" + srcKey + " uploaded to - " + TARGET_BUCKET;
	}

}
