package com.jn.commons;

import java.util.HashSet;
import java.util.Set;

import com.ccp.decorators.CcpMapDecorator;
import com.ccp.decorators.CcpStringDecorator;
import com.ccp.decorators.CcpTimeDecorator;
import com.ccp.dependency.injection.CcpEspecification;
import com.ccp.especifications.db.crud.CcpDbCrud;

public enum JnBusinessEntity {
	professional_alias {
		@Override
		String getId(CcpMapDecorator data) {
			String professional = data.getAsString("professional");
			String hash = new CcpStringDecorator(professional).hash().asString("SHA1");
			return hash;
		}
	}
	, professional {
		@Override
		String getId(CcpMapDecorator data) {
			String professional = data.getAsString("id");
			return professional;
		}
		
		@Override
		public Set<String> getSynonyms(Set<String> wordsToAnalyze) {
			Set<String> synonyms = this.crud.getSynonyms(wordsToAnalyze, this.name(), "Synonyms_words", "Synonyms_phrases");
			return synonyms;
		}
	}
	, recruiter {
		@Override
		String getId(CcpMapDecorator data) {
			String professional = data.getAsString("id");
			return professional;
		}
	}
	, restriction_to_view_resume {
		@Override
		String getId(CcpMapDecorator data) {
			String professional = data.getAsString("professional");
			String domain = data.getAsString("domain");
			return professional + "_" + domain;
		}
	}
	, cache_view_resume {
		@Override
		String getId(CcpMapDecorator data) {
			String professional = data.getAsString("professional");
			String recruiter = data.getAsString("recruiter");
			return professional + "_" + recruiter;
		}
	}
	, duplicated_view_resume {
		@Override
		String getId(CcpMapDecorator data) {
			String professional = data.getAsString("professional");
			String recruiter = data.getAsString("recruiter");
			return professional + "_" + recruiter;
		}
	}
	, view_resume {
		@Override
		String getId(CcpMapDecorator data) {
			String recruiter = data.getAsString("recruiter");
			String professional = data.getAsString("professional");
			String currentDate = new CcpTimeDecorator().getFormattedCurrentDateTime("ddMMyyyy");
			return professional + "_" + recruiter + "_" + currentDate;
		}
	}

	, keyword_synonym {

		@Override
		String getId(CcpMapDecorator data) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	;
	@CcpEspecification
	CcpDbCrud crud;

	public CcpMapDecorator get(String id) {
		CcpMapDecorator oneById = this.crud.getOneById(id, this.name());
		return oneById;
	}

	public boolean exists(CcpMapDecorator data) {
		String id = this.getId(data);
		boolean exists = this.crud.exists(id, this.name());
		return exists;
	}
	
	public boolean save(CcpMapDecorator data) {
		String id = this.getId(data);
		boolean updated = this.crud.updateOrSave(data, id, this.name());
		return updated;
	}
	
	abstract String getId(CcpMapDecorator data);
	
	public Set<String> getSynonyms(Set<String> wordsToAnalyze){
		return new HashSet<>();
	}
}

