package com.sawapps.baymaxhealthcare;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sawapps.baymaxhealthcare.Adapters.ItemRecyclerViewAdapter;
import com.sawapps.baymaxhealthcare.Network.Remote.ApiUtils;
import com.sawapps.baymaxhealthcare.Network.Remote.GetDietResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDietActivity extends AppCompatActivity {

    RecyclerView mealsRv;
    EditText calories;
    View submit;
//    TextView t1, t2, t3, t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_diet);
        setTitle("My Diet");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        t1 = (TextView) findViewById(R.id.t1);
//
//        t2 = (TextView) findViewById(R.id.t2);
//
//        t3 = (TextView) findViewById(R.id.t3);
//
//        t4 = (TextView) findViewById(R.id.t4);
        calories = (EditText) findViewById(R.id.calories);
        submit = findViewById(R.id.submit);
        mealsRv = (RecyclerView) findViewById(R.id.mealsRv);
        mealsRv.setLayoutManager(new LinearLayoutManager(this));
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utils.hideKeyboard(MyDietActivity.this);

                if (calories.getText().toString().trim().length() != 0)
                    getDiet(calories.getText().toString());


            }
        });

    }

    private void getDiet(String calories) {

        String query = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/mealplans/generate?timeFrame=week&targetCalories=" + calories;

        ApiUtils
                .getService().getMeal(query, "1Dnz7LuzBVmshia88a9IKqmf7n82p1v8KSGjsnP7R2gBashGJA", "spoonacular-recipe-food-nutrition-v1.p.mashape.com")
                .enqueue(new Callback<GetDietResponse>() {
                    @Override
                    public void onResponse(Call<GetDietResponse> call, Response<GetDietResponse> response) {
                        try {

                            if (response.body().items != null) {
                                mealsRv.setAdapter(new ItemRecyclerViewAdapter(response.body().items));

                            }
//                            if (response.body().nutrients != null) {
//
////                                t1.setText("Calories:\t" + response.body().nutrients.calories);
////
////                                t2.setText("Protien:\t" + response.body().nutrients.protein);
////
////                                t3.setText("Fat:\t" + response.body().nutrients.fat);
////
////                                t4.setText("Carbohydrates:\t" + response.body().nutrients.carbohydrates);
//                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetDietResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });

    }

}
