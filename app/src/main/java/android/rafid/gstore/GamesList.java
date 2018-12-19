package android.rafid.gstore;

import android.content.Intent;
import android.rafid.gstore.Interface.ItemClickListener;
import android.rafid.gstore.Model.Games;
import android.rafid.gstore.ViewHolder.AllGamesViewHolder;
import android.rafid.gstore.ViewHolder.GamesViewHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class GamesList extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference gamesList;

    String categoryId = "";
    FirebaseRecyclerAdapter<Games, AllGamesViewHolder> adapter;

    //search fnc
    FirebaseRecyclerAdapter<Games, AllGamesViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    @Override protected void onStart()
    {
        materialSearchBar.enableSearch(); super.onStart();
    }


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_list);
        database = FirebaseDatabase.getInstance();
        gamesList = database.getReference("Games");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_games);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        if (getIntent() != null) {
            categoryId = getIntent().getStringExtra("CategoryId");
        }
        if (!categoryId.isEmpty() && categoryId != null) {
            loadListGames(categoryId);
        }

        //searching
        materialSearchBar = (MaterialSearchBar) findViewById(R.id.searchBar);
        materialSearchBar.setHint("Search for games....");
        loadSuggest();
        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                List<String> suggest = new ArrayList<String>();
                for(String search:suggestList)
                {
                    if(search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))
                        suggest.add(search);
                }
                materialSearchBar.setLastSuggestions(suggest);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //when the search bar is close restore to original adapter
                if(!enabled)
                    recyclerView.setAdapter(adapter);

            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                //when search finish show result of search adapter
                startSearch(text);

            }

            @Override
            public void onButtonClicked(int buttonCode) {

            }
        });



    }

    private void startSearch(CharSequence text) {
        searchAdapter = new FirebaseRecyclerAdapter<Games, AllGamesViewHolder>(
                Games.class,
                R.layout.allgames_item,
                AllGamesViewHolder.class,
                gamesList.orderByChild("Name").equalTo(text.toString())
        ) {
            @Override
            protected void populateViewHolder(AllGamesViewHolder viewHolder, Games model, int position) {
                viewHolder.allgames_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.allgames_image);

                final Games local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent gameDetail = new Intent(GamesList.this,GameDetail.class);
                        gameDetail.putExtra("GameId",searchAdapter.getRef(position).getKey());
                        startActivity(gameDetail);



                    }
                });

            }
        };
        recyclerView.setAdapter(searchAdapter);

    }

    private void loadSuggest() {
        gamesList.orderByChild("MenuId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                        {
                            Games item = postSnapshot.getValue(Games.class);
                            suggestList.add(item.getName());
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    private void loadListGames(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Games, AllGamesViewHolder>(Games.class, R.layout.allgames_item, AllGamesViewHolder.class, gamesList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(AllGamesViewHolder viewHolder, Games model, int position) {
                viewHolder.allgames_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.allgames_image);

                final Games local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent gameDetail = new Intent(GamesList.this,GameDetail.class);
                        gameDetail.putExtra("GameId",adapter.getRef(position).getKey());
                        startActivity(gameDetail);



                    }
                });
            }
        };
        Log.d("TAG",""+adapter.getItemCount());

        recyclerView.setAdapter(adapter);

    }
}


