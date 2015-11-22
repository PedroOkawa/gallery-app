package com.okawa.pedro.greendao;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoManager {

    private static String OUTPUT_DIR = "../app/src/main/java-gen";
    private static String PACKAGE_NAME = "greendao";
    private static int DATABASE_VERSION = 1;

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(DATABASE_VERSION, PACKAGE_NAME);
        createTables(schema);
        new DaoGenerator().generateAll(schema, OUTPUT_DIR);
    }

    private static void createTables(Schema schema) {
        /* DATA TABLE */
        Entity data = schema.addEntity("ImageData");

        data.addIdProperty().primaryKey();
        data.addStringProperty("imageType");
        data.addStringProperty("imageURL");
        data.addStringProperty("contributor");

        /* CATEGORIES TABLE */
        Entity categories = schema.addEntity("CategoryData");

        Property imageIdProperty = categories.addLongProperty("imageId").notNull().getProperty();
        categories.addIdProperty().primaryKey().autoincrement();
        categories.addLongProperty("categoryId").notNull();
        categories.addStringProperty("name").notNull();

        /* RELATIONSHIP BETWEEN CATEGORIES AND DATA*/
        data.addToMany(categories, imageIdProperty);
    }
}
