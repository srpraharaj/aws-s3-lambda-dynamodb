package com.ranjan.aws.util;

import com.amazonaws.regions.Regions;

public class Constants {

	private Constants() {}
	
	public static final String UNDEFINED = "undefined";
	public static Regions clientRegion = Regions.US_EAST_2;
	public static Regions defaultClientRegion = Regions.DEFAULT_REGION;
	public static String DYNAMODB_TABLE_NAME = "employee";
}
