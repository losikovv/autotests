package ru.instamart.kraken.service.testit.model.attachment;

import ru.testit.client.model.AttachmentModel;
import ru.testit.client.model.AttachmentPutModel;

public class AttachmentPut extends AttachmentPutModel {

    public AttachmentPut(final AttachmentModel attachmentModel) {
        this.setId(attachmentModel.getId());
    }
}
