package org.jbei.ice.lib.dto.sample;

import org.jbei.ice.lib.account.AccountTransfer;
import org.jbei.ice.lib.dao.IDataTransferModel;
import org.jbei.ice.lib.dto.StorageInfo;

/**
 * Parent class for the different types of samples
 *
 * @author Hector Plahar
 */
public class PartSample implements IDataTransferModel {

    private AccountTransfer depositor;
    private String label;
    private SampleType type;
    private long creationTime;
    private boolean inCart;
    private StorageInfo main;

    public AccountTransfer getDepositor() {
        return depositor;
    }

    public void setDepositor(AccountTransfer depositor) {
        this.depositor = depositor;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public SampleType getType() {
        return type;
    }

    public void setType(SampleType type) {
        this.type = type;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public StorageInfo getMain() {
        return main;
    }

    public void setMain(StorageInfo main) {
        this.main = main;
    }
}