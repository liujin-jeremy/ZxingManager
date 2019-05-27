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
import android.widget.Toast;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

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
                        QRCodeReader reader = new QRCodeReader();
                        LuminanceSource source = new RGBLuminanceSource( mWidth, mHeight, mData );
                        Binarizer binarizer = new HybridBinarizer( source );
                        BinaryBitmap binaryBitmap = new BinaryBitmap( binarizer );
                        try {
                              long start = System.currentTimeMillis();
                              Result decode = reader.decode( binaryBitmap );
                              long finish = System.currentTimeMillis();
                              long result = finish - start;

                              Log.i( TAG, "onClick: 解析成功" + decode.getText() );
                              mTextView.setText( decode.getText() );

                              Toast.makeText( this, "耗时" + result, Toast.LENGTH_SHORT ).show();
                        } catch(NotFoundException e) {
                              e.printStackTrace();
                        } catch(ChecksumException e) {
                              e.printStackTrace();
                        } catch(FormatException e) {
                              e.printStackTrace();
                        }
                        break;
                  default:
                        break;
            }
      }
}
