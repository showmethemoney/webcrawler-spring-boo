package com.mycompany;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.mycompany.bean.VideoCollection;
import com.mycompany.enttity.Video;

public class TestParseFile
{
	protected static final Logger logger = LoggerFactory.getLogger( TestParseFile.class );

	@Ignore
	@Test
	public void testParse() {

		try {
			Pattern jsonPattern = Pattern.compile( "parseJSON\\('(.*)'\\)\\s*\\).appendTo" );
			File[] files = new File( "C:/Users/ethan/Desktop/baylife/" ).listFiles();
			Gson gson = new Gson();
			File file = null;

			for (int i = 0; i < files.length; i++) {
				file = files[i];

				if (!file.isDirectory()) {

					Matcher jsonMatcher = jsonPattern.matcher( FileUtils.readFileToString( file ) );

					if (!jsonMatcher.find()) {
						logger.error( "not found json : {} ", file.getAbsolutePath() );
					} else {
						VideoCollection collection = gson.fromJson( StringEscapeUtils.unescapeJavaScript( jsonMatcher.group( 1 ) ), VideoCollection.class );

						for (Video video : collection.getVideos()) {
							video.setSerial( video.getId() );
							video.setId( null );
							logger.info( "{} {} {}", video.getSerial(), video.getTitle() );
						}
					}
				}
			}
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}
}
