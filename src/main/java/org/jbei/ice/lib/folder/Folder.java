package org.jbei.ice.lib.folder;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;

import org.jbei.ice.lib.dao.IModel;
import org.jbei.ice.lib.entry.model.Entry;
import org.jbei.ice.shared.dto.folder.FolderStatus;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Encapsulates the notion of a collection of {@link org.jbei.ice.lib.entry.model.Entry}s
 * Each folder has an owner.
 *
 * @author Hector Plahar
 */
@Entity
@Table(name = "folder")
@SequenceGenerator(name = "sequence", sequenceName = "folder_id_seq", allocationSize = 1)
public class Folder implements IModel {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Folder parent;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "description", length = 1023)
    private String description;

    @Column(name = "owner_email", length = 255, nullable = false)
    private String ownerEmail;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "modification_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationTime;

    @Column(name = "status")
    private FolderStatus status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "folder_entry", joinColumns = {@JoinColumn(name = "folder_id", nullable = false)},
               inverseJoinColumns = {@JoinColumn(name = "entry_id", nullable = false)})
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<Entry> contents = new LinkedHashSet<>();

    public Folder() {}

    public Folder(String name) {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public Set<Entry> getContents() {
        return contents;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getModificationTime() {
        return modificationTime;
    }

    public void setModificationTime(Date modificationTime) {
        this.modificationTime = modificationTime;
    }

    public FolderStatus getStatus() {
        return status;
    }

    public void setStatus(FolderStatus status) {
        this.status = status;
    }
}
