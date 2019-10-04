package com.example.anggarisky.splashtohomeangga;

import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by BlackMark on 5/16/2016.
 */
public  class Utilities {

    static MediaPlayer mediaPlayer;
    static Double currentLatitute=10.0;
    static Double currentLontitute;
    //static String dbUrl = "http://tourz.000webhostapp.com/itraffic/android_services/Services/";
    static String dbUrl = "http://192.168.8.102/itraffic/";
    //static String dbUrl = "http://192.168.1.35/itraffic/";
    //static String dbUrl = "http://192.168.8.104/itraffic/";

    public static void recoverDissappearingViews(View tmpV){
        try{
            tmpV.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),SystemClock.uptimeMillis(),
                    (int)MotionEvent.ACTION_UP,0,0,0));

        }catch(Exception e)
        {
            Log.e("Null Images",e+"");
        }
    }

    public static void playAudio(AssetFileDescriptor afd) {
        try {
            //AssetFileDescriptor afd = getAssets().openFd(audioFile);
            stopPlaying();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.release();

                }

            });

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static void stopPlaying() {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        } catch (Exception e) {
            Log.e("Exception", e + "");
        }

    }

    public static int getHotspotColor(ImageView img, int x, int y) {
        int returnBVal = 0;
        // ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Log.d("ImageAreasActivity", "Hot spot image not found");
            return returnBVal;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Log.d("ImageAreasActivity", "Hot spot bitmap was not created");
                return returnBVal;
            } else {
                img.setDrawingCacheEnabled(false);
                try {
                    returnBVal = hotspots.getPixel(x, y);
                    Log.d("returnBVal", "returnBVal is " + returnBVal + "");

                } catch (Exception e) {
                    Log.e("Inside Catch", "Clicked on Two Colors.");
                }
                return returnBVal;
            }

        }
    }

    public static int[][] globalNamesAndShapesArray;// = new int[][];

    public static String[][] globalColorsAndSoundsArray;

    public static void shuffleItems(int[][] namesandShapes, String[][] colorsAndSounds)
    {
        List<int[]> nands = Arrays.asList(namesandShapes);
        List<String[]> cands = Arrays.asList(colorsAndSounds);
        long seed = System.nanoTime();
        Collections.shuffle(nands, new Random(seed));
        Collections.shuffle(cands, new Random(seed));
        globalNamesAndShapesArray = nands.toArray(new int[0][0]);
        globalColorsAndSoundsArray = cands.toArray(new String[0][0]);
    }



}
