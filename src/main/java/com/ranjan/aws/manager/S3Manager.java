package com.ranjan.aws.manager;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ranjan.aws.util.Constants;

public class S3Manager {

	private static volatile S3Manager instance;
	private AmazonS3 s3Client;

	private S3Manager() {

		// This code expects that you have AWS credentials set up per:
		// https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html
		s3Client = AmazonS3ClientBuilder.standard().withRegion(Constants.clientRegion).build();
	}

	public static S3Manager instance() {

		if (instance == null) {
			synchronized (S3Manager.class) {
				if (instance == null)
					instance = new S3Manager();
			}
		}
		return instance;
	}

	public static AmazonS3 getS3Client() {
		S3Manager manager = instance();
		return manager.s3Client;
	}

}
