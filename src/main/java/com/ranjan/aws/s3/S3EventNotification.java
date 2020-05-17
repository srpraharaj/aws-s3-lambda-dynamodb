package com.ranjan.aws.s3;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.s3.event.S3EventNotification.S3EventNotificationRecord;

public class S3EventNotification {

	public static Map<String, String> getEventDetails(S3Event input) {

		Map<String, String> map = new HashMap<>();

		S3EventNotificationRecord record = input.getRecords().get(0);
		String srcBucketName = record.getS3().getBucket().getName();
		String srcKey = record.getS3().getObject().getKey(); // file name

		map.put(srcBucketName, srcKey);
		
		return map;
	}
}
