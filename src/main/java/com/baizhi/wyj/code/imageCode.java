package com.baizhi.wyj.code;

import com.baizhi.wyj.util.ImageCodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
@Controller
@RequestMapping("code")
public class imageCode {
    @RequestMapping("code")
    public String codeImage(HttpSession session, HttpServletResponse response){
        //获取验证码随机字符
        String imagecode = ImageCodeUtil.getSecurityCode();
        //并将图片标记存入session
        session.setAttribute("imagecode",imagecode );
        //将验证码字符放入图片里面生成验证码
        BufferedImage Image = ImageCodeUtil.createImage(imagecode);
        System.out.println("验证码 = " + imagecode);
        //将验证码响应到页面
        try {
            OutputStream outputStream = response.getOutputStream();
            ImageIO.write(Image, "png", outputStream);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
