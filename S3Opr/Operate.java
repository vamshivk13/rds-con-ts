package s3operate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;


public class Operate {
public static void main(String args[]) throws IOException{
	try {
		
		AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
	              .withCredentials(new InstanceProfileCredentialsProvider(false))
	              .build();
		
		 List<Bucket> bucketList = null;
		 System.out.println(bucketList);
		 bucketList = s3Client.listBuckets();
		 //s3Client.listObjects("s3bucketjava1");
		 if(bucketList!=null)
		 {
		 System.out.print("S3 Connectivity object Created");
		 System.out.println(bucketList);
		 }
		S3util obj= new S3util();
		final Properties prop = new Properties();
	
	    final FileInputStream file = new FileInputStream("config.properties");
	    prop.load(file);
        file.close();

        String Mode = prop.getProperty("Mode"); 
        String FileName = prop.getProperty("Filename"); 
        String FilePath = prop.getProperty("Filepath"); 
        String BucketName = prop.getProperty("Bucketname"); 
        String ExpireTime = prop.getProperty("Expiretime"); 
        long ExpireTimeL=Long.parseLong(ExpireTime);
		switch(Mode) {
		case "UPLOAD": 
			  System.out.println(Mode+"entered");
			 File f=new File(FilePath);
			obj.uploadS3(s3Client,FileName,f,BucketName); 
	        break;
		case "DELETE": 
			obj.deleteS3(s3Client,FileName,BucketName); 
	        break;
		case "PRESIGNEDURL": 
			obj.getPresignedUrl(s3Client,BucketName,FileName,ExpireTimeL); 
	        break;
		case "COPY": 
			obj.copyS3(s3Client,FileName,FileName+"_"+"Copy",BucketName); 
	        break;
		default: System.out.println("Successfully Created S3 Object- NO MODE SELECTED");
		 
		}
		obj.listBucketObjects(s3Client,BucketName);	
        }
	   catch(Exception e) {
    	
    	System.out.println("Operation Failed "+e);
    	
       }
	  
	   
}

}