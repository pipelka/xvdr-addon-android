package org.xvdr.addon;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends Activity {

	protected boolean copyAddonDataToXBMC() {
		String android_data  = Environment.getExternalStorageDirectory() + "/Android/data";
		String xbmc_addons = android_data + "/org.xbmc.kodi/files/.kodi/addons";
		
		InputStream is = null;
		try {
			is = getResources().getAssets().open("addons/xvdr.zip");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		
		ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
		try {
			ZipEntry ze;
			while ((ze = zis.getNextEntry()) != null) {
				String filename = xbmc_addons + "/" + ze.getName();

				if(filename.endsWith("/")) {
					Log.i("XVDR", "Creating directory '" + filename + "'");
					File dir = new File(filename);
					dir.mkdirs();
					continue;
				}

				FileOutputStream out = new FileOutputStream(filename);
				Log.i("XVDR", "Writing into '" + filename + "'");

				byte[] buffer = new byte[1024];
				int count;
				while ((count = zis.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
				out.close();
			}
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		finally {
			try {
				zis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return true;
	}
	
	protected boolean haveXBMC() {
		try {
			getPackageManager().getApplicationInfo("org.xbmc.kodi", 0);
		} catch (NameNotFoundException e) {
			return false;
		}
		
		return true;
	}

	protected boolean registerAddon() {
		return copyAddonDataToXBMC();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        CheckBox checkXBMCinstalled = (CheckBox) findViewById(R.id.checkXBMCinstalled);
        final CheckBox checkXVDRregistered = (CheckBox) findViewById(R.id.checkXVDRregistered);
        final Button btnRegister = (Button) findViewById(R.id.btnRegister);

		if(haveXBMC()) {
			checkXBMCinstalled.setChecked(true);
		}
		else {
			btnRegister.setEnabled(false);
		}
		
        btnRegister.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
        		if(registerAddon()) {
        			checkXVDRregistered.setChecked(true);
        			btnRegister.setEnabled(false);
        		}
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
