package org.jbei.ice.client.common.header;

import org.jbei.ice.client.common.widget.FAIconType;
import org.jbei.ice.client.common.widget.Icon;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Composite box for quick search.
 * Combines a text box and a holder for search widgets
 *
 * @author Hector Plahar
 */
public class SearchCompositeBox extends Composite {
    private final TextBox box;
    private final FlexTable widgetHolder;
    private final HTMLPanel imagePanel;

    public SearchCompositeBox() {
        Grid grid = new Grid(1, 3);
        grid.setCellPadding(0);
        grid.setCellSpacing(0);
        grid.setStyleName("quick_search");
        initWidget(grid);

        widgetHolder = new FlexTable();
        widgetHolder.setCellPadding(4);
        widgetHolder.setCellSpacing(0);

        // widget holder
        grid.setWidget(0, 0, widgetHolder);

        // text box
        box = new TextBox();
        box.setWidth("370px");
        box.setStyleName("quick_search_input");
        grid.setWidget(0, 1, box);

        // search arrow
        Icon icon = new Icon(FAIconType.CARET_DOWN);
        imagePanel = new HTMLPanel("<span style=\"position: relative; left: 6px; top: 6px; font-size: 0.85em\" " +
                                           "id=\"search_arrow\"></span>");
        imagePanel.setStyleName("quick_search_arrow_panel");
        imagePanel.add(icon, "search_arrow");
        grid.setWidget(0, 2, imagePanel);
        grid.getCellFormatter().setStyleName(0, 2, "search_arrow_td");
    }

    public void setSearchWidget(Widget widget) {
        widgetHolder.setWidget(0, 0, widget);
    }

    public TextBox getTextBox() {
        return this.box;
    }

    public Element getPullDownAreaElement() {
        return imagePanel.getElement();
    }

    public void appendFilter(String filter) {
        if (box.getText().isEmpty())
            box.setText(filter);
        else
            box.setText(box.getText() + " " + filter);
    }

    public void setTextFilter(String filter) {
        box.setText(filter);
    }

    public void removeSearchWidget(Widget filter) {
        widgetHolder.remove(filter);
    }

    public void addTextBoxKeyDownHandler(KeyDownHandler keyDownHandler) {
        box.addKeyDownHandler(keyDownHandler);
    }

    public void setPullDownClickhandler(ClickHandler handler) {
        imagePanel.addDomHandler(handler, ClickEvent.getType());
    }
}
