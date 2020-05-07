package com.example.a1725121013_lichuanjian_lesson09;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.Adapter dataItemAdapter;
    RecyclerView.LayoutManager layoutManager;
    List<DataItem> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setSupportActionBar(toolbar);
        recyclerView=findViewById(R.id.rv);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initDadtaItems();
        dataItemAdapter =new DataItemAdapter(list);
        recyclerView.setAdapter(dataItemAdapter);

        ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START| ItemTouchHelper.END) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition=viewHolder.getAdapterPosition();
                int toPosition=target.getAdapterPosition();
                if(fromPosition<toPosition){
                    for(int i=fromPosition;i<toPosition;i++){
                        Collections.swap(list,i,i+1);
                    }
                }else {
                    for(int i =fromPosition;i>toPosition;i--){
                        Collections.swap(list,i,i-1);
                    }
                }
                dataItemAdapter.notifyItemMoved(fromPosition,toPosition);
                return  true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                list.remove(viewHolder.getAdapterPosition());
                dataItemAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }
    private void initDadtaItems(){
        String[] name_list=getResources().getStringArray(R.array.name_list);
        int nameSize=name_list.length;
        for(String s :name_list){
            list.add(new DataItem(s,false));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        final MenuItem searchItem=menu.findItem(R.id.serch_action);
        SearchView searchView=(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                for(DataItem item :list){
                    if(item.getName().contains(query)){
                        layoutManager.scrollToPosition(list.indexOf(item));
                        return false;

                    }
                }
                Toast.makeText(getApplicationContext(), "没有匹配的城市!", Toast.LENGTH_LONG).show();

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.add_action){
            final EditText et = new EditText(this);
            new AlertDialog.Builder(this).setTitle("新建城市")
                    .setIcon(R.drawable.ic_add_black_24dp)
                    .setView(et)
                    .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog,int which){
                            String string=et.getText().toString();
                            DataItem item=new DataItem(string,false);
                            list.add(item);
                            dataItemAdapter.notifyItemInserted(list.size()-1);
                            layoutManager.scrollToPosition(list.indexOf(item));
                        }
                    })
            .setNegativeButton("取消",null)
            .show();

        }
        return  true;
    }
}
