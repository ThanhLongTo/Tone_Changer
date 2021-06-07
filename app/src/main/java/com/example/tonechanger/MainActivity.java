package com.example.tonechanger;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText originalLyric;
    Button btnDone;
    Button btnTextReplaceDone;
    Button toneUp;
    Button toneDown;
    Button clearAll;
    Button checkedLyricCard2;
    Button checkedLyricCard3;
    Button checkedLyricCard4;
    TextView formattedLyric;

    int tone = 0;
    int searchPos = 0;

    RelativeLayout expandableCard1;
    Button btnDownCard1;
    RelativeLayout expandableCard2;
    Button btnDownCard2;
    RelativeLayout expandableCard3;
    Button btnDownCard3;
    RelativeLayout expandableCard4;
    Button btnDownCard4;
    RelativeLayout expandableCard5;
    Button btnDownCard5;
    RelativeLayout mainView;

    Button switchChord;
    Button replaceText;
    EditText textBeforeReplace;
    TextView textAfterReplace;

    Button btnFindChordByName;
    EditText findChordByName;
    TextView findChordByURL;
    TextView scrapedLyric;
    TextView scrapedLyricURL;

    Button btnFindChordByNameURL;
    EditText findChordByNameURL;

    Button btnCopyCard2;
    Button btnCopyCard3;
    Button btnCopyCard4;
    Button btnCopyCard5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        originalLyric = findViewById(R.id.mainLyric);

        toneUp = findViewById(R.id.btnUp);
        toneDown = findViewById(R.id.btnDown);
        clearAll = findViewById(R.id.btnClear);
        formattedLyric = findViewById(R.id.mainText);

        btnCopyCard2 = findViewById(R.id.btnCopyCard2);
        btnCopyCard3 = findViewById(R.id.btnCopyCard3);
        btnCopyCard4 = findViewById(R.id.btnCopyCard4);
        btnCopyCard5 = findViewById(R.id.btnCopyCard5);

        btnCopyCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("CopyText", originalLyric.getText().toString());
                clipboard.setPrimaryClip(clip);
                clip.getDescription();
                Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        btnCopyCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("CopyText", originalLyric.getText().toString());
                clipboard.setPrimaryClip(clip);
                clip.getDescription();
                Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        btnCopyCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("CopyText", originalLyric.getText().toString());
                clipboard.setPrimaryClip(clip);
                clip.getDescription();
                Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });

        btnCopyCard5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("CopyText", textBeforeReplace.getText().toString());
                clipboard.setPrimaryClip(clip);
                clip.getDescription();
                Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
            }
        });


        toneUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formattedLyric.setText(Html.fromHtml(toneChangerUp(originalLyric.getText().toString()).replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
                tone++;
                Toast.makeText(MainActivity.this, "Tone " + tone, Toast.LENGTH_SHORT).show();
                originalLyric.setText(toneChangerUp(originalLyric.getText().toString()));
            }
        });

        toneDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formattedLyric.setText(Html.fromHtml(toneChangerDown(originalLyric.getText().toString()).replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
                tone--;
                Toast.makeText(MainActivity.this, "Tone " + tone, Toast.LENGTH_SHORT).show();
                originalLyric.setText(toneChangerDown(originalLyric.getText().toString()));
            }
        });

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalLyric.setText("");
                formattedLyric.setText("");
                tone = 0;
            }
        });

        checkedLyricCard2 = findViewById(R.id.checkedLyricCard2);
        checkedLyricCard3 = findViewById(R.id.checkedLyricCard3);
        checkedLyricCard4 = findViewById(R.id.checkedLyricCard4);

        checkedLyricCard2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formattedLyric.setText(Html.fromHtml(originalLyric.getText().toString().replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
            }
        });

        checkedLyricCard3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formattedLyric.setText(Html.fromHtml(scrapedLyric.getText().toString().replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
            }
        });

        checkedLyricCard4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                formattedLyric.setText(Html.fromHtml(scrapedLyricURL.getText().toString().replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
            }
        });


        btnDone = findViewById(R.id.btnDone);
        btnTextReplaceDone = findViewById(R.id.btnTextReplaceDone);

        expandableCard1 = findViewById(R.id.expandableCard1);
        btnDownCard1 = findViewById(R.id.btnDownCard1);
        expandableCard2 = findViewById(R.id.expandableCard2);
        btnDownCard2 = findViewById(R.id.btnDownCard2);
        expandableCard3 = findViewById(R.id.expandableCard3);
        btnDownCard3 = findViewById(R.id.btnDownCard3);
        expandableCard4 = findViewById(R.id.expandableCard4);
        btnDownCard4 = findViewById(R.id.btnDownCard4);
        expandableCard5 = findViewById(R.id.expandableCard5);
        btnDownCard5 = findViewById(R.id.btnDownCard5);

        mainView = findViewById(R.id.mainView);

        btnDownCard1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard1.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard1.setVisibility(View.VISIBLE);
                    btnDownCard1.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard1.setVisibility(View.GONE);
                    btnDownCard1.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard1.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard1.setVisibility(View.VISIBLE);
                    btnDownCard1.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard1.setVisibility(View.GONE);
                    btnDownCard1.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnDownCard2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard2.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard2.setVisibility(View.VISIBLE);
                    btnDownCard2.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard2.setVisibility(View.GONE);
                    btnDownCard2.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnDownCard3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard3.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard3.setVisibility(View.VISIBLE);
                    btnDownCard3.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard3.setVisibility(View.GONE);
                    btnDownCard3.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnDownCard4.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard4.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard4.setVisibility(View.VISIBLE);
                    btnDownCard4.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard4.setVisibility(View.GONE);
                    btnDownCard4.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnDownCard5.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard5.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard5.setVisibility(View.VISIBLE);
                    btnDownCard5.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard5.setVisibility(View.GONE);
                    btnDownCard5.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });

        btnTextReplaceDone.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (expandableCard5.getVisibility()==View.GONE) {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard5.setVisibility(View.VISIBLE);
                    btnDownCard5.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_up_24);
                } else {
                    TransitionManager.beginDelayedTransition(mainView, new AutoTransition());
                    expandableCard5.setVisibility(View.GONE);
                    btnDownCard5.setBackgroundResource(R.drawable.ic_baseline_arrow_drop_down_24);
                }
            }
        });



        switchChord = findViewById(R.id.switchChord);
        switchChord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFeedbackDialog();
            }
        });

        textBeforeReplace = findViewById(R.id.textBeforeReplace);
        textAfterReplace = findViewById(R.id.textAfterReplace);

        replaceText = findViewById(R.id.replaceText);
        replaceText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReplaceTextFeedbackDialog();
            }
        });

        findChordByName = findViewById(R.id.findChordByName);
        btnFindChordByName = findViewById(R.id.btnFindChordByName);
        findChordByNameURL = findViewById(R.id.findChordByNameURL);
        btnFindChordByNameURL = findViewById(R.id.btnFindChordByNameURL);
        findChordByURL = findViewById(R.id.findChordByURL);
        scrapedLyric = findViewById(R.id.scrapedLyric);
        scrapedLyricURL = findViewById(R.id.scrapedLyricURL);


        btnFindChordByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = findChordByName.getText().toString().replace(" ", "%20");
                List<String> result = lyricScraper(input,searchPos);
                findChordByURL.setText(result.get(1));
                originalLyric.setText(Html.fromHtml(result.get(0).replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
                scrapedLyric.setText(Html.fromHtml(result.get(0).replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
                Toast.makeText(MainActivity.this, "Successfully scraped lyric", Toast.LENGTH_SHORT).show();
            }
        });

        btnFindChordByNameURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result = lyricScraperURL(findChordByNameURL.getText().toString());
                originalLyric.setText(Html.fromHtml(result.replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
                scrapedLyricURL.setText(Html.fromHtml(result.replace("\n", "<br>").replace("[", "<b><u>[").replace("]","]</u></b> ")));
                Toast.makeText(MainActivity.this, "Successfully scraped lyric", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public static List<String> lyricScraper(String query, int position){
        List<String> linkList = new ArrayList<>();
        List<String> lyricList = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<String> artist = new ArrayList<>();
        List<String> finalList = new ArrayList<>();
        StringBuilder finalLyric = new StringBuilder();

        try {
            System.setProperty("http.proxyhost", "127.0.0.1");
            System.setProperty("http.proxyhost", "3128");
            Document doc = Jsoup.connect("https://hopamchuan.com/search?q=" + query).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36\n").get();


            Elements link = doc.select("div.song-title-singers");
            Elements titleLink = link.select("a.song-title");
            Elements artistLink = link.select("a.author-item");

            for (Element i : titleLink) {
                title.add(i.select("a").text());
            }
            for (Element i : artistLink) {
                artist.add(i.select("a").text());
            }

            for (Element i : link) {
                Elements songTitle = i.select("a[href]");
                for (Element g : songTitle) {
                    System.out.println(g.attr("href"));
                    linkList.add(g.attr("href"));
                }
            }

            try {
                Document doc2 = Jsoup.connect(linkList.get(position)).get();

                Elements link2 = doc2.select("div.pre");
                for (Element lyric : link2) {
                    Elements a = lyric.select("div.chord_lyric_line");
                    for (Element detailedLyric : a) {
                        lyricList.add(detailedLyric.text());
                    }
                }

                for (String value : lyricList) {
                    finalLyric.append(value).append("\n");
                }

                finalList.add(String.format("<h2>%s - %s</h2><br>%s", title.get(position), artist.get(position), finalLyric.toString()));
                finalList.add(linkList.get(position));
            } catch (Exception ex) {
                finalList.add("<h2 style:\"color:red\">No result</h2>");
                finalList.add("...");
            }

        } catch (IOException e) {
                e.printStackTrace();
        }
        return finalList;

    }

    public static String lyricScraperURL(String query){
        List<String> lyricList = new ArrayList<>();
        String title = "";
        String artist = "";
        StringBuilder finalLyric = new StringBuilder();
        try {
            System.setProperty("http.proxyhost", "127.0.0.1");
            System.setProperty("http.proxyhost", "3128");
                Document doc2 = Jsoup.connect(query).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36\n").get();

                Elements link2 = doc2.select("div.pre");
                for (Element lyric : link2) {
                    Elements a = lyric.select("div.chord_lyric_line");
                    for (Element detailedLyric : a) {
                        lyricList.add(detailedLyric.text());
                    }
                }

                for (String value : lyricList) {
                    finalLyric.append(value).append("\n");
                }

                Elements titleAttr = doc2.select("h1");
                title = titleAttr.text();
                Elements artistAttr = doc2.select("span.perform-singer-list");
                artist = artistAttr.text();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            title = "Empty";
            artist = "Empty";
            finalLyric = new StringBuilder(ex.toString());
        }
        return String.format("<h2>%s - %s</h2><br>%s", title, artist, finalLyric.toString());
    }

    public void openFeedbackDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog, (RelativeLayout) findViewById(R.id.dialogContainer));

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        final EditText chordBeforeReplace = view.findViewById(R.id.chordBeforeReplace);
        final EditText chordAfterReplace = view.findViewById(R.id.chordAfterReplace);
        final TextView chordNotFound = view.findViewById(R.id.chordNotFound);

        view.findViewById(R.id.submitChord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chordBefore = chordBeforeReplace.getText().toString();
                String chordAfter = chordAfterReplace.getText().toString();
                String notFound = chordBefore + " is not in lyric";

                if (formattedLyric.getText().toString().contains(chordBefore)) {
                    originalLyric.setText(formattedLyric.getText().toString().replace(chordBefore, chordAfter));
                    Toast.makeText(MainActivity.this, "Chord replaced: " + chordBefore + " > " + chordAfter, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    chordNotFound.setText(notFound);
                    chordBeforeReplace.setText("");
                    chordAfterReplace.setText("");
                }
            }
        });
        if (alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }

    public void openReplaceTextFeedbackDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogTheme);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_dialog, (RelativeLayout) findViewById(R.id.dialogContainer));

        builder.setView(view);

        final AlertDialog alertDialog = builder.create();

        final EditText chordBeforeReplace = view.findViewById(R.id.chordBeforeReplace);
        final EditText chordAfterReplace = view.findViewById(R.id.chordAfterReplace);
        final TextView chordNotFound = view.findViewById(R.id.chordNotFound);

        view.findViewById(R.id.submitChord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chordBefore = chordBeforeReplace.getText().toString();
                String chordAfter = chordAfterReplace.getText().toString();
                String notFound = chordBefore + " is not in lyric";

                if (textBeforeReplace.getText().toString().contains(chordBefore)) {
                    textAfterReplace.setText(textBeforeReplace.getText().toString().replace(chordBefore, chordAfter));
                    Toast.makeText(MainActivity.this, "Text replaced: " + chordBefore + " > " + chordAfter, Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                } else {
                    chordNotFound.setText(notFound);
                    chordBeforeReplace.setText("");
                    chordAfterReplace.setText("");
                }
            }
        });
        if (alertDialog.getWindow()!=null) {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        alertDialog.show();
    }


    public static String toneChangerUp(String lyric) {
        String str = lyric;
        Map<String, String> chordMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("[C]","[C#]");
        map.put("[C#]","[D]");
        map.put("[D]","[D#]");
        map.put("[D#]","[E]");
        map.put("[E]","[F]");
        map.put("[F]","[F#]");
        map.put("[F#]","[G]");
        map.put("[G]","[G#]");
        map.put("[G#]","[A]");
        map.put("[A]","[A#]");
        map.put("[A#]","[B]");
        map.put("[B]","[C]");

        map.put("[Cm]","[C#m]");
        map.put("[C#m]","[Dm]");
        map.put("[Dm]","[D#m]");
        map.put("[D#m]","[Em]");
        map.put("[Em]","[Fm]");
        map.put("[Fm]","[F#m]");
        map.put("[F#m]","[Gm]");
        map.put("[Gm]","[G#m]");
        map.put("[G#m]","[Am]");
        map.put("[Am]","[A#m]");
        map.put("[A#m]","[Bm]");
        map.put("[Bm]","[Cm]");

        map.put("[C7]","[C#7]");
        map.put("[C#7]","[D7]");
        map.put("[D7]","[D#7]");
        map.put("[D#7]","[E7]");
        map.put("[E7]","[F7]");
        map.put("[F7]","[F#7]");
        map.put("[F#7]","[G7]");
        map.put("[G7]","[G#7]");
        map.put("[G#7]","[A7]");
        map.put("[A7]","[A#7]");
        map.put("[A#7]","[B7]");
        map.put("[B7]","[C7]");

        map.put("[Cm7]","[C#m7]");
        map.put("[C#m7]","[Dm7]");
        map.put("[Dm7]","[D#m7]");
        map.put("[D#m7]","[Em7]");
        map.put("[Em7]","[Fm7]");
        map.put("[Fm7]","[F#m7]");
        map.put("[F#m7]","[Gm7]");
        map.put("[Gm7]","[G#m7]");
        map.put("[G#m7]","[Am7]");
        map.put("[Am7]","[A#m7]");
        map.put("[A#m7]","[Bm7]");
        map.put("[Bm7]","[Cm7]");

        map.put("[Cmaj7]","[C#maj7]");
        map.put("[C#maj7]","[Dmaj7]");
        map.put("[Dmaj7]","[D#maj7]");
        map.put("[D#maj7]","[Emaj7]");
        map.put("[Emaj7]","[Fmaj7]");
        map.put("[Fmaj7]","[F#maj7]");
        map.put("[F#maj7]","[Gmaj7]");
        map.put("[Gmaj7]","[G#maj7]");
        map.put("[G#maj7]","[Amaj7]");
        map.put("[Amaj7]","[A#maj7]");
        map.put("[A#maj7]","[Bmaj7]");
        map.put("[Bmaj7]","[Cmaj7]");

        map.put("[Cm7b5]","[C#m7b5]");
        map.put("[C#m7b5]","[Dm7b5]");
        map.put("[Dm7b5]","[D#m7b5]");
        map.put("[D#m7b5]","[Em7b5]");
        map.put("[Em7b5]","[Fm7b5]");
        map.put("[Fm7b5]","[F#m7b5]");
        map.put("[F#m7b5]","[Gm7b5]");
        map.put("[Gm7b5]","[G#m7b5]");
        map.put("[G#m7b5]","[Am7b5]");
        map.put("[Am7b5]","[A#m7b5]");
        map.put("[A#m7b5]","[Bm7b5]");
        map.put("[Bm7b5]","[Cm7b5]");

        map.put("[C7b5]","[C#7b5]");
        map.put("[C#7b5]","[D7b5]");
        map.put("[D7b5]","[D#7b5]");
        map.put("[D#7b5]","[E7b5]");
        map.put("[E7b5]","[F7b5]");
        map.put("[F7b5]","[F#7b5]");
        map.put("[F#7b5]","[G7b5]");
        map.put("[G7b5]","[G#7b5]");
        map.put("[G#7b5]","[A7b5]");
        map.put("[A7b5]","[A#7b5]");
        map.put("[A#7b5]","[B7b5]");
        map.put("[B7b5]","[C7b5]");


        List<String> myCheckedList = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String originalKey = entry.getKey();
            if (lyric.contains(originalKey)) {
                if (!myCheckedList.contains(originalKey)){
                    myCheckedList.add(originalKey);
                }
            }
        }

        for (int i = 0; i < myCheckedList.size(); i++) {
            str = str.replace(myCheckedList.get(i), String.format("[chord %s]", i));
            chordMap.put(String.format("[chord %s]", i), myCheckedList.get(i));
        }

        for (Map.Entry<String, String> entry : chordMap.entrySet()) {
            String originalKey = entry.getKey();
            String originalValue = entry.getValue();
            chordMap.put(originalKey, map.get(originalValue));
            str = str.replace(originalKey, map.get(originalValue));
        }


        return str;
    }

    public static String toneChangerDown(String lyric) {
        String str = lyric;
        Map<String, String> map = new HashMap<>();
        Map<String, String> chordMap = new HashMap<>();

        map.put("[C]", "[B]");
        map.put("[B]", "[A#]");
        map.put("[A#]", "[A]");
        map.put("[A]", "[G#]");
        map.put("[G#]", "[G]");
        map.put("[G]", "[F#]");
        map.put("[F#]", "[F]");
        map.put("[F]", "[E]");
        map.put("[E]", "[D#]");
        map.put("[D#]", "[D]");
        map.put("[D]", "[C#]");
        map.put("[C#]", "[C]");

        map.put("[Cm]", "[Bm]");
        map.put("[Bm]", "[A#m]");
        map.put("[A#m]", "[Am]");
        map.put("[Am]", "[G#m]");
        map.put("[G#m]", "[Gm]");
        map.put("[Gm]", "[F#m]");
        map.put("[F#m]", "[Fm]");
        map.put("[Fm]", "[Em]");
        map.put("[Em]", "[D#m]");
        map.put("[D#m]", "[Dm]");
        map.put("[Dm]", "[C#m]");
        map.put("[C#m]", "[Cm]");

        map.put("[C7]", "[B7]");
        map.put("[B7]", "[A#7]");
        map.put("[A#7]", "[A7]");
        map.put("[A7]", "[G#7]");
        map.put("[G#7]", "[G7]");
        map.put("[G7]", "[F#7]");
        map.put("[F#7]", "[F7]");
        map.put("[F7]", "[E7]");
        map.put("[E7]", "[D#7]");
        map.put("[D#7]", "[D7]");
        map.put("[D7]", "[C#7]");
        map.put("[C#7]", "[C7]");

        map.put("[Cm7]", "[Bm7]");
        map.put("[Bm7]", "[A#m7]");
        map.put("[A#m7]", "[Am7]");
        map.put("[Am7]", "[G#m7]");
        map.put("[G#m7]", "[Gm7]");
        map.put("[Gm7]", "[F#m7]");
        map.put("[F#m7]", "[Fm7]");
        map.put("[Fm7]", "[Em7]");
        map.put("[Em7]", "[D#m7]");
        map.put("[D#m7]", "[Dm7]");
        map.put("[Dm7]", "[C#m7]");
        map.put("[C#m7]", "[Cm7]");

        map.put("[Cmaj7]", "[Bmaj7]");
        map.put("[Bmaj7]", "[A#maj7]");
        map.put("[A#maj7]", "[Amaj7]");
        map.put("[Amaj7]", "[G#maj7]");
        map.put("[G#maj7]", "[Gmaj7]");
        map.put("[Gmaj7]", "[F#maj7]");
        map.put("[F#maj7]", "[Fmaj7]");
        map.put("[Fmaj7]", "[Emaj7]");
        map.put("[Emaj7]", "[D#maj7]");
        map.put("[D#maj7]", "[Dmaj7]");
        map.put("[Dmaj7]", "[C#maj7]");
        map.put("[C#maj7]", "[Cmaj7]");

        map.put("[Cm7b5]", "[Bm7b5]");
        map.put("[Bm7b5]", "[A#m7b5]");
        map.put("[A#m7b5]", "[Am7b5]");
        map.put("[Am7b5]", "[G#m7b5]");
        map.put("[G#m7b5]", "[Gm7b5]");
        map.put("[Gm7b5]", "[F#m7b5]");
        map.put("[F#m7b5]", "[Fm7b5]");
        map.put("[Fm7b5]", "[Em7b5]");
        map.put("[Em7b5]", "[D#m7b5]");
        map.put("[D#m7b5]", "[Dm7b5]");
        map.put("[Dm7b5]", "[C#m7b5]");
        map.put("[C#m7b5]", "[Cm7b5]");

        map.put("[C7b5]", "[B7b5]");
        map.put("[B7b5]", "[A#7b5]");
        map.put("[A#7b5]", "[A7b5]");
        map.put("[A7b5]", "[G#7b5]");
        map.put("[G#7b5]", "[G7b5]");
        map.put("[G7b5]", "[F#7b5]");
        map.put("[F#7b5]", "[F7b5]");
        map.put("[F7b5]", "[E7b5]");
        map.put("[E7b5]", "[D#7b5]");
        map.put("[D#7b5]", "[D7b5]");
        map.put("[D7b5]", "[C#7b5]");
        map.put("[C#7b5]", "[C7b5]");

        List<String> myCheckedList = new ArrayList<>();

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String originalKey = entry.getKey();
            if (lyric.contains(originalKey)) {
                if (!myCheckedList.contains(originalKey)){
                    myCheckedList.add(originalKey);
                }
            }
        }

        for (int i = 0; i < myCheckedList.size(); i++) {
            str = str.replace(myCheckedList.get(i), String.format("[chord %s]", i));
            chordMap.put(String.format("[chord %s]", i), myCheckedList.get(i));
        }

        for (Map.Entry<String, String> entry : chordMap.entrySet()) {
            String originalKey = entry.getKey();
            String originalValue = entry.getValue();
            chordMap.put(originalKey, map.get(originalValue));
            str = str.replace(originalKey, map.get(originalValue));
        }


        return str;
    }

    public static SpannableString formattedString(String lyric) {

        List<Integer> openBracketCount = new ArrayList<>();
        List<Integer> closeBracketCount = new ArrayList<>();

        int open = lyric.indexOf('[');
        while(open >= 0) {
            openBracketCount.add(open);
            open = lyric.indexOf('[', open+1);
        }
        int close = lyric.indexOf(']');
        while(close >= 0) {
            closeBracketCount.add(close);
            close = lyric.indexOf(']', close+1);
        }

        SpannableString spannableString = new SpannableString(lyric);
        //ForegroundColorSpan blue = new ForegroundColorSpan(Color.BLUE);
        StyleSpan bold_italic = new StyleSpan(Typeface.BOLD_ITALIC);

        for (int i=0; i<openBracketCount.size(); i++) {
            spannableString.setSpan(bold_italic, openBracketCount.get(i), closeBracketCount.get(i)+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }
}
