package com.ranjan.aws.s3;

import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.ranjan.aws.manager.S3Manager;

public class PutObject {

	private static final AmazonS3 s3Client = S3Manager.getS3Client();
	static String bucketName = "*** Bucket name ***";
	static String stringObjKeyName = "*** String object key name ***";
	static String fileObjKeyName = "*** File object key name (file name) ***";
	static String fileName = "*** Path to a local file to upload ***";
	static String contentType = "plain/text";

	public static S3Object uploadS3Object(String destBucketName, String destKey, String content_Type,
			InputStream inputStream) {

		try {

			// override these objects if the name & key are different
			bucketName = destBucketName;
			fileObjKeyName = destKey;
			contentType = content_Type;

			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(contentType);
			metadata.addUserMetadata("title", "someTitle");

			
			/*
			 * // Upload a text string as a new object. s3Client.putObject(bucketName,
			 * stringObjKeyName, "Uploaded String Object");
			 * 
			 * // Upload a local file as a new object with ContentType and title specified.
			 * PutObjectRequest fileRequest = new PutObjectRequest(bucketName,
			 * fileObjKeyName, new File(fileName)); fileRequest.setMetadata(metadata);
			 * s3Client.putObject(fileRequest);
			 */
			
			// upload a file as a new Object from input stream
			PutObjectRequest inputRequest = new PutObjectRequest(bucketName, fileObjKeyName, inputStream, metadata);
			s3Client.putObject(inputRequest);

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
