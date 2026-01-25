package com.pdk.rentvideoapi.controller.endpoints;


public class ApiEndpoints {
    public static final String RENTVIDEOAPP_API_ENDPOINT = "/api/v2";
    public static final String USER_REGISTER_API_ENDPOINT = "/auth/register";
    public static final String USER_LOGIN_API_ENDPOINT = "/auth/login";
    public static final String VIDEO_API_ENDPOINT = "/videos";
    public static final String RENT_VIDEO_API_ENDPOINT = "/videos/{videoId}/rent";
    public static final String RETURN_VIDEO_API_ENDPOINT = "/videos/{videoId}/return";
    public static final String VIDEO_ADMIN_API_ENDPOINT = "/admin/videos";
    public static final String PUBLIC_API_ENDPOINT = RENTVIDEOAPP_API_ENDPOINT + "/auth/**";
    public static final String ADMIN_ONLY_API_ENDPOINT = RENTVIDEOAPP_API_ENDPOINT + "/admin/**";
}
