package io.github.tsangela.prioritizedto_dolist;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.graphics.Color;

public class MainActivity extends AppCompatActivity {
    private int priorityLevel;
    private boolean cardVisible;
    private CardView warningCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        priorityLevel = 1;
        cardVisible = false;
        warningCard = findViewById(R.id.warning_card);

    }

    public void changePriority(View view) {
        // Retrieve priority level
        Button button = findViewById(R.id.priority_lvl_button);
        String level = button.getText().toString();

        switch (level) {
            case "1":
                button.setText("2");
                priorityLevel = 2;
                break;
            case "2":
                button.setText("3");
                priorityLevel = 3;
                break;
            case "3":
                button.setText("1");
                priorityLevel = 1;
                break;
            default:
                break;
        }
    }

    public void priorityFormat(CheckBox listItem) {
        switch (priorityLevel) {
            case 2:
                listItem.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 3:
                listItem.setTypeface(Typeface.DEFAULT_BOLD);
                listItem.setBackgroundColor(Color.CYAN);
                break;
            default:
                break;
        }
    }

    public void add(View view) {
        // Retrieve input
        EditText input = findViewById(R.id.text_input);
        String text = input.getText().toString().trim();

        if (text.isEmpty() && !cardVisible) {
            input.setText("");
            warningCard.setVisibility(View.VISIBLE);
            cardVisible = true;

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    warningCard.setVisibility(View.GONE);
                }
            }, 1500);

            cardVisible = false;
        } else {
            // New to-do item
            final CheckBox listItem = new CheckBox(this);
            listItem.setText(text);
            listItem.setBackgroundColor(Color.WHITE);
            listItem.setTextColor(Color.BLACK);

            // Format based on priority level
            priorityFormat(listItem);

            listItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        listItem.setBackgroundColor(Color.LTGRAY);
                        listItem.setTypeface(Typeface.DEFAULT);
                        listItem.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                        listItem.clearComposingText();
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listItem.setVisibility(View.GONE);
                            }
                        }, 500);
                    }
                }
            });

            // Add to to-do list
            LinearLayout list = findViewById(R.id.list);
            list.addView(listItem);

            // Clear input text box
            input.setText("");
        }

    }
}
