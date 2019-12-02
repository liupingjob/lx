package com.example.lx.util;

import com.example.lx.entity.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送帮助类
 *
 * @author Mac(刘平) 20180728
 */
@Component
public class MailUtil {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    // 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    // 企业邮箱如果发信过于频繁 系统将自动退信 并短时冻结账号 此处使用两个账号里进行轮流发信
    private static Config config;
    @Autowired
    private  Config config1;

    @PostConstruct
    public void init(){
        config = this.config1;
    }    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
    public final static String myEmailSMTPHost = "smtp.163.com";

    // 企业邮箱如果发信过于频繁 系统将自动退信 并短时冻结账号 此处使用两个账号里进行轮流发信


    /**
     * 发送邮件的方法
     * <p>
     * // * @param to 邮件的接收方
     */
    public static void sendMail(String to, String suject, String content) {
        try {
            // 1.创建连接对象，链接到邮箱服务器
            Properties props = new Properties(); // 参数配置
            props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
            props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
            props.setProperty("mail.smtp.auth", "true"); // 需要请求认证
            System.out.println(config.emailUsername+"-====--=-=-=-==--==--=" + config.emailPassword);
            // 2.根据配置创建会话对象, 用于和邮件服务器交互
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.emailUsername, config.emailPassword);
                }
            });


            // 3.创建邮件对象
            Message message = new MimeMessage(session);
            // 3.1设置发件人
            message.setFrom(new InternetAddress(config.emailUsername));
            // 3.2设置收件人
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
            if(!config.MailCC.equals("")){
                String mails[]=config.MailCC.split(",");
                for (String mail:mails) {
                    message.addRecipient(RecipientType.CC, new InternetAddress(mail));
                }
            }
            // 3.3设置邮件的主题
            message.setSubject(suject);
            // 3.4设置邮件的正文
            // message.setContent("<h1>来自智慧电梯的激活邮件，您的验证码是：</h1><h3><a
            // href='http://localhost:10080/Demo_JavaMail/active?code=" + code +
            // "'>http://localhost:10080/Demo_JavaMail/active?code=" + code + "</h3>",
            // "text/html;charset=UTF-8");
            message.setContent("<h1>" + content + "</h1>", "text/html;charset=UTF-8");
            // 4.发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        for (; ; ) {
            MailUtil.sendMail("ping.liu@evpowergroup.com", "缘分缘分", "感谢感谢缘分");// 接收方 接受码
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}