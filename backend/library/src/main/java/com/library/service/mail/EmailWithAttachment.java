package com.library.service.mail;

import org.thymeleaf.context.Context;
import org.thymeleaf.util.MapUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class EmailWithAttachment extends BaseEmail {

    protected abstract Map<AttachmentType, String> getAttachmentFilesWithPath();

    protected abstract Map<AttachmentType, Context> attachmentFileContext();

    public Map<AttachmentType, String> getAttachments() {
        Map<AttachmentType, String> attachments = getAttachmentFilesWithPath();
        if (MapUtils.isEmpty(attachments)) {
            return Collections.emptyMap();
        }
        Map<AttachmentType, Context> context = attachmentFileContext();
        Map<AttachmentType, String> iCerts = new HashMap<>();
        attachments.forEach((type, templatePath) -> iCerts.put(type, process(templatePath, context.get(type))));
        return iCerts;
    }

}
