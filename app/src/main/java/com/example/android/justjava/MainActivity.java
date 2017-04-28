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

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity
{

    int quantity = 1;
    Toast Toast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view)
    {
        EditText nameEditText = (EditText) findViewById(R.id.name_editText);
        String name = nameEditText.getText().toString();

        CheckBox goodStuffCheckBox = (CheckBox) findViewById(R.id.good_stuff_checkbox);
        boolean hasGoodStuff = goodStuffCheckBox.isChecked();

        CheckBox badStuffCheckBox = (CheckBox) findViewById(R.id.bad_stuff_checkbox);
        boolean hasBadStuff = badStuffCheckBox.isChecked();

        int price = calculatePrice(hasGoodStuff, hasBadStuff);
        String priceMessage = createOrderSummary(name, price, hasGoodStuff, hasBadStuff);

        String addresses[] = {"faraday", "jekyll"};
        composeEmail(addresses, "Subject", priceMessage);
    }

    public void composeEmail(String[] addresses, String subject, String attachment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice (boolean addGoodStuff, boolean addBadStuff)
    {
        int basePrice = 5;

        if (addGoodStuff)
        {
            basePrice += 1;
        }

        if (addBadStuff)
        {
            basePrice += 2;
        }

        return  quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addGoodStuff, boolean addBadStuff)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd good stuff? " + addGoodStuff;
        priceMessage += "\nAdd bad stuff? " + addBadStuff;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + price;
        return priceMessage;
    }

    public void increment(View view)
    {
        if (quantity == 10)
        {
            showAToast("Slow down! This is too much!");
            return;
        }
        ++quantity;
        display(quantity);
    }

    public void decrement(View view)
    {
        if (quantity == 1)
        {
            showAToast("You cannot have less than 1");
            return;
        }
        --quantity;
        display(quantity);
    }

    public void showAToast (String message){
        if (Toast != null) {
            Toast.cancel();
        }
        Toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        Toast.show();
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number)
    {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

//    /**
//     * This method displays the given price on the screen.
//     */
//    private void displayPrice(int number)
//    {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
//    }

//    /**
//     * This method displays the given text on the screen.
//     */
//    private void displayMessage(String message)
//    {
//        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
//        priceTextView.setText(message);
//    }
}