package org.jbei.ice.lib.permissions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.dao.hibernate.HibernateRepository;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.logging.Logger;
import org.jbei.ice.lib.permissions.model.WriteUser;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * @author Hector Plahar
 */
public class WriteUserDAO extends HibernateRepository<WriteUser> {

    public void removeWriteUser(Entry entry, Account account) throws DAOException {
        String queryString = "delete WriteUser writeUser where writeUser.entry = :entry and writeUser.account = " +
                ":account";
        Session session = newSession();

        try {
            session.getTransaction().begin();
            Query query = session.createQuery(queryString);
            query.setEntity("entry", entry);
            query.setEntity("account", account);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            String msg = "Could not remove write user \"" + account.getEmail() + "\" for entry \""
                    + entry.getId() + "\"";
            throw new DAOException(msg, e);
        } finally {
            if (session != null)
                session.close();
        }
    }

    /**
     * Set write permissions for specified user {@link Account}s to the given {@link Entry}.
     * <p/>
     * This method creates new {@link org.jbei.ice.lib.permissions.model.WriteUser} objects using the given {@link
     * Account}s.
     *
     * @param entry    Entry to give permission to.
     * @param accounts Accounts to give write permission to.
     * @throws DAOException
     */
    public void setWriteUser(Entry entry, Set<Account> accounts) throws DAOException {
        String queryString = "delete  WriteUser writeUser where writeUser.entry = :entry";

        Session session = newSession();
        try {
            Query query = session.createQuery(queryString);
            query.setEntity("entry", entry);
            session.getTransaction().begin();
            query.executeUpdate();
            session.getTransaction().commit();
            for (Account account : accounts) {
                WriteUser writeUser = new WriteUser(entry, account);
                super.saveOrUpdate(writeUser);
            }
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            String msg = "Could not set Write User of " + entry.getRecordId();
            throw new DAOException(msg, e);
        } finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

    /**
     * Add write permission for the specified {@link Account} to the specified {@link Entry}.
     * <p/>
     * This method adds a new {@link WriteUser} object to the database..
     *
     * @param entry   Entry to give write permission to.
     * @param account Account to give write permission to.
     * @throws DAOException
     */
    public void addWriteUser(Entry entry, Account account) throws DAOException {
        Set<Account> accounts = getWriteUsers(entry);
        boolean alreadyAdded = false;
        for (Account existingAccount : accounts) {
            if (existingAccount.getId() == account.getId()) {
                alreadyAdded = true;
                break;
            }
        }
        if (alreadyAdded == false) {
            accounts.add(account);
            setWriteUser(entry, accounts);
        }
    }

    /**
     * Retrieve {@link Account}s with write permissions set for the specified {@link Entry}.
     *
     * @param entry entry to query on.
     * @return Set of Accounts.
     * @throws DAOException
     */
    @SuppressWarnings("unchecked")
    public Set<Account> getWriteUsers(Entry entry) throws DAOException {

        Session session = newSession();

        try {
            Criteria criteria = session.createCriteria(WriteUser.class)
                                       .add(Restrictions.eq("entry", entry))
                                       .setProjection(Projections.property("account"));
            List list = criteria.list();
            return new HashSet<Account>(list);

        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

}