package s3operate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
public class S3util {
	
	public void uploadS3(AmazonS3 s3Client,String key_name,File f,String bucketName) throws IOException{
		 try {
			  System.out.println("Starting upload");
	            s3Client.putObject(bucketName, key_name,f);
	            System.out.println("upload done");
	        } catch (AmazonServiceException e) {
	            System.out.println("S3UtilUploadErr "+e.getErrorMessage());
	            throw e;
	        }
	}
	public void deleteS3(AmazonS3 s3Client,String key_name,String bucketName) throws IOException{
		try {
			 System.out.println("Starting delete");
			 s3Client.deleteObject(bucketName,key_name); 
			 System.out.println("deletedone");
		} catch (AmazonServiceException e) {
		    System.out.println("S3UtilDeleteErr "+e.getErrorMessage());
		    throw e;
		}
	}
	public void copyS3(AmazonS3 s3Client,String key_name,String newkey,String bucketName) throws IOException{
		try {
			 System.out.println("Starting Copying");
			s3Client.copyObject(bucketName, key_name, bucketName,newkey);
			 System.out.println("Copying done");
		} catch (AmazonServiceException e) {
		    System.out.println("S3UtilDeleteErr "+e.getErrorMessage());
		    throw e;
		}
	}
    public void listBucketObjects(AmazonS3 s3Client, String bucketName ) {

        try {
             final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName);
             ListObjectsV2Result result = s3Client.listObjectsV2(req);
             List<S3ObjectSummary> summaries = result.getObjectSummaries();
         	System.out.println("UPDATED LIST OF OBJECTS");
             for (S3ObjectSummary summary : summaries) {

                 String summaryKey = summary.getKey();               

                 System.out.println(summaryKey);

             }
         } catch (Exception e) {
             System.out.println("S3UtilListObjectsErr "+e.getMessage());
          
         }
     }
    public void getPresignedUrl(AmazonS3 s3Client, String bucketName,String keyname,long ExpireTime) {
    	String url=null;
    	try {
    	 System.out.println("Started Creating URL");
          java.util.Date expiration = new java.util.Date();
          long expTimeMillis = Instant.now().toEpochMilli();
          expTimeMillis +=ExpireTime;
          expiration.setTime(expTimeMillis);
          GeneratePresignedUrlRequest generatePresignedUrlRequest =
                  new GeneratePresignedUrlRequest(bucketName, keyname)
                          .withMethod(HttpMethod.GET)
                          .withExpiration(expiration);
          URL u = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
     	url=u.toString();
     	System.out.println(url);
    	}
    	catch(Exception e) {
    		 System.out.println("S3UtilPresignedUrlErr "+e.getMessage());
    	}
    }

}