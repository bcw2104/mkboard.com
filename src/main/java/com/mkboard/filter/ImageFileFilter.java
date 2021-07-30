package com.mkboard.filter;

import java.io.File;
import java.io.FilenameFilter;

public class ImageFileFilter implements FilenameFilter{

	@Override
	public boolean accept(File file, String fileName) {
		boolean isAccept = false;

        if (fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".png")
        		|| fileName.toLowerCase().endsWith(".bmp") || fileName.toLowerCase().endsWith(".svg"))
        {
            isAccept = true;
        }
        else
        {
            isAccept = false;
        }

        return isAccept;
	}

}
