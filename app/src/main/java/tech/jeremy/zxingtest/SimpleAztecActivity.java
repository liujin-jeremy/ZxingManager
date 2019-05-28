package tech.jeremy.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.Result;
import tech.jeremy.zxing.ZXingManager;

public class SimpleAztecActivity extends AppCompatActivity implements OnClickListener {

      private ImageView mImageView;
      private Button    mDecode;
      private TextView  mTextView;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, SimpleAztecActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_simple_aztec );
            initView();
      }

      private void initView ( ) {

            mImageView = findViewById( R.id.imageView );
            mDecode = findViewById( R.id.decode );
            mDecode.setOnClickListener( this );
            mTextView = findViewById( R.id.textView );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.decode:
                        Bitmap bitmap = BitmapFactory.decodeResource( getResources(), R.drawable.a6 );
                        Result result = ZXingManager.decodeAztec( bitmap );
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
