package com.okawa.pedro.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

public class GreenDaoManager {

    private static final String OUTPUT_DIR = "../app/src/main/java-gen";
    private static final String PACKAGE_NAME = "greendao";
    private static final int DATABASE_VERSION = 1;

    private static final String IMAGE_TABLE = "ImageData";
    private static final String IMAGE_COLUMN_ID = "imageId";
    private static final String IMAGE_COLUMN_TYPE = "imageType";
    private static final String IMAGE_COLUMN_URL = "imageURL";
    private static final String IMAGE_COLUMN_CONTRIBUTOR = "contributor";
    private static final String IMAGE_COLUMN_DESCRIPTION = "description";

    private static final String CATEGORY_TABLE = "CategoryData";
    private static final String CATEGORY_COLUMN_IMAGE_ID = "imageId";
    private static final String CATEGORY_COLUMN_ID = "categoryId";
    private static final String CATEGORY_COLUMN_NAME = "name";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DATABASE_VERSION, PACKAGE_NAME);
        createTables(schema);
        new DaoGenerator().generateAll(schema, OUTPUT_DIR);
    }

    private static void createTables(Schema schema) {
        /* IMAGE TABLE */
        Entity image = schema.addEntity(IMAGE_TABLE);

        image.addIdProperty().primaryKey().autoincrement();
        image.addLongProperty(IMAGE_COLUMN_ID).notNull();
        image.addStringProperty(IMAGE_COLUMN_TYPE);
        image.addStringProperty(IMAGE_COLUMN_URL);
        image.addStringProperty(IMAGE_COLUMN_CONTRIBUTOR);
        image.addStringProperty(IMAGE_COLUMN_DESCRIPTION);

        image.setHasKeepSections(true);

        /* CATEGORY TABLE */
        Entity category = schema.addEntity(CATEGORY_TABLE);

        category.addIdProperty().primaryKey().autoincrement();
        Property imageIdProperty = category.addLongProperty(CATEGORY_COLUMN_IMAGE_ID).notNull().getProperty();
        category.addLongProperty(CATEGORY_COLUMN_ID).notNull();
        category.addStringProperty(CATEGORY_COLUMN_NAME).notNull();

        /* RELATIONSHIP BETWEEN CATEGORIES AND DATA*/
        image.addToMany(category, imageIdProperty);
    }
}
