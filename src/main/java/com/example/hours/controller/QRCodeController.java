package com.example.hours.controller;

import com.example.hours.common.Result;
import com.example.hours.common.constant.EntityConstant;
import com.example.hours.service.RegisterActivityService;
import com.example.hours.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/qr")
public class QRCodeController {

    @Autowired
    private RegisterActivityService registerActivityService;

    private static final String rootPath = "./QRCode";
    private static final String fileFormat =".png";

    private static final ThreadLocal<SimpleDateFormat> LOCAL_DATE_FORMAT=ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyyMMddHHmmss"));

    /**
     * 生成二维码并将其存放于本地目录
     * @param activityId 活动id
     * @return {@link Result<>}
     */
    @GetMapping("generate/v1")
    public Result<Object> generateV1(@RequestParam("activityId") Integer activityId){
        try {
            Integer registerId = registerActivityService.getRegisterId(activityId);
            String content = EntityConstant.REGISTER_ACTIVITY_ID + registerId;
            final String fileName = LOCAL_DATE_FORMAT.get().format(new Date());
            QRCodeUtils.createCodeToFile(content, new File(rootPath), fileName + fileFormat);
        } catch (Exception e){
            return Result.failed(e.getMessage());
        }
        return Result.success();
    }

    /**
     * 生成二维码并将其返回给前端调用者
     * @param activityId 活动id
     * @param servletResponse HttpServletResponse
     * @return {@link Result<>}
     */
    @GetMapping("generate/v2")
    public Result<Object> generateV2(@RequestParam("activityId") Integer activityId, HttpServletResponse servletResponse){
        try {
            Integer registerId = registerActivityService.getRegisterId(activityId);
            String content = EntityConstant.REGISTER_ACTIVITY_ID + registerId;
            QRCodeUtils.createCodeToOutputStream(content, servletResponse.getOutputStream());
        } catch (Exception e){
            return Result.failed(e.getMessage());
        }
        return Result.success();
    }

    @GetMapping("/tt")
    public void test() {
        Integer a = null;
        System.out.println("asdf: " + a);
        System.out.println("---------------------------rrtt");
    }
}
