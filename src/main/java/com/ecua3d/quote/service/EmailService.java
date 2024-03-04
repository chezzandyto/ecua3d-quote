package com.ecua3d.quote.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${quote.email.company-name}")
    String companyName;
    @Value("${quote.email.contact-number}")
    String contactNumber;
    @Value("${quote.email.logo-email}")
    String logoEmail;
    @Value("${quote.email.logo-whatsapp}")
    String logoWhatsapp;
    @Value("${quote.email.logo-company}")
    String logoCompany;

    public void sendEmail(String to, String name, List<String> fileNames) throws MessagingException, IOException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        String content = loadHtmlContent("html/email-template.html");
        content = replaceHtmlParameters(content,name,companyName,fileNames,contactNumber);
        helper.setFrom("magaly.chicaiza94@gmail.com");
        helper.setTo(to);
        helper.setSubject("Cotizacion ecua3D");
        helper.setText(content, true);
        javaMailSender.send(mimeMessage);
    }

    private String loadHtmlContent(String filePath) throws IOException {
        Resource resource = new ClassPathResource(filePath);
        byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
        return new String(bytes, StandardCharsets.UTF_8);
    }

    private String replaceHtmlParameters(String htmlContent, String name, String companyName, List<String> fileNames, String contactNumber) {
        final String fileHtmlBase = "<h3 style=\"Margin:0;line-height:34px;mso-line-height-rule:exactly;font-family:Imprima, Arial, sans-serif;font-size:20px;font-style:normal;font-weight:bold;color:#2D3142\">${file}</h3>";
        htmlContent = htmlContent.replace("${name}", name.toUpperCase());
        htmlContent = htmlContent.replace("${company_name}", companyName);
        htmlContent = htmlContent.replace("${contact_number}", contactNumber);
        htmlContent = htmlContent.replace("${imgLogoEmail}", logoEmail);
        htmlContent = htmlContent.replace("${imgLogoWhatsapp}", logoWhatsapp);
        htmlContent = htmlContent.replace("${imgLogoCompany}", logoCompany);
        StringBuilder fileHtml = new StringBuilder();
        for (String file:fileNames){
            fileHtml.append(fileHtmlBase.replace("${file}", file));
        }
        htmlContent = htmlContent.replace("${files}", fileHtml.toString());
        return htmlContent;
    }
}
