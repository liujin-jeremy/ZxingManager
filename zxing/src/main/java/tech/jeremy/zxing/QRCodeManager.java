package tech.jeremy.zxing;

/**
 * *          _       _
 * *   __   _(_)_   _(_) __ _ _ __
 * *   \ \ / / \ \ / / |/ _` | '_ \
 * *    \ V /| |\ V /| | (_| | | | |
 * *     \_/ |_| \_/ |_|\__,_|_| |_|
 * <p>
 * Created by vivian on 2016/11/28.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;

public class QRCodeManager {

      private static int IMAGE_HALF_WIDTH = 50;

      /**
       * 生成二维码，默认大小为500*500
       *
       * @param text 需要生成二维码的文字、网址等
       *
       * @return bitmap
       */
      public static Bitmap createQRCode ( String text ) {

            return createQRCode( text, 500 );
      }

      /**
       * 生成二维码
       *
       * @param text 文字或网址
       * @param size 生成二维码的大小
       *
       * @return bitmap
       */
      public static Bitmap createQRCode ( String text, int size ) {

            try {
                  Hashtable<EncodeHintType, String> hints = new Hashtable<>();
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );
                  BitMatrix bitMatrix = new QRCodeWriter().encode( text,
                                                                   BarcodeFormat.QR_CODE, size, size, hints
                  );
                  int[] pixels = new int[ size * size ];
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( bitMatrix.get( x, y ) ) {
                                    pixels[ y * size + x ] = 0xff000000;
                              } else {
                                    pixels[ y * size + x ] = 0xffffffff;
                              }
                        }
                  }
                  Bitmap bitmap = Bitmap.createBitmap( size, size,
                                                       Bitmap.Config.ARGB_8888
                  );
                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      /**
       * bitmap的颜色代替黑色的二维码
       */
      public static Bitmap createQRCodeWithLogo2 ( String text, int size, Bitmap mBitmap ) {

            try {
                  IMAGE_HALF_WIDTH = size / 10;
                  Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );

                  hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H );
                  BitMatrix bitMatrix = new QRCodeWriter().encode( text,
                                                                   BarcodeFormat.QR_CODE, size, size, hints
                  );

                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createScaledBitmap( mBitmap, size, size, false );

                  int[] pixels = new int[ size * size ];
                  int color = 0xffffffff;
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( bitMatrix.get( x, y ) ) {
                                    pixels[ y * size + x ] = mBitmap.getPixel( x, y );
                              } else {
                                    pixels[ y * size + x ] = color;
                              }
                        }
                  }
                  Bitmap bitmap = Bitmap.createBitmap( size, size,
                                                       Bitmap.Config.ARGB_8888
                  );
                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      /**
       * bitmap作为底色
       */
      public static Bitmap createQRCodeWithLogo3 ( String text, int size, Bitmap mBitmap ) {

            try {
                  IMAGE_HALF_WIDTH = size / 10;
                  Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );

                  hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H );
                  BitMatrix bitMatrix = new QRCodeWriter().encode( text,
                                                                   BarcodeFormat.QR_CODE, size, size, hints
                  );

                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createScaledBitmap( mBitmap, size, size, false );

                  int[] pixels = new int[ size * size ];
                  int color = 0xfff92736;
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( bitMatrix.get( x, y ) ) {
                                    pixels[ y * size + x ] = color;
                              } else {
                                    pixels[ y * size + x ] = mBitmap.getPixel( x, y ) & 0x66ffffff;
                              }
                        }
                  }
                  Bitmap bitmap = Bitmap.createBitmap( size, size,
                                                       Bitmap.Config.ARGB_8888
                  );
                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      /**
       * 比方法2的颜色黑一些
       */
      public static Bitmap createQRCodeWithLogo4 ( String text, int size, Bitmap mBitmap ) {

            try {
                  IMAGE_HALF_WIDTH = size / 10;
                  Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );

                  hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H );
                  BitMatrix bitMatrix = new QRCodeWriter().encode( text,
                                                                   BarcodeFormat.QR_CODE, size, size, hints
                  );

                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createScaledBitmap( mBitmap, size, size, false );

                  int[] pixels = new int[ size * size ];
                  boolean flag = true;
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( bitMatrix.get( x, y ) ) {
                                    if( flag ) {
                                          flag = false;
                                          pixels[ y * size + x ] = 0xff000000;
                                    } else {
                                          pixels[ y * size + x ] = mBitmap.getPixel( x, y );
                                          flag = true;
                                    }
                              } else {
                                    pixels[ y * size + x ] = 0xffffffff;
                              }
                        }
                  }
                  Bitmap bitmap = Bitmap.createBitmap( size, size,
                                                       Bitmap.Config.ARGB_8888
                  );
                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      /**
       * 生成带logo的二维码
       */
      public static Bitmap createQRCodeWithLogo5 ( String text, int size, Bitmap mBitmap ) {

            try {
                  IMAGE_HALF_WIDTH = size / 10;
                  Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );

                  hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H );
                  BitMatrix bitMatrix = new QRCodeWriter().encode( text,
                                                                   BarcodeFormat.QR_CODE, size, size, hints
                  );

                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createScaledBitmap( mBitmap, size, size, false );

                  int width = bitMatrix.getWidth();//矩阵高度
                  int height = bitMatrix.getHeight();//矩阵宽度
                  int halfW = width / 2;
                  int halfH = height / 2;

                  Matrix m = new Matrix();
                  float sx = (float) 2 * IMAGE_HALF_WIDTH / mBitmap.getWidth();
                  float sy = (float) 2 * IMAGE_HALF_WIDTH
                      / mBitmap.getHeight();
                  m.setScale( sx, sy );
                  //设置缩放信息
                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createBitmap( mBitmap, 0, 0,
                                                 mBitmap.getWidth(), mBitmap.getHeight(), m, false
                  );

                  int[] pixels = new int[ size * size ];
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH
                                  && y > halfH - IMAGE_HALF_WIDTH
                                  && y < halfH + IMAGE_HALF_WIDTH ) {
                                    //该位置用于存放图片信息
                                    //记录图片每个像素信息
                                    pixels[ y * width + x ] = mBitmap.getPixel(
                                        x - halfW
                                            + IMAGE_HALF_WIDTH, y - halfH + IMAGE_HALF_WIDTH );
                              } else {
                                    if( bitMatrix.get( x, y ) ) {
                                          pixels[ y * size + x ] = 0xff37b19e;
                                    } else {
                                          pixels[ y * size + x ] = 0xffffffff;
                                    }
                              }
                        }
                  }
                  Bitmap bitmap = Bitmap.createBitmap( size, size,
                                                       Bitmap.Config.ARGB_8888
                  );
                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      /**
       * 修改三个顶角颜色的，带logo的二维码
       */
      public static Bitmap createQRCodeWithLogo6 ( String text, int size, Bitmap mBitmap ) {

            try {
                  IMAGE_HALF_WIDTH = size / 10;
                  Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
                  hints.put( EncodeHintType.CHARACTER_SET, "utf-8" );
                  /*
                   * 设置容错级别，默认为ErrorCorrectionLevel.L
                   * 因为中间加入logo所以建议你把容错级别调至H,否则可能会出现识别不了
                   */
                  hints.put( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H );
                  BitMatrix bitMatrix = new QRCodeWriter().encode( text,
                                                                   BarcodeFormat.QR_CODE, size, size, hints
                  );

                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createScaledBitmap( mBitmap, size, size, false );

                  int width = bitMatrix.getWidth();//矩阵高度
                  int height = bitMatrix.getHeight();//矩阵宽度
                  int halfW = width / 2;
                  int halfH = height / 2;

                  Matrix m = new Matrix();
                  float sx = (float) 2 * IMAGE_HALF_WIDTH / mBitmap.getWidth();
                  float sy = (float) 2 * IMAGE_HALF_WIDTH
                      / mBitmap.getHeight();
                  m.setScale( sx, sy );
                  //设置缩放信息
                  //将logo图片按martix设置的信息缩放
                  mBitmap = Bitmap.createBitmap( mBitmap, 0, 0,
                                                 mBitmap.getWidth(), mBitmap.getHeight(), m, false
                  );

                  int[] pixels = new int[ size * size ];
                  for( int y = 0; y < size; y++ ) {
                        for( int x = 0; x < size; x++ ) {
                              if( x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH
                                  && y > halfH - IMAGE_HALF_WIDTH
                                  && y < halfH + IMAGE_HALF_WIDTH ) {
                                    //该位置用于存放图片信息
                                    //记录图片每个像素信息
                                    pixels[ y * width + x ] = mBitmap.getPixel(
                                        x - halfW
                                            + IMAGE_HALF_WIDTH, y - halfH + IMAGE_HALF_WIDTH );
                              } else {
                                    if( bitMatrix.get( x, y ) ) {
                                          pixels[ y * size + x ] = 0xff111111;
                                          if( x < 115 && ( y < 115 || y >= size - 115 ) || ( y < 115 && x >= size - 115 ) ) {
                                                pixels[ y * size + x ] = 0xfff92736;
                                          }
                                    } else {
                                          pixels[ y * size + x ] = 0xffffffff;
                                    }
                              }
                        }
                  }
                  Bitmap bitmap = Bitmap.createBitmap( size, size,
                                                       Bitmap.Config.ARGB_8888
                  );
                  bitmap.setPixels( pixels, 0, size, 0, 0, size, size );
                  return bitmap;
            } catch(WriterException e) {
                  e.printStackTrace();
                  return null;
            }
      }

      public static Result decodeQRCode ( Context context, int QRCodeResource ) {

            Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), QRCodeResource );
            return decodeQRCode( bitmap );
      }

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
}
