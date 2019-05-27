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
            mGeneratePdf417 = (Button) findViewById( R.id.generatePdf417 );
            mGeneratePdf417.setOnClickListener( this );
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
                  default:
                        break;
            }
      }
}
