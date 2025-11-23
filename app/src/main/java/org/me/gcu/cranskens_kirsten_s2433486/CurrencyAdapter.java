package org.me.gcu.cranskens_kirsten_s2433486;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CurrencyAdapter extends ArrayAdapter<CurrencyItem> {

    public CurrencyAdapter(Context context, ArrayList<CurrencyItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Reuse old row if possible
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.currency_row, parent, false);
        }

        // Get current item
        CurrencyItem item = getItem(position);

        TextView titleView = convertView.findViewById(R.id.rowTitleText);
        TextView rateView = convertView.findViewById(R.id.rowRateText);
        View strengthBar = convertView.findViewById(R.id.strengthBar);

        // Make sure default background is light so text is readable
        int rowBg = getContext().getResources().getColor(R.color.White, null);
        convertView.setBackgroundColor(rowBg);

        if (item != null) {
            titleView.setText(item.getTitle());
            rateView.setText(String.valueOf(item.getRate()));

            float rate = item.getRate();

            // Choose colour for the strength bar only
            int barColor;

            if (rate < 1.0f) {
                // Very strong GBP
                barColor = getContext().getResources().getColor(R.color.MediumSeaGreen, null);
            } else if (rate < 5.0f) {
                // Strong-ish
                barColor = getContext().getResources().getColor(R.color.PaleGreen, null);
            } else if (rate < 10.0f) {
                // Medium
                barColor = getContext().getResources().getColor(R.color.LightGoldenrodYellow, null);
            } else {
                // GBP weak vs this currency
                barColor = getContext().getResources().getColor(R.color.FireBrick, null);
            }

            strengthBar.setBackgroundColor(barColor);
        }

        return convertView;
    }

}
