package cn.c4code.robotchat;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.SpannableString;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;

import cn.c4code.robotchat.utils.AppUtils;
import cn.c4code.robotchat.utils.TulingRobot;
import cn.c4code.robotchat.widget.item.BiaoQingItem;
import cn.c4code.robotchat.widget.adapter.BiaoQingRecyclerViewAdapter;
import cn.c4code.robotchat.widget.ChatViewManager;
import cn.c4code.robotchat.widget.item.MessageItem;
import cn.c4code.robotchat.widget.utils.BiaoQingHelper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TulingRobot.MessageListener, AdapterView.OnItemClickListener {

    private static Drawable mHeadMe, mHeadRobot;

    private RecyclerView mChatView;
    private ChatViewManager mChatViewManager;

    private RecyclerView mBiaoQingView;
    private BiaoQingRecyclerViewAdapter mBiaoQingViewAdapter;
    private BiaoQingHelper mBqHelper;

    private EditText mInputMsg;
    private ImageButton mBtnSend;
    private Handler mHandler;

    private TulingRobot robot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();

        robot = new TulingRobot("382dc200dfd34d48a5155f9cadfa713f", this);

    }

    private void initView() {
        mHandler = new Handler();

        /* 头像 */
        mHeadMe = getResources().getDrawable(R.drawable.head_right);
        mHeadRobot = getResources().getDrawable(R.drawable.head_left);

        /* 表情列表 */
        mBiaoQingView = (RecyclerView)findViewById(R.id.biaoqing_view);
        mBiaoQingView.setLayoutManager(new GridLayoutManager(this, 8));
        mBiaoQingViewAdapter = new BiaoQingRecyclerViewAdapter(this);

        mBqHelper = new BiaoQingHelper(this);

        for(String key : mBqHelper.getmImageSpanSet().keySet())
            mBiaoQingViewAdapter.getData().add(new BiaoQingItem(key, mBqHelper.getmImageSpanSet().get(key)));

        mBiaoQingView.setAdapter(mBiaoQingViewAdapter);
        mBiaoQingViewAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Editable edit = mInputMsg.getEditableText();
                edit.insert(mInputMsg.getSelectionStart(),
                        mBqHelper.biaoQingtoCharSequence(mBiaoQingViewAdapter.getData().get(position)));
            }
        });


        mChatView = (RecyclerView) findViewById(R.id.chat_view);

        mChatViewManager = new ChatViewManager(this, mChatView);
        mChatViewManager.getAdapter().setOnItemClickListener(this);

        mInputMsg = (EditText)findViewById(R.id.input_msg);

        mBtnSend = (ImageButton) findViewById(R.id.sendButton);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = mInputMsg.getText().toString();

                mChatViewManager.pushItem(new MessageItem("我", "666", mHeadMe, mBqHelper.parseMessage(message), MessageItem.MsgType.SEND));

                new MessageSender(mChatView, mHandler, robot ,message, mBtnSend).start();
                mBtnSend.setEnabled(false);

                mInputMsg.setText("");
            }
        });

        findViewById(R.id.btn_bq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBiaoQingView.getVisibility() == View.GONE) {
                    AppUtils.displayView(MainActivity.this, mBiaoQingView);
                } else {
                    AppUtils.hiddenView(MainActivity.this, mBiaoQingView);
                }
            }
        });
    }

    @Override
    public void onMessage(final String message) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mChatViewManager.pushItem(new MessageItem("萌萌", "0.1cm", mHeadRobot, new SpannableString(message), MessageItem.MsgType.RECV));
                mBtnSend.setEnabled(true);
            }
        });
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("操作")
                .setItems(new String[]{"复制", "删除" }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                                clip.setText(mChatViewManager.getItemData(position).message);
                                break;
                            case 1:
                                mChatViewManager.removeItem(position);
                                break;
                        }
                    }
                }).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (mBiaoQingView.getVisibility() == View.VISIBLE) {
            AppUtils.hiddenView(MainActivity.this, mBiaoQingView);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
