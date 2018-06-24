package top.binweber.lampsay;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.*;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import top.binweber.lampsay.view.AudioRecorderButton;

/**
 * Created by wangb on 2017/12/7.
 */

public class ChatActivity extends AppCompatActivity{

    private ListView mListView;
    private ArrayAdapter<Recorder> mAdapter;
    private List<Recorder> mDatas = new ArrayList<>();

    private AudioRecorderButton mAudioRecorderButton;

    private View mAnimView;

    private ImageView mVoice;
    private ImageView mRecorder;
    private Button mVoiceButton;

    private Thread speakThread;

    private Toolbar toolbar;

    private boolean isPlay = false;
    private int mRecBuffSize;//录音缓存区大小
    private AudioRecord mAudioRecord;//声明一个录音对象
    private int mPlayBufSize;//放音缓存区大小
    private AudioTrack mAudioTrack;//声明一个放音对象
    static final int frequency = 44100;//频率
    static final int channelConfiguration = AudioFormat.CHANNEL_OUT_STEREO;  //通道配置
    static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;   //声音编码

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        toolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        toolbar.setTitle(StateFragment.DeviceName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init_AudioRecordAndTrack();

        mListView = (ListView) findViewById(R.id.chat_list);
        mAudioRecorderButton = (AudioRecorderButton) findViewById(R.id.recorder_button);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                Recorder recorder = new Recorder(seconds, filePath);
                mDatas.add(recorder);
                mAdapter.notifyDataSetChanged();
                mListView.setSelection(mDatas.size() - 1);

                MediaManager.playSound(filePath, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {

                    }
                });
            }
        });

        mAdapter = new RecorderAdapter(this, mDatas);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(mAnimView != null){
                    mAnimView.setBackgroundResource(R.drawable.adj);
                    mAnimView = null;
                }
                mAnimView = view.findViewById(R.id.recorder_anim);
                mAnimView.setBackgroundResource(R.drawable.play_anim);
                AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
                anim.start();

                MediaManager.playSound(mDatas.get(position).filePath, new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mAnimView.setBackgroundResource(R.drawable.adj);
                    }
                });
            }
        });

        mRecorder = (ImageView) findViewById(R.id.recorder_view);
        mRecorder.setImageResource(R.drawable.ic_voice_recoder_red);
        mRecorder.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                speakThread.interrupt();
                speakThread = null;
                mAudioRecord.stop();
                mAudioTrack.stop();
                mRecorder.setImageResource(R.drawable.ic_voice_recoder_red);
                mVoice.setImageResource(R.drawable.ic_voice_out);
                mVoiceButton.setVisibility(View.GONE);
                mAudioRecorderButton.setVisibility(View.VISIBLE);
            }
        });

        speakThread = new Thread(){
            public void run(){
                byte[] byteBuff = new byte[mRecBuffSize]; //缓存数组
                int size ;
                mAudioRecord.startRecording();//开始录音
                mAudioTrack.play();// 开始播放
                isPlay = true;
                while(isPlay){
                    size = mAudioRecord.read(byteBuff, 0, mRecBuffSize); //将读到的录音 放到缓存数组中
                    mAudioTrack.write(byteBuff, 0, size);//播放，这里是通过听筒播放的
                }
            }
        };

        mVoiceButton = (Button) findViewById(R.id.voice_button);
        mVoice = (ImageView) findViewById(R.id.voice_view);
        mVoice.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mRecorder.setImageResource(R.drawable.ic_voice_recoder);
                mVoice.setImageResource(R.drawable.ic_voice_out_red);
                mAudioRecorderButton.setVisibility(View.GONE);
                mVoiceButton.setVisibility(View.VISIBLE);
                speakThread.start();
            }
        });

    }

    class Recorder {
        float time;
        String filePath;

        public Recorder(float time, String filePath) {
            super();
            this.time = time;
            this.filePath = filePath;
        }

        public float getTime() {
            return time;
        }

        public void SetTime(float time) {
            this.time = time;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
    }

    private void init_AudioRecordAndTrack() {
        // 调用getMinBufferSize方法获得录音的最小缓冲空间
        mRecBuffSize = mAudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);//频率 通道配置  声音格式
        System.out.println("得录音的最小缓冲空间  "+mRecBuffSize);
        // 调用构造函数实例化录音对象
        mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency,
                channelConfiguration, audioEncoding,
                mRecBuffSize);

        // 调用getMinBufferSize方法获得放音最小的缓冲区大小
        mPlayBufSize = AudioTrack.getMinBufferSize(frequency,
                channelConfiguration, audioEncoding);
        // 调用构造函数实例化放音对象，以听筒模式播放
        mAudioTrack = new AudioTrack(android.media.AudioManager.STREAM_VOICE_CALL, frequency, channelConfiguration, audioEncoding, mPlayBufSize,  AudioTrack.MODE_STREAM);
    }

}
