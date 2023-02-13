package com.example.hours.strategy.impl.sign;

import com.example.hours.strategy.SignStrategy;
import com.example.hours.utils.QRCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 二维码打卡策略实现
 */
@Service("qrCodeImpl")
@Slf4j
public class QRCodeImpl implements SignStrategy {

    @Override
    public void signIn() {
        try {
            RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
            HttpServletResponse response = ((ServletRequestAttributes) requestAttributes).getResponse();
            if (response == null) {
                log.error("HttpServletResponse is null");
                return;
            }
            // TODO 获取当前用户id
            String content = "demoData";
            QRCodeUtils.createCodeToOutputStream(content, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void signOut() {

    }
}
