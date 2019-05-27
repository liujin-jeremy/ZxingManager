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
import tech.jeremy.zxing.QRCodeManager;

public class GenerateQRCodeActivity extends AppCompatActivity implements OnClickListener {

      private EditText  mTextEdit;
      private Button    mGenerate;
      private ImageView mImageView;
      private Button    mDecode;
      private Bitmap    mQrCode;
      private TextView  mTextView;

      public static void start ( Context context ) {

            Intent starter = new Intent( context, GenerateQRCodeActivity.class );
            context.startActivity( starter );
      }

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_generate_qrcode );
            initView();
      }

      private void initView ( ) {

            mTextEdit = findViewById( R.id.edit_text );
            mGenerate = findViewById( R.id.generate );
            mGenerate.setOnClickListener( this );
            mImageView = findViewById( R.id.imageView );
            mDecode = findViewById( R.id.decode );
            mDecode.setOnClickListener( this );
            mTextView = findViewById( R.id.textView );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.generate:
                        String text = mTextEdit.getText().toString();
                        mQrCode = QRCodeManager.createQRCode( text );
                        mImageView.setImageBitmap( mQrCode );
                        break;
                  case R.id.decode:
                        if( mQrCode != null ) {
                              Result result = QRCodeManager.decodeQRCode( mQrCode );
                              mTextView.setText( result.getText() );
                        }
                        break;
                  default:
                        break;
            }
      }
}
