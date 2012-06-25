package org.jbei.ice.lib.account;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.account.model.AccountPreferences;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.server.dao.hibernate.HibernateRepository;

/**
 * DAO to manipulate {@link AccountPreferences} objects in the database.
 *
 * @author Hector Plahar
 */
class PreferencesDAO extends HibernateRepository {

    /**
     * Retrieve the {@link AccountPreferences} of the given {@link Account}.
     *
     * @param account account whose preferences are being retrieved
     * @return retrieved AccountPreferences
     * @throws DAOException
     */
    public AccountPreferences getAccountPreferences(Account account) throws DAOException {

        AccountPreferences accountPreferences = null;
        Session session = newSession();

        try {
            Query query = session.createQuery("from " + AccountPreferences.class.getName()
                                                      + " where account = :account");
            query.setParameter("account", account);

            accountPreferences = (AccountPreferences) query.uniqueResult();
        } catch (HibernateException e) {
            throw new DAOException("Failed to get AccountPreferences by Account: "
                                           + account.getFullName(), e);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }

        return accountPreferences;
    }

    /**
     * Save the given {@link AccountPreferences} into the database.
     *
     * @param accountPreferences preferences to save for account
     * @return Saved AccountPreferences.
     * @throws DAOException
     */
    public AccountPreferences save(AccountPreferences accountPreferences)
            throws DAOException {

        return (AccountPreferences) saveOrUpdate(accountPreferences);
    }
}