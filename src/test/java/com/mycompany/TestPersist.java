package com.mycompany;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Fields;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;
import com.mycompany.bean.VideoCollection;
import com.mycompany.enttity.Video;
import com.mycompany.service.VideoService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Boostrapper.class)
public class TestPersist
{
	protected static final Logger logger = LoggerFactory.getLogger( TestParseFile.class );
	@Autowired
	private VideoService service = null;

	@Ignore
	@Test
	public void testIndexes() {
		try {
			IndexReader reader = DirectoryReader
			        .open( FSDirectory.open( Paths.get( "C:/Users/ethan/workspace/webcrawler-spring-boo/indexes/com.mycompany.enttity.Video" ) ) );

			Fields fields = MultiFields.getFields( reader );
			Terms terms = fields.terms( "title" );
			TermsEnum iterator = terms.iterator();
			BytesRef byteRef = null;

			List<String> termsList = new ArrayList<String>();

			while ((byteRef = iterator.next()) != null) {
				String term = new String( byteRef.bytes, byteRef.offset, byteRef.length );
				termsList.add( term );
			}

			FileUtils.writeLines( new File( "C:/Users/ethan/workspace/webcrawler-spring-boo/terms.txt" ), termsList );
			
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

	@Ignore
	@Test
	public void testDelete() {
		try {
			List<Video> result = service.findByKeyword( "荻窪 " );

			logger.info( "Before : result size : {}", result.size() );

			service.delete( result );

			result = service.findByKeyword( "414" );

			logger.info( "After : result size : {}", result.size() );
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

//	@Ignore
	@Test
	public void testFind() {
		try {
			List<Video> result = service.findByKeyword( "強姦" );

			logger.info( "result size : {}", result.size() );

			for (Video video : result) {
				logger.info( "{} - {}", video.getTitle(), video.getImages().size() );
			}
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

	@Ignore
	@Test
	public void testPersist() {
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

							service.persist( video );
						}
					}
				}
			}

			List<Video> result = service.findByKeyword( "141" );

			logger.info( "result size : {}", result.size() );

			for (Video video : result) {
				logger.info( "{}", video.getTitle() );
			}
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

}
