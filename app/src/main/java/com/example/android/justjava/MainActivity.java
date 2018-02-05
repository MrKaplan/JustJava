package com.example.android.justjava;



import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Parameter;
import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */


public class MainActivity extends AppCompatActivity {

    int quantity = 1;
    int ct = 1;
    int totalBeverages = 12;
    //TypedArray imgs = getResources().obtainTypedArray(R.array.beverageImageArray);

    String bevs[] = new String[totalBeverages];
    String beverageNames[] = new String[totalBeverages];
    String beverageDescriptions[] = new String[totalBeverages];
    String beverageShortDescriptions[] = new String[totalBeverages];
    String beverageImages[] = new String[totalBeverages];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayQuantity(quantity);

        Resources res = getResources();
        bevs = res.getStringArray(R.array.beverageImageArray);
        beverageNames = res.getStringArray(R.array.beverageNameArray);
        beverageDescriptions = res.getStringArray(R.array.beverageDescriptionArray);
        beverageShortDescriptions = res.getStringArray(R.array.beverageShortDescriptionArray);
        beverageImages = res.getStringArray(R.array.beverageImageArray);
    }

    /**
     * This method is called to UPDATE BEVERAGE IMAGE
     */
    public void updateBeverage() {
        ((TextView) findViewById(R.id.textView_beverageName)).setText((beverageNames[ct-1]));
        ((TextView) findViewById(R.id.textView_beverageShortDescription)).setText((beverageShortDescriptions[ct-1]));
        ((TextView) findViewById(R.id.textView_beverageDescription)).setText((beverageDescriptions[ct-1]));

        /** 2 NEXT LINES TAKEN FROM: https://stackoverflow.com/questions/6783327/setimageresource-from-a-string */
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("@drawable/"+beverageImages[ct-1], null, c.getPackageName());

        /**  SETS THE CORRECT DRAWABLE TO THE BEVERAGE IMAGE VIEW */
        ImageView imageViewBeverage = (ImageView) findViewById(R.id.imageViewBeverageImage);
        imageViewBeverage.setImageResource(id);


        //middleImageView.setImageResource(getResources().getDrawable(R.drawable.bevs[ct-1]);

//        getResources().getDrawable() WAS DEPRECATED in api 22 so
//       you should now be using ContextCompat.getDrawable e.g.
//                Drawable d = ContextCompat.getDrawable(context, R.drawable.smiley);

        //android:src="@drawable/flatwhite"
        //priceTextView.setText(message);

        // HELP ON USING DRAWABLES WITH TYPED ARRAYS
        //...

//        In the value folder create xml file name it arrays.xml add the data to it in this way
//
//                <integer-array name="your_array_name">
//    <item>@drawable/1</item>
//    <item>@drawable/2</item>
//    <item>@drawable/3</item>
//    <item>@drawable/4</item>
//</integer-array>
//                Then obtain it to your code this way
//
//        private TypedArray img;
//        img = getResources().obtainTypedArray(R.array.your_array_name);
//        Then to use a Drawable of these in the img TypedArray for example as an ImageView background use the following code
//
//        ImageView.setBackgroundResource(img.getResourceId(index, defaultValue));
//        where index is the Drawable index. defaultValue is a value you give if there is no item at this index
//
//        For more information about TypedArray visit this link http://developer.android.com/reference/android/content/res/TypedArray.html

    }


    /**
     * This method is called when the PREVIOUS BUTTON is clicked.
     */
    public void previousBeverage(View view) {
        ct = ct - 1;
        if (ct == 0) {
            ct = totalBeverages;
        }
        displayMessage(bevs[ct - 1]);
        updateBeverage();
    }


    /**
     * This method is called when the NEXT BUTTON is clicked.
     */
    public void nextBeverage(View view) {
        ct = ct + 1;
        if (ct == totalBeverages) {
            ct = 1;
        }
        displayMessage(bevs[ct-1]);
        updateBeverage();
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
            quantity = quantity + 1;
            if (!findViewById(R.id.button_minus).isEnabled()) {
                findViewById(R.id.button_minus).setEnabled(true);
            }
            displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        }
        if (quantity == 1) {
            findViewById(R.id.button_minus).setEnabled(false);
        }
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.textViewPriceValue);
        priceTextView.setText(message);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants choclate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        displayMessage(message);


    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int basePrice = 5;

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,
                NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }
}
/*

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        quantity = quantity + 1 ;
        display(quantity);
    }
    public void decrement(View view) {
        quantity = quantity - 1;
        display(quantity);
    }
    *//**
     * This method is called when the order button is clicked.
     *//*
    public void submitOrder(View view) {
        int price = quantity * 5;
        String priceMessage = "Total: " + "€" + price + "\nThank you!";
        displayMessage(priceMessage);
    }


    *//**
     * This method displays the given quantity value on the screen.
     *//*
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    *//**
     * This method displays the given price on the screen.
     *//*
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.textView_priceValue);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    *//**
     * This method displays the given text on the screen.
     *//*
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.textView_priceValue);
        priceTextView.setText(message);
    }

}*/

