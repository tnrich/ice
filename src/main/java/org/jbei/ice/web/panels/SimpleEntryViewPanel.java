package org.jbei.ice.web.panels;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.jbei.ice.lib.managers.AttachmentManager;
import org.jbei.ice.lib.managers.SampleManager;
import org.jbei.ice.lib.managers.SequenceManager;
import org.jbei.ice.lib.models.Entry;
import org.jbei.ice.web.pages.UnprotectedPage;
import org.jbei.ice.web.utils.WebUtils;

public class SimpleEntryViewPanel<T extends Entry> extends AbstractEntryViewPanel<T> {
    private static final long serialVersionUID = 1L;
    private static final int MAX_LONG_FIELD_LENGTH = 100;

    public SimpleEntryViewPanel(String id, Model<T> entryModel) {
        super(id, entryModel);
    }

    @Override
    protected void renderSummary() {
        add(new MultiLineLabel("shortDescription", trimLongField(WebUtils
                .jbeiLinkifyText(getEntry().getShortDescription()), MAX_LONG_FIELD_LENGTH))
                .setEscapeModelStrings(false));
    }

    @Override
    protected void renderNotes() {
        add(new MultiLineLabel("longDescription", trimLongField(WebUtils.jbeiLinkifyText(getEntry()
                .getLongDescription()), MAX_LONG_FIELD_LENGTH)).setEscapeModelStrings(false));
    }

    @Override
    protected void renderReferences() {
        add(new MultiLineLabel("references", trimLongField(WebUtils.jbeiLinkifyText(getEntry()
                .getReferences()), MAX_LONG_FIELD_LENGTH)).setEscapeModelStrings(false));
    }

    @Override
    protected void renderAttachments() {
        ResourceReference hasAttachmentImage = new ResourceReference(UnprotectedPage.class,
                UnprotectedPage.IMAGES_RESOURCE_LOCATION + "attachment.gif");
        add(new Image("attachments", hasAttachmentImage).setVisible(AttachmentManager
                .hasAttachment(getEntry())));
    }

    @Override
    protected void renderSamples() {
        ResourceReference hasSampleImage = new ResourceReference(UnprotectedPage.class,
                UnprotectedPage.IMAGES_RESOURCE_LOCATION + "sample.png");
        add(new Image("samples", hasSampleImage).setVisible(SampleManager.hasSample(getEntry())));
    }

    @Override
    protected void renderSequence() {
        ResourceReference hasSequenceImage = new ResourceReference(UnprotectedPage.class,
                UnprotectedPage.IMAGES_RESOURCE_LOCATION + "sequence.gif");
        add(new Image("sequence", hasSequenceImage).setVisible(SequenceManager
                .hasSequence(getEntry())));
    }
}