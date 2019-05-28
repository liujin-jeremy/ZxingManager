package tech.jeremy.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.Result;
import tech.jeremy.zxing.ZXingManager;

public class GenerateDataMatrixActivity extends AppCompatActivity implements OnClickListener {

      private EditText  mTextEdit;
      private Button    mGenerate;
      private ImageView mImageView;
      private Button    mDecode;
      private TextView  mTextView;
      private Bitmap    mBitmap;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, GenerateDataMatrixActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_generate_data_matrix );
            initView();
      }

      private void initView ( ) {

            mTextEdit = (EditText) findViewById( R.id.edit_text );
            mGenerate = (Button) findViewById( R.id.generate );
            mGenerate.setOnClickListener( this );
            mImageView = (ImageView) findViewById( R.id.imageView );
            mDecode = (Button) findViewById( R.id.decode );
            mDecode.setOnClickListener( this );
            mTextView = (TextView) findViewById( R.id.textView );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.generate:
                        String text = mTextEdit.getText().toString();
                        if( !TextUtils.isEmpty( text ) ) {
                              mBitmap = ZXingManager.createDataMatrix( text, 500 );
                              mImageView.setImageBitmap( mBitmap );
                        }
                        break;
                  case R.id.decode:
                        if( mBitmap != null ) {
                              Result result = ZXingManager.decodeDataMatrix( mBitmap );
                              if( result != null ) {
                                    mTextView.setText( result.getText() );
                              } else {
                                    mTextView.setText( "解析失败" );
                              }
                        }
                        break;
                  default:
                        break;
            }
      }
}
