package tech.jeremy.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.Result;
import tech.jeremy.zxing.ZXingManager;

public class SimpleQRCodeActivity extends AppCompatActivity implements OnClickListener {

      private static final String TAG = SimpleQRCodeActivity.class.getSimpleName();

      private ImageView mImageView;
      private Button    mButton;
      private TextView  mTextView;

      private int   mHeight;
      private int   mWidth;
      private int[] mData;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, SimpleQRCodeActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_simple_qrcode );
            initView();

            mImageView.post( new Runnable() {

                  @Override
                  public void run ( ) {

                        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.a1 );
                        mWidth = bitmap.getWidth();
                        mHeight = bitmap.getHeight();
                        mData = new int[ mWidth * mHeight ];
                        bitmap.getPixels( mData, 0, mWidth, 0, 0, mWidth, mHeight );

                        Log.i( TAG, "run: " + "二维码图片数据准备完毕" );
                  }
            } );
      }

      private void initView ( ) {

            mImageView = findViewById( R.id.imageView );
            mButton = findViewById( R.id.button );
            mButton.setOnClickListener( this );
            mTextView = findViewById( R.id.textView );
            mImageView.setImageResource( R.drawable.a1 );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.button:
                        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.a1 );
                        Result result = ZXingManager.decodeQRCode( bitmap );
                        if( result != null ) {
                              String text = result.getText();
                              mTextView.setText( text );
                        } else {
                              mTextView.setText( "解析失败" );
                        }
                        break;
                  default:
                        break;
            }
      }
}
