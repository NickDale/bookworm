package com.library.service.mail;

import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.FileSystemUtils;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.lang.Boolean.TRUE;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

@SuppressWarnings("unused")
@Slf4j
class MailBuilder {

    private static final String TYPE = "text/HTML; charset=UTF-8";
    private static final String FORMAT = "format";
    private static final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";
    private static final String DEFAULT_PERSONAL = "noReply";
    private final JavaMailSender javaMailSender;
    private Path dir;
    private MimeMessageHelper mimeMessageHelper;

    public MailBuilder(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
        createMineMessage();
    }

    private void createMineMessage() {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            mimeMessage.addHeader(FORMAT, "flowed");
            mimeMessage.addHeader(CONTENT_TRANSFER_ENCODING, "8bit");
            this.mimeMessageHelper = new MimeMessageHelper(mimeMessage, TRUE, StandardCharsets.UTF_8.name());
            this.mimeMessageHelper.setSentDate(new Date());
        } catch (MessagingException ex) {
            log.error("Mail builder create mineMessage ex:", ex);
        }
    }

    public MailBuilder from(@NonNull final String from) {
        return from(from, DEFAULT_PERSONAL);
    }

    public MailBuilder from(final String from, final String personal) {
        try {
            this.mimeMessageHelper.setFrom(from.trim(), personal);
        } catch (MessagingException | UnsupportedEncodingException ex) {
            log.error("Mail from setting error ", ex);
        }
        return this;
    }

    public MailBuilder replayTo(final String... replayTo) {
        try {
            this.mimeMessageHelper.getMimeMessage()
                    .setReplyTo(getInternetAddresses(String.join(",", replayTo)));
        } catch (MessagingException ex) {
            log.error("Mail replay setting error ", ex);
        }
        return this;
    }

    public MailBuilder recipients(final String... recipient) {
        return recipients(Message.RecipientType.TO, recipient);
    }

    public MailBuilder recipients(final Message.RecipientType recipientType, final String... recipient) {
        try {
            this.mimeMessageHelper.getMimeMessage().setRecipients(recipientType,
                    getInternetAddresses(String.join(",", recipient)));
        } catch (MessagingException ex) {
            log.error("Mail recipient setting error ", ex);
        }
        return this;
    }

    private InternetAddress[] getInternetAddresses(final String addressString) throws AddressException {
        return InternetAddress.parse(addressString.replaceAll(" ", ""), false);
    }

    public MailBuilder subject(final String subject) {
        try {
            this.mimeMessageHelper.setSubject(subject);
        } catch (MessagingException ex) {
            log.error("Mail subject setting error ", ex);
        }
        return this;
    }

    public MailBuilder plainBody(final String emailBody) {
        try {
            this.mimeMessageHelper.setText(emailBody);
        } catch (MessagingException ex) {
            log.error("Mail set body error ", ex);
        }
        return this;
    }

    public MailBuilder htmlBody(final String emailBody) {
        try {
            this.mimeMessageHelper.setText(emailBody, TRUE);
        } catch (MessagingException ex) {
            log.error("Mail set html body error ", ex);
        }
        return this;
    }

    public MailBuilder addFileFromUrl(final URL url, final String fileName) {
        addAttachment(new URLDataSource(url), fileName);
        return this;
    }

    public MailBuilder addFiles(File... file) {
        return addFiles(Arrays.asList(file));
    }

    public MailBuilder addFileFromUrl(URL... urls) {
        return addFileFromUrl(Arrays.asList(urls));
    }

    public MailBuilder addFileFromUrl(List<URL> urls) {
        try {
            for (var url : urls) {
                DataSource source = new URLDataSource(url);
                addAttachment(source, source.getName());
            }
        } catch (Exception ex) {
            log.error("Mail add files error ", ex);
        }
        return this;
    }

    public MailBuilder addFile(final File file, final String name) {
        try {
            addAttachment(new FileDataSource(file.getPath()), name);
        } catch (Exception ex) {
            log.error("Mail add files error ", ex);
        }
        return this;
    }

    public MailBuilder addFiles(final Collection<File> files) {
        if (isEmpty(files)) {
            return this;
        }
        try {
            for (var file : files) {
                addAttachment(new FileDataSource(file.getPath()), file.getName());
            }
        } catch (Exception ex) {
            log.error("Mail add files error ", ex);
        }
        return this;
    }

    private void addAttachment(final DataSource source, final String attachmentName) {
        try {
            this.mimeMessageHelper.addAttachment(attachmentName, source);
        } catch (MessagingException ex) {
            log.error("Mail add attachment ", ex);
        }
    }

    public MailBuilder addTemplate(final BaseEmail template) {
        try {
            this.mimeMessageHelper.setSubject(template.getEmailSubject());
            var textBodyPart = new MimeBodyPart();
            textBodyPart.setContent(template.getHTMLEmail(), TYPE);
            this.mimeMessageHelper.getMimeMultipart().addBodyPart(textBodyPart);

            var attachmentFiles = template.getInlinedFiles();
            dir = Files.createTempDirectory("mail_builder_" + UUID.randomUUID());
            if (MapUtils.isNotEmpty(attachmentFiles)) {
                for (var entry : attachmentFiles.entrySet()) {
                    var split = entry.getValue().split("/");
                    var file = creatFileAndCopyInputStream(split[split.length - 1], entry.getValue());
                    this.mimeMessageHelper.addInline(entry.getKey(), new FileDataSource(file));
                }
            }
            if (template instanceof EmailWithAttachment) {
                addHTMLAttachments((EmailWithAttachment) template);
            }
        } catch (MessagingException | IOException ex) {
            log.error("Mail template add error ", ex);
        }
        return this;
    }

    private File creatFileAndCopyInputStream(final String fileName, final String filePath) throws IOException {
        File file;
        try {
            file = Files.createFile(dir.resolve(fileName)).toFile();
            try {
                copyInputStreamToFile(new ClassPathResource(filePath).getInputStream(), file);
            } catch (Exception ignored) {
                copyInputStreamToFile(new FileInputStream(filePath), file);
            }
        } catch (FileAlreadyExistsException ignored) {
            return new File(filePath);
        }
        return file;
    }

    private void addHTMLAttachments(final EmailWithAttachment template) {
        Map<AttachmentType, String> iCertAttachments = template.getAttachments();
        if (MapUtils.isEmpty(iCertAttachments)) {
            return;
        }
        try {
            var renderer = new ITextRenderer(20f * 4.5f / 3f, 20);
            List<File> tempFiles = new ArrayList<>();
            for (Map.Entry<AttachmentType, String> entry : iCertAttachments.entrySet()) {
                File file = Files.createFile(dir.resolve(entry.getKey().getFileName())).toFile();
                SharedContext sharedContext = renderer.getSharedContext();
                sharedContext.setPrint(true);
                sharedContext.setInteractive(false);
                sharedContext.setReplacedElementFactory(new ImageReplacedElementFactory());
                sharedContext.getTextRenderer().setSmoothingThreshold(0);

                renderer.setDocumentFromString(entry.getValue());
                renderer.layout();
                try (OutputStream outputStream = new FileOutputStream(file)) {
                    renderer.createPDF(outputStream);
                }
                tempFiles.add(file);
            }
            addFiles(tempFiles);
        } catch (IOException | DocumentException ex) {
            log.error("Error with html attachment", ex);
        }
    }

    public String build() {
        try {
            var mimeMessage = this.mimeMessageHelper.getMimeMessage();
            log.info(" sending email to: {}", Arrays.toString(mimeMessage.getAllRecipients()));
            javaMailSender.send(mimeMessage);
            return mimeMessage.getMessageID();
        } catch (MessagingException ex) {
            log.error("Mail sending error", ex);
        } finally {
            try {
                FileSystemUtils.deleteRecursively(dir);
            } catch (IOException ex) {
                log.error("Couldn't delete the temp dir ", ex);
            }
            createMineMessage();
        }
        return null;
    }

}

