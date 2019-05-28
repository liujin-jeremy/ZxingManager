package tech.jeremy.zxingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

      private Button mSimpleQRCode;
      private Button mGenerateQRCode;
      private Button mSimplePdf417;
      private Button mGeneratePdf417;
      private Button mDecodeOneD;
      private Button mGenerateCode128;
      private Button mDecodeDataMatrix;
      private Button mGenerateDataMatrix;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
            initView();
      }

      private void initView ( ) {

            mSimpleQRCode = findViewById( R.id.simpleQRCode );
            mSimpleQRCode.setOnClickListener( this );
            mGenerateQRCode = findViewById( R.id.generateQRCode );
            mGenerateQRCode.setOnClickListener( this );
            mSimplePdf417 = findViewById( R.id.simplePdf417 );
            mSimplePdf417.setOnClickListener( this );
            mGeneratePdf417 = findViewById( R.id.generatePdf417 );
            mGeneratePdf417.setOnClickListener( this );
            mDecodeOneD = findViewById( R.id.decodeOneD );
            mDecodeOneD.setOnClickListener( this );
            mGenerateCode128 = findViewById( R.id.generateCode128 );
            mGenerateCode128.setOnClickListener( this );
            mDecodeDataMatrix = findViewById( R.id.decodeDataMatrix );
            mDecodeDataMatrix.setOnClickListener( this );
            mGenerateDataMatrix = findViewById( R.id.generateDataMatrix );
            mGenerateDataMatrix.setOnClickListener( this );
      }

      @Override
      public void onClick ( View v ) {

            switch( v.getId() ) {
                  case R.id.simpleQRCode:
                        SimpleQRCodeActivity.start( this );
                        break;
                  case R.id.generateQRCode:
                        GenerateQRCodeActivity.start( this );
                        break;
                  case R.id.simplePdf417:
                        SimplePdf417Activity.start( this );
                        break;
                  case R.id.generatePdf417:
                        GeneratePdf417Activity.start( this );
                        break;
                  case R.id.decodeOneD:
                        SimpleOneDActivity.start( this );
                        break;
                  case R.id.generateCode128:
                        GenerateCode128Activity.start( this );
                        break;
                  case R.id.decodeDataMatrix:
                        SimpleDataMatrixActivity.start( this );
                        break;
                  case R.id.generateDataMatrix:
                        GenerateDataMatrixActivity.start( this );
                        break;
                  default:
                        break;
            }
      }
}
