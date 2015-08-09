package com.example.android.sunshine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    PlaceholderFragment p=new PlaceholderFragment();
    public static MainActivity generalObject = new MainActivity();
    static String UserList;
    static String currentUser;
    ArrayList<ListOfLists> ListNames = new ArrayList<ListOfLists>();
    ArrayList<String> testarray=new ArrayList<>();
    static String ownerName;
    static String task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Parse.initialize(getApplicationContext(), "N62UdspJn6aJiNRqJdocMVBj9V2rjM3MFMeEhiAL", "KVQn4BWZNEvy8ygdL9xvprnbX7pe1VafLZmpUjuc");
        generalObject.refreshListNamesList();
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        ParseUser CurrentUser=ParseUser.getCurrentUser();
        currentUser = CurrentUser.getUsername().toString();



        Toast.makeText(getApplicationContext(),currentUser,Toast.LENGTH_SHORT).show();
        String titleName= currentUser+"'s lists";
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(titleName);
        String id=CurrentUser.getObjectId().toString();
        UserList = currentUser+"_Lists";



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


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

    private void refreshListNamesList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(UserList);
        Log.d("aaaaaaa","adjkajskdaskd");
       // setSupportProgressBarIndeterminateVisibility(true);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                Log.d("dddd","eeeee");
                if (e == null) {
                    //setSupportProgressBarIndeterminateVisibility(false);
                    // If there are results, update the list of posts
                    // and notify the adapter
                    //ListNames.clear();
                    testarray.clear();
                    for (ParseObject post : postList) {
                       // ListOfLists lists = new ListOfLists(post.getObjectId(),post.getString("Name"),post.getInt("CheckValue"));
                        Log.d("testlog",post.getString("Name"));
                        //ListNames.add(lists);
                        testarray.add(post.getString("Name"));
                        Log.d("bbbbb", "stringsssss");
                    }
                    //((ArrayAdapter<ListOfLists>)
                     p.listAdapter.notifyDataSetChanged();
                } else {
                    Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                }
            }
        });
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, AdapterView.OnItemLongClickListener {


        public static ArrayAdapter<String> listAdapter;
        public PlaceholderFragment() {


        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
           // String[] ListArray = {"zz","xxx","ddd","ee","rrr","ww","asaas","fzvxv","adfafd","erweaw","afdad","nbvvnb"};

            //List <String> weekForecast = new ArrayList<String>(Arrays.asList(ListArray));

           listAdapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item_forecast, R.id.list_item_forecast_textview, generalObject.testarray);
            generalObject.refreshListNamesList();
            ListView listView = (ListView) rootView.findViewById(R.id.listview_forecast);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(this);
            ImageButton addButton = (ImageButton) rootView.findViewById(R.id.imageButton);
            addButton.setOnClickListener(this);

            return rootView;
        }



        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            TextView textView= (TextView) view.findViewById(R.id.list_item_forecast_textview);
            Log.d("Individual","button click");
            Intent intent=new Intent(getActivity(),IndividualList.class);
            task=textView.getText().toString();
            Log.d("listname",task);
            Log.d("Checked", "Individual List");

            ParseQuery<ParseObject> query = ParseQuery.getQuery(UserList);
            Toast.makeText(getActivity(),"task :"+task,Toast.LENGTH_SHORT).show();
            query.whereEqualTo("Name",task);
              //  query.whereEqualTo("Name",task);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> parseObjects, ParseException e) {
                    Toast.makeText(getActivity(),""+parseObjects.size(),Toast.LENGTH_SHORT).show();
                    ParseObject parseObject = parseObjects.get(0);
                    String owner=  parseObject.getString("Owner");
                    Toast.makeText(getActivity(),"owner :"+owner,Toast.LENGTH_SHORT).show();

                    Intent intent1 = new Intent(getActivity(),IndividualList.class);

                    //intent1.putExtra("owner",owner);
                  //  intent1.putExtra("name",task);
                    Bundle bundle = new Bundle();
                    bundle.putString("owner",owner);
                    bundle.putString("name",task);
                    intent1.putExtras(bundle);
                    getActivity().startActivity(intent1);

                }
            });
                Log.d(UserList,task);


                    }


        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.imageButton){
                Log.d("Lee","Button Clicked");
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add a List");
                builder.setMessage("Enter name of new list");
                final EditText inputField;
                inputField = new EditText(v.getContext());
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = inputField.getText().toString();
                        Log.d("MainActivity", task);
                       
                        if(task != null && !task.isEmpty () && !task.trim().equals("")) {
                            ParseObject testObject = new ParseObject(UserList);
                            testObject.put("Name", task);
                            testObject.put("Owner",currentUser);
                            testObject.saveInBackground();
                            //generalObject.refreshListNamesList();

                            Intent intent = new Intent(getActivity(), IndividualList.class);

                            Bundle extras = new Bundle();
                            extras.putString("name",task);
                            extras.putString("owner",currentUser);
                            intent.putExtras(extras);
                            startActivity(intent);
                            Log.d("list", "added");

                        }

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
                generalObject.refreshListNamesList();
                Log.d("main","dialogcomplete");

            }
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView= (TextView) view.findViewById(R.id.list_item_forecast_textview);
            final String listname = textView.getText().toString();
            Log.d("Individual","button click");
            AlertDialog.Builder builder;
            builder = new AlertDialog.Builder(view.getContext());
            builder.setTitle("Delete List");
            builder.setMessage("Delete "+listname+" ?");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ParseQuery<ParseObject> query = ParseQuery.getQuery(UserList);
                    query.whereEqualTo("Name", listname);
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> parseObjects, ParseException e) {
                            ParseObject parseObject = parseObjects.get(0);
                            final String owner = parseObject.getString("Owner");
                            //Toast.makeText(getActivity(),owner,Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getActivity(),currentUser,Toast.LENGTH_SHORT).show();
                            if (owner.equals(currentUser)) {
                                String collab = owner + "_" + listname + "_collaborators";
                                ParseQuery<ParseObject> getNames = ParseQuery.getQuery(collab);
                                getNames.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> userlist, ParseException e) {
                                        ParseObject deleteCollab = userlist.get(0);
                                        for (ParseObject deleteList : userlist) {
                                            String UserList = deleteList.getString("username") + "_Lists";
                                            Toast.makeText(getActivity(),UserList, Toast.LENGTH_SHORT).show();
                                            ParseQuery<ParseObject> deleteEntries = ParseQuery.getQuery(UserList);
                                            deleteEntries.whereEqualTo("Name", listname);
                                            deleteEntries.findInBackground(new FindCallback<ParseObject>() {
                                                @Override
                                                public void done(List<ParseObject> itemslist, ParseException e) {
                                                    for (ParseObject delete : itemslist)
                                                    {
                                                        if(delete.getString("Owner").equals(owner))
                                                        {
                                                            delete.deleteInBackground();
                                                        }
                                                    }
                                                }
                                            });

                                        }
                                        deleteCollab.deleteInBackground();
                                    }
                                });
                                parseObject.deleteInBackground();
                                String OwnerList = owner+"_"+listname;
                                ParseQuery<ParseObject> deleteItems = ParseQuery.getQuery(OwnerList);
                                deleteItems.findInBackground(new FindCallback<ParseObject>() {
                                    @Override
                                    public void done(List<ParseObject> toDelete, ParseException e) {
                                        for(ParseObject del : toDelete)
                                        {
                                            del.deleteInBackground();
                                        }
                                    }
                                });
                                generalObject.refreshListNamesList();
                                Toast.makeText(getActivity(), "Deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "You have no permission to delete this list", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", null);
            builder.create().show();
            return true;
    }

    }
    }



