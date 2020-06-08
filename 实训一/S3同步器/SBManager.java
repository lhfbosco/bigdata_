package fileWatch;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CompleteMultipartUploadRequest;
import com.amazonaws.services.s3.model.InitiateMultipartUploadRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PartETag;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.model.UploadPartRequest;

import sun.swing.FilePane;

public class SBManager {
	private final static String bucketName = "ttest";
	private final static String filePath   = "F:\\其他资料\\sync\\";//注意要多加一个 "\\"
	private final static String accessKey = "9DCE9324A61490675D3D";
	private final static String secretKey = "WzBGNEFBQTI2REIyOTc4N0RERkFBMjQ2NTIyQUM4OTFDMjRCMzIyNjBd";
	private final static String serviceEndpoint = "http://scuts3.depts.bingosoft.net:29999";
	private final static String signingRegion = "";
	private static long partSize = 20 << 20;
	
	public void Init()
	{
		final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		final ClientConfiguration ccfg = new ClientConfiguration().
		        withUseExpectContinue(false);
		
		final EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);
		
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
		        .withCredentials(new AWSStaticCredentialsProvider(credentials))
		        .withClientConfiguration(ccfg)
		        .withEndpointConfiguration(endpoint)
		        .withPathStyleAccessEnabled(true)
		        .build();
		try {
			//先删掉bucket所有的文件
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
			listObjectsRequest.setBucketName(bucketName);
			listObjectsRequest.setDelimiter("/");
			ObjectListing ol = s3.listObjects(bucketName);//取出特定前缀的文件
			List<S3ObjectSummary> objects = ol.getObjectSummaries();		
			
			for (S3ObjectSummary os : objects) {
//				System.out.println("* " + os.getKey());
				s3.deleteObject(bucketName, os.getKey());
			}
			//把目录下所有文件上传到s3
	        String[] files = new File(filePath).list();// 读取目录下的所有目录文件信息
	        for (int i = 0; i < files.length; i++) {// 循环，添加文件名或回调自身
	            File file = new File(filePath, files[i]);
	            s3.putObject(bucketName, files[i], new File(filePath + files[i]));
	        }
			System.out.println("Inilization Finished!");
		}
		catch (AmazonServiceException e) {
			System.err.println(e.toString());
		}
		
	}
	
	
	public void Upload(String filename)	
	{
		final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final ClientConfiguration ccfg = new ClientConfiguration().
                withUseExpectContinue(false);

        final EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(ccfg)
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();

//		String keyName = Paths.get(filePath).getFileName().toString();
		
		// Create a list of UploadPartResponse objects. You get one of these
        // for each part upload.
		ArrayList<PartETag> partETags = new ArrayList<PartETag>();
		File file = new File(filePath + filename);
		long contentLength = file.length();
		String uploadId = null;
		
		
        try {
        	//上传文件
//        	s3.putObject(bucketName,filename,new File(filePath + filename));
        	
        	// Step 1: Initialize.
			InitiateMultipartUploadRequest initRequest = 
					new InitiateMultipartUploadRequest(bucketName, filename);
			uploadId = s3.initiateMultipartUpload(initRequest).getUploadId();
			System.out.format("Created upload ID was %s\n", uploadId);

			// Step 2: Upload parts.
			long filePosition = 0;
			for (int i = 1; filePosition < contentLength; i++) {
				// Last part can be less than 5 MB. Adjust part size.
				partSize = Math.min(partSize, contentLength - filePosition);

				// Create request to upload a part.
				UploadPartRequest uploadRequest = new UploadPartRequest()
						.withBucketName(bucketName)
						.withKey(filename)
						.withUploadId(uploadId)
						.withPartNumber(i)
						.withFileOffset(filePosition)
						.withFile(file)
						.withPartSize(partSize);

				// Upload part and add response to our list.
				System.out.format("Uploading part %d\n", i);
				partETags.add(s3.uploadPart(uploadRequest).getPartETag());

				filePosition += partSize;
			}

			// Step 3: Complete.
			System.out.println("Completing upload");
			CompleteMultipartUploadRequest compRequest = 
					new CompleteMultipartUploadRequest(bucketName, filename, uploadId, partETags);

			s3.completeMultipartUpload(compRequest);


        }catch(AmazonClientException e){
        	System.err.println(e.toString());
        	System.exit(1);
        }

        System.out.println(filename + " has benn successfully uploaded");
    }
	
	public void Delete(String filename)
	{
		final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final ClientConfiguration ccfg = new ClientConfiguration().
                withUseExpectContinue(false);

        final EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(ccfg)
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();

        try {
        	//删除文件
        	s3.deleteObject(bucketName, filename);


        }catch(AmazonClientException e){
        	System.err.println(e.toString());
        	System.exit(1);
        }

        System.out.println(filename+" has been successfully deleted");
	}

	public void Modify_(String filename) {
		final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final ClientConfiguration ccfg = new ClientConfiguration().
                withUseExpectContinue(false);

        final EndpointConfiguration endpoint = new EndpointConfiguration(serviceEndpoint, signingRegion);

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(ccfg)
                .withEndpointConfiguration(endpoint)
                .withPathStyleAccessEnabled(true)
                .build();
        
        //先删除，后上传
        try {
        	s3.deleteObject(bucketName, filename);
        }catch(AmazonClientException e){
        	System.err.println(e.toString());
        	System.exit(1);
        }
        
        try {
        	s3.putObject(bucketName,filename,new File(filePath+filename));

        }catch(AmazonClientException e){
        	System.err.println(e.toString());
        	System.exit(1);
        }
        System.out.println(filename + " has been successfully modified");
	}
}
