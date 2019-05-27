package tech.jeremy.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.util.ArrayMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

public class ZXingManager {

      /**
       * 生成二维码，大小为500*500
       *
       * @param text 二维码内容
       *
       * @return bitmap
       */
      public static Bitmap createQRCode ( String text ) {

            return createQRCode( text, 500 );
      }

      /**
       * 生成二维码
       *
       * @param text 二维码大小
       * @param size 生成二维码的大小
       *
       * @return bitmap 二维码图片
       */
      public static Bitmap createQRCode ( String text, int size ) {

            try {
                  ArrayMap<EncodeHintType, String> hints = new ArrayMap<>( 1 );
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );
                  BitMatrix bitMatrix = new QRCodeWriter().encode(
                      text,
                      BarcodeFormat.QR_CODE,
                      size,
                      size,
                      hints
                  );

                  int[] pixels = new int[ size * size ];
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( bitMatrix.get( x, y ) ) {
                                    pixels[ y * size + x ] = Color.BLACK;
                              } else {
                                    pixels[ y * size + x ] = Color.WHITE;
                              }
                        }
                  }

                  Bitmap bitmap = Bitmap.createBitmap(
                      size,
                      size,
                      Config.RGB_565
                  );

                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * 解析二维码资源
       *
       * @param context context
       * @param QRCodeResource 二维码资源图片
       *
       * @return 解析结果
       */
      public static Result decodeQRCode ( Context context, int QRCodeResource ) {

            Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), QRCodeResource );
            return decodeQRCode( bitmap );
      }

      /**
       * 解析二维码图片
       *
       * @param bitmap 二维码bitmap
       *
       * @return 解析结果
       */
      public static Result decodeQRCode ( Bitmap bitmap ) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] data = new int[ width * height ];
            bitmap.getPixels( data, 0, width, 0, 0, width, height );

            QRCodeReader reader = new QRCodeReader();
            LuminanceSource source = new RGBLuminanceSource( width, height, data );
            Binarizer binarizer = new HybridBinarizer( source );
            BinaryBitmap binaryBitmap = new BinaryBitmap( binarizer );
            try {
                  return reader.decode( binaryBitmap );
            } catch(NotFoundException e) {
                  e.printStackTrace();
            } catch(ChecksumException e) {
                  e.printStackTrace();
            } catch(FormatException e) {
                  e.printStackTrace();
            }
            return null;
      }

      /**
       * 解析Pdf417码
       *
       * @param context context
       * @param QRCodeResource 资源Id
       *
       * @return 解析结果
       */
      public static Result decodePdf417 ( Context context, int QRCodeResource ) {

            Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), QRCodeResource );
            return decodePdf417( bitmap );
      }

      /**
       * 解析Pdf417码
       *
       * @param bitmap pdf417 图片
       *
       * @return 解析结果
       */
      public static Result decodePdf417 ( Bitmap bitmap ) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] data = new int[ width * height ];
            bitmap.getPixels( data, 0, width, 0, 0, width, height );
            LuminanceSource source = new RGBLuminanceSource( width, height, data );
            Binarizer binarizer = new HybridBinarizer( source );
            BinaryBitmap binaryBitmap = new BinaryBitmap( binarizer );
            PDF417Reader reader = new PDF417Reader();

            try {
                  return reader.decode( binaryBitmap );
            } catch(NotFoundException e) {
                  e.printStackTrace();
            } catch(FormatException e) {
                  e.printStackTrace();
            } catch(ChecksumException e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * 生成Pdf417码图片
       *
       * @param text 信息
       * @param width 码宽度
       * @param height 码高度
       *
       * @return bitmap Pdf417码图片
       */
      public static Bitmap createPdf417 ( String text, int width, int height ) {

            try {
                  ArrayMap<EncodeHintType, String> hints = new ArrayMap<>( 1 );
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );
                  BitMatrix bitMatrix = new PDF417Writer().encode(
                      text,
                      BarcodeFormat.PDF_417,
                      width,
                      height,
                      hints
                  );

                  height = bitMatrix.getHeight();
                  width = bitMatrix.getWidth();

                  int[] pixels = new int[ width * height ];
                  for( int y = 0; y < height; y++ ) {
                        for( int x = 0; x < width; x++ ) {
                              if( bitMatrix.get( x, y ) ) {
                                    pixels[ y * width + x ] = Color.BLACK;
                              } else {
                                    pixels[ y * width + x ] = Color.WHITE;
                              }
                        }
                  }

                  Bitmap bitmap = Bitmap.createBitmap(
                      width,
                      height,
                      Config.RGB_565
                  );

                  bitmap.setPixels( pixels, 0, width, 0, 0, width, height );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
            }

            return null;
      }
}
