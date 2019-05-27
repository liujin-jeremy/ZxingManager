package tech.jeremy.zxingtest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.Result;
import tech.jeremy.zxing.ZXingManager;

public class GeneratePdf417Activity extends AppCompatActivity implements OnClickListener {

      private EditText  mTextEdit;
      private Button    mGenerate;
      private ImageView mImageView;
      private Button    mDecode;
      private TextView  mTextView;
      private Bitmap    mPdf417;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, GeneratePdf417Activity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_generate_pdf417 );
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
                        mPdf417 = ZXingManager.createPdf417( text, 500,200 );
                        mImageView.setImageBitmap( mPdf417 );
                        break;
                  case R.id.decode:
                        if( mPdf417 != null ) {
                              Result result = ZXingManager.decodePdf417( mPdf417 );
                              if( result != null ) {
                                    mTextView.setText( result.getText() );
                              }
                        }
                        break;
                  default:
                        break;
            }
      }
}
