package greendao;

import java.util.List;
import greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table IMAGE_DATA.
 */
public class ImageData {

    private Long id;
    private long imageId;
    private String imageType;
    private String imageURL;
    private String contributor;
    private String description;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ImageDataDao myDao;

    private List<CategoryData> categoryDataList;

    // KEEP FIELDS - put your custom fields here

    public static final String TYPE_ALL = "all";
    public static final String TYPE_PHOTO = "photo";
    public static final String TYPE_ILLUSTRATION = "illustration";
    public static final String TYPE_VECTOR = "vector";
    // KEEP FIELDS END

    public ImageData() {
    }

    public ImageData(Long id) {
        this.id = id;
    }

    public ImageData(Long id, long imageId, String imageType, String imageURL, String contributor, String description) {
        this.id = id;
        this.imageId = imageId;
        this.imageType = imageType;
        this.imageURL = imageURL;
        this.contributor = contributor;
        this.description = description;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getImageDataDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getImageId() {
        return imageId;
    }

    public void setImageId(long imageId) {
        this.imageId = imageId;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetCategoryDataList() {
        categoryDataList = null;
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

    // KEEP METHODS - put your custom methods here

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<CategoryData> getCategoryDataList() {
        if (categoryDataList == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            CategoryDataDao targetDao = daoSession.getCategoryDataDao();
            List<CategoryData> categoryDataListNew = targetDao._queryImageData_CategoryDataList(imageId);
            synchronized (this) {
                if(categoryDataList == null) {
                    categoryDataList = categoryDataListNew;
                }
            }
        }
        return categoryDataList;
    }
    // KEEP METHODS END

}
