package com.example.android.sunshine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class IndividualList extends ActionBarActivity  {

    public static IndividualList genobject = new IndividualList();
    static String ListName ;
    static String collab;
    static String titleName;
    static String ownerName;

    ArrayList<String> names = new ArrayList<String>();
    ArrayList<String> testarray= new ArrayList<String>();
    public static PlaceholderFragment p = new PlaceholderFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_list);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
        //Parse.initialize(getApplicationContext(), "N62UdspJn6aJiNRqJdocMVBj9V2rjM3MFMeEhiAL", "KVQn4BWZNEvy8ygdL9xvprnbX7pe1VafLZmpUjuc");
        Log.d("IndividualList", "beginning");
        Bundle extras = getIntent().getExtras();
        ParseUser CurrentUser=ParseUser.getCurrentUser();
        String currentUser=CurrentUser.getUsername().toString();
            titleName = extras.getString("name");
            ownerName = extras.getString("owner");
            Log.d("Owner", ownerName);
            collab = ownerName+"_"+titleName+"_collaborators";
            ListName = ownerName+"_"+titleName;

        refreshListItemsList();
        deleteItems();
        getSupportActionBar().setTitle(titleName);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_individual, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.collab_list) {
            Dialog dialog = new Dialog(IndividualList.this);
            dialog.setContentView(R.layout.collaboratorlist);
            ListView lv = (ListView ) dialog.findViewById(R.id.lv);
            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
            lv.setAdapter(adapter);
            ParseQuery<ParseObject> query = ParseQuery.getQuery(collab);

            // setSupportProgressBarIndeterminateVisibility(true);
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> postList, ParseException e) {
                    Log.d("item","parsing");
                    if (e == null) {
                        //setSupportProgressBarIndeterminateVisibility(false);
                        // If there are results, update the list of posts
                        // and notify the adapter
                        //ListNames.clear();
                        names.clear();
                        for (ParseObject post : postList) {
                            //ListOfLists lists = new ListOfLists(post.getObjectId(),post.getString("Name"));
                            Log.d("item",post.getString("username"));
                            //ListNames.add(lists);
                            String Item = post.getString("username");
                            names.add(Item);
                            Log.d("collaborator", "updated");
                        }
                        //((ArrayAdapter<ListOfLists>)
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d(getClass().getSimpleName(), "Error: " + e.getMessage());
                    }
                }
            });
            dialog.setCancelable(true);
            dialog.setTitle("Shared Users");
            dialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

       private void deleteItems(){
           ParseQuery<ParseObject> query = ParseQuery.getQuery(ListName);
           query.findInBackground(new FindCallback<ParseObject>() {

               @Override
               public void done(List<ParseObject> postList, ParseException e) {
                   for(ParseObject modify : postList)
                   {
                       if(modify.getInt("CheckValue")==1) {

                           modify.deleteInBackground();

                       }

                   }
               }
           });

       }

    private void refreshListItemsList() {

        ParseQuery<ParseObject> query = ParseQuery.getQuery(ListName);
        Log.d("list","refresh function called");
        // setSupportProgressBarIndeterminateVisibility(true);
        query.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> postList, ParseException e) {
                Log.d("item","parsing");
                if (e == null) {
                    //setSupportProgressBarIndeterminateVisibility(false);
                    // If there are results, update the list of posts
                    // and notify the adapter
                    //ListNames.clear();
                    testarray.clear();
                    for (ParseObject post : postList) {
                        //ListOfLists lists = new ListOfLists(post.getObjectId(),post.getString("Name"));
                        Log.d("item",post.getString("Name"));
                        //ListNames.add(lists);
                        String Item = post.getString("Name");
                        testarray.add(Item);
                        Log.d("item", "added");
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{


        public static ArrayAdapter<String> listAdapter;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_individual_list, container, false);
            genobject.deleteItems();
            listAdapter = new ArrayAdapter<>(getActivity(), R.layout.individual_list_item_forecast, R.id.individual_list_item_forecast_textview, genobject.testarray);
            ImageButton addItemButton = (ImageButton) rootView.findViewById(R.id.addItem);
            addItemButton.setOnClickListener(this);
            ImageButton addUser = (ImageButton) rootView.findViewById(R.id.addUser);
            addUser.setOnClickListener(this);
            ListView listView = (ListView) rootView.findViewById(R.id.individual_listview_forecast);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(this);
            genobject.deleteItems();
            genobject.refreshListItemsList();
            //  public static final String listName="List 1";
            //getActivity().getActionBar().setTitle(listName);
            return rootView;
        }

           @Override
        public void onClick(View v) {
            if (v.getId() == R.id.addItem) {
                Log.d("Lee", "Add list item Button Clicked");
                //Log.d("Item","Button Clicked");
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add a List");
                builder.setMessage("Enter task");
                final EditText inputField;
                inputField = new EditText(v.getContext());
                builder.setView(inputField);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String task = inputField.getText().toString();
                        Log.d("itemlist", task);
                        ParseObject testObject = new ParseObject(genobject.ListName);
                        if(task != null && !task.isEmpty () && !task.trim().equals("")) {
                            testObject.put("Name", task);
                            testObject.put("CheckValue",0);
                            // ParseObject Object2 = new ParseObject(task);
                            //Object2.put("Name", "");
                            testObject.saveInBackground();
                            genobject.refreshListItemsList();
                            //Object2.saveInBackground();

                            Log.d("list", "added");
                        }

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
                genobject.refreshListItemsList();
                Log.d("individual", "dialogcomplete");
            }
            else if(v.getId()==R.id.addUser)
            {
                Log.d("Lee", "Add User Button Clicked");
                AlertDialog.Builder builder;
                builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add a Collaborator");
                builder.setMessage("Enter name of collaborator");
                final EditText inputField;
                inputField = new EditText(v.getContext());
                builder.setView(inputField);
                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String sUser = inputField.getText().toString();
                        Log.d("userlist", sUser);
                        final String sUserList=sUser+"_Lists";
                        final ParseObject testObject = new ParseObject(genobject.collab);
                        final ParseObject sharing = new ParseObject(sUserList);
                        if(sUser!= null && !sUser.isEmpty () && !sUser.trim().equals(""))
                        {
                            ParseQuery<ParseObject> query = ParseQuery.getQuery(collab);
                            query.whereEqualTo("username",sUser);
                            query.findInBackground(new FindCallback<ParseObject>()
                                                   {
                                                       @Override
                                                       public void done(List<ParseObject> parseObjects, ParseException e)
                                                       {
                                                           testObject.put("username",sUser);
                                                           testObject.saveInBackground();
                                                           sharing.put("Name",titleName);
                                                           sharing.put("Owner",ownerName);
                                                           sharing.saveInBackground();
                                                       }
                                                   }
                            );
                                    genobject.refreshListItemsList();
                            Log.d("user", "added");
                        }

                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
                genobject.refreshListItemsList();
                Log.d("useradd", "dialogcomplete");
            }


        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final TextView textView= (TextView) view.findViewById(R.id.individual_list_item_forecast_textview);
            //Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_SHORT).show();
            ParseQuery<ParseObject> query = ParseQuery.getQuery(ListName);
            query.whereEqualTo("Name", textView.getText());
            query.findInBackground(new FindCallback<ParseObject>() {

                @Override
                public void done(List<ParseObject> postList, ParseException e) {
                for(ParseObject modify : postList)
                {
                       if(modify.getInt("CheckValue")==0) {
                           modify.put("CheckValue", 1);
                           modify.saveInBackground();

                           textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                       }
                        else if(modify.getInt("CheckValue")==1){
                           modify.put("CheckValue", 0);
                           modify.saveInBackground();
                           textView.setPaintFlags(textView.getPaintFlags()& (~ Paint.STRIKE_THRU_TEXT_FLAG));
                       }
                }
                }
            });
           // textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
          genobject.refreshListItemsList();
        }
    }
}
