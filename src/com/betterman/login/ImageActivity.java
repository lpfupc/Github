package com.betterman.login;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.betterman.util.RGBLuminanceSource;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.R;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

public class ImageActivity extends Activity implements OnClickListener {
	private static final String TAG = "TestActivity";

	private ImageView qr_image;
	private TextView qr_text, qr_result;

	private final static int QR_WIDTH = 200, QR_HEIGHT = 200;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_qr);
		init();
	}

	private void init() {
		qr_image = (ImageView) findViewById(R.id.qr_image);
		qr_text = (EditText) findViewById(R.id.qr_text);
		qr_result = (TextView) findViewById(R.id.qr_result);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qr_create_image:
			createImage();
			break;
		case R.id.qr_scanning_image:
			scanningImage();
			break;
		default:
			break;
		}
	}

	// 生成QR图
	private void createImage() {
		try {
			QRCodeWriter writer = new QRCodeWriter();

			String text = qr_text.getText().toString();
			if (text == null || "".equals(text) || text.length() < 1) {
				return;
			}

			BitMatrix martix = writer.encode(text, BarcodeFormat.QR_CODE,
					QR_WIDTH, QR_HEIGHT);

			System.out.println("w:" + martix.getWidth() + "h:"
					+ martix.getHeight());

			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			BitMatrix bitMatrix = new QRCodeWriter().encode(text,
					BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
			int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
			for (int y = 0; y < QR_HEIGHT; y++) {
				for (int x = 0; x < QR_WIDTH; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * QR_WIDTH + x] = 0xff000000;
					} else {
						pixels[y * QR_WIDTH + x] = 0xffffffff;
					}

				}
			}

			Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT,
					Bitmap.Config.ARGB_8888);

			bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
			qr_image.setImageBitmap(bitmap);

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}

	// 解析QR图片
	private void scanningImage() {


		Map<DecodeHintType, String> hints = new HashMap<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8");

		Bitmap bitmap = ((BitmapDrawable) qr_image.getDrawable()).getBitmap();
		RGBLuminanceSource source = new RGBLuminanceSource(bitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		Result result;
		try {
			result = reader.decode(bitmap1, hints);
			System.out.println("res：》》》》》》》：" + result.getText());
			qr_result.setText(result.getText());
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}
	}
}
