package com.capsuladigital.androidcore.presentation.home;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.capsuladigital.androidcore.R;
import com.capsuladigital.androidcore.data.repository.local.session.SessionManager;
import com.capsuladigital.androidcore.presentation.home.about_us.AboutUsActivity;
import com.capsuladigital.androidcore.presentation.home.league.LeagueFragment;

import com.capsuladigital.androidcore.presentation.login.LoginActivity;
import com.capsuladigital.androidcore.presentation.profile.ProfileActivity;
import com.capsuladigital.androidcore.util.GoogleSingInWrapper;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    Context context;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private Drawer drawerResult;
    private Fragment fragment;
    private final String TAG = MainActivity.class.getSimpleName();
    private int currentFragment;
    PrimaryDrawerItem worldCup;
    SectionDrawerItem communications;
    PrimaryDrawerItem aboutUs;
    PrimaryDrawerItem share;
    PrimaryDrawerItem rateApp;
    SectionDrawerItem account;
    PrimaryDrawerItem profile;
    PrimaryDrawerItem logout;

    SessionManager sessionManager;
    //private Drawable icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        sessionManager = SessionManager.getInstance(this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpToolbar();
        setUpNavDrawer();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(10.0f);
    }

    //clean fragment back stack!
    private void cleanStack() {
        this.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void setUpNavDrawer() {

        new DrawerBuilder().withActivity(this).build();

        worldCup = new PrimaryDrawerItem().withIdentifier(R.string.nav_worldcup).withName(R.string.nav_worldcup).withSelectable(true).withIcon(R.drawable.nav_ranking);
        communications = new SectionDrawerItem().withIdentifier(R.string.nav_communications).withName(R.string.nav_communications).withSelectable(false);
        aboutUs = new PrimaryDrawerItem().withIdentifier(R.string.nav_about_us).withName(R.string.nav_about_us).withSelectable(false).withIcon(R.drawable.about_us);
        share = new PrimaryDrawerItem().withIdentifier(R.string.nav_share).withName(R.string.nav_share).withSelectable(false).withIcon(R.drawable.share);
        rateApp = new PrimaryDrawerItem().withIdentifier(R.string.nav_rate_app).withName(R.string.nav_rate_app).withSelectable(false).withIcon(R.drawable.rate_app);
        account = new SectionDrawerItem().withIdentifier(R.string.nav_account).withName(R.string.nav_account).withSelectable(false);
        profile = new PrimaryDrawerItem().withIdentifier(R.string.up_title).withName(R.string.up_title).withSelectable(true).withIcon(R.drawable.user);
        logout = new PrimaryDrawerItem().withIdentifier(R.string.nav_logout).withName(R.string.nav_logout).withSelectable(true).withIcon(R.drawable.nav_logout);

        if (getCurrentFragment() == null)
            loadFragment(R.string.nav_worldcup);

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(final ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri)
                        .resize(400, 400)
                        .onlyScaleDown()
                        .centerCrop()
                        .placeholder(R.drawable.user_placeholder)
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                imageView.setImageResource(R.drawable.user_placeholder);
                            }
                        });
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

        });

        String userIconPath;
        if (sessionManager.getUserInfo().getPersonImageUrl().equals("")) {
            userIconPath = getString(R.string.placeholder_path);
        } else {
            userIconPath = sessionManager.getUserInfo().getPersonImageUrl();
        }
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                //.withCompactStyle(true)//This activate the compact mode for header
                .withHeaderBackground(R.drawable.nav_drawer_banner)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(sessionManager.getUserInfo().getPersonName() + " " + sessionManager.getUserInfo().getPersonLastName())
                                .withEmail(sessionManager.getKeyPEmail())
                                .withIcon(userIconPath)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean current) {
                        drawerResult.closeDrawer();
                        launchActivity(getApplicationContext(), ProfileActivity.class);
                        return false;
                    }
                })
                .build();

        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withActionBarDrawerToggleAnimated(true)
                .withAccountHeader(headerResult)
                .addDrawerItems(
                        worldCup,
                        communications, aboutUs, share, rateApp,
                        account, profile, logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        cleanStack();
                        int id = (int) drawerItem.getIdentifier();
                        drawerResult.closeDrawer();
                        switch (id) {
                            default:
                                loadFragment(id);
                                break;
                            case R.string.nav_about_us:
                                launchActivity(getApplicationContext(), AboutUsActivity.class);
                                return true;
                            case R.string.nav_share:
                                shareAppIntent();
                                return true;
                            case R.string.nav_rate_app:
                                rateAppIntent();
                                return true;
                            case R.string.up_title:
                                launchActivity(getApplicationContext(), ProfileActivity.class);
                                return true;
                            case R.string.nav_logout:
                                startLogOutActivity();
                                return true;
                        }
                        return false;
                    }
                })
                .build();

    }

    private void loadFragment(int id) {
        currentFragment = id;
        if (!isFragmentVisible(id)) {
            switch (id) {
                case R.string.nav_worldcup:
                    fragment = LeagueFragment.newInstance(1);
                    break;
            }
            clearToolbarMenu();
            invalidateOptionsMenu();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.left_in, 0);
            ft.replace(R.id.content_layout, fragment, Integer.toString(id)).commit();
        }
    }

    public void clearToolbarMenu() {
        toolbar.getMenu().clear();
    }

    public void shareAppIntent() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.share_message) + "https://play.google.com/store/apps/details?id=" + getPackageName());
        startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_this_app)));
    }

    public void rateAppIntent() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        // Setting Dialog Message
        alertDialog.setTitle(getString(R.string.rate_title))
                .setMessage(getString(R.string.rate_message))
                .setCancelable(true)
                .setPositiveButton(getString(R.string.rate_btn_title), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException anfe) {
                        }
                    }
                });
        alertDialog.show();
    }

    //handle on back pressed for all fragments
    @Override
    public void onBackPressed() {
        if (isFragmentVisible(R.string.nav_worldcup)) {
            super.onBackPressed();
        } else {
            drawerResult.setSelection(R.string.nav_worldcup);
            drawerResult.closeDrawer();
        }
    }

    //to know if a fragment is currently being shown on the screen
    public boolean isFragmentVisible(int tag) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(Integer.toString(tag));
        if (f != null && f.isVisible()) return true;
        return false;
    }

    Fragment getCurrentFragment() {
        return getSupportFragmentManager()
                .findFragmentById(R.id.content_layout);
    }

    public void startLogOutActivity() {
        //App logout
        sessionManager = SessionManager.getInstance(getApplicationContext());
        sessionManager.logOut();
        //For Facebook LogOut
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            LoginManager.getInstance().logOut();
        }
        GoogleSingInWrapper googleSingInWrapper = GoogleSingInWrapper.getInstance(context);
        googleSingInWrapper.logout();

        FirebaseMessaging.getInstance().unsubscribeFromTopic("peru");
        FirebaseMessaging.getInstance().unsubscribeFromTopic("general");
        Intent loginIntent = new Intent().setClass(
                getApplicationContext(), LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private void launchActivity(Context origin, Class destiny) {
        Intent intent = new Intent().setClass(origin, destiny);
        startActivity(intent);
    }


}
