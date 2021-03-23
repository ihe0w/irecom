package com.example.business_server.utils;

import com.example.business_server.model.dto.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class FileUtil {
    /**
     * 上传文件并返回保存的url*/
    public static String uploadFile(MultipartFile file,String dir) throws IOException {
        log.info("uploading file ---->");
        // 获取存储文件路径
        String fileName= file.getOriginalFilename();
        fileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+"_"+fileName;
        log.info("saving file"+fileName);
        String path=dir+fileName;

        // 判断文件是否存在
        File dest=new File(path);
        if (dest.exists()){
            log.warn("file "+path+" exists");
            throw new FileAlreadyExistsException("文件"+path+"已存在");
        }

        // 判断目录是否存在
        if (!dest.getParentFile().exists()){
            dest.getParentFile().mkdir();
        }

        // 保存文件

        file.transferTo(dest);
        return fileName;

    }

    public static void deleteFile(String url) {
    }
}
