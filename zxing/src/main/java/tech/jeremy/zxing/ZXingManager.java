package tech.jeremy.zxing;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.os.Build.VERSION_CODES;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.util.ArrayMap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.aztec.AztecReader;
import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.MultiFormatOneDReader;
import com.google.zxing.pdf417.PDF417Reader;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ZXingManager {

      /**
       * 解析二维码图片
       *
       * @param bitmap 图片
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
       * @param text 内容
       * @param size 尺寸
       *
       * @return bitmap 二维码图片
       */
      public static Bitmap createQRCode ( String text, int size ) {

            return createQRCode( text, size, "utf-8", ErrorCorrectionLevel.H, 8, Color.BLACK, Color.WHITE );
      }

      /**
       * 生成二维码
       *
       * @param text 内容
       * @param size 尺寸
       * @param charset 内容编码方式
       * @param level 容错级别
       * @param margin 边距
       * @param colorDark 深颜色定义
       * @param colorLight 浅颜色定义
       *
       * @return bitmap 二维码图片
       */
      public static Bitmap createQRCode (
          String text,
          int size,
          String charset,
          ErrorCorrectionLevel level,
          int margin,
          @ColorInt int colorDark,
          @ColorInt int colorLight
      ) {

            try {
                  ArrayMap<EncodeHintType, Object> hints = new ArrayMap<>( 3 );
                  hints.put( EncodeHintType.CHARACTER_SET, charset );
                  hints.put( EncodeHintType.ERROR_CORRECTION, level );
                  hints.put( EncodeHintType.MARGIN, margin );

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
                                    pixels[ y * size + x ] = colorDark;
                              } else {
                                    pixels[ y * size + x ] = colorLight;
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

            return createPdf417(
                text,
                width, height, 30,
                4,
                "utf-8",
                false, null, null
            );
      }

      /**
       * @param text 内容
       * @param width 宽度
       * @param height 高度
       * @param margin 边距
       * @param error_correction 纠错级别,0~8
       * @param charset 编码方式("utf-8")
       * @param pdf417_compact 是否紧凑
       * @param pdf417_compaction 紧凑样式
       * @param pdf417_dimensions 尺寸信息
       *
       * @return 图片
       */
      public static Bitmap createPdf417 (
          String text,
          int width, int height,
          int margin,
          int error_correction,
          String charset,
          boolean pdf417_compact, Compaction pdf417_compaction,
          Dimensions pdf417_dimensions
      ) {

            try {
                  ArrayMap<EncodeHintType, Object> hints = new ArrayMap<>( 6 );
                  hints.put( EncodeHintType.MARGIN, margin );
                  hints.put( EncodeHintType.CHARACTER_SET, charset );
                  hints.put( EncodeHintType.ERROR_CORRECTION, error_correction );
                  hints.put( EncodeHintType.PDF417_COMPACT, pdf417_compact );
                  hints.put( EncodeHintType.PDF417_COMPACTION, pdf417_compaction );
                  hints.put( EncodeHintType.PDF417_DIMENSIONS, pdf417_dimensions );

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

      /**
       * 解析条形码
       *
       * @param bitmap 图片
       *
       * @return 解析结果
       */
      public static Result decodeOneD ( Bitmap bitmap ) {

            return decodeOneD( bitmap, false );
      }

      /**
       * 解析条形码
       *
       * @param bitmap 图片
       * @param tryHard 是否尽全力解析
       *
       * @return 解析结果
       */
      public static Result decodeOneD ( Bitmap bitmap, boolean tryHard ) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] data = new int[ width * height ];
            bitmap.getPixels( data, 0, width, 0, 0, width, height );
            LuminanceSource source = new RGBLuminanceSource( width, height, data );
            Binarizer binarizer = new HybridBinarizer( source );
            BinaryBitmap binaryBitmap = new BinaryBitmap( binarizer );

            ArrayMap<DecodeHintType, Object> hints = new ArrayMap<>( 1 );
            hints.put( DecodeHintType.TRY_HARDER, tryHard );
            MultiFormatOneDReader reader = new MultiFormatOneDReader( hints );

            try {
                  return reader.decode( binaryBitmap );
            } catch(NotFoundException e) {
                  e.printStackTrace();
            } catch(FormatException e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * 生成code128码图片
       *
       * @param text 信息
       * @param width 码宽度
       * @param height 码高度
       *
       * @return bitmap 结果图片
       */
      public static Bitmap createCode128 ( String text, int width, int height ) {

            return createCode128( text, width, height, 10 );
      }

      /**
       * 生成code128码图片
       *
       * @param text 信息
       * @param width 码宽度
       * @param height 码高度
       * @param margin 边距
       *
       * @return bitmap 结果图片
       */
      public static Bitmap createCode128 ( String text, int width, int height, int margin ) {

            try {
                  ArrayMap<EncodeHintType, Object> hints = new ArrayMap<>( 1 );
                  hints.put( EncodeHintType.MARGIN, margin );
                  BitMatrix bitMatrix = new Code128Writer().encode(
                      text,
                      BarcodeFormat.CODE_128,
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

      /**
       * @param bitmap 图片
       *
       * @return 解析结果
       */
      public static Result decodeDataMatrix ( Bitmap bitmap ) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] data = new int[ width * height ];
            bitmap.getPixels( data, 0, width, 0, 0, width, height );

            LuminanceSource source = new RGBLuminanceSource( width, height, data );
            Binarizer binarizer = new HybridBinarizer( source );
            BinaryBitmap binaryBitmap = new BinaryBitmap( binarizer );

            DataMatrixReader reader = new DataMatrixReader();

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
       * @param content 内容
       * @param width 宽度
       * @param height 高度
       *
       * @return 图片
       */
      public static Bitmap createDataMatrix ( String content, int width, int height ) {

            try {
                  BitMatrix bitMatrix = new DataMatrixWriter().encode( content, BarcodeFormat.DATA_MATRIX, width, height );

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
            } catch(Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * @param content 内容
       * @param width 宽度
       * @param height 高度
       * @param data_matrix_shape 形状
       *
       * @return 图片
       */
      public static Bitmap createDataMatrix (
          String content,
          int width, int height,
          SymbolShapeHint data_matrix_shape ) {

            try {
                  ArrayMap<EncodeHintType, Object> hints = new ArrayMap<>( 1 );
                  hints.put( EncodeHintType.DATA_MATRIX_SHAPE, data_matrix_shape );
                  BitMatrix bitMatrix = new DataMatrixWriter().encode( content, BarcodeFormat.DATA_MATRIX, width, height, hints );

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
            } catch(Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * 解析aztec码
       *
       * @param bitmap 图片
       *
       * @return 解析结果
       */
      public static Result decodeAztec ( Bitmap bitmap ) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int[] data = new int[ width * height ];
            bitmap.getPixels( data, 0, width, 0, 0, width, height );

            LuminanceSource source = new RGBLuminanceSource( width, height, data );
            Binarizer binarizer = new HybridBinarizer( source );
            BinaryBitmap binaryBitmap = new BinaryBitmap( binarizer );

            AztecReader reader = new AztecReader();

            try {
                  return reader.decode( binaryBitmap );
            } catch(NotFoundException e) {
                  e.printStackTrace();
            } catch(FormatException e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * 创建aztec码
       *
       * @param content 内容
       * @param width 宽度
       * @param height 高度
       *
       * @return 图片
       */
      @RequiresApi(api = VERSION_CODES.KITKAT)
      public static Bitmap createAztec ( String content, int width, int height ) {

            try {
                  BitMatrix bitMatrix = new AztecWriter().encode( content, BarcodeFormat.DATA_MATRIX, width, height );

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
            } catch(Exception e) {
                  e.printStackTrace();
            }

            return null;
      }

      /**
       * 创建aztec码
       *
       * @param content 内容
       * @param width 宽度
       * @param height 高度
       * @param charset 编码集
       * @param error_correction 纠错级别(0~100)
       * @param aztec_layers 层数
       *
       * @return 图片
       */
      @RequiresApi(api = VERSION_CODES.KITKAT)
      public static Bitmap createAztec (
          String content, int width, int height, String charset, int error_correction, int aztec_layers ) {

            try {
                  ArrayMap<EncodeHintType, Object> hints = new ArrayMap<>();
                  hints.put( EncodeHintType.CHARACTER_SET, charset );
                  hints.put( EncodeHintType.ERROR_CORRECTION, error_correction );
                  hints.put( EncodeHintType.AZTEC_LAYERS, aztec_layers );

                  BitMatrix bitMatrix = new AztecWriter().encode( content, BarcodeFormat.DATA_MATRIX, width, height, hints );

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
            } catch(Exception e) {
                  e.printStackTrace();
            }

            return null;
      }
}
