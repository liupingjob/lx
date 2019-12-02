package com.example.lx.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Config {
    @Value("${config.emailUsername}")
    public  String emailUsername;
    @Value("${config.emailPassword}")
    public  String emailPassword;
    @Value("${config.excelFilePath}")
    public  String excelFilePath;
    @Value("${config.addFileName}")
    public  String addFile;
    @Value("${config.MailCC}")
    public  String MailCC;



}
