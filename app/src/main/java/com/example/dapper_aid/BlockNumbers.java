package com.example.dapper_aid;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BlockedNumberContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class BlockNumbers extends AppCompatActivity {

    private EditText mEditTextNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_numbers);
        mEditTextNumber = (EditText) findViewById();


    }

    private Object findViewById() {
        return null;
    }

    public void onClickBlock(View view) {
        String number = mEditTextNumber.getText().toString();
        if (number.length() > 0) {
            blockNumber(number);
        }
    }

    public void onClickUnblock(View view) {
        String number = mEditTextNumber.getText().toString();
        if (number.length() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                unblockNumber(number);
            }
        }
    }

    public void onClickIsBlocked(View view) {
        String number = mEditTextNumber.getText().toString();
        if (number.length() > 0) {
            isBlocked(number);
        }
    }

    private void blockNumber(String number) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (BlockedNumberContract.canCurrentUserBlockNumbers(this)) {
                ContentValues values = new ContentValues();
                values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER, number);
                getContentResolver().insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void unblockNumber(String number) {
        if (BlockedNumberContract.canCurrentUserBlockNumbers(this)) {
            ContentValues values = new ContentValues();
            values.put(BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                    number);
            Uri uri = getContentResolver()
                    .insert(BlockedNumberContract.BlockedNumbers.CONTENT_URI, values);
            getContentResolver().delete(uri, null, null);
        }
    }

    public void isBlocked(String number) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (BlockedNumberContract.canCurrentUserBlockNumbers(this)) {
                boolean blocked = BlockedNumberContract.isBlocked(this, number);
                Toast.makeText(BlockNumbers.this, number + "blocked: " + blocked,
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BlockNumbers.this, "User cannot perform this operation",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    @SuppressWarnings("unused")
    private void getBlockedList() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Cursor cursor = getContentResolver().query(
                    BlockedNumberContract.BlockedNumbers.CONTENT_URI,
                    new String[]{BlockedNumberContract.BlockedNumbers.COLUMN_ID,
                            BlockedNumberContract.BlockedNumbers.COLUMN_ORIGINAL_NUMBER,
                            BlockedNumberContract.BlockedNumbers.COLUMN_E164_NUMBER},
                    null, null, null);

        }
    }
}