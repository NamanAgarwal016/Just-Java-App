package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        } else {
            quantity = quantity - 1;
            display(quantity);
        }
    }

    public void submitOrder(View view) {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whipped_cream_chechbox);
        boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate_chechbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText name = (EditText) findViewById(R.id.type_name);
        String getName = name.getText().toString();

        int basePrice = calculateBasePrice(hasWhippedCream, hasChocolate);

        String orderSummary = createOrderSummary(basePrice, hasWhippedCream, hasChocolate, getName);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for: " + getName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    public String createOrderSummary(int basePrice, boolean isWhippedCream, boolean isChocolate, String getName) {
        String message = "Name: " + getName;
        message += "\nAdd whipped cream? " + isWhippedCream;
        message += "\nAdd chocolate? " + isChocolate;

        message = message + "\nQuantity: " + quantity + "\nTotal: $" + (quantity * basePrice) + "\n\nHave A Good Coffee Day!!!";
        return message;
    }

    public int calculateBasePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int price = 5;
        if (hasWhippedCream) price = price + 1;
        if (hasChocolate) price = price + 2;

        return price;
    }
}