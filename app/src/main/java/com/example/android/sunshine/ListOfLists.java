package com.example.android.sunshine;

/**
 * Created by Ashwin on 3/10/2015.
 */
public class ListOfLists {
    private String id;
    private String name;
    private int checkValue;


    ListOfLists(String listId, String listName,int cVal) {
         id = listId;
        name = listName;
        checkValue=cVal;

    }

    public String getId() {
      return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String lname) {
        this.name = lname;
    }
    public void setCheckValue(int i){this.checkValue=i;}
    public int getCheckValue() {
        return checkValue;
    }

}
