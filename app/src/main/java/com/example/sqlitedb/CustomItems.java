package com.example.sqlitedb;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;

import java.util.List;

public class CustomItems extends BaseAdapter
{
    private List<Security> list;

    CustomItems(List<Security> list)
    {
        //constructor
      this.list = list;
    }

    @Override
    public int getCount()
    {
        //how many items in the list
        return list.size();
    }

    @Override
    public Object getItem(int position)
    {
        //indexing of list items
        return list.get(position);
    }

    @Override
    public long getItemId(int id)
    {
        return id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customlist,null);

       EditText rollCL = v.findViewById(R.id.rollCL);
       EditText nameCL  = v.findViewById(R.id.nameCL);

       Security security = list.get(i);  //get will return you one object at a time & i is the position.

       rollCL.setText(String.valueOf(security.getRoll()));
       nameCL.setText(security.getName());

        return v;
    }
}
