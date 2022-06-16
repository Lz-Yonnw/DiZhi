package com;

import com.ruoyi.RuoYiApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RuoYiApplication.class})
public class U3dCeshi {

    @Value("${spring.tengxun.accessKey}")
    private String accessKey;
    @Value("${spring.tengxun.secretKey}")
    private String secretKey;
    @Value("${spring.tengxun.bucket}")
    private String bucket;
    @Value("${spring.tengxun.bucketName}")
    private String bucketName;
    @Value("${spring.tengxun.path}")
    private String path;
    @Value("${spring.tengxun.qianzui}")
    private String qianzui;

    /*public String u3dUploadFile(MultipartFile file) throws Exception
    {
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosClient = new COSClient(cred, clientConfig);
        try{
            String bucketName=this.bucketName;


            cosClient.putObject(bucketName,qianzui+"11111.png",multipartFile);
            File file2 = new File(path);
            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(),file2);


            File file1 = multipartFileToFile(file);
            String extension = getExtension(file);
            if(StringUtils.isBlank(extension) || "dat".equals(extension)){
                extension = "jpg";
            }
            //文件名
            String fileName = DateUtils.datePath() + "/" + IdUtils.fastSimpleUUID() + "." + extension;

            // 上传文件路径
            PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,this.qianzui+ fileName, file1);
            putObjectRequest.setStorageClass(StorageClass.Standard);//标准上传

            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            String etag = putObjectResult.getETag();
            System.out.println(etag);

            String url = this.path + putObjectRequest.getKey();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName",putObjectRequest.getKey());
            ajax.put("url", url);
            return ajax;
        }catch (Exception e){
            return AjaxResult.error(e.getMessage());
        }finally {
            cosClient.shutdown();
        }
    }*/

}
