## ZxingManager

zxing管理器



### 二维码

解析

```
Result result = ZXingManager.decodeQRCode( bitmap );
```

生成

```
mBitmap = ZXingManager.createQRCode( text );
```

![](img/a1.png)

### 条形码

解析

```
Result result = ZXingManager.decodeOneD( bitmap );
```

生成

```
mBitmap = ZXingManager.createCode128( text, 1000, 300 );
```

![](img/a3.png)

### Pdf417

解析

```
Result result = ZXingManager.decodePdf417( bitmap );
```

生成

```
mBitmap = ZXingManager.createPdf417( text, 500, 200 );
```

![](img/a2.png)

### DataMatrix

解析

```
Result result = ZXingManager.decodeDataMatrix( bitmap, true );
```

生成

```
mBitmap = ZXingManager.createDataMatrix( text, 500 );
```

![](img/a5.png)

### Aztec

解析

```
Result result = ZXingManager.decodeAztec( bitmap );
```

生成

```
mBitmap = ZXingManager.createAztec( text, 500 );
```

![](img/a6.png)

