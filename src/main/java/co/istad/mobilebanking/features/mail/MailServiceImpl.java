package co.istad.mobilebanking.features.mail;

import co.istad.mobilebanking.features.mail.dto.MailRequest;
import co.istad.mobilebanking.features.mail.dto.MailResponse;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService{

    //to be able to send email (inject bean)
    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    @Override
    public MailResponse send(MailRequest mailRequest) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        Context context = new Context();
        context.setVariable("name","ISTAD");

        // template engine inject above
        String htmlTemplate = templateEngine.process("mail/sample",context);

        try {
            mimeMessageHelper.setTo(mailRequest.to());
            mimeMessageHelper.setSubject(mailRequest.subject());
            mimeMessageHelper.setText(htmlTemplate,true); //the true is for html
        } catch (MessagingException e) {
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }

        javaMailSender.send(mimeMessage);
        return new MailResponse("Mail has been send");
    }
}
