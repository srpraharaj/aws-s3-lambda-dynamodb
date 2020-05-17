package com.ranjan.aws.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ranjan.aws.manager.S3Manager;

public class GetObject {

	private static final AmazonS3 s3Client = S3Manager.getS3Client();
	static String bucketName = "*******";
	static String key = "*******";

	public static S3Object getS3Object(String srcBucket, String srcKey) {

		try {

			// override these objects if the name & key are different
			bucketName = srcBucket;
			key = srcKey;

			// Get an object and print its contents.
			S3Object s3Object = s3Client.getObject(new GetObjectRequest(bucketName, key));

			return s3Object;

		} catch (AmazonServiceException aex) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			aex.printStackTrace();
		} catch (SdkClientException ex) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			ex.printStackTrace();
		}

		return null;
	}
}
