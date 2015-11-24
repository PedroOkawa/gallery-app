package com.okawa.pedro.galleryapp.tests;

import com.okawa.pedro.galleryapp.model.Assets;
import com.okawa.pedro.galleryapp.model.Categories;
import com.okawa.pedro.galleryapp.model.Content;
import com.okawa.pedro.galleryapp.model.Contributor;
import com.okawa.pedro.galleryapp.model.Data;
import com.okawa.pedro.galleryapp.util.manager.ParserManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Created by pokawa on 24/11/15.
 */
public class ParserManagerTest {

    private static final long IMAGE_ID = 1;
    private static final String DESCRIPTION = "description";
    private static final String TYPE = "type";
    private static final String URL = "url";
    private static final long CONTRIBUTOR_ID = 1;
    private static final long CATEGORY_ID_1 = 1;
    private static final String CATEGORY_NAME_1 = "category_1";
    private static final long CATEGORY_ID_2 = 1;
    private static final String CATEGORY_NAME_2 = "category_2";

    @Mock Data mData;
    @Mock Assets mAssets;
    @Mock Content mContent;
    @Mock Contributor mContributor;

    List<Categories> mCategorySet;
    @Mock Categories mCategory1;
    @Mock Categories mCategory2;

    @Before
    public void setup() {
        initMocks(this);

        mCategorySet = new ArrayList<>();

        when(mContent.getUrl()).thenReturn(URL);

        when(mAssets.getPreview()).thenReturn(mContent);

        when(mContributor.getId()).thenReturn(CONTRIBUTOR_ID);

        when(mCategory1.getId()).thenReturn(CATEGORY_ID_1);
        when(mCategory1.getName()).thenReturn(CATEGORY_NAME_1);

        when(mCategory2.getId()).thenReturn(CATEGORY_ID_2);
        when(mCategory2.getName()).thenReturn(CATEGORY_NAME_2);

        mCategorySet.add(mCategory1);
        mCategorySet.add(mCategory2);

        when(mData.getId()).thenReturn(IMAGE_ID);
        when(mData.getDescription()).thenReturn(DESCRIPTION);
        when(mData.getImage_type()).thenReturn(TYPE);
        when(mData.getAssets()).thenReturn(mAssets);
        when(mData.getContributor()).thenReturn(mContributor);
        when(mData.getCategories()).thenReturn(mCategorySet);
    }

    @Test
    public void testImageParse() {
        ParserManager parserManager = new ParserManager();
        assertEquals(parserManager.parseData(mData).getImageId(), IMAGE_ID);
        assertEquals(parserManager.parseData(mData).getDescription(), DESCRIPTION);
        assertEquals(parserManager.parseData(mData).getImageType(), TYPE);
        assertEquals(parserManager.parseData(mData).getImageURL(), URL);
        assertEquals(parserManager.parseData(mData).getContributorId(), CONTRIBUTOR_ID);
    }

    @Test
    public void testCategoryParse() {
        ParserManager parserManager = new ParserManager();
        assertEquals(parserManager.parseCategories(mData).get(0).getCategoryId(), CATEGORY_ID_1);
        assertEquals(parserManager.parseCategories(mData).get(0).getName(), CATEGORY_NAME_1);
        assertEquals(parserManager.parseCategories(mData).get(1).getCategoryId(), CATEGORY_ID_2);
        assertEquals(parserManager.parseCategories(mData).get(1).getName(), CATEGORY_NAME_2);
    }

    @After
    public void dispose() {
        mCategorySet.clear();
    }
}