package com.library.service.mail;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Image;
import org.apache.commons.io.IOUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.FSImage;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextFSImage;
import org.xhtmlrenderer.pdf.ITextImageElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

import java.io.FileInputStream;
import java.io.IOException;

public class ImageReplacedElementFactory implements ReplacedElementFactory {

    private static final String NODE_IMG = "img";
    private static final String SRC = "src";

    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box,
                                                 UserAgentCallback uac, int cssWidth, int cssHeight) {
        var element = box.getElement();
        if (element == null) {
            return null;
        }
        if (element.getNodeName().equals(NODE_IMG)) {
            FSImage fsImage;
            try {
                fsImage = imageForPDF(element.getAttribute(SRC), uac);
            } catch (BadElementException | IOException e1) {
                fsImage = null;
            }
            if (fsImage != null) {
                if (cssWidth != -1 || cssHeight != -1) {
                    fsImage.scale(cssWidth, cssHeight);
                } else {
                    fsImage.scale(250, 150);
                }
                return new ITextImageElement(fsImage);
            }
        }
        return null;
    }

    private FSImage imageForPDF(String attribute, UserAgentCallback uac) throws IOException, BadElementException {
        return new ITextFSImage(Image.getInstance(IOUtils.toByteArray(new FileInputStream(attribute))));
    }

    @Override
    public void reset() {
    }

    @Override
    public void remove(Element e) {
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
    }
}
