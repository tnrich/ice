package org.jbei.ice.client.common.widget;

/**
 * Font awesome icons that are used in this application. Note that this does not
 * represent a complete list of the icons that are available.
 *
 * @author Hector Plahar
 */
public enum FAIconType {

    ARROW_CIRCLE_LEFT("fa fa-arrow-circle-left"),
    ARROW_RIGHT("fa fa-arrow-right"),
    BAN_CIRCLE("fa fa-ban-circle"),
    BOOK("fa fa-book"),
    CARET_DOWN("fa fa-caret-down"),
    CARET_LEFT("fa fa-caret-left"),
    CARET_RIGHT("fa fa-caret-right"),
    CARET_UP("fa fa-caret-up"),
    CHECK("fa fa-check"),
    CHECK_SQUARE("fa fa-check-square"),
    CHEVRON_LEFT("fa fa-chevron-left"),
    CHEVRON_CIRCLE_LEFT("fa fa-chevron-circle-left"),
    CHEVRON_RIGHT("fa fa-chevron-right"),
    CHEVRON_CIRCLE_RIGHT("fa fa-chevron-circle-right"),
    COG("fa fa-cog"),
    COGS("fa fa-cogs"),
    COMMENTS_ALT("fa fa-comments-o"),
    COPY("fa fa-copy"),
    CUT("fa fa-cut"),
    DOWNLOAD("fa fa-download"),
    EDIT("fa fa-edit"),
    ENVELOPE("fa fa-envelope"),
    ENVELOPE_ALT("fa fa-envelope-o"),
    EXCLAMATION_TRIANGLE("fa fa-exclamation-triangle"),
    EXTERNAL_LINK("fa fa-external-link"),
    EYE_CLOSE("fa fa-eye-close"),
    EYE_OPEN("fa fa-eye-open"),
    FAST_BACKWARD("fa fa-fast-backward"),
    FAST_FORWARD("fa fa-fast-forward"),
    FOLDER("fa fa-folder"),
    FOLDER_ALT("fa fa-folder-o"),
    FOLDER_OPEN("fa fa-folder-open"),
    FOLDER_OPEN_ALT("fa fa-folder-open-o"),
    GEAR("fa fa-gear"),
    GLOBE("fa fa-globe"),
    GROUP("fa fa-group"),
    HDD("fa fa-hdd"),
    INBOX("fa fa-inbox"),
    INDENT_LEFT("fa fa-indent-left"),
    INDENT_RIGHT("fa fa-indent-right"),
    INFO_CIRCLE("fa fa-info-circle"),
    KEY("fa fa-key"),
    LIST("fa fa-list"),
    LOCK("fa fa-lock"),
    MINUS("fa fa-minus"),
    MINUS_SIGN("fa fa-minus-sign"),
    MOVE("fa fa-move"),
    PAPER_CLIP("fa fa-paperclip"),
    PASTE("fa fa-paste"),
    PLUS("fa fa-plus"),
    PLUS_CIRCLE("fa fa-plus-circle"),
    QUESTION_SIGN("fa fa-question-sign"),
    QUOTE_LEFT("fa fa-quote-left"),
    QUOTE_RIGHT("fa fa-quote-right"),
    REFRESH("fa fa-refresh"),
    SAVE("fa fa-save"),
    SEARCH("fa fa-search"),
    SHARE("fa fa-share"),
    SHARE_SQUARE("fa fa-share-square"),
    SHIELD("fa fa-shield"),
    SIGN_IN("fa fa-sign-in"),
    SIGN_OUT("fa fa-sign-out"),
    STEP_BACKWARD("fa fa-step-backward"),
    STEP_FORWARD("fa fa-step-forward"),
    TABLE("fa fa-table"),
    TAG("fa fa-tag"),
    TAGS("fa fa-tags"),
    TASKS("fa fa-tasks"),
    TH_LIST("fa fa-th-list"),
    THUMB_TACK("fa fa-thumb-tack"),
    TIMES("fa fa-times"),
    TIMES_CIRCLE("fa fa-times-circle"),
    TRASH("fa fa-trash-o"),
    UNDO("fa fa-undo"),
    UNLOCK("fa fa-unlock"),
    UNLOCK_ALT("fa fa-unlock-o"),
    UPLOAD("fa fa-upload"),
    USER("fa fa-user"),
    WARNING("fa fa-warning");

    private String styleName;

    FAIconType(String styleName) {
        this.styleName = styleName;
    }

    public String getStyleName() {
        return styleName;
    }
}
