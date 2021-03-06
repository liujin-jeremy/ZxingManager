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

public class SimplePdf417Activity extends AppCompatActivity implements OnClickListener {

      private static final String TAG = SimplePdf417Activity.class.getSimpleName();

      private ImageView mImageView;
      private Button    mDecode;
      private TextView  mTextView;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, SimplePdf417Activity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_simple_pdf417 );
            initView();
      }

      private void initView ( ) {

            mImageView = (ImageView) findViewById( R.id.imageView );
            mDecode = (Button) findViewById( R.id.decode );
            mDecode.setOnClickListener( this );
            mTextView = (TextView) findViewById( R.id.textView );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.decode:
                        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.a2 );
                        Result result = ZXingManager.decodePdf417( bitmap );
                        Log.i( TAG, "onClick: " + result.getText() );
                        mTextView.setText( result.getText() );
                        break;
                  default:
                        break;
            }
      }
}
