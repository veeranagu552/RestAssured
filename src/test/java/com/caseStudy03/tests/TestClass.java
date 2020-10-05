package com.caseStudy03.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.caseStudy03.utils.Constants;
import com.caseStudy03.utils.ReusableMethods;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestClass extends Base {
	private String placeId = null;
	private String updatedAddressMessage = null;
	private String postStatus = null;
	private String deleteStatus = null;

	@Test(dataProvider = "data", priority = 3)
	public void getPositiveTest(Map<String, String> dataMap) {
		basePath = configReader.getBasePath();
		Map<String, String> getQueryParameters = new HashMap();
		getQueryParameters.put("key", dataMap.get(Constants.QUERYPARAMETERKEY));
		getQueryParameters.put("place_id", placeId); // we will get this place id from post request
														// post will be executed first because of the priority
		// sending get request and storing response
		Response getLocationResponse = given().log().all().queryParams(getQueryParameters).when().get().then().log()
				.all().extract().response();
		// getting status code and asserting
		int getLocationStatusCode = getLocationResponse.getStatusCode();
		System.out.println("Get Status Code : " + getLocationStatusCode);
		Assert.assertEquals(getLocationStatusCode, 200);
		// converting response to json using reusable method
		JsonPath getLocationResponseJson = ReusableMethods.rawToJson(getLocationResponse.asString());
		// extracting values from the json
		Map<String, String> locationMap = getLocationResponseJson.getMap("location");
		String latitude = locationMap.get("latitude");
		String longitude = locationMap.get("longitude");
		int accuracy = getLocationResponseJson.getInt("accuracy");
		String name = getLocationResponseJson.getString("name");
		String phoneNumber = getLocationResponseJson.getString("phone_number");
		String address = getLocationResponseJson.getString("address");
		String website = getLocationResponseJson.getString("website");
		String language = getLocationResponseJson.getString("language");
		// asserting values
		Assert.assertEquals(latitude, dataMap.get(Constants.LATITUDE));
		Assert.assertEquals(longitude, dataMap.get(Constants.LONGITUDE));
		Assert.assertEquals(accuracy, Integer.parseInt(dataMap.get(Constants.ACCURACY)));
		Assert.assertEquals(name, dataMap.get(Constants.NAME));
		Assert.assertEquals(phoneNumber, dataMap.get(Constants.PHONENUMBER));
		Assert.assertEquals(address, dataMap.get(Constants.NEWADDRESS));
		Assert.assertEquals(website, dataMap.get(Constants.WEBSITE));
		Assert.assertEquals(language, dataMap.get(Constants.LANGUAGE));
	}

	@Test(dataProvider = "data", priority = 1)
	public void postPositiveTest(Map<String, String> dataMap) {
		basePath = configReader.postBasePath();
		// used linked hash map to preserve the insertion order
		Map<String, String> postQueryParameters = new LinkedHashMap<String, String>();
		// query parameters map
		postQueryParameters.put("key", dataMap.get(Constants.QUERYPARAMETERKEY));
		Map<String, Object> bodyMap = new LinkedHashMap<String, Object>();
		Map<String, Double> locationMap = new LinkedHashMap<String, Double>();
		locationMap.put("lat", Double.parseDouble(dataMap.get(Constants.LATITUDE)));
		locationMap.put("lng", Double.parseDouble(dataMap.get(Constants.LONGITUDE)));
		// adding all the key value pairs to map
		bodyMap.put("location", locationMap);
		bodyMap.put("accuracy", Integer.parseInt(dataMap.get(Constants.ACCURACY)));
		bodyMap.put("name", dataMap.get(Constants.NAME));
		bodyMap.put("phone_number", dataMap.get(Constants.PHONENUMBER));
		bodyMap.put("address", dataMap.get(Constants.ADDRESS));
		ArrayList<String> types = new ArrayList<String>();
		String typesFromExcel = dataMap.get(Constants.TYPES);
		String[] typesArray = typesFromExcel.split(",");
		for (String type : typesArray)
			types.add(type);
		bodyMap.put("types", types);
		bodyMap.put("website", dataMap.get(Constants.WEBSITE));
		bodyMap.put("language", dataMap.get(Constants.LANGUAGE));
		// sending post request and storing response
		Response addLocationResponse = given().log().all().contentType(ContentType.JSON)
				.queryParams(postQueryParameters).body(bodyMap).when().post().then().log().all().extract().response();
		// getting status code and asserting
		int statusCode = addLocationResponse.statusCode();
		System.out.println("Post Status Code : " + statusCode);
		Assert.assertEquals(statusCode, 200);
		// converting to resonse to json
		JsonPath addLocationResponseJson = ReusableMethods.rawToJson(addLocationResponse.asString());
		// extracting place id and status
		placeId = addLocationResponseJson.getString("place_id"); // storing place id for future reference
		postStatus = addLocationResponseJson.getString("status");
		Assert.assertEquals(postStatus, "OK"); // asserting status
		System.out.println("Add Place Id: " + placeId);
	}

	@Test(dataProvider = "data", priority = 2)
	public void putPositiveTest(Map<String, String> dataMap) {
		basePath = configReader.putBasePath();
		// query parameters map
		Map<String, String> putQueryParameters = new LinkedHashMap<String, String>();
		putQueryParameters.put("key", dataMap.get(Constants.QUERYPARAMETERKEY));
		Map<String, Object> bodyMap = new LinkedHashMap<String, Object>();
		// adding key value pairs to body map
		bodyMap.put("place_id", placeId); // using place id which we got from post request
		bodyMap.put("address", dataMap.get(Constants.NEWADDRESS));
		bodyMap.put("key", "qaclick123");
		// sending put req and storing response
		Response updateLocationResponse = given().log().all().contentType(ContentType.JSON)
				.queryParams(putQueryParameters).body(bodyMap).when().put().then().log().all().extract().response();
		System.out.println("Put Status Code: " + updateLocationResponse.statusCode());
		// converting response to json
		JsonPath addLocationResponseJson = ReusableMethods.rawToJson(updateLocationResponse.asString());
		updatedAddressMessage = addLocationResponseJson.getString("msg");
		System.out.println("Update Address Message : " + updatedAddressMessage);
		Assert.assertEquals(updatedAddressMessage, "Address successfully updated");
	}

	@Test(dataProvider = "data", priority = 4)
	public void daletePositiveTest(Map<String, String> dataMap) {
		basePath = configReader.deleteBasePath();
		// query parameters map
		Map<String, String> deleteQueryParameters = new LinkedHashMap<String, String>();
		deleteQueryParameters.put("key", dataMap.get(Constants.QUERYPARAMETERKEY));
		Map<String, Object> bodyMap = new LinkedHashMap<String, Object>();
		bodyMap.put("place_id", placeId); // using place id from post request
		// sending delete request and storing response
		Response deleteLocationResponse = given().log().all().contentType(ContentType.JSON)
				.queryParams(deleteQueryParameters).body(bodyMap).when().delete().then().log().all().extract()
				.response();
		// getting and asserting delete status code
		System.out.println("Delete Status Code: " + deleteLocationResponse.statusCode());
		int statusCode = deleteLocationResponse.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		// converting response to json
		JsonPath addLocationResponseJson = ReusableMethods.rawToJson(deleteLocationResponse.asString());
		deleteStatus = addLocationResponseJson.getString("status");
		System.out.println("Delete Status : " + deleteStatus);
	}
}
