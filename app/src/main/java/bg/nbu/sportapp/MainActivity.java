package bg.nbu.sportapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import bg.nbu.sportapp.adapters.SportsAdapter;
import bg.nbu.sportapp.models.League;
import bg.nbu.sportapp.models.Sport;
import bg.nbu.sportapp.services.SportsApi;
import bg.nbu.sportapp.services.SportsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressDialog progressDialog;

    private ExpandableListView mainExpandableList;
    private ListView mainList;

    private MainActivity context;

    private List<Sport> sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        mainExpandableList = findViewById(R.id.main_expandable_list);
        mainList = findViewById(R.id.main_list);

        mainExpandableList.setVisibility(View.GONE);
        mainList.setVisibility(View.GONE);

        context = this;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading. Please wait...");
        progressDialog.setCancelable(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Leagues list is opened
        if (sports != null && mainList.getVisibility() == View.VISIBLE) {
            setSportsList();
        } else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_sports) {
            setSportsList();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setLeaguesList(String sport) {
        // Manage list views
        mainExpandableList.setVisibility(View.GONE);
        mainList.setVisibility(View.VISIBLE);

        SportsService.GetSportsService().getLeagues(sport).enqueue(new Callback<SportsApi.LeaguesResponse>() {
            @Override
            public void onResponse(Call<SportsApi.LeaguesResponse> call, Response<SportsApi.LeaguesResponse> response) {
                if (response.body() != null) {
                    List<League> leaguesList = response.body().leagues;
                    mainList.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, leaguesList));
                }
            }

            @Override
            public void onFailure(Call<SportsApi.LeaguesResponse> call, Throwable t) {

            }
        });
    }

    private void setSportsList() {
        toggleProgressDialog();

        // Manage list views
        mainExpandableList.setVisibility(View.VISIBLE);
        mainList.setVisibility(View.GONE);

        SportsService.GetSportsService().getSports().enqueue(new Callback<SportsApi.SportsResponse>() {
            @Override
            public void onResponse(Call<SportsApi.SportsResponse> call, Response<SportsApi.SportsResponse> response) {
                if (response.body() != null) {
                    List<Sport> sportList = response.body().sports;
                    context.sports = sportList;
                    mainExpandableList.setAdapter(new SportsAdapter(context, sportList));

                    toggleProgressDialog();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SportsApi.SportsResponse> call, Throwable t) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();

                toggleProgressDialog();
            }
        });
    }

    public void toggleProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.cancel();
        } else {
            progressDialog.show();
        }
    }

}
