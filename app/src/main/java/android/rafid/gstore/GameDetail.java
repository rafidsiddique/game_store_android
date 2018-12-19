package android.rafid.gstore;

import android.rafid.gstore.Database.Database;
import android.rafid.gstore.Model.Games;
import android.rafid.gstore.Model.Order;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;



public class GameDetail extends AppCompatActivity {

    TextView game_Name,game_Price,game_Description;
    ImageView game_Image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;

    String gameId="";

    FirebaseDatabase database;
    DatabaseReference games;

    Games currentGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        database = FirebaseDatabase.getInstance();
        games = database.getReference("Games");

        numberButton = (ElegantNumberButton)findViewById(R.id.number_button);
        btnCart = (FloatingActionButton)findViewById(R.id.btnCart);

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database(getBaseContext()).addToCart(new Order(
                        gameId,
                        currentGame.getName(),
                        numberButton.getNumber(),
                        currentGame.getPrice(),
                        currentGame.getDiscount()
                ));
                Toast.makeText(GameDetail.this, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        });

        game_Description = (TextView)findViewById(R.id.game_description);
        game_Name = (TextView)findViewById(R.id.game_name);
        game_Price = (TextView)findViewById(R.id.game_price);
        game_Image = (ImageView) findViewById(R.id.game_image);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent() != null)
            gameId = getIntent().getStringExtra("GameId");
        if (!gameId.isEmpty())    {
            getDetailGame(gameId);
        }

    }

    private void getDetailGame(String gameId) {
        games.child(gameId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentGame =dataSnapshot.getValue(Games.class);


                Picasso.with(getBaseContext()).load(currentGame.getImage())
                        .into(game_Image);

                collapsingToolbarLayout.setTitle(currentGame.getName());

                game_Price.setText(currentGame.getPrice());

                game_Name.setText(currentGame.getName());

                game_Description.setText(currentGame.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
