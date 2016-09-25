package br.com.fisioQCM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import android.os.Environment;

public class GravaSD{
	
	public static boolean grava(String nome_arquivo, Vector<String> conteudo)
	{
		@SuppressWarnings("unused")
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		if(!mExternalStorageWriteable)
		{
			return false;
		}
		File file = new File(Environment.getExternalStorageDirectory(), nome_arquivo);
		try {
			FileWriter aux = new FileWriter(file);
			BufferedWriter buffer = new BufferedWriter(aux);
			for(int i = 0 ; i < conteudo.size(); ++i)
			{
				buffer.write(conteudo.elementAt(i));
				buffer.newLine();
			}
			buffer.close();
			aux.close();
		    return true;
		} catch (FileNotFoundException e) {
		    // handle exception
			return false;
		} catch (IOException e) {
		    // handle exception
			return false;
		}

	}
	
	public GravaSD()
	{}
}
