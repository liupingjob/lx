package com.example.lx.controller;

import com.example.lx.entity.AddUserInfo;
import com.example.lx.entity.Config;
import com.example.lx.entity.UserInfo;
import com.example.lx.util.ExcelUtil;
import com.example.lx.util.MailUtil;
import com.example.lx.util.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class MainController {
    @Autowired
    private Config config;
     private ExecutorService executor = Executors.newFixedThreadPool(10);

    @RequestMapping("/addUser")
    public ModelAndView addUser(AddUserInfo info) {
        List<UserInfo> list = info.getUser();
        for (UserInfo userInfo : list) {
            if ("是".equals(userInfo.getRoom())) {
            } else {
                userInfo.setHomeName("");
            }
            String msg = " <h1> 尊敬的 '";
            msg += userInfo.getName() + "' 先生/女士 <br>&nbsp;&nbsp; 恭喜您成功报名“2019湖北省医院大会” <br> " +
                    "&nbsp;&nbsp;请您于2019年12月12日武汉东湖国际会议中心荆楚会堂前厅报到并领取资料<br>&nbsp;&nbsp; &nbsp;&nbsp; （湖北省医院协会会务组）<h1>";
            msg += "<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='http://hbyyxh.thanks.echosite.cn/pdf/" + config.addFile + "'> 点击下载会议相关资料</a>";
            String finalMsg = msg;
            executor.submit(() -> {
                final String msg1 = new String(finalMsg);
                MailUtil.sendMail(userInfo.getEmail(), "您好！湖北省医院大会邀请涵【" + userInfo.getName() + "】，谢谢！", msg1);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        executor.submit(() -> {
            ExcelUtil.addExcel(config.excelFilePath, list);
        });
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("pay.html");
        modelAndView.addObject("price1",info.getPrice1());
        return modelAndView;
    }


}
