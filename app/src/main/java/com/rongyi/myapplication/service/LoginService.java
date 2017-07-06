package com.rongyi.myapplication.service;

import com.rongyi.myapplication.bean.HttpResult;
import com.rongyi.myapplication.bean.ListResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by rongyi on 2017/7/5.
 */

public interface LoginService {

    @POST("JobSearch")
    Observable<HttpResult<List<ListResult>>> getLogin(@Query("pageNo") String pageNo, @Query("seaName") String SeaName);
}
