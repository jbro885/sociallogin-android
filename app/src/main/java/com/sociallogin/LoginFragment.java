package com.sociallogin;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.sociallogin.constants.AppConstants;
import com.sociallogin.helpers.FacebookHelper;
import com.sociallogin.helpers.GoogleHelper;
import com.sociallogin.helpers.TwitterHelper;
import com.sociallogin.managers.SharedPreferenceManager;
import com.sociallogin.model.UserMeta;
import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.plus.model.people.Person;
import com.twitter.sdk.android.core.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements TwitterHelper.OnTwitterSignInListener, FacebookHelper.OnFbSignInListener, GoogleHelper.OnGoogleSignInListener{

    private static final String TAG = LoginFragment.class.getSimpleName();
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.login_layout)
    LinearLayout view;

    private FacebookHelper fbConnectHelper;
    private GoogleHelper gSignInHelper;
    private TwitterHelper twitterConnectHelper;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        setup();
    }

    private void setup() {
        GoogleHelper.setClientID(AppConstants.GOOGLE_CLIENT_ID);
        gSignInHelper = GoogleHelper.getInstance();
        gSignInHelper.initialize(getActivity(), this);

        fbConnectHelper = new FacebookHelper(this,this);
        twitterConnectHelper = new TwitterHelper(getActivity(), this);
    }

    @OnClick(R.id.login_google)
    public void loginwithGoogle(View view) {
        gSignInHelper.signIn(getActivity());
        setBackground(R.color.g_color);
    }

    @OnClick(R.id.login_facebook)
    public void loginwithFacebook(View view) {
        fbConnectHelper.connect();
        setBackground(R.color.fb_color);
    }

    @OnClick(R.id.login_twitter)
    public void loginwithTwitter(View view) {
        twitterConnectHelper.connect();
        setBackground(R.color.twitter_color);
    }

    private void setBackground(int colorId)
    {
        getView().setBackgroundColor(getActivity().getResources().getColor(colorId));
        progressBar.setVisibility(View.VISIBLE);
        view.setVisibility(View.GONE);
    }

    private void resetToDefaultView(String message)
    {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        getView().setBackgroundColor(getActivity().getResources().getColor(android.R.color.white));
        progressBar.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbConnectHelper.onActivityResult(requestCode, resultCode, data);
        gSignInHelper.onActivityResult(requestCode, resultCode, data);
        twitterConnectHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void OnFbSuccess(GraphResponse graphResponse) {
        UserMeta userMeta = getUserModelFromGraphResponse(graphResponse);
        if(userMeta !=null) {
            SharedPreferenceManager.getSharedInstance().saveUserModel(userMeta);
            startHomeActivity(userMeta);
        }
    }

    private UserMeta getUserModelFromGraphResponse(GraphResponse graphResponse)
    {
        UserMeta userMeta = new UserMeta();
        try {
            JSONObject jsonObject = graphResponse.getJSONObject();
            userMeta.userName = jsonObject.getString("name");
            userMeta.userEmail = jsonObject.getString("email");
            String id = jsonObject.getString("id");
            String profileImg = "http://graph.facebook.com/"+ id+ "/picture?type=large";
            userMeta.profilePic = profileImg;
            Log.i(TAG,profileImg);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userMeta;
    }

    @Override
    public void OnFbError(String errorMessage) {
        resetToDefaultView(errorMessage);
    }

    @Override
    public void OnGSignSuccess(GoogleSignInAccount acct, Person person) {
        UserMeta userMeta = new UserMeta();
        userMeta.userName = (acct.getDisplayName()==null)?"":acct.getDisplayName();
        userMeta.userEmail = acct.getEmail();

        Log.i(TAG, "OnGSignSuccess: AccessToken "+ acct.getIdToken());

        if(person!=null) {
            int gender = person.getGender();

            if (gender == 0)
                userMeta.gender = "MALE";
            else if (gender == 1)
                userMeta.gender = "FEMALE";
            else
                userMeta.gender = "OTHERS";

            Log.i(TAG, "OnGSignSuccess: gender "+ userMeta.gender);
        }

        Uri photoUrl = acct.getPhotoUrl();
        if(photoUrl!=null)
            userMeta.profilePic = photoUrl.toString();
        else
            userMeta.profilePic = "";
        Log.i(TAG, acct.getIdToken());

        SharedPreferenceManager.getSharedInstance().saveUserModel(userMeta);
        startHomeActivity(userMeta);
    }

    @Override
    public void OnGSignError(GoogleSignInResult errorMessage) {
        resetToDefaultView("Google Sign in error@");
    }

    private void startHomeActivity(UserMeta userMeta)
    {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.putExtra(UserMeta.class.getSimpleName(), userMeta);
        startActivity(intent);
        getActivity().finishAffinity();
    }

    @Override
    public void onTwitterSuccess(User user, String email) {
        UserMeta userMeta = new UserMeta();
        userMeta.userName = user.name;
        userMeta.userEmail = email;
        userMeta.profilePic = user.profileImageUrl;

        SharedPreferenceManager.getSharedInstance().saveUserModel(userMeta);
        startHomeActivity(userMeta);
    }

    @Override
    public void onTwitterError(String errorMessage) {
        resetToDefaultView(errorMessage);
    }
}
