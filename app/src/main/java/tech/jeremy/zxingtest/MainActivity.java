package tech.jeremy.zxingtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements OnClickListener {

      private Button mSimpleQRCode;
      private Button mGenerateQRCode;

      @Override
      protected void onCreate ( Bundle savedInstanceState ) {

            super.onCreate( savedInstanceState );
            setContentView( R.layout.activity_main );
            initView();
      }

      private void initView ( ) {

            mSimpleQRCode = (Button) findViewById( R.id.simpleQRCode );
            mSimpleQRCode.setOnClickListener( this );
            mGenerateQRCode = (Button) findViewById( R.id.generateQRCode );
            mGenerateQRCode.setOnClickListener( this );
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
                  default:
                        break;
            }
      }
}
