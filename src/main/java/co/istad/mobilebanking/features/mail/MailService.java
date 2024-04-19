package co.istad.mobilebanking.features.mail;

import co.istad.mobilebanking.features.mail.dto.MailRequest;
import co.istad.mobilebanking.features.mail.dto.MailResponse;

public interface MailService {
    MailResponse send(MailRequest mailRequest);
}
