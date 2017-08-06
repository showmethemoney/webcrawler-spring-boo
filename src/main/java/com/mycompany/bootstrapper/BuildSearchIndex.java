package com.mycompany.bootstrapper;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent>
{
	protected static final Logger logger = LoggerFactory.getLogger( BuildSearchIndex.class );
	@PersistenceContext
	private EntityManager entityManager = null;

	public void onApplicationEvent(ApplicationReadyEvent event) {
		try {
			FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager( entityManager );
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (Throwable cause) {
			logger.error( cause.getMessage(), cause );
		}
	}

}
