package org.jbei.ice.client.collection.add.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.jbei.ice.client.ClientController;
import org.jbei.ice.client.collection.add.form.CustomFieldPanel.Parameter;
import org.jbei.ice.client.common.widget.MultipleTextBox;
import org.jbei.ice.client.entry.display.detail.SequenceViewPanel;
import org.jbei.ice.client.entry.display.detail.SequenceViewPanelPresenter;
import org.jbei.ice.client.entry.display.model.AutoCompleteSuggestOracle;
import org.jbei.ice.lib.shared.BioSafetyOption;
import org.jbei.ice.lib.shared.StatusType;
import org.jbei.ice.lib.shared.dto.entry.AutoCompleteField;
import org.jbei.ice.lib.shared.dto.entry.CustomField;
import org.jbei.ice.lib.shared.dto.entry.PartData;
import org.jbei.ice.lib.shared.dto.user.PreferenceKey;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.*;

/**
 * Parent class for forms used to create single entries
 *
 * @author Hector Plahar
 */
public abstract class EntryForm<T extends PartData> extends Composite implements IEntryFormSubmit {

    protected final FlexTable layout;
    protected HTML cancel;
    protected Button submit;
    protected TextBox creator;
    protected TextBox creatorEmail;
    protected TextBox name;
    protected TextBox alias;
    protected TextBox principalInvestigator;
    protected TextArea summary;
    protected TextBox fundingSource;
    protected ListBox status;
    protected ListBox bioSafety;
    protected TextBox links;
    protected TextBox keywords;
    protected TextArea references;
    protected TextArea ip;

    private final T entryInfo;
    private CustomFieldPanel customFieldPanel;
    private SequenceViewPanel sequencePanel;
    private TextArea notesText;

    private HandlerRegistration submitRegistration;
    private HandlerRegistration cancelRegistration;

    public EntryForm(T entryInfo) {
        layout = new FlexTable();
        initWidget(layout);

        this.entryInfo = entryInfo;

        initComponents();

        this.creator.setText(entryInfo.getCreator());
        this.creatorEmail.setText(entryInfo.getCreatorEmail());
        this.name.setText(entryInfo.getName());
        this.alias.setText(entryInfo.getAlias());
        this.principalInvestigator.setText(entryInfo.getPrincipalInvestigator());
        this.summary.setText(entryInfo.getShortDescription());
        this.references.setText(entryInfo.getReferences());
        this.fundingSource.setText(entryInfo.getFundingSource());
        status.setVisibleItemCount(1);
        for (StatusType type : StatusType.values()) {
            status.addItem(type.getDisplayName());
        }
        status.setStyleName("pull_down");

        if (entryInfo.getStatus() != null) {
            for (int i = 0; i < this.status.getItemCount(); i += 1) {
                if (status.getValue(i).equalsIgnoreCase(entryInfo.getStatus())) {
                    status.setSelectedIndex(i);
                    break;
                }
            }
        }

        bioSafety.setVisibleItemCount(1);
        for (BioSafetyOption options : BioSafetyOption.values()) {
            bioSafety.addItem(options.getDisplayName(), options.getValue());
        }
        bioSafety.setStyleName("pull_down");
        if (entryInfo.getBioSafetyLevel() != null) {
            for (int i = 0; i < this.bioSafety.getItemCount(); i += 1) {
                if (bioSafety.getValue(i).equalsIgnoreCase(entryInfo.getBioSafetyLevel().toString())) {
                    this.bioSafety.setSelectedIndex(i);
                    break;
                }
            }
        }
        links.setText(entryInfo.getLinks());
        keywords.setText(entryInfo.getKeywords());
        ip.setText(entryInfo.getIntellectualProperty());
        this.notesText.setText(entryInfo.getLongDescription());

        this.creator.setText(entryInfo.getCreator());
        this.creatorEmail.setText(entryInfo.getCreatorEmail());

        initLayout();
    }

    protected void initLayout() {
        layout.setWidth("100%");
        layout.setCellPadding(2);
        layout.setCellSpacing(0);

        layout.setWidget(0, 0, createGeneralWidget());
        layout.setWidget(1, 0, createCustomFieldsWidget());
        layout.setWidget(2, 0, sequencePanel);
        layout.setWidget(3, 0, createNotesWidget());
        layout.setWidget(4, 0, createSubmitCancelButtons());
    }

    protected abstract Widget createGeneralWidget();

    protected void initComponents() {
        submit = new Button("Submit");
        submit.setStyleName("btn_submit_entry_form");
        cancel = new HTML("Cancel");
        cancel.setStyleName("footer_feedback_widget");
        cancel.addStyleName("font-85em");
        creator = createStandardTextBox("205px", 125);
        creatorEmail = createStandardTextBox("205px", 125);
        name = createStandardTextBox("205px", 125);
        alias = createStandardTextBox("205px", 125);
        principalInvestigator = createStandardTextBox("205px", 125);
        summary = createTextArea("640px", "50px");

        fundingSource = createStandardTextBox("205px", 250);
        status = new ListBox();
        bioSafety = new ListBox();
        links = createStandardTextBox("300px", 500);
        keywords = createStandardTextBox("640px", 125);
        references = createTextArea("640px", "50px");
        ip = createTextArea("640px", "50px");
        notesText = new TextArea();

        sequencePanel = new SequenceViewPanel(entryInfo, "sequence");

        // are we creating a new part or updating an existing one
        if (this.entryInfo.getId() > 0)
            this.sequencePanel.switchToEditMode();
    }

    public T getEntryInfo() {
        return this.entryInfo;
    }

    @Override
    public void addSubmitHandler(ClickHandler handler) {
        if (submitRegistration != null)
            submitRegistration.removeHandler();
        submitRegistration = this.submit.addClickHandler(handler);
    }

    @Override
    public void addCancelHandler(ClickHandler handler) {
        if (cancelRegistration != null)
            cancelRegistration.removeHandler();
        cancelRegistration = this.cancel.addClickHandler(handler);
    }

    protected Widget createTextBoxWithHelp(Widget box, String helpText) {
        box.getElement().setAttribute("placeHolder", helpText);
        return box;
    }

    protected void setLabel(boolean required, String label, FlexTable layout, int row, int col) {
        String html;
        if (required)
            html = "<span class=\"font-85em\" style=\"white-space:nowrap\">" + label
                    + "  <span class=\"required\">*</span></span>";
        else
            html = "<span class=\"font-85em\" style=\"white-space:nowrap\">" + label + "</span>";

        layout.setHTML(row, col, html);
        layout.getFlexCellFormatter().setVerticalAlignment(row, col, HasAlignment.ALIGN_TOP);
        layout.getFlexCellFormatter().setWidth(row, col, "170px");
    }

    protected Widget createNotesWidget() {
        FlexTable notes = new FlexTable();
        notes.setCellPadding(0);
        notes.setCellSpacing(3);
        notes.setWidth("100%");

        notes.setWidget(0, 0, new Label("Notes"));
        notes.getFlexCellFormatter().setStyleName(0, 0, "entry_add_sub_header");
        notes.getFlexCellFormatter().setColSpan(0, 0, 2);
        notes.setWidget(1, 0, new Label(""));
        notes.getFlexCellFormatter().setHeight(1, 0, "10px");
        notes.getFlexCellFormatter().setColSpan(1, 0, 2);

        // input
        notes.setWidget(2, 0, new Label(""));
        notes.getFlexCellFormatter().setWidth(3, 0, "170px");

        notesText.setStyleName("entry_add_notes_input");
        notes.setWidget(2, 1, notesText);

        return notes;
    }

    protected Widget createSubmitCancelButtons() {
        FlexTable layout = new FlexTable();
        layout.setCellPadding(0);
        layout.setCellSpacing(3);
        layout.setWidth("100%");

        layout.setWidget(0, 0, new HTML("&nbsp;"));
        layout.getCellFormatter().setWidth(0, 0, "160px");

        layout.setWidget(0, 1, submit);
        layout.getFlexCellFormatter().setWidth(0, 1, "85px");
        layout.setWidget(0, 2, cancel);

        return layout;
    }

    protected Widget createCustomFieldsWidget() {
        VerticalPanel panel = new VerticalPanel();
        customFieldPanel = new CustomFieldPanel();
        customFieldPanel.setFields(entryInfo.getCustomFields());
        panel.add(customFieldPanel);
        panel.add(new HTML("&nbsp;"));
        panel.add(customFieldPanel.getFieldButton());
        return panel;
    }

    protected TextBox createStandardTextBox(String width, int length) {
        final TextBox box = new TextBox();
        box.setMaxLength(length);
        box.setStyleName("input_box");
        box.setWidth(width);
        return box;
    }

    protected TextArea createTextArea(String width, String height) {
        final TextArea area = new TextArea();
        area.setStyleName("input_box");
        area.setWidth(width);
        area.setHeight(height);
        return area;
    }

    /**
     * @return text input for auto complete data
     */
    protected SuggestBox createSuggestBox(AutoCompleteField field, String width) {
        AutoCompleteSuggestOracle oracle = new AutoCompleteSuggestOracle(field);
        SuggestBox box = new SuggestBox(oracle, new MultipleTextBox());
        box.setStyleName("input_box");
        box.setWidth(width);
        return box;
    }

    @Override
    public FocusWidget validateForm() {
        FocusWidget invalid = null;

        // name
        if (name.getText().trim().isEmpty()) {
            name.setStyleName("input_box_error");
            invalid = name;
        } else {
            name.setStyleName("input_box");
        }

        // creator
        if (creator.getText().trim().isEmpty()) {
            creator.setStyleName("input_box_error");
            if (invalid == null)
                invalid = creator;
        } else {
            creator.setStyleName("input_box");
        }

        // principal investigator
        if (principalInvestigator.getText().trim().isEmpty()) {
            principalInvestigator.setStyleName("input_box_error");
            if (invalid == null)
                invalid = principalInvestigator;
        } else {
            principalInvestigator.setStyleName("input_box");
        }

        // summary
        if (summary.getText().trim().isEmpty()) {
            summary.setStyleName("input_box_error");
            if (invalid == null)
                invalid = summary;
        } else {
            summary.setStyleName("input_box");
        }

        // parameters
        LinkedHashMap<Integer, Parameter> map = customFieldPanel.getParameterMap();

        for (Integer key : map.keySet()) {
            Parameter parameter = map.get(key);
            String name = parameter.getName();
            String value = parameter.getValue();

            if (name.trim().isEmpty() && !value.isEmpty()) {
                parameter.getNameBox().setStyleName("input_box_error");
                if (invalid == null)
                    invalid = parameter.getNameBox();
            } else {
                parameter.getNameBox().setStyleName("input_box");
            }

            if (value.trim().isEmpty() && !name.isEmpty()) {
                parameter.getValueBox().setStyleName("input_box_error");
                if (invalid == null)
                    invalid = parameter.getValueBox();
            } else {
                parameter.getValueBox().setStyleName("input_box");
            }
        }

        return invalid;
    }

    /**
     * populates the entry info fields that are common to all. this is meant to be sub-classed so
     * that the specializations can
     * input their class specific fields.
     */
    @Override
    public void populateEntries() {
        if (this.entryInfo.getOwnerEmail() == null || this.entryInfo.getOwnerEmail().isEmpty()) {
            this.entryInfo.setOwner(ClientController.account.getFullName());
            this.entryInfo.setOwnerEmail(ClientController.account.getEmail());
        }

        // parameters
        ArrayList<CustomField> parameters = new ArrayList<CustomField>();
        LinkedHashMap<Integer, Parameter> map = customFieldPanel.getParameterMap();

        for (Integer key : map.keySet()) {
            Parameter parameter = map.get(key);
            String name = parameter.getName();
            String value = parameter.getValue();

            if (!name.isEmpty() && !value.isEmpty()) {
                parameters.add(new CustomField(name, value));
            }
        }
        this.entryInfo.setCustomFields(parameters);

        this.entryInfo.setShortDescription(summary.getText());
        this.entryInfo.setLongDescription(this.notesText.getText());

        entryInfo.setName(name.getText());
        entryInfo.setAlias(this.alias.getText());
        entryInfo.setCreator(this.creator.getText());
        entryInfo.setCreatorEmail(this.creatorEmail.getText());
        entryInfo.setShortDescription(this.summary.getText());

        entryInfo.setStatus(status.getValue(status.getSelectedIndex()));
        entryInfo.setLinks(links.getText());
        entryInfo.setKeywords(keywords.getText());
        entryInfo.setReferences(references.getText());
        int bioSafetySelectedIndex = bioSafety.getSelectedIndex();
        int value = Integer.parseInt(bioSafety.getValue(bioSafetySelectedIndex));
        entryInfo.setBioSafetyLevel(value);
        entryInfo.setIntellectualProperty(ip.getText());
        entryInfo.setPrincipalInvestigator(principalInvestigator.getText());
        entryInfo.setFundingSource(fundingSource.getText());
    }

    @Override
    public void setPreferences(HashMap<PreferenceKey, String> preferences) {
        if (preferences.containsKey(PreferenceKey.FUNDING_SOURCE)) {
            if (fundingSource.getText().isEmpty())
                fundingSource.setText(preferences.get(PreferenceKey.FUNDING_SOURCE));
        }

        if (preferences.containsKey(PreferenceKey.PRINCIPAL_INVESTIGATOR)) {
            if (principalInvestigator.getText().isEmpty())
                principalInvestigator.setText(preferences.get(PreferenceKey.PRINCIPAL_INVESTIGATOR));
        }
    }

    @Override
    public PartData getEntry() {
        return getEntryInfo();
    }

    @Override
    public SequenceViewPanelPresenter getSequenceViewPresenter() {
        return sequencePanel.getPresenter();
    }
}