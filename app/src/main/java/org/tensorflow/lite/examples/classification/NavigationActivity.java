package org.tensorflow.lite.examples.classification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class NavigationActivity extends AppCompatActivity {
    String serviceTag = "";
    Button verify1;
    Button verify2;
    Button verify3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        verify1 = findViewById(R.id.cardView1verify);
        verify2 = findViewById(R.id.cardView2verify);
        verify3 = findViewById(R.id.cardView3verify);
    }


    public void startBarcodeActivity(View view) {
        Intent intent = new Intent(this, BarcodeActivity.class);
        intent.putExtra("status", false);
        intent.putExtra("serviceTag", serviceTag);
        startActivityForResult(intent, 1);
    }

    public void startOCRActivity(View view) {
        Intent intent = new Intent(this, OCRActivity.class);
        intent.putExtra("status", false);
        intent.putExtra("serviceTag", serviceTag);
        startActivityForResult(intent, 2);
    }

    public void startTFActivity(View view) {

        Intent intent = new Intent(this, ClassifierActivity.class);
        intent.putExtra("status", false);
        startActivityForResult(intent, 3);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) return;
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 1 && resultCode == 1) {
            serviceTag = data.getStringExtra("serviceTag");
            setCard1Success();
        }

        if (requestCode == 2 && resultCode == 2) {

            setCard2Success();
        }

        if (requestCode == 3 && resultCode == 3) {
            setCard3Success();
        }


    }

    private void setCard1Success() {
        Button button = findViewById(R.id.cardView1verify);
        String message = "VERIFIED";
        button.setText(message);
        button.setBackgroundColor(getResources().getColor(R.color.green));

        CardView cardView = findViewById(R.id.cardView1);
        cardView.setCardBackgroundColor(getColor(R.color.colorPrimary));
    }

    private void setCard2Success() {
        Button button = findViewById(R.id.cardView2verify);
        String message = "VERIFIED";
        button.setText(message);
        button.setBackgroundColor(getResources().getColor(R.color.green));
        CardView cardView = findViewById(R.id.cardView2);
        cardView.setCardBackgroundColor(getColor(R.color.colorPrimary));
    }

    public void setCard3Success() {
        Button button = findViewById(R.id.cardView3verify);
        String message = "VERIFIED";
        button.setText(message);
        button.setBackgroundColor(getResources().getColor(R.color.green));
        CardView cardView = findViewById(R.id.cardView3);
        cardView.setCardBackgroundColor(getColor(R.color.colorPrimary));
    }

    public void validateAllCards(View view) {
        if (verify1.getText().equals("VERIFIED") && verify2.getText().equals("VERIFIED") && verify3.getText().equals("VERIFIED")){
            Intent intent = new Intent(this, LastActivity.class);
            startActivity(intent);
        }
    }

    public void overrideCard1(View view) {
        if(!verify1.getText().equals("VERIFIED")){
            setCard1Success();
        }
    }

    public void overrideCard2(View view) {
        if(!verify2.getText().equals("VERIFIED")){
            setCard2Success();
        }
    }

    public void overrideCard3(View view) {
        if(!verify3.getText().equals("VERIFIED")){
            setCard3Success();
        }
    }
}
