package com.okawa.pedro.galleryapp.network;

import com.okawa.pedro.galleryapp.model.Contributor;
import com.okawa.pedro.galleryapp.model.Response;

import java.util.Map;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.QueryMap;
import rx.Observable;

/**
 * Created by pokawa on 19/11/15.
 */
public interface ShutterStockInterface {
    long PARAMETER_CATEGORY_ALL = -1;

    String IMAGE_LIST = "images/search";
    String PARAMETER_VIEW = "view";
    String PARAMETER_FULL_VALUE = "full";
    String PARAMETER_MINI_VALUE = "minimal";
    String PARAMETER_PAGE = "page";
    String PARAMETER_CATEGORY = "category";

    String CONTRIBUTOR_DETAILS = "contributors/{contributor_id}";

    @GET(IMAGE_LIST)
    Observable<Response> imageList(
            @QueryMap Map<String, String> parameters
    );

    @GET(CONTRIBUTOR_DETAILS)
    Observable<Contributor> contributorDetails(
            @Path("contributor_id") long contributor_id
    );
}
