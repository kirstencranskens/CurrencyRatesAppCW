/*  Starter project for Mobile Platform Development - 1st diet 25/26
    You should use this project as the starting point for your assignment.
    This project simply reads the data from the required URL and displays the
    raw data in a TextField
*/

//
// Name                 Kirsten Cranskens
// Student ID           s2433486
// Programme of Study   Computing
//
package org.me.gcu.cranskens_kirsten_s2433486;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    private TextView rawDataDisplay;
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="https://www.fx-exchange.com/gbp/rss.xml";
    private ArrayList<CurrencyItem> currencyList = new ArrayList<CurrencyItem>();
    private ListView currencyListView;
    private CurrencyItem selectedItem = null;
    private TextView mainHeaderText;
    private Button usdButton;
    private Button eurButton;
    private Button jpyButton;
    private Button audButton;
    private TextView searchLabelText;
    private EditText searchInput;
    private Button searchButton;
    private boolean dataLoaded = false;
    private boolean forceShowAllOnNextUpdate = false;

    private static final String KEY_XML_RESULT = "xmlResult";
    private static final String KEY_DATA_LOADED = "dataLoaded";
    private static final String KEY_SEARCH_TEXT = "searchText";
    private static final String CACHE_FILE_NAME = "cached_rates.xml";

    private android.os.Handler autoUpdateHandler = new android.os.Handler();
    private Runnable autoUpdateTask;
    private static final long REFRESH_INTERVAL = 5 * 60 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1) Find ALL views first
        rawDataDisplay = (TextView)findViewById(R.id.rawDataDisplay);
        startButton = (Button)findViewById(R.id.startButton);
        currencyListView = (ListView)findViewById(R.id.currencyListView);
        mainHeaderText = (TextView)findViewById(R.id.mainHeaderText);
        usdButton = (Button)findViewById(R.id.usdButton);
        eurButton = (Button)findViewById(R.id.eurButton);
        jpyButton = (Button)findViewById(R.id.jpyButton);
        audButton = (Button)findViewById(R.id.audButton);

        searchLabelText = (TextView)findViewById(R.id.searchLabelText);
        searchInput = (EditText)findViewById(R.id.searchInput);
        searchButton = (Button)findViewById(R.id.searchButton);

        // 2) Set listeners
        startButton.setOnClickListener(this);

        usdButton.setOnClickListener(this);
        eurButton.setOnClickListener(this);
        jpyButton.setOnClickListener(this);
        audButton.setOnClickListener(this);

        searchButton.setOnClickListener(this);

        // 3) Initial visibility (hide main buttons + search at startup)
        mainHeaderText.setVisibility(View.GONE);
        usdButton.setVisibility(View.GONE);
        eurButton.setVisibility(View.GONE);
        jpyButton.setVisibility(View.GONE);
        audButton.setVisibility(View.GONE);

        searchLabelText.setVisibility(View.GONE);
        searchInput.setVisibility(View.GONE);
        searchButton.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            dataLoaded = savedInstanceState.getBoolean(KEY_DATA_LOADED, false);
            String savedResult = savedInstanceState.getString(KEY_XML_RESULT, "");

            if (dataLoaded && savedResult != null && !savedResult.equals("")) {
                result = savedResult;
                parseAndDisplayResult();
            }

            String savedSearch = savedInstanceState.getString(KEY_SEARCH_TEXT, "");
            if (savedSearch != null && !savedSearch.equals("")) {
                searchInput.setText(savedSearch);
            }
        }

        if (savedInstanceState == null) {
            startProgress();
        }

        autoUpdateTask = new Runnable() {
            @Override
            public void run() {
                startProgress();

                autoUpdateHandler.postDelayed(this, REFRESH_INTERVAL);
            }
        };
    }

    private void updateUI() {
        Log.d("UI thread", "Running UI update method");

        // 1) Show item count
        rawDataDisplay.setText("Number of items: " + currencyList.size());

        // 2) Extract USD/EUR/JPY/AUD rates
        float usdRate = 0f, eurRate = 0f, jpyRate = 0f, audRate = 0f;

        for (CurrencyItem item : currencyList) {
            String title = item.getTitle();
            if (title == null) continue;

            if (title.contains("(USD)")) usdRate = item.getRate();
            if (title.contains("(EUR)")) eurRate = item.getRate();
            if (title.contains("(JPY)")) jpyRate = item.getRate();
            if (title.contains("(AUD)")) audRate = item.getRate();
        }

        // 3) Update buttons
        usdButton.setText("USD: " + usdRate);
        eurButton.setText("EUR: " + eurRate);
        jpyButton.setText("JPY: " + jpyRate);
        audButton.setText("AUD: " + audRate);

        // 4) Show controls
        mainHeaderText.setVisibility(View.VISIBLE);
        usdButton.setVisibility(View.VISIBLE);
        eurButton.setVisibility(View.VISIBLE);
        jpyButton.setVisibility(View.VISIBLE);
        audButton.setVisibility(View.VISIBLE);

        searchLabelText.setVisibility(View.VISIBLE);
        searchInput.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);

        // 5) Respect current query OR force full list after manual refresh
        ArrayList<CurrencyItem> toShow;
        if (forceShowAllOnNextUpdate) {
            toShow = new ArrayList<>(currencyList);    // show everything once
            forceShowAllOnNextUpdate = false;
            if (searchInput != null) searchInput.setText(""); // clear the search box
            android.widget.Toast.makeText(this, "Rates refreshed", android.widget.Toast.LENGTH_SHORT).show();
        } else {
            String currentQuery = (searchInput.getText() == null) ? "" : searchInput.getText().toString();
            toShow = getFilteredList(currentQuery);
        }

        CurrencyAdapter adapter = new CurrencyAdapter(MainActivity.this, toShow);
        currencyListView.setAdapter(adapter);


        // 6) Keep click behaviour
        currencyListView.setOnItemClickListener(
                new android.widget.AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(android.widget.AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {

                        CurrencyItem item = (CurrencyItem) parent.getItemAtPosition(position);

                        String message = "Selected: " + item.getTitle()
                                + " | rate = " + item.getRate();
                        android.widget.Toast.makeText(
                                MainActivity.this,
                                message,
                                android.widget.Toast.LENGTH_SHORT).show();

                        android.content.Intent intent =
                                new android.content.Intent(MainActivity.this, CurrencyDetailActivity.class);

                        intent.putExtra("title", item.getTitle());
                        intent.putExtra("pubDate", item.getPubDate());
                        intent.putExtra("rate", item.getRate());

                        startActivity(intent);
                    }
                }
        );

        dataLoaded = true;
    }





    @Override
    public void onClick(View v)
    {
        if (v == startButton) {
            startProgress();
        }
        else if (v == usdButton) {
            openMainCurrency("(USD)");
        }
        else if (v == eurButton) {
            openMainCurrency("(EUR)");
        }
        else if (v == jpyButton) {
            openMainCurrency("(JPY)");
        }
        else if (v == audButton) {
            openMainCurrency("(AUD)");
        }
        else if (v == searchButton) {
            performSearch();
        }

        if (v == startButton) {
            // optional: hide keyboard if search box had focus
            android.view.inputmethod.InputMethodManager imm =
                    (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (imm != null && searchInput != null) {
                imm.hideSoftInputFromWindow(searchInput.getWindowToken(), 0);
            }

            // tell UI to show full list after this fetch
            forceShowAllOnNextUpdate = true;

            startProgress();
        }


    }


    public void startProgress()
    {
        // Run network access on a separate thread;
        new Thread(new Task(urlSource)).start();
    } //

    // Need separate thread to access the internet resource over network
    // Other neater solutions should be adopted in later iterations.
    private class Task implements Runnable
    {
        private String url;
        public Task(String aurl){
            url = aurl;
        }
        @Override
        public void run() {
            result = "";

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";

            Log.d("MyTask", "in run");

            boolean loadedFromNetwork = false;

            try {
                Log.d("MyTask", "in try");
                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
                while ((inputLine = in.readLine()) != null) {
                    result = result + inputLine;
                }
                in.close();

                loadedFromNetwork = true;
                Log.d("MyTask", "Finished network load, length=" + result.length());
            }
            catch (IOException ae) {
                Log.e("MyTask", "IOException during download, will try cache instead", ae);
            }

            // 🔹 If network failed, try to load from cache
            if (!loadedFromNetwork) {
                String cached = loadResultFromFile();
                if (cached != null && !cached.equals("")) {
                    result = cached;
                    Log.d("MyTask", "Using cached XML from file");
                    // Optional: tell user we're offline but using old data
                    currencyListView.post(() ->
                            android.widget.Toast.makeText(
                                    MainActivity.this,
                                    "No internet. Showing last saved rates.",
                                    android.widget.Toast.LENGTH_SHORT
                            ).show()
                    );
                } else {
                    Log.e("MyTask", "No cached data available, cannot update list");
                    currencyListView.post(() ->
                            android.widget.Toast.makeText(
                                    MainActivity.this,
                                    "No internet and no saved rates available.",
                                    android.widget.Toast.LENGTH_LONG
                            ).show()
                    );
                    return;
                }
            } else {
                // 🔹 Network was OK – save fresh XML to cache
                saveResultToFile(result);
            }

            // 🔹 Clean up leading/trailing garbage safely
            int start = result.indexOf("<?");  // initial XML tag
            if (start != -1) {
                result = result.substring(start);
            }

            int end = result.indexOf("</rss>"); // final tag
            if (end != -1) {
                result = result.substring(0, end + 6);
            }

            // Parse and update UI
            parseAndDisplayResult();
        }


    }

    private void parseAndDisplayResult() {
        if (result == null || result.equals("")) {
            return;
        }

        // Avoid duplicates if we call this again after rotation
        currencyList.clear();

        // --- PARSING (same as before) ---
        try {
            XmlPullParserFactory factory =
                    XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(result));

            int eventType = xpp.getEventType();
            String tagName = "";
            CurrencyItem currentItem = null;
            boolean insideItem = false;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equalsIgnoreCase("item")) {
                        insideItem = true;
                        currentItem = new CurrencyItem();
                    }

                } else if (eventType == XmlPullParser.TEXT) {
                    String text = xpp.getText();

                    if (insideItem && currentItem != null) {
                        if (tagName.equalsIgnoreCase("title")) {
                            currentItem.setTitle(text);
                        } else if (tagName.equalsIgnoreCase("link")) {
                            currentItem.setLink(text);
                        } else if (tagName.equalsIgnoreCase("pubDate")) {
                            currentItem.setPubDate(text);
                        } else if (tagName.equalsIgnoreCase("description")) {
                            currentItem.setDescription(text);
                            float rate = extractRateFromDescription(text);
                            currentItem.setRate(rate);
                        }
                    }

                } else if (eventType == XmlPullParser.END_TAG) {
                    tagName = xpp.getName();

                    if (tagName.equalsIgnoreCase("item")) {
                        if (currentItem != null) {
                            currencyList.add(currentItem);
                            Log.d("Parsing", "Added item: " + currentItem.toString());
                        }
                        insideItem = false;
                        currentItem = null;
                    }
                }

                eventType = xpp.next();
            }

        } catch (XmlPullParserException e) {
            Log.e("Parsing","EXCEPTION " + e);
        } catch (IOException e) {
            Log.e("Parsing","I/O EXCEPTION " + e);
        }

        // 🔹 Safely request UI update on the main thread
        currencyListView.post(this::updateUI);

    }


    private float extractRateFromDescription(String desc) {
        // Example: "1 British Pound Sterling = 4.9471 United Arab Emirates Dirham"
        float rateValue = 0.0f;

        if (desc == null) {
            return rateValue;
        }

        int equalsPos = desc.indexOf("=");
        if (equalsPos != -1 && equalsPos + 1 < desc.length()) {
            String rightSide = desc.substring(equalsPos + 1).trim();
            // rightSide: "4.9471 United Arab Emirates Dirham"

            String[] parts = rightSide.split(" ");
            if (parts.length > 0) {
                try {
                    rateValue = Float.parseFloat(parts[0]);
                } catch (NumberFormatException e) {
                    Log.e("Parsing", "Error parsing rate from: " + desc);
                }
            }
        }

        return rateValue;
    }

    // 🔹 Save the latest XML result to internal storage
    private void saveResultToFile(String xml) {
        if (xml == null || xml.equals("")) {
            return;
        }

        try {
            java.io.FileOutputStream fos = openFileOutput(CACHE_FILE_NAME, MODE_PRIVATE);
            java.io.OutputStreamWriter osw = new java.io.OutputStreamWriter(fos);
            java.io.BufferedWriter bw = new java.io.BufferedWriter(osw);

            bw.write(xml);
            bw.flush();
            bw.close();
            fos.close();

            Log.d("Storage", "Saved XML cache to " + CACHE_FILE_NAME);
        } catch (IOException e) {
            Log.e("Storage", "Error saving cache: " + e);
        }
    }

    // 🔹 Load cached XML from internal storage (or return null if not available)
    private String loadResultFromFile() {
        StringBuilder builder = new StringBuilder();

        try {
            java.io.FileInputStream fis = openFileInput(CACHE_FILE_NAME);
            java.io.InputStreamReader isr = new java.io.InputStreamReader(fis);
            java.io.BufferedReader br = new java.io.BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            br.close();
            isr.close();
            fis.close();

            Log.d("Storage", "Loaded XML cache from " + CACHE_FILE_NAME);
            return builder.toString();
        } catch (IOException e) {
            Log.e("Storage", "No cached file available: " + e);
            return null;
        }
    }


    private void openMainCurrency(String code) {
        // If data not loaded yet, show a Toast
        if (currencyList.size() == 0) {
            android.widget.Toast.makeText(
                    this,
                    "Please wait for rates to load or tap 'Refresh rates'.",
                    android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        CurrencyItem found = null;

        // Find the first item whose title contains the currency code, e.g. (USD)
        for (CurrencyItem item : currencyList) {
            String title = item.getTitle();
            if (title != null && title.contains(code)) {
                found = item;
                break;
            }
        }

        if (found == null) {
            android.widget.Toast.makeText(
                    this,
                    "Currency " + code + " not found in list.",
                    android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        // This is the SAME Intent logic as  ListView click listener
        android.content.Intent intent =
                new android.content.Intent(MainActivity.this, CurrencyDetailActivity.class);

        intent.putExtra("title", found.getTitle());
        intent.putExtra("pubDate", found.getPubDate());
        intent.putExtra("rate", found.getRate());

        startActivity(intent);
    }

    private void performSearch() {
        if (currencyList.size() == 0) {
            android.widget.Toast.makeText(this,
                    "Please press 'Press to get data' first.",
                    android.widget.Toast.LENGTH_SHORT).show();
            return;
        }

        String query = searchInput.getText().toString().trim();
        if (query.isEmpty()) {
            // show full list
            CurrencyAdapter adapter = new CurrencyAdapter(MainActivity.this, currencyList);
            currencyListView.setAdapter(adapter);
            setSafeItemClickListener();
            return;
        }

        String lowerQuery = query.toLowerCase();
        ArrayList<CurrencyItem> filteredList = new ArrayList<>();

        for (CurrencyItem item : currencyList) {
            String title = item.getTitle();
            String titleLower = title == null ? "" : title.toLowerCase();

            // currency code from title, e.g. "CNY"
            String code = FlagUtils.extractCurrencyCodeFromTitle(title);
            String codeLower = code == null ? "" : code.toLowerCase();

            // country name via Locale, e.g. "China"
            String country = FlagUtils.getCountryNameForCurrency(this, code);
            String countryLower = country == null ? "" : country.toLowerCase();

            if (titleLower.contains(lowerQuery) ||
                    codeLower.contains(lowerQuery)  ||
                    countryLower.contains(lowerQuery)) {
                filteredList.add(item);
            }
        }

        if (filteredList.isEmpty()) {
            android.widget.Toast.makeText(this,
                    "No results for: " + query,
                    android.widget.Toast.LENGTH_SHORT).show();
        }

        CurrencyAdapter adapter = new CurrencyAdapter(MainActivity.this, filteredList);
        currencyListView.setAdapter(adapter);
        // IMPORTANT: click should use parent.getItemAtPosition
        setSafeItemClickListener();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean(KEY_DATA_LOADED, dataLoaded);
        outState.putString(KEY_XML_RESULT, result);

        if (searchInput != null) {
            outState.putString(KEY_SEARCH_TEXT, searchInput.getText().toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoUpdateHandler.postDelayed(autoUpdateTask, REFRESH_INTERVAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        autoUpdateHandler.removeCallbacks(autoUpdateTask);
    }

    private void setSafeItemClickListener() {
        currencyListView.setOnItemClickListener(
                (parent, view, position, id) -> {
                    CurrencyItem item = (CurrencyItem) parent.getItemAtPosition(position);
                    if (item == null) return;

                    android.content.Intent intent =
                            new android.content.Intent(MainActivity.this, CurrencyDetailActivity.class);
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("pubDate", item.getPubDate());
                    intent.putExtra("rate", item.getRate());
                    startActivity(intent);
                }
        );
    }

    private ArrayList<CurrencyItem> getFilteredList(String query) {
        String q = (query == null) ? "" : query.trim().toLowerCase();
        if (q.isEmpty()) return new ArrayList<>(currencyList);

        ArrayList<CurrencyItem> filtered = new ArrayList<>();
        for (CurrencyItem item : currencyList) {
            String title = (item.getTitle() == null) ? "" : item.getTitle();
            String titleLower = title.toLowerCase();

            String code = FlagUtils.extractCurrencyCodeFromTitle(title);
            String codeLower = (code == null) ? "" : code.toLowerCase();

            String country = FlagUtils.getCountryNameForCurrency(this, code);
            String countryLower = (country == null) ? "" : country.toLowerCase();

            if (titleLower.contains(q) || codeLower.contains(q) || countryLower.contains(q)) {
                filtered.add(item);
            }
        }
        return filtered;
    }

}