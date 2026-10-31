package org.example.sb3.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 下载文件
 *
 * @Author: zongz
 * @Date: 2024/10/26
 */
@RestController
@RequestMapping("/download")
@Slf4j
public class DownloadController {

    /**
     * 下载文件
     *
     * @return
     */
    @PostMapping("/file")
    public String file(HttpServletResponse response) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file = new File(path + "/file/book.jpg");

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("{}", e);
            return "下载失败";
        }
        return "下载成功";
    }

    /**
     * 下载文件，返回response
     *
     * @param response
     * @return
     */
    @PostMapping("/response")
    public HttpServletResponse response(HttpServletResponse response) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file = new File(path + "/file/book.jpg");

        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error("{}", e);
        }
        return response;
    }

    /**
     * 使用ResponseEntity下载文件
     *
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/responseEntity", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> responseEntity() throws IOException {
        log.info("进入下载方法...");
        //读取文件
        String path = this.getClass().getClassLoader().getResource("").getPath();
        String filePath = path + "/file/book.jpg";
        FileSystemResource file = new FileSystemResource(filePath);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getFilename()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.contentLength())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new InputStreamResource(file.getInputStream()));
    }

    /**
     * 下载压缩文件
     * todo 优化
     *
     * @return
     */
    @PostMapping("/zip")
    public String zip(HttpServletResponse response) {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file1 = new File(path + "/file/book.jpg");
        File file2 = new File(path + "/file/books.jpg");

        File[] paths = new File[]{file1, file2};

        // 设置最终输出zip文件的目录+文件名
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        String zipFileName = formatter.format(new Date()) + ".zip";
        String strZipPath = zipFileName;

        // 创建流
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        File zipFile = new File(strZipPath);
        try {
            // 构造最终压缩包的输出流
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));
            for (int i = 0; i < paths.length; i++) {
                File file = paths[i];
                // 解码获取真实路径与文件名
                String realFileName = URLDecoder.decode(file.getName(), "UTF-8");
                //String realFilePath = URLDecoder.decode(file.getAbsolutePath(), "UTF-8");

                if (file.exists()) {
                    // 将需要压缩的文件格式化为输入流
                    zipSource = new FileInputStream(file);
                    // 在压缩目录中文件的名字
                    ZipEntry zipEntry = new ZipEntry(realFileName);
                    // 定位该压缩条目位置，开始写入文件到压缩包中
                    zipStream.putNextEntry(zipEntry);
                    bufferStream = new BufferedInputStream(zipSource, 1024 * 10);
                    int read = 0;
                    byte[] buf = new byte[1024 * 10];
                    while ((read = bufferStream.read(buf, 0, 1024 * 10)) != -1) {
                        zipStream.write(buf, 0, read);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (null != bufferStream) {
                    bufferStream.close();
                }
                if (null != zipStream) {
                    zipStream.flush();
                    zipStream.close();
                }
                if (null != zipSource) {
                    zipSource.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (zipFile.exists()) {
            downFile(response, zipFileName, strZipPath);
            zipFile.delete();
        }

        return "下载成功";
    }

    public void downFile(HttpServletResponse response, String filename, String path) {
        if (filename != null) {
            FileInputStream is = null;
            BufferedInputStream bs = null;
            OutputStream os = null;
            try {
                File file = new File(path);
                if (file.exists()) {
                    //设置Headers
                    response.setHeader("Content-Type", "application/octet-stream");
                    //设置下载的文件的名称-该方式已解决中文乱码问题
                    response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("gb2312"), "ISO8859-1"));
                    is = new FileInputStream(file);
                    bs = new BufferedInputStream(is);
                    os = response.getOutputStream();
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = bs.read(buffer)) != -1) {
                        os.write(buffer, 0, len);
                    }
                } else {
                    String error = Base64Util.encode("下载的文件资源不存在");
                    response.sendRedirect("/imgUpload/imgList?error=" + error);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (bs != null) {
                        bs.close();
                    }
                    if (os != null) {
                        os.flush();
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文件转换为base64
     *
     * @return
     */
    @GetMapping("/file2base64")
    public String file2base64() {
        String path = this.getClass().getClassLoader().getResource("").getPath();
        File file = new File(path + "/file/book.jpg");
        String base64 = fileToBase64(file);
        return base64;
    }

    /**
     * 文件转换为base64
     *
     * @param file
     * @return
     */
    private static String fileToBase64(File file) {
        String base64 = null;
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            in.read(bytes);
            base64 = Base64.encodeBase64String(bytes);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return base64;
    }

}
