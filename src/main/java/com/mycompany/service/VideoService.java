package com.mycompany.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.ja.JapaneseAnalyzer;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.enttity.Video;

@Service
public class VideoService
{
	protected static final Logger logger = LoggerFactory.getLogger( VideoService.class );

	@PersistenceContext
	private EntityManager entityManager = null;

	@Transactional(propagation = Propagation.NEVER)
	public List<Video> findByKeyword(String keyword) {
		List<Video> result = null;
		try {
			// // get the full text entity manager
			// FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager( entityManager );
			//
			// // create the query using Hibernate Search query DSL
			// QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity( Video.class ).get();
			//
			// // a very basic query by keywords
			// org.apache.lucene.search.Query query = queryBuilder.keyword().onFields( "title" ).matching( keyword ).createQuery();
			//
			// // wrap Lucene query in an Hibernate Query object
			// org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery( query, Video.class );
			//
			// // execute search and return results (sorted by relevance as default)
			// result = jpaQuery.getResultList();

			FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager( entityManager );
			Analyzer analyzer = new JapaneseAnalyzer();
			QueryParser parser = new QueryParser( "title", analyzer );
			Query query = parser.parse( keyword );

			// BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
			// booleanQuery.add( query, Occur.SHOULD );
			// FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery( booleanQuery.build(), Video.class );

			FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery( query, Video.class );

			result = fullTextQuery.getResultList();
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}

		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void persist(Video video) {
		try {
			entityManager.persist( video );
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void delete(List<Video> videos) {
		try {
			for (Video video : videos) {
				entityManager.remove( entityManager.merge( video ) );
			}
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}
}
