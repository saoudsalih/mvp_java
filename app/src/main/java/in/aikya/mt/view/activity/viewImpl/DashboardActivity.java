package in.aikya.mt.view.activity.viewImpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import in.aikya.mt.R;
import in.aikya.mt.databinding.ActivityMainBinding;
import in.aikya.mt.prersenter.activity.presenter.DashboardActivityPresenter;
import in.aikya.mt.prersenter.activity.presenterImpl.DashboardActivityPresenterImpl;
import in.aikya.mt.view.activity.view.DashboardActivityView;
import in.aikya.mt.view.base.BaseActivity;

public class DashboardActivity extends BaseActivity implements DashboardActivityView {


    DashboardActivityPresenter presenter;
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        presenter = new DashboardActivityPresenterImpl(this);
        mainBinding.viewProfile.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Dashboard");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // R.menu.mymenu is a reference to an xml file named mymenu.xml which should be inside your res/menu directory.
        // If you don't have res/menu, just create a directory named "menu" inside res
        getMenuInflater().inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.view_profile){
            presenter.onClickViewProfile();
        }
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            showPopup();
        }
        return super.onOptionsItemSelected(item);
    }
    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DashboardActivity.this);
        alert.setMessage("Are you sure want to logout?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.logout();
                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }
    @Override
    public Context getContext() {
        return this;
    }
}
