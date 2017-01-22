package com.yoaccess.mdcalcdemo;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jjalan on 1/22/17.
 * Copyright (C) 2016 Binary Meter Technologies Pvt. Ltd. - All Rights Reserved
 */

public class User {

    private static final String TAG = "User";

    private static final String PREF_FILE = "com.yoaccess.mdcalcdemo";

    private static final String USER_JWT_TOKEN_KEY = "jwt";

    private static final String USER_PROFILE_KEY = "profile";

    private static Context mContext = null;

    private static User mCurrentUser = null;

    private String mUserName;

    private String mFirstName;

    private String mLastName;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static void signUp(
            String userName,
            String password,
            String confirmPassword,
            String firstName,
            String lastName,
            final AuthenticationCallback authenticationCallback) {

        JSONObject signUpParams = new JSONObject();
        try {
            signUpParams.put("username", userName);
            signUpParams.put("password", password);
            signUpParams.put("confirmPassword", confirmPassword);
            signUpParams.put("firstName", firstName);
            signUpParams.put("lastName", lastName);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        JsonObjectRequest signUpRequest = new JsonObjectRequest
                (Request.Method.POST, mContext.getString(R.string.user_signup_url), signUpParams,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if (authenticationCallback != null) {
                                    authenticationCallback.onUserSignUpSuccess();
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (authenticationCallback != null) {
                            authenticationCallback.onUserSignUpError(getError(error));
                        }
                    }
                });

        queueRequest(mContext, signUpRequest);
    }

    public static void login(
            String userName,
            String password,
            final AuthenticationCallback authenticationCallback) {

        JSONObject loginParams = new JSONObject();
        try {
            loginParams.put("username", userName);
            loginParams.put("password", password);
        } catch (JSONException e) {
        }

        JsonObjectRequest loginRequest = new JsonObjectRequest
                (Request.Method.POST, mContext.getString(R.string.user_authenticate_url),
                        loginParams, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject loginResponse) {

                        try {
                            storeUserToken(mContext, loginResponse.getString("token"));
                            JsonObjectRequest profileRequest = new JsonObjectRequest
                                    (Request.Method.GET,
                                            mContext.getString(R.string.user_profile_url),
                                            null, new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject profileResponse) {
                                            storeUserProfile(mContext, profileResponse);
                                            initUser(profileResponse);

                                            if (authenticationCallback != null) {
                                                authenticationCallback.onUserLoginSuccess();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (authenticationCallback != null) {
                                                authenticationCallback
                                                        .onUserLoginError(getError(error));
                                            }
                                        }
                                    }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> map = new HashMap<>();
                                    map.put("Authorization", String.format("Bearer %s",
                                            getCurrentUserToken(mContext)));
                                    return map;
                                }
                            };

                            queueRequest(mContext, profileRequest);
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage(), e);
                            if (authenticationCallback != null) {
                                authenticationCallback.onUserLoginError(e);
                            }
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (authenticationCallback != null) {
                            authenticationCallback.onUserLoginError(getError(error));
                        }
                    }
                });

        queueRequest(mContext, loginRequest);
    }

    private static void queueRequest(Context context,
            JsonObjectRequest request) {
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private static VolleyError getError(VolleyError volleyError) {

        if (volleyError.networkResponse != null && volleyError.networkResponse.data != null) {
            String errorStr = new String(volleyError.networkResponse.data);
            if (errorStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(errorStr);
                    volleyError = new VolleyError(jsonObject.getString("error"));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }

        return volleyError;
    }

    public static void logout() {

        SharedPreferences sharedPref = mContext
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove(USER_JWT_TOKEN_KEY);
        editor.remove(USER_PROFILE_KEY);
        editor.commit();

        mCurrentUser = null;
    }

    public static User getCurrentUser() {
        if (mCurrentUser == null) {
            // try to read from disk
            initUser(getUserProfile(mContext));
        }

        return mCurrentUser;
    }

    private static void storeUserToken(Context context,
            String token) {
        SharedPreferences sharedPref = context
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_JWT_TOKEN_KEY, token);
        editor.commit();
    }

    private static String getCurrentUserToken(Context context) {
        SharedPreferences sharedPref = context
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(USER_JWT_TOKEN_KEY, null);
    }

    private static void storeUserProfile(Context context, JSONObject userProfile) {
        SharedPreferences sharedPref = context
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_PROFILE_KEY, userProfile.toString());
        editor.commit();
    }

    private static JSONObject getUserProfile(Context context) {
        SharedPreferences sharedPref = context
                .getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        String userProfileStr = sharedPref.getString(USER_PROFILE_KEY, null);
        if (userProfileStr != null) {
            try {
                return new JSONObject(userProfileStr);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                return null;
            }
        } else {
            return null;
        }
    }

    private static void initUser(JSONObject userObj) {
        if (userObj != null) {
            try {
                mCurrentUser = new User();
                mCurrentUser.mUserName = userObj.getString("username");
                mCurrentUser.mFirstName = userObj.getString("firstName");
                mCurrentUser.mLastName = userObj.getString("lastName");
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
                mCurrentUser = null;
            }
        }
    }

    public String getUserName() {
        return mUserName;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public interface AuthenticationCallback {

        void onUserSignUpSuccess();

        void onUserSignUpError(Exception error);

        void onUserLoginSuccess();

        void onUserLoginError(Exception error);
    }

}
