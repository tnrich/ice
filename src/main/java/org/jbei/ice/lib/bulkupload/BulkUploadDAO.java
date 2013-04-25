package org.jbei.ice.lib.bulkupload;

import java.util.ArrayList;
import java.util.Iterator;

import org.jbei.ice.lib.account.model.Account;
import org.jbei.ice.lib.dao.DAOException;
import org.jbei.ice.lib.dao.hibernate.HibernateRepository;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.lib.logging.Logger;
import org.jbei.ice.shared.dto.entry.EntryType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * Hibernate Data accessor object for retrieving {@link BulkUpload} objects
 * from the database
 *
 * @author Hector Plahar
 */
class BulkUploadDAO extends HibernateRepository<BulkUpload> {

    public BulkUpload retrieveById(long id) throws DAOException {
        return super.get(BulkUpload.class, id);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BulkUpload> retrieveByAccount(Account account) throws DAOException {
        Session session = currentSession();
        ArrayList<BulkUpload> result;

        try {
            Query query = session.createQuery("from " + BulkUpload.class.getName() + " where account = :account AND "
                                                      + "status != :status");
            query.setParameter("account", account);
            query.setParameter("status", BulkUploadStatus.PENDING_APPROVAL);
            result = new ArrayList<BulkUpload>(query.list());
            return result;
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<BulkUpload> retrieveByStatus(BulkUploadStatus status) throws DAOException {
        Session session = currentSession();
        ArrayList<BulkUpload> result;

        try {
            Query query = session.createQuery("from " + BulkUpload.class.getName() + " where status = :status");
            query.setParameter("status", status);
            result = new ArrayList<BulkUpload>(query.list());
            return result;
        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    public int retrieveSavedDraftCount(long draftId) throws DAOException {
        Session session = currentSession();

        try {
            Query query = session
                    .createSQLQuery("select count(*) from bulk_upload_entry where bulk_upload_id = " + draftId);
            int count = ((Number) query.uniqueResult()).intValue();
            return count;

        } catch (HibernateException he) {
            Logger.error(he);
            throw new DAOException(he);
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<Entry> retrieveDraftEntries(EntryType type, long id, int start, int limit) throws DAOException {
        BulkUpload bulkUpload = super.get(BulkUpload.class, id);
        Iterator<Entry> iterator = bulkUpload.getContents().iterator();
        int i = -1;
        ArrayList<Entry> results = new ArrayList<>();
        while (iterator.hasNext()) {
            i += 1;
            if (i < start)
                continue;

            if (results.size() == limit)
                return results;

            Entry next = iterator.next();

            if (type == null) {
                results.add(next);
                continue;
            }

            if (next.getRecordType().equalsIgnoreCase(type.getName())) {
                results.add(next);
            }
        }

        return results;
    }
}
