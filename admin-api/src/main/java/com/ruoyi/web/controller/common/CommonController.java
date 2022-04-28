package com.ruoyi.web.controller.common;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.framework.config.ServerConfig;
import com.ruoyi.netty.util.SMSUtil;
import com.ruoyi.netty.util.keyUtil;
import com.ruoyi.system.domain.TbPattern;
import com.ruoyi.system.service.ITbPatternService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.ruoyi.common.utils.file.FileUploadUtils.getExtension;

/**
 * 通用请求处理
 * 
 * @author ruoyi
 */
@RestController
public class CommonController
{
    private static final Logger log = LoggerFactory.getLogger(CommonController.class);

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

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private ITbPatternService tbPatternService;

    /**
     * 通用下载请求
     * 
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    @GetMapping("common/download")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {

            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StringUtils.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String filePath = RuoYiConfig.getDownloadPath() + fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(filePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(filePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }

    //点击发送验证码
    @GetMapping("/common/sendMs")
    public String sendMs (HttpServletRequest request, String phoneNumber){
        if(phoneNumber!=null&&!phoneNumber.equals("")){
            String code = keyUtil.keyUtils();
            String s = new SMSUtil().sendSMS(phoneNumber, code);
            return s;
        }else{
            return "error";
        }
    }

    @ResponseBody
    @PostMapping("/common/uploadCategory")
    public AjaxResult uploadCategory(HttpServletRequest request,@RequestBody MultipartFile[] files,String classId) {
        log.info(request.getQueryString());

            if (files != null && files.length > 0) {

                for (MultipartFile file : files) {
                    //处理上传的文件
                    COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
                    ClientConfig clientConfig = new ClientConfig(new Region(bucket));
                    COSClient cosClient = new COSClient(cred, clientConfig);

                    try
                    {
                        TbPattern tbPattern=new TbPattern();
                        tbPattern.setTypeId(classId);
                        tbPattern.setName(file.getOriginalFilename());
                        tbPattern.setPrice(1L);
                        tbPattern.setConsumption(1L);

                        String bucketName=this.bucketName;
                        File file1 = multipartFileToFile(file);
//            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);

                        // 上传文件路径
                        PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,this.qianzui+extractFilename(file),file1);
                        cosClient.putObject(putObjectRequest);
                        // 上传并返回新文件名称
//            String fileNameOne = putObjectRequest.getFile().getName();
                        String url = this.path + putObjectRequest.getKey();
                        AjaxResult ajax = AjaxResult.success();
                        tbPattern.setPicture(putObjectRequest.getKey());
                        tbPatternService.insertTbPattern(tbPattern);
                        ajax.put("fileName",putObjectRequest.getKey());
                        ajax.put("url", url);
                        return ajax;
                    }
                    catch (Exception e)
                    {
                        return AjaxResult.error(e.getMessage());
                    }finally {
                        cosClient.shutdown();
                    }
                    //其他逻辑
                }
            }
            return AjaxResult.error("文件夾沒有内容");
    }


    /**
     * 通用上传请求
     */
    @PostMapping("/common/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosClient = new COSClient(cred, clientConfig);
        try
        {
            String bucketName=this.bucketName;
            File file1 = multipartFileToFile(file);
//            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);

            // 上传文件路径
            PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,this.qianzui+extractFilename(file),file1);
            cosClient.putObject(putObjectRequest);
            // 上传并返回新文件名称
//            String fileNameOne = putObjectRequest.getFile().getName();
            String url = this.path + putObjectRequest.getKey();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName",putObjectRequest.getKey());
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }finally {
            cosClient.shutdown();
        }
//        try
//        {
//            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);
//            String url = serverConfig.getUrl() + fileName;
//            AjaxResult ajax = AjaxResult.success();
//            ajax.put("fileName", fileName);
//            ajax.put("url", url);
//            return ajax;
//        }
//        catch (Exception e)
//        {
//            return AjaxResult.error(e.getMessage());
//        }
    }

    public static File multipartFileToFile(MultipartFile mulFile) throws IOException {
        InputStream ins = mulFile.getInputStream();
        String fileName = mulFile.getOriginalFilename();
        String prefix = getFileNameNoEx(fileName)+ UUID.fastUUID();
        String suffix = "."+getExtensionName(fileName);
        File toFile = File.createTempFile(prefix,suffix);
        OutputStream os = new FileOutputStream(toFile);
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return toFile;
    }

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * 获取不带扩展名的文件名
     *
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    /**
     * 编码文件名
     */
    public static final String extractFilename(MultipartFile file)
    {
//        String fileName = file.getOriginalFilename();
        String extension = getExtension(file);
        String fileName = DateUtils.datePath() + "/" + IdUtils.fastSimpleUUID() + "." + extension;
        return fileName;
    }

    //获取流文件
    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 通用上传请求
     */
    @PostMapping("/common/uploadFile")
    public AjaxResult uploadFileOne(MultipartFile file) throws Exception
    {
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosClient = new COSClient(cred, clientConfig);
        try
        {
            String bucketName=this.bucketName;
            File file1 = multipartFileToFile(file);
//            // 上传文件路径
//            String filePath = RuoYiConfig.getUploadPath();
//            // 上传并返回新文件名称
//            String fileName = FileUploadUtils.upload(filePath, file);

            // 上传文件路径
            PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,this.qianzui+extractFilename(file),file1);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            // 上传并返回新文件名称
            String fileNameOne = putObjectRequest.getFile().getName();
            String url = this.path + putObjectRequest.getKey();
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName",putObjectRequest.getKey());
            ajax.put("url", url);
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }finally {
            cosClient.shutdown();
        }
    }



    /**
     * U3D上传请求
     */
    @PostMapping("/common/u3dUploadFile")
    public AjaxResult u3dUploadFile(MultipartFile file) throws Exception
    {
        COSCredentials cred = new BasicCOSCredentials(accessKey, secretKey);
        ClientConfig clientConfig = new ClientConfig(new Region(bucket));
        COSClient cosClient = new COSClient(cred, clientConfig);
        try{
            String bucketName=this.bucketName;
            File file1 = multipartFileToFile(file);
            String extension = getExtension(file);
            if(StringUtils.isBlank(extension) || "dat".equals(extension)){
                extension = "jpg";
            }
            String fileName = DateUtils.datePath() + "/" + IdUtils.fastSimpleUUID() + "." + extension;

            // 上传文件路径
            PutObjectRequest putObjectRequest=new PutObjectRequest(bucketName,this.qianzui+ fileName, file1);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
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
    }


    /**
     * 本地资源通用下载
     */
    @GetMapping("/common/download/resource")
    public void resourceDownload(String name, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // 本地资源路径
        String localPath = RuoYiConfig.getProfile();
        // 数据库资源地址
        String downloadPath = localPath + StringUtils.substringAfter(name, Constants.RESOURCE_PREFIX);
        // 下载名称
        String downloadName = StringUtils.substringAfterLast(downloadPath, "/");
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, downloadName));
        FileUtils.writeBytes(downloadPath, response.getOutputStream());
    }

    @Autowired
    private RedisCache redisCache;

    //点击发送验证码
    @GetMapping("/common/getCode")
    public String getCode (String phoneNumber){
        return redisCache.getCacheObject("MSG_"+phoneNumber);
    }

}
