package greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.ImageData;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table IMAGE_DATA.
*/
public class ImageDataDao extends AbstractDao<ImageData, Long> {

    public static final String TABLENAME = "IMAGE_DATA";

    /**
     * Properties of entity ImageData.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property ImageId = new Property(1, long.class, "imageId", false, "IMAGE_ID");
        public final static Property ImageType = new Property(2, String.class, "imageType", false, "IMAGE_TYPE");
        public final static Property ImageURL = new Property(3, String.class, "imageURL", false, "IMAGE_URL");
        public final static Property Contributor = new Property(4, String.class, "contributor", false, "CONTRIBUTOR");
        public final static Property Description = new Property(5, String.class, "description", false, "DESCRIPTION");
    };

    private DaoSession daoSession;


    public ImageDataDao(DaoConfig config) {
        super(config);
    }
    
    public ImageDataDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'IMAGE_DATA' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'IMAGE_ID' INTEGER NOT NULL ," + // 1: imageId
                "'IMAGE_TYPE' TEXT," + // 2: imageType
                "'IMAGE_URL' TEXT," + // 3: imageURL
                "'CONTRIBUTOR' TEXT," + // 4: contributor
                "'DESCRIPTION' TEXT);"); // 5: description
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'IMAGE_DATA'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, ImageData entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getImageId());
 
        String imageType = entity.getImageType();
        if (imageType != null) {
            stmt.bindString(3, imageType);
        }
 
        String imageURL = entity.getImageURL();
        if (imageURL != null) {
            stmt.bindString(4, imageURL);
        }
 
        String contributor = entity.getContributor();
        if (contributor != null) {
            stmt.bindString(5, contributor);
        }
 
        String description = entity.getDescription();
        if (description != null) {
            stmt.bindString(6, description);
        }
    }

    @Override
    protected void attachEntity(ImageData entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public ImageData readEntity(Cursor cursor, int offset) {
        ImageData entity = new ImageData( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getLong(offset + 1), // imageId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // imageType
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // imageURL
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // contributor
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // description
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, ImageData entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setImageId(cursor.getLong(offset + 1));
        entity.setImageType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setImageURL(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setContributor(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDescription(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(ImageData entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(ImageData entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
