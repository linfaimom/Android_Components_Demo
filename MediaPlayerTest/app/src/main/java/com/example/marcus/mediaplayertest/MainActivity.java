package com.example.marcus.mediaplayertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    //定义系统的示波器
    private Visualizer visualizer;
    //定义系统的均衡器
    private Equalizer equalizer;
    //定义系统的重低音控制器
    private BassBoost bassBoost;
    //定义系统的预设音场控制器
    private PresetReverb presetReverb;
    private LinearLayout linearLayout;
    private List<Short> reverbNames = new ArrayList<>();
    private List<String> reverbVals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置控制音乐声音
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        linearLayout = new LinearLayout(this);
        //设置垂直分布
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(linearLayout);
        //创建MediaPlayer对象
        mediaPlayer = MediaPlayer.create(this,R.raw.beautiful);
        //初始化示波器
        setupVisualize();
        //初始化均衡控制器
        setupEqualizer();
        //初始化重低音控制器
        setupBassBoost();
        //初始化预设音场控制器
        setupPresetReverb();
        //开始播放音乐
        mediaPlayer.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && isFinishing()){
            //释放资源
            equalizer.release();
            visualizer.release();
            presetReverb.release();
            bassBoost.release();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void setupVisualize(){
        final MyVisualizerView view = new MyVisualizerView(this);
        //设置示波器占屏大小
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) (120f * getResources().getDisplayMetrics().density)));
        //将MyVisualizerView组件添加到layout容器中
        linearLayout.addView(view);
        //以MediaPlayer的AudioSessionId创建Visualizer
        //相当于设置Visualizer负责显示该MediaPlayer的音频数据
        visualizer = new Visualizer(mediaPlayer.getAudioSessionId());
        visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        //为visualizer设置监听器
        visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] waveform, int samplingRate) {
                //使用waveform波形数据更新visualizer
                view.updateVisualizer(waveform);
            }

            //不使用fft
            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft, int samplingRate) {

            }

        }, Visualizer.getMaxCaptureRate() / 2, true, false);
        visualizer.setEnabled(true);
    }

    private void setupEqualizer(){
        equalizer = new Equalizer(0,mediaPlayer.getAudioSessionId());
        equalizer.setEnabled(true);
        TextView eqTitle = new TextView(this);
        eqTitle.setText("均衡器:");
        linearLayout.addView(eqTitle);
        //获取均衡器支持的最大和最小指
        final short minEQLevel = equalizer.getBandLevelRange()[0];
        short maxEQLevel = equalizer.getBandLevelRange()[1];
        //获取均衡器支持的所有频率
        short brands = equalizer.getNumberOfBands();
        for (short i = 0; i < brands; i++){
            TextView eqTextView = new TextView(this);
            eqTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            eqTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            eqTextView.setText((equalizer.getCenterFreq(i) / 1000) + " Hz");
            linearLayout.addView(eqTextView);
            LinearLayout tmpLinearLayout = new LinearLayout(this);
            tmpLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
            //最小显示在左边,最大显示在右边
            TextView minTextView = new TextView(this);
            minTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            minTextView.setText((minEQLevel / 100) + " dB");
            TextView maxTextView = new TextView(this);
            maxTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            maxTextView.setText((maxEQLevel / 100) + " dB");
            //用进度条SeekBar作为调整工具
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            SeekBar seekBar = new SeekBar(this);
            seekBar.setLayoutParams(layoutParams);
            seekBar.setMax(maxEQLevel - minEQLevel);
            seekBar.setProgress(equalizer.getBandLevel(i));
            final short band = i;
            //为seekBar的拖动事件设置监听器
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    //设置该频率的均衡值
                    equalizer.setBandLevel(band, (short) (progress + minEQLevel));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            //将三个组件放进水平分布的tmpLinerLayout中
            tmpLinearLayout.addView(minTextView);
            tmpLinearLayout.addView(seekBar);
            tmpLinearLayout.addView(maxTextView);
            //在linearLayout中加入tmpLinearLayout布局
            linearLayout.addView(tmpLinearLayout);
        }

    }

    private void setupBassBoost(){
        bassBoost = new BassBoost(0, mediaPlayer.getAudioSessionId());
        bassBoost.setEnabled(true);
        TextView bTextView = new TextView(this);
        bTextView.setText("重低音:");
        linearLayout.addView(bTextView);
        //同样采用SeekBar
        SeekBar bar = new SeekBar(this);
        //重音范围0~1000
        bar.setMax(1000);
        bar.setProgress(0);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bassBoost.setStrength((short) progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        linearLayout.addView(bar);
    }

    private void setupPresetReverb(){
        presetReverb = new PresetReverb(0, mediaPlayer.getAudioSessionId());
        presetReverb.setEnabled(true);
        TextView pTextView = new TextView(this);
        pTextView.setText("音场:");
        linearLayout.addView(pTextView);
        //获取系统所支持的所有预设音场,存放在两个ArrayList当中
        for (short i = 0; i < equalizer.getNumberOfPresets(); i++){
            reverbNames.add(i);
            reverbVals.add(equalizer.getPresetName(i));
        }
        //使用Spinner作为选择工具
        Spinner selector = new Spinner(this);
        selector.setAdapter(new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_item,
                reverbVals));
        selector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presetReverb.setPreset(reverbNames.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        linearLayout.addView(selector);
    }

    private static class MyVisualizerView extends View {
        //bytes数组用于保存波形抽样点的值
        private byte[] bytes;
        private float[] points;
        private Rect rect = new Rect();
        private Paint paint = new Paint();
        private byte type = 0;


        public MyVisualizerView(Context context) {
            super(context);
            bytes = null;
            //设置画笔属性
            paint.setStrokeWidth(1f);
            paint.setAntiAlias(true);
            paint.setColor(Color.GREEN);
            paint.setStyle(Paint.Style.FILL);
        }

        public void updateVisualizer(byte[] waveform) {
            bytes = waveform;
            //通知该组件重绘自己
            invalidate();
        }

        //设置点击事件,用户每点击一次更换风格
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() != MotionEvent.ACTION_DOWN){
                return false;
            }
            type++;
            if (type >= 3){
                type = 0;
            }
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (bytes == null){
                return;
            }
            //绘制白色背景
            canvas.drawColor(Color.WHITE);
            //使用rect对象记录该组件的宽与高
            rect.set(0,0,getWidth(),getHeight());
            //设置三种情况的绘制
            switch (type){
                //————————绘制块状的波形图————————
                case 0 :
                    for (int i = 0; i < bytes.length - 1; i++){
                        float left = getWidth() * i / (bytes.length - 1);
                        //根据波形值计算该矩形的高度
                        float top = rect.height() - (byte) (bytes[i+1] + 128) * rect.height() / 128;
                        float right = left + 6;
                        float bottom = rect.height();
                        canvas.drawRect(left, top, right, bottom, paint);
                    }
                    break;

                //————————绘制柱形的波形图（每隔18个抽样点绘制一个矩形）————————
                case 1 :
                    for (int i = 0; i < bytes.length - 1; i += 18){
                        float left = rect.width() * i / (bytes.length - 1);
                        //根据波形值计算该矩形的高度
                        float top = rect.height() - (byte) (bytes[i+1] + 128) * rect.height() / 128;
                        float right = left + 6;
                        float bottom = rect.height();
                        canvas.drawRect(left, top, right, bottom, paint);
                    }
                    break;

                //————————绘制曲线波形图————————
                case 2 :
                    //如果points数组未被初始化
                    if (points == null || points.length < bytes.length * 4){
                        points = new float[bytes.length * 4];
                    }
                    for (int i = 0; i < bytes.length - 1; i++){
                        //计算第i个点的x坐标
                        points[i * 4] = rect.width() * i / (bytes.length - 1);
                        //根据byte[i]的值（波形点的值）计算第i个点的y坐标
                        points[i * 4 + 1] = (rect.height() / 2) + ((byte) (bytes[i] + 128)) * 128 / (rect.height() / 2);
                        //计算第i+1个点的x坐标
                        points[i * 4 + 2] = rect.width() * (i+1) / (bytes.length - 1);
                        //根据byte[i + 1]的值（波形值的点）计算第i+1个点的y坐标
                        points[i * 4 + 3] = (rect.height() / 2) + ((byte) (bytes[i + 1] + 128)) * 128 / (rect.height() / 2);
                    }
                    canvas.drawLines(points,paint);
                    break;
            }
        }
    }
}

