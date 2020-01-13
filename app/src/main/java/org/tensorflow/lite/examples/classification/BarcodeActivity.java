package org.tensorflow.lite.examples.classification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.List;

public class BarcodeActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {


    private static final int CAMERA_PIC_REQUEST = 1337;
    private Context context;
    public static boolean proceed = false;
    private  String serviceTag;

    private DecoratedBarcodeView decoratedBarcodeView;

    private boolean isFlashOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode);

        context = this;

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        serviceTag = getIntent().getStringExtra("serviceTag");

        setupBarcodeView();

        Button button = findViewById(R.id.scan_bar_code);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                toggleBarcodeScan(false);
                toggleBarcodeScan(true);
            }
        });

        ImageView ivFlash = findViewById(R.id.iv_flash);
        ivFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlash(!isFlashOn);
            }
        });
    }

    private void setupBarcodeView() {

        decoratedBarcodeView = findViewById(R.id.barcode_view);
        decoratedBarcodeView.setStatusText("");
        decoratedBarcodeView.setTorchListener(BarcodeActivity.this);
    }

    private void toggleFlash(Boolean enableFlash) {

        if (enableFlash) {
            decoratedBarcodeView.setTorchOn();
        } else {
            decoratedBarcodeView.setTorchOff();
        }

    }

    private void toggleBarcodeScan(Boolean startScan) {

        if (startScan) {
            decoratedBarcodeView.resume();
            decoratedBarcodeView.decodeSingle(getBarcodeCallback());
        } else {
            decoratedBarcodeView.pause();
        }

    }


    private BarcodeCallback getBarcodeCallback() {

        return new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                Toast.makeText(context, result.getText(), Toast.LENGTH_SHORT).show();
                serviceTag = result.getText();
                toggleBarcodeScan(false);
                proceed = true;
            }

            @Override
            public void possibleResultPoints(List<ResultPoint> resultPoints) {

            }
        };

    }


    public void onClickValidate(View view) {
        if (proceed) {
            Intent intent = new Intent();
            intent.putExtra("status", true);
            intent.putExtra("serviceTag", serviceTag);
            setResult(1, intent);
            finish();
        } else {
            Toast.makeText(context, "Please Scan Barcode before proceeding", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onTorchOn() {
        isFlashOn = true;
    }

    @Override
    public void onTorchOff() {
        isFlashOn = false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
