package org.jbei.ice.web.panels;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.jbei.ice.controllers.AccountController;
import org.jbei.ice.controllers.common.ControllerException;
import org.jbei.ice.lib.models.Account;
import org.jbei.ice.lib.utils.JbeirSettings;
import org.jbei.ice.web.IceSession;
import org.jbei.ice.web.common.ViewException;
import org.jbei.ice.web.pages.ProfilePage;

public class ProfileAboutUserPanel extends Panel {
    private static final long serialVersionUID = 1L;

    public ProfileAboutUserPanel(String id, String accountEmail) {
        super(id);

        Account account = null;
        try {
            account = AccountController.getByEmail(accountEmail);
        } catch (ControllerException e) {
            throw new ViewException(e);
        }

        if (account == null) {
            add(new Label("name", accountEmail));
            add(new Label("email", accountEmail));
            add(new Label("memberSince", ""));
            add(new MultiLineLabel("institution", ""));
            add(new MultiLineLabel("description", ""));
        } else {

            WebMarkupContainer topLinkContainer = new WebMarkupContainer("updateSpan");

            boolean updateAllowed = (JbeirSettings.getSetting("PROFILE_EDIT_ALLOWED")
                    .equalsIgnoreCase("yes")) ? true : false;

            topLinkContainer.setOutputMarkupId(true);
            topLinkContainer.setOutputMarkupPlaceholderTag(true);
            topLinkContainer.setVisible(updateAllowed
                    && accountEmail.equals(IceSession.get().getAccount().getEmail()));
            topLinkContainer.add(new BookmarkablePageLink<WebPage>("updateLink", ProfilePage.class,
                    new PageParameters("0=update, 1=" + account.getEmail())));
            add(topLinkContainer);

            WebMarkupContainer changePasswordSpan = new WebMarkupContainer("passwordSpan");
            boolean passwordAllowed = (JbeirSettings.getSetting("PASSWORD_CHANGE_ALLOWED")
                    .equalsIgnoreCase("yes")) ? true : false;
            changePasswordSpan.setOutputMarkupId(true);
            changePasswordSpan.setOutputMarkupPlaceholderTag(true);
            changePasswordSpan.setVisible(passwordAllowed
                    && accountEmail.equals(IceSession.get().getAccount().getEmail()));
            changePasswordSpan.add(new BookmarkablePageLink<WebPage>("passwordLink",
                    ProfilePage.class, new PageParameters("0=password, 1=" + account.getEmail())));
            add(changePasswordSpan);

            Date memberSinceDate = account.getCreationTime();
            String memberSince = "";

            if (memberSinceDate != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM d yyyy");
                memberSince = dateFormat.format(memberSinceDate);
            }

            add(new Label("name", account.getFullName()));
            add(new Label("email", account.getEmail()));
            add(new Label("memberSince", memberSince));
            add(new MultiLineLabel("institution", account.getInstitution()));
            add(new MultiLineLabel("description", account.getDescription()));
        }
    }
}
