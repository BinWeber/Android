package top.binweber.shannon;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;


public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setTitle(R.string.about);

        TextView blog = findViewById(R.id.blog);
        TextView github = findViewById(R.id.github);

        String blogText = "<a href='http://binweber.top'>Blog</a>";
        String githubText = "<a href='https://github.com/BinWeber/Android'>Github</a>";

        blog.setText(Html.fromHtml(blogText));
        blog.setMovementMethod(LinkMovementMethod.getInstance());

        github.setText(Html.fromHtml(githubText));
        github.setMovementMethod(LinkMovementMethod.getInstance());

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
